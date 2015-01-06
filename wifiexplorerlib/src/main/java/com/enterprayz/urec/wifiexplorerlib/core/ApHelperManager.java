package com.enterprayz.urec.wifiexplorerlib.core;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.enterprayz.urec.wifiexplorerlib.enum_state.WIFI_APN_STATE;
import com.enterprayz.urec.wifiexplorerlib.items.ClientScanResultItem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.ArrayList;


/**
 * Created by ura on 08.12.14.
 */
public class ApHelperManager {
    private String TAG = ApHelperManager.class.getName();
    private Context context;
    private final WifiManager mWifiManager;
    private ArrayList<ClientScanResultItem> newClientScanResultList;
    private ArrayList<ClientScanResultItem> oldClientScanResultList;


    public static final int CONNECT_FLAG = 1;
    public static final int DISSCONNECT_FLAG = 2;
    private boolean scanMonitorEnable;


    public ApHelperManager(Context context, WifiManager wifiManager) {
        this.context = context;
        this.mWifiManager = wifiManager;

    }

    public String getWifiApSSID() {
        WifiConfiguration configuration = getWifiApConfiguration();
        return configuration.SSID;
    }

    public WifiConfiguration getWifiApConfiguration() {
        try {
            Method method = mWifiManager.getClass().getMethod("getWifiApConfiguration");
            return (WifiConfiguration) method.invoke(mWifiManager);
        } catch (Exception e) {
            Log.e(TAG, "", e);
            return null;
        }
    }

    public boolean setApNetworkEnable(WifiConfiguration wifiConfigs, boolean enable) {
        try {
            if (enable) { // disable WiFi in any case
                mWifiManager.setWifiEnabled(false);
            }
            Method method = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);

            boolean wasOpen = (Boolean) method.invoke(mWifiManager, wifiConfigs, enable);

            return wasOpen;
        } catch (Exception e) {
            Log.e(this.getClass().toString(), "", e);
            return false;
        }
    }

    public boolean closeApNetwork(WIFI_APN_STATE state) {
        boolean canCLose = false;
        boolean result = false;
        if (state.equals(WIFI_APN_STATE.WIFI_AP_STATE_ENABLED)) {
            canCLose = true;
        } else if (state.equals(WIFI_APN_STATE.WIFI_AP_STATE_ENABLING)) {
            while (state.equals(WIFI_APN_STATE.WIFI_AP_STATE_ENABLING)) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (state.equals(WIFI_APN_STATE.WIFI_AP_STATE_ENABLED)) {
                canCLose = true;
            }
        }
        if (canCLose) {
            try {
                Method method = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
                boolean wasOpen = (Boolean) method.invoke(mWifiManager, null, false);
                result = wasOpen;
            } catch (Exception e) {
                Log.e(this.getClass().toString(), "", e);
                result = false;
            }
        }
        return result;
    }

    public void setAPNScanMonitor(final int dellay, final boolean enable) {
        if (newClientScanResultList == null) {
            getClientsChangeList(100, false);
        }
        scanMonitorEnable = enable;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (scanMonitorEnable) {
                    try {
                        getClientsChangeList(2000, true);
                        Thread.sleep(dellay);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }).start();
    }

    private void getClientsChangeList(final int reachableTimeout,
                                      final boolean isMonitorScan) {
        if (isMonitorScan) {
            oldClientScanResultList = new ArrayList<>();
            if (newClientScanResultList != null) {
                for (ClientScanResultItem item : newClientScanResultList) {
                    oldClientScanResultList.add(item);
                }
            }
        }
        this.newClientScanResultList = getListOfAPNUsers(reachableTimeout, true);
        if (isMonitorScan) {
            for (ClientScanResultItem tempItem : oldClientScanResultList) {
                boolean offLineSwicher = false;
                for (ClientScanResultItem item : newClientScanResultList) {
                    if (tempItem.getHWAddr().equals(item.getHWAddr()) && !item.isReachable() && tempItem.isReachable() != item.isReachable()) {
                        offLineSwicher = true;
                    }
                }
                if (offLineSwicher) {
                    WifiClientModel.Actions.setOnAPNUserChangeState(tempItem, DISSCONNECT_FLAG);//User gone offline
                }
            }
            for (ClientScanResultItem item : newClientScanResultList) {
                boolean onLineSwicher = false;

                if (newClientScanResultList.size() == oldClientScanResultList.size()) {
                    for (ClientScanResultItem tempItem : oldClientScanResultList) {//search avaible users
                        if (item.getHWAddr().equals(tempItem.getHWAddr())
                                && item.isReachable()
                                && item.isReachable() != tempItem.isReachable()) {
                            onLineSwicher = true;
                        }
                    }
                } else {
                    if (oldClientScanResultList.size() < newClientScanResultList.size()//if new users become and they is first moment on host spot
                            && item.isReachable()) {
                        boolean isFound = false;
                        for (ClientScanResultItem oldItem : oldClientScanResultList) {
                            if (item.getHWAddr().equals(oldItem.getHWAddr())) {
                                isFound = true;
                            }
                        }
                        if (!isFound) {
                            onLineSwicher = true;
                        }
                    }
                }
                if (onLineSwicher) {
                    WifiClientModel.Actions.setOnAPNUserChangeState(item, CONNECT_FLAG);//USER COME ONLINE
                }
            }
        }
    }

    public synchronized ArrayList<ClientScanResultItem> getListOfAPNUsers(int reachableTimeout, boolean isMonitor) {
        ArrayList<ClientScanResultItem> list = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("/proc/net/arp"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitted = line.split(" +");

                if ((splitted != null) && (splitted.length >= 4)) {
                    // Basic sanity check
                    String mac = splitted[3];

                    if (mac.matches("..:..:..:..:..:..")) {
                        boolean isReachable = InetAddress.getByName(splitted[0]).isReachable(reachableTimeout);
                        String deviceName = InetAddress.getByName(splitted[0]).getHostName();
                        ClientScanResultItem resultItem = new ClientScanResultItem(splitted[0], splitted[3], deviceName, isReachable);
                        list.add(resultItem);

                    }
                }
            }
        } catch (Exception t) {
            if (t.getMessage() != null) {
                Log.e(TAG, t.getMessage());
            }
        } finally {
            try {
                assert br != null;
                br.close();
            } catch (IOException y) {
                if (y.getMessage() != null) {
                    Log.e(TAG, y.getMessage());
                }
            }
        }
        if (!isMonitor) {
            WifiClientModel.Actions.setOnFinishScanAPNUsers(list, isMonitor);
        }
        return list;
    }
}



