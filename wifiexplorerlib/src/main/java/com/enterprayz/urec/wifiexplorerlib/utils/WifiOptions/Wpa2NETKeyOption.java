package com.enterprayz.urec.wifiexplorerlib.utils.WifiOptions;

import android.net.wifi.WifiConfiguration;

/**
 * Created by con on 02.01.15.
 */
public class Wpa2NETKeyOption extends WifiKeyOptions {

    /**
     * Wifi configuration for connect to network, based on WPA2 algorithm.
     */
    public Wpa2NETKeyOption (String SSID, String pass) {
        setSSID(SSID);
        setKEY_VALUE(pass);
    }

    @Override
    public WifiConfiguration getConfig() {
        WifiConfiguration config = new WifiConfiguration();
        config.SSID = "\"" + getSSID() + "\"";
        config.preSharedKey = quoteNonHex(getKEY_VALUE(), 64);
        config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
        config.allowedProtocols.set(WifiConfiguration.Protocol.RSN); // For WPA2
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_EAP);
        config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);

        return config;
    }
}