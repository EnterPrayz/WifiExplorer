package com.enterprayz.urec.wifiexplorerlib.enum_state;

/**
 * Created by con on 02.01.15.
 */
public enum WIFI_MODULE_MODE {
    /**
     * Wifi module is turn off
     */
    OFF_MODE(0),
    /**
     * APN mode is enabled.
     */
    APN_MODE(1),
    /**
     * NET mode is enabled.
     */
    NET_MODE(2);

    private int index;

    private WIFI_MODULE_MODE(int index) {
        this.index = index;
    }

    public static WIFI_MODULE_MODE getStateByIndex(int index) {
        for (WIFI_MODULE_MODE l : WIFI_MODULE_MODE.values()) {
            if (l.index == index) {
                return l;
            }
        }
        throw new IllegalArgumentException("State not found");
    }
}
