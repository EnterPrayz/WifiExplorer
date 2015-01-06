package com.enterprayz.urec.wifiexplorerlib.utils.WifiOptions;

import android.net.wifi.WifiConfiguration;

/**
 * Created by con on 29.12.14.
 */
public class WepNETKeyOption extends WifiKeyOptions {

    /**
     * Wifi configuration for connect to network, based on WEP algorithm.
     */
    public WepNETKeyOption(String SSID, String pass) {
        setSSID(SSID);
        setKEY_VALUE(pass);
    }

    @Override
    public WifiConfiguration getConfig() {
        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"" + getSSID() + "\"";
        conf.wepKeys[0] = "\"" + getKEY_VALUE() + "\"";
        conf.wepTxKeyIndex = 0;
        conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
        return conf;
    }
}
