package com.enterprayz.urec.wifiexplorerlib.interfaces;

import com.enterprayz.urec.wifiexplorerlib.enum_state.WIFI_APN_STATE;


public interface OnAPNCheckListener {
    /**
     * On APN state was change
     * @param state changed state
     * */
    public void onAPNChangeCheck(WIFI_APN_STATE state);
}
