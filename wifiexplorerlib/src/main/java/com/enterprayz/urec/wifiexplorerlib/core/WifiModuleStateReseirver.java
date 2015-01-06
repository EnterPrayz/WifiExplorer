package com.enterprayz.urec.wifiexplorerlib.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.enterprayz.urec.wifiexplorerlib.enum_state.WIFI_MODULE_STATE;

/**
 * Created by ura on 10.12.14.
 */
public class WifiModuleStateReseirver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        int stateId = bundle.getInt("wifi_state");
        WIFI_MODULE_STATE state = null;
        switch (stateId) {
            case 0: {
                state = WIFI_MODULE_STATE.WIFI_MODULE_STATE_DISABLING;
                break;
            }
            case 1: {
                state = WIFI_MODULE_STATE.WIFI_MODULE_STATE_DISABLED;
                break;
            }
            case 2: {
                state = WIFI_MODULE_STATE.WIFI_MODULE_STATE_ENABLING;
                break;
            }
            case 3: {
                state = WIFI_MODULE_STATE.WIFI_MODULE_STATE_ENABLED;
                break;
            }
            case 4: {
                state = WIFI_MODULE_STATE.WIFI_MODULE_STATE_FAILED;
                break;
            }
        }
        WifiClientModel.Actions.setOnWifiModuleEnableCheck(state);
     }
}
