package com.enterprayz.urec.wifiexplorerlib.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by ura on 10.12.14.
 */
public class WifiAPNStateReseirver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("android.net.wifi.WIFI_AP_STATE_CHANGED".equals(action)) {
            // get Wi-Fi Hotspot state here
            int state = intent.getIntExtra("wifi_state", 0);
            WifiClientModel.Actions.setOnAPNSheckListenerState(state);
        }
    }
}
