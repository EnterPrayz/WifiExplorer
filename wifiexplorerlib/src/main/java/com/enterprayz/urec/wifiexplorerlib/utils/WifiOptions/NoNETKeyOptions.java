package com.enterprayz.urec.wifiexplorerlib.utils.WifiOptions;

import android.net.wifi.WifiConfiguration;

/**
 * Created by ura on 08.12.14.
 */
public class NoNETKeyOptions extends WifiKeyOptions {

    /**
     * Wifi configuration for connect to open network.
     */
    public NoNETKeyOptions(String SSID) {
        setSSID(SSID);
    }

    @Override
    public WifiConfiguration getConfig() {
        WifiConfiguration configuration = new WifiConfiguration();
        configuration.SSID = "\"" + getSSID() + "\"";
        configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        return configuration;
    }
}
