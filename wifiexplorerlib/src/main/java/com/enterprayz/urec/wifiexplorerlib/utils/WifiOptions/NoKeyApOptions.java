package com.enterprayz.urec.wifiexplorerlib.utils.WifiOptions;

import android.net.wifi.WifiConfiguration;

/**
 * Created by ura on 09.12.14.
 */
public class NoKeyApOptions extends WifiKeyOptions {
    /**
     * Wifi configuration for create open network.
     * */
    public NoKeyApOptions(String SSID, boolean hiddenSSID) {
        setSSID(SSID);
        setShow(hiddenSSID);
    }

    @Override
    public WifiConfiguration getConfig() {
        WifiConfiguration configuration = new WifiConfiguration();
        configuration.SSID = getSSID();
        configuration.hiddenSSID = isShow();
        configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        return configuration;
    }
}
