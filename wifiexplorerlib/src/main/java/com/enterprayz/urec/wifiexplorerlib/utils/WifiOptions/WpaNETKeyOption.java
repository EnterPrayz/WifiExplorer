package com.enterprayz.urec.wifiexplorerlib.utils.WifiOptions;

import android.net.wifi.WifiConfiguration;

/**
 * Created by con on 29.12.14.
 */
public class WpaNETKeyOption extends WifiKeyOptions {

    /**
     * Wifi configuration for connect to network, based on WPA algorithm.
     */
    public WpaNETKeyOption(String SSID, String pass) {
        setSSID(SSID);
        setKEY_VALUE(pass);
    }

    @Override
    public WifiConfiguration getConfig() {
        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"" + getSSID() + "\"";
        conf.preSharedKey = "\""+ getKEY_VALUE() +"\"";

        return conf;
    }

}