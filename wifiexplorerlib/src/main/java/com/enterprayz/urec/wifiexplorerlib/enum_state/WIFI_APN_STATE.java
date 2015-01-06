package com.enterprayz.urec.wifiexplorerlib.enum_state;

/**
 * Created by ura on 10.12.14.
 */
public enum WIFI_APN_STATE {
    WIFI_AP_STATE_DISABLING(10),
    WIFI_AP_STATE_DISABLED(11),
    WIFI_AP_STATE_ENABLING(12),
    WIFI_AP_STATE_ENABLED(13),
    WIFI_AP_STATE_FAILED(14);


    private int index;

    private WIFI_APN_STATE(int index) {
        this.index = index;
    }

    public static WIFI_APN_STATE getStateByIndex(int index) {
        for (WIFI_APN_STATE l : WIFI_APN_STATE.values()) {
            if (l.index == index) {
                return l;
            }
        }
        throw new IllegalArgumentException("State not found");
    }
}
