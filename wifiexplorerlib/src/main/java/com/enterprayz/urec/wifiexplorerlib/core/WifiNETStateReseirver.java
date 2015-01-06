package com.enterprayz.urec.wifiexplorerlib.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.enterprayz.urec.wifiexplorerlib.enum_state.WIFI_NET_STATE;
import com.enterprayz.urec.wifiexplorerlib.helpers.WifiNetStateHelper;

/**
 * Created by con on 24.12.14.
 */
public class WifiNETStateReseirver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        WIFI_NET_STATE mState = WifiNetStateHelper.getNetStateByDetailStateHashKey(activeNetInfo.getDetailedState());

        WifiClientModel.Actions.setOnNETChangeState(mState);
    }
}

