package com.enterprayz.urec.wifiexplorerlib.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

/**
 * Created by con on 24.12.14.
 */
public class WifiNetworkScanerReseiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(intent.getAction())) {
            WifiClientModel.Actions.setOnNetworkScanCheckChange();
        }
    }
}
