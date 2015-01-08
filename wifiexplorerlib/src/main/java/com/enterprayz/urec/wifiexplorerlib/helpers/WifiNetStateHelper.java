package com.enterprayz.urec.wifiexplorerlib.helpers;

import android.net.NetworkInfo.DetailedState;
import android.os.Build;

import com.enterprayz.urec.wifiexplorerlib.enum_state.WIFI_NET_STATE;
import com.enterprayz.urec.wifiexplorerlib.helpers.hash_keys.StateHightJellyBeanMap;
import com.enterprayz.urec.wifiexplorerlib.helpers.hash_keys.StateLowerJellyBeanMap;

/**
 * Created by con on 24.12.14.
 */
public class WifiNetStateHelper {
    public static WIFI_NET_STATE getNetStateByDetailStateHashKey(DetailedState detailedState) {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        WIFI_NET_STATE state;
        if (currentapiVersion >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            state = StateHightJellyBeanMap.stateHight_JELLY_BEAN_Map.get(detailedState);
        } else {
            state = StateLowerJellyBeanMap.stateLower_JELLY_BEAN_Map.get(detailedState);
        }
        return state;
    }
}

