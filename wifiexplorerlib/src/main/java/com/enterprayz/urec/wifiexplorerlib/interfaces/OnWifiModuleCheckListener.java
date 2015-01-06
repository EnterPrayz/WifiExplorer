package com.enterprayz.urec.wifiexplorerlib.interfaces;

import com.enterprayz.urec.wifiexplorerlib.enum_state.WIFI_MODULE_STATE;
import com.enterprayz.urec.wifiexplorerlib.core.WifiModuleStateReseirver;

/**
 * Broadcast message from {@link WifiModuleStateReseirver}.
 */
public interface OnWifiModuleCheckListener {
    /**
     * On Wifi module state was change
     * @param state changed state
     * */
    public void onWifiModuleChangeCheck(WIFI_MODULE_STATE state);

}
