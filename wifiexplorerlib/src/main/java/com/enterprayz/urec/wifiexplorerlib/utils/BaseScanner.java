/*
 * Copyright (C) 2009-2010 Aubort Jean-Baptiste (Rorist)
 * Licensed under GNU's GPL 2, see README
 */

package com.enterprayz.urec.wifiexplorerlib.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.enterprayz.urec.wifiexplorerlib.database.DbActionsHelper;
import com.enterprayz.urec.wifiexplorerlib.items.HostBean;
import com.enterprayz.urec.wifiexplorerlib.utils.ScannerOptions.BaseScannerConfiguration;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import jcifs.netbios.NbtAddress;


public abstract class BaseScanner extends AsyncTask<Void, HostBean, Void> {

    private final String TAG = "DefaultDiscovery";
    private static int[] DPORTS;
    private static int TIMEOUT_SCAN; // seconds
    private static int TIMEOUT_SHUTDOWN; // seconds
    private static int THREADS;
    private int mRateMult; // Number of alive hosts between Rate
    private int pt_move; // 1=backward 2=forward
    private ExecutorService mPool;
    private boolean doRateControl;
    protected long ip;
    private RateControl mRateControl;

    protected int hosts_done = 0;
    private static Context context;
    protected long start = 0;
    protected long end = 0;
    protected String gateIp = "";
    protected long size = 254;

    public BaseScanner(BaseScannerConfiguration configuration, Context context) {
        BaseScanner.context = context;
        DPORTS = configuration.getDPORTS();
        TIMEOUT_SCAN = configuration.getTIMEOUT_SCAN();
        TIMEOUT_SHUTDOWN = configuration.getTIMEOUT_SHUTDOWN();
        THREADS = configuration.getTHREADS();
        mRateMult = configuration.getmRateMult();
        pt_move = configuration.getPtmove();
        doRateControl = configuration.isRateControl();
        start = configuration.getStart();
        end = configuration.getEnd();
        gateIp = configuration.getGateIp();
        mRateControl = new RateControl();
    }

    abstract protected void onPreExecute();

    abstract protected void onProgressUpdate(HostBean... host);

    abstract protected void onPostExecute(Void unused);

    @Override
    protected void onCancelled(Void result) {
        if (mPool != null) {
            synchronized (mPool) {
                mPool.shutdownNow();
            }
        }
        super.onCancelled();
    }

    @Override
    protected void onCancelled() {
        if (mPool != null) {
            synchronized (mPool) {
                mPool.shutdownNow();
            }
        }
        super.onCancelled();
    }


    @Override
    protected Void doInBackground(Void... params) {
        Log.v(TAG, "start=" + NetInfo.getIpFromLongUnsigned(start) + " (" + start
                + "), end=" + NetInfo.getIpFromLongUnsigned(end) + " (" + end
                + "), length=" + size);
        mPool = Executors.newFixedThreadPool(THREADS);
        if (ip <= end && ip >= start) {
            Log.i(TAG, "Back and forth scanning");
            // gateway
            launch(start);

            // hosts
            long pt_backward = ip;
            long pt_forward = ip + 1;
            long size_hosts = size - 1;

            for (int i = 0; i < size_hosts; i++) {
                // Set pointer if of limits
                if (pt_backward <= start) {
                    pt_move = 2;
                } else if (pt_forward > end) {
                    pt_move = 1;
                }
                // Move back and forth
                if (pt_move == 1) {
                    launch(pt_backward);
                    pt_backward--;
                    pt_move = 2;
                } else if (pt_move == 2) {
                    launch(pt_forward);
                    pt_forward++;
                    pt_move = 1;
                }
            }
        } else {
            Log.i(TAG, "Sequencial scanning");
            for (long i = start; i <= end; i++) {
                launch(i);
            }
        }
        mPool.shutdown();
        try {
            if (!mPool.awaitTermination(TIMEOUT_SCAN, TimeUnit.SECONDS)) {
                mPool.shutdownNow();
                Log.e(TAG, "Shutting down pool");
                if (!mPool.awaitTermination(TIMEOUT_SHUTDOWN, TimeUnit.SECONDS)) {
                    Log.e(TAG, "Pool did not terminate");
                }
            }
        } catch (InterruptedException e) {
            Log.e(TAG, e.getMessage());
            mPool.shutdownNow();
            Thread.currentThread().interrupt();

        }
        return null;
    }


    private void launch(long i) {
        if (!mPool.isShutdown()) {
            mPool.execute(new CheckRunnable(NetInfo.getIpFromLongUnsigned(i)));
        }
    }

    private int getRate() {
        if (doRateControl) {
            return mRateControl.rate;
        }
        return 1;
    }

    private class CheckRunnable implements Runnable {
        private String addr;

        CheckRunnable(String addr) {
            this.addr = addr;
        }

        public void run() {
            if (isCancelled()) {
                publish(null);
                return;
            }
            Log.e(TAG, "run=" + addr);
            // Create host object
            final HostBean host = new HostBean();

            host.ipAddress = addr;
            try {
                InetAddress h = InetAddress.getByName(addr);
                // Rate control check
                // Arp Check #1
                host.hardwareAddress = DbActionsHelper.getHardwareAddress(addr);
                if (!NetInfo.NOMAC.equals(host.hardwareAddress)) {
                    Log.e(TAG, "found using arp #1 " + addr);
                    publish(host);
                    return;
                }
                // Native InetAddress check
                if (h.isReachable(getRate())) {
                    Log.e(TAG, "found using InetAddress  " + addr);

                    publish(host);
                    // Set indicator and get a rate
                    return;
                }
                // Arp Check #2
                host.hardwareAddress = DbActionsHelper.getHardwareAddress(addr);
                if (!NetInfo.NOMAC.equals(host.hardwareAddress)) {
                    Log.e(TAG, "found using arp #2 " + addr);

                    publish(host);
                    return;
                }

                // Arp Check #3
                host.hardwareAddress = DbActionsHelper.getHardwareAddress(addr);
                if (!NetInfo.NOMAC.equals(host.hardwareAddress)) {
                    Log.e(TAG, "found using arp #3 " + addr);
                    publish(host);
                    return;
                }
                publish(null);

            } catch (IOException e) {
                publish(null);
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private void publish(final HostBean host) {
        hosts_done++;
        if (host == null) {
            publishProgress((HostBean) null);
            return;
        }
        // Mac Addr not already detected
        if (NetInfo.NOMAC.equals(host.hardwareAddress)) {
            host.hardwareAddress = DbActionsHelper.getHardwareAddress(host.ipAddress);
        }

        if (NetInfo.NOMAC.equals(host.hardwareAddress)) {
            publishProgress((HostBean) null);
            return;
        }

        if (host.ipAddress.equals(gateIp)) {
            host.deviceType = HostBean.TYPE_GATEWAY;
        }
        if (DPORTS.length > 1) {
            // Custom check
            Socket s = new Socket();
            for (int i = 0; i < DPORTS.length; i++) {
                boolean isOpen = false;
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(host.ipAddress, DPORTS[i]), TIMEOUT_SCAN);
                    socket.close();
                    isOpen = true;
                    Log.e(TAG, "Opened TCP port  - " + DPORTS[i]);

                } catch (ConnectException ce) {
                    ce.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (isOpen){
                    host.portsOpen.add(DPORTS[i]);
                }else {
                    host.portsClosed.add(DPORTS[i]);
                }
            }
        }

        // NIC vendor
        host.nicVendor = DbActionsHelper.getNicVendor(host.hardwareAddress, context);
        NbtAddress address = null;
        try {
            address = NbtAddress.getByName(host.ipAddress);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if (address != null) {
            String hostname = address.getHostName();
            Log.e(TAG, "HostName  - " + hostname);

        }

        if (doRateControl) {
            mRateControl.indicator = host.ipAddress;
            mRateControl.adaptRate();
            host.responseTime = getRate();
        }

        publishProgress(host);
    }
}
