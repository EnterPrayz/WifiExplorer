package com.enterprayz.urec.wifiexplorerlib.core;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.enterprayz.urec.wifiexplorerlib.enum_state.WIFI_APN_STATE;
import com.enterprayz.urec.wifiexplorerlib.enum_state.WIFI_MODULE_STATE;
import com.enterprayz.urec.wifiexplorerlib.enum_state.WIFI_NET_STATE;
import com.enterprayz.urec.wifiexplorerlib.helpers.Converter;
import com.enterprayz.urec.wifiexplorerlib.helpers.WifiNetStateHelper;
import com.enterprayz.urec.wifiexplorerlib.interfaces.OnNetDeviceScanListener;
import com.enterprayz.urec.wifiexplorerlib.items.WifiScanResultsItem;
import com.enterprayz.urec.wifiexplorerlib.utils.NetworkDeviceScanner;
import com.enterprayz.urec.wifiexplorerlib.utils.ScannerOptions.BaseScannerConfiguration;
import com.enterprayz.urec.wifiexplorerlib.utils.ScannerOptions.BaseScannerOptions;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ura on 08.12.14.
 */
public class ClientCore {
    private final WifiManager mWifiManager;
    private Context context;

    private ApHelperManager apManager;
    private NetworkDeviceScanner scanner = null;

    public ClientCore(Context context) {
        this.context = context;
        this.mWifiManager = (WifiManager) this.context.getSystemService(Context.WIFI_SERVICE);
        this.apManager = new ApHelperManager(this.context, mWifiManager);

    }

    public void scanWifiNetwork() {
        mWifiManager.startScan();
    }

    public String getSSIDofCurrentWifi() {
        String SSID = "";
        if (getWifiNetState().equals(WIFI_NET_STATE.CONNECTED)) {
            WifiInfo info = mWifiManager.getConnectionInfo();
            return info.getSSID();
        }
        return SSID;
    }

    public void connectToNetwork(WifiConfiguration configuration) {
        mWifiManager.addNetwork(configuration);
        List<WifiConfiguration> list = mWifiManager.getConfiguredNetworks();
        for (WifiConfiguration i : list) {
            if (i.SSID != null && i.SSID.equals(configuration.SSID)) {
                mWifiManager.disconnect();
                mWifiManager.enableNetwork(i.networkId, true);
                mWifiManager.reconnect();
                break;
            }
        }
    }

    public void disconnectFromNetwork() {
        mWifiManager.disconnect();
    }

    public ArrayList<WifiScanResultsItem> getScanResults() {
        String SSID = getSSIDofCurrentWifi();
        return Converter.convertScanResult(mWifiManager.getScanResults(), SSID);
    }

    public void scanLocalNetwork(BaseScannerConfiguration configuration, OnNetDeviceScanListener listener) {
        if (scanner != null && !scanner.isCancelled()) {
            scanner.cancel(true);
        }

        scanner = new NetworkDeviceScanner(configuration, context);
        scanner.setListener(listener);
        scanner.execute();
    }

    public void stopScanLocalNetwork(){
        if (scanner != null && !scanner.isCancelled()) {
            scanner.cancel(true);
        }
    }

    public void createAp(WifiConfiguration configuration) {
        apManager.setApNetworkEnable(configuration, true);
    }

    public void closeAP() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                apManager.closeApNetwork(getWifiApState());
            }
        }).start();
    }

    public void setScanMonitorState(boolean enable, int dellay) {
        apManager.setAPNScanMonitor(dellay, enable);
    }

    public boolean getScanMonitorState() {
        return apManager.getScanMonitorState();
    }

    public WifiConfiguration getLastAPNConfiguration() {
        return apManager.getWifiApConfiguration();
    }

    public boolean isWifiModuleEnabled() {
        return mWifiManager.isWifiEnabled();
    }

    public void setWifiModuleEnabled(boolean check) {
        mWifiManager.setWifiEnabled(check);
    }

    public void getClientsList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                apManager.getListOfAPNUsers(1000, false);
            }
        }).start();
    }

    public WIFI_APN_STATE getWifiApState() {
        try {
            Method method = mWifiManager.getClass().getMethod("getWifiApState");

            int tmp = ((Integer) method.invoke(mWifiManager));

            // Fix for Android 4
            if (tmp >= 10) {
                tmp = tmp - 10;
            }

            return WIFI_APN_STATE.class.getEnumConstants()[tmp];
        } catch (Exception e) {
            Log.e(this.getClass().toString(), "", e);
            return WIFI_APN_STATE.WIFI_AP_STATE_FAILED;
        }
    }

    public WIFI_MODULE_STATE getWifiModuleState() {
        return WIFI_MODULE_STATE.getStateByIndex(mWifiManager.getWifiState());
    }

    public WIFI_NET_STATE getWifiNetState() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        WIFI_NET_STATE mState = WifiNetStateHelper.getNetStateByDetailStateHashKey(activeNetInfo.getDetailedState());

        return mState;
    }

}
