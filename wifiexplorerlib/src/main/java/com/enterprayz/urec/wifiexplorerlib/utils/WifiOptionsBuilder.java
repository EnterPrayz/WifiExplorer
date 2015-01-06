package com.enterprayz.urec.wifiexplorerlib.utils;

import android.net.wifi.WifiConfiguration;

import com.enterprayz.urec.wifiexplorerlib.utils.WifiOptions.Factory;
import com.enterprayz.urec.wifiexplorerlib.utils.WifiOptions.WifiKeyOptions;

/**
 * Created by ura on 08.12.14.
 */
public class WifiOptionsBuilder extends Factory {

    private final String TAG = WifiOptionsBuilder.class.getName();
    private WifiConfiguration configuration;
    private WifiKeyOptions choicenKeyOption;
    private String SSID = "MyAPN";
    private boolean hiddenSSID = false;
    private WifiOptionsBuilder result;

    public static final int NO_KEY_AP_ATTR = 201;


    public static final int WPS_KEY_NET_ATTR = 1;
    public static final int NO_KEY_NET_ATTR = 101;
    public static final int WEP_KEY_NET_ATTR = 102;
    public static final int WPA_KEY_NET_ATTR = 103;
    public static final int WPA2_KEY_NET_ATTR = 104;

    public WifiOptionsBuilder() {
    }


    /**
     * Get secure of wifi network.
     * @return int[] where present secure key attribute, and 1 - if WPS enable, and 0 - if WPS disabled
     *  first
     */
    public static  int [] getWifiSecure(String capabilities) {
        int secure = 0;
        if (capabilities.toUpperCase().contains("WEP")) {
            // WEP Network
            secure = WEP_KEY_NET_ATTR;
        } else if (capabilities.toUpperCase().contains("WPA2")) {
            // WPA2 Network
            secure = WPA2_KEY_NET_ATTR;
        } else if (capabilities.toUpperCase().contains("WPA")) {
            // WPA Network
            secure = WPA_KEY_NET_ATTR;
        } else {
            // Open Network
            secure = NO_KEY_NET_ATTR;
        }
        int WPS = 0;

        if (capabilities.toUpperCase().contains("WPS")) {
            // WEP Network
            WPS = WPS_KEY_NET_ATTR;
        }

        int[] result = {secure,WPS};
        return result;
    }

    @Override
    public WifiConfiguration getConfig(WifiKeyOptions keyOptions) {
        return keyOptions.getConfig();
    }

}
