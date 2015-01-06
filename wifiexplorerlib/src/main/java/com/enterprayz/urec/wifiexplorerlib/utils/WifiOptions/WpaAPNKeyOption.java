package com.enterprayz.urec.wifiexplorerlib.utils.WifiOptions;

import android.net.wifi.WifiConfiguration;

/**
 * Created by con on 04.01.15.
 */
public class WpaAPNKeyOption extends WifiKeyOptions {

    /**
     * Wifi configuration for create network, based on WPA algorithm.
     */
    public WpaAPNKeyOption(String SSID, String pass, boolean needHide) {
        setSSID(SSID);
        setKEY_VALUE(pass);
        setShow(needHide);
    }

    @Override
    public WifiConfiguration getConfig() {
        WifiConfiguration config = new WifiConfiguration();
        config.SSID = getSSID();
        config.preSharedKey  =getKEY_VALUE();
        config.hiddenSSID = isShow();
        config.status = WifiConfiguration.Status.ENABLED;
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        config.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        return config;
    }
}
