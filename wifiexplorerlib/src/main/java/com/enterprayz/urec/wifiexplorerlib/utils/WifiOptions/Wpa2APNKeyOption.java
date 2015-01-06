package com.enterprayz.urec.wifiexplorerlib.utils.WifiOptions;

import android.net.wifi.WifiConfiguration;

/**
 * Created by con on 04.01.15.
 */
public class Wpa2APNKeyOption extends WifiKeyOptions {

    /**
     * Wifi configuration for create network, based on WPA2 algorithm.
     */
    public Wpa2APNKeyOption(String SSID, String pass, boolean needHide) {
        setSSID(SSID);
        setKEY_VALUE(pass);
    }

    /** get APN configuration of WPA2 secure */
    @Override
    public WifiConfiguration getConfig() {
        WifiConfiguration config = new WifiConfiguration();
        config.SSID = getSSID();
        config.hiddenSSID = isShow();
        config.preSharedKey = quoteNonHex(getKEY_VALUE(), 64);
        config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
        config.allowedKeyManagement.set(4);//WPA2
        return config;
    }
}