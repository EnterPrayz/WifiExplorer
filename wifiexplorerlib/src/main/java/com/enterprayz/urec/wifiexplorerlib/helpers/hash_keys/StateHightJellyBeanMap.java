package com.enterprayz.urec.wifiexplorerlib.helpers.hash_keys;

import android.annotation.TargetApi;
import android.net.NetworkInfo;
import android.os.Build;

import com.enterprayz.urec.wifiexplorerlib.enum_state.WIFI_NET_STATE;

import java.util.EnumMap;

/**
 * Created by con on 24.12.14.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class StateHightJellyBeanMap {
    public static EnumMap<NetworkInfo.DetailedState, WIFI_NET_STATE> stateHight_JELLY_BEAN_Map =
            new EnumMap<>(NetworkInfo.DetailedState.class);

    static {
        stateHight_JELLY_BEAN_Map.put(NetworkInfo.DetailedState.IDLE, WIFI_NET_STATE.IDLE);
        stateHight_JELLY_BEAN_Map.put(NetworkInfo.DetailedState.SCANNING, WIFI_NET_STATE.SCANNING);
        stateHight_JELLY_BEAN_Map.put(NetworkInfo.DetailedState.CONNECTING, WIFI_NET_STATE.CONNECTING);
        stateHight_JELLY_BEAN_Map.put(NetworkInfo.DetailedState.AUTHENTICATING, WIFI_NET_STATE.AUTHENTICATING);
        stateHight_JELLY_BEAN_Map.put(NetworkInfo.DetailedState.OBTAINING_IPADDR, WIFI_NET_STATE.OBTAINING_IPADDR);
        stateHight_JELLY_BEAN_Map.put(NetworkInfo.DetailedState.VERIFYING_POOR_LINK, WIFI_NET_STATE.VERIFYING_POOR_LINK);
        stateHight_JELLY_BEAN_Map.put(NetworkInfo.DetailedState.CAPTIVE_PORTAL_CHECK, WIFI_NET_STATE.CAPTIVE_PORTAL_CHECK);
        stateHight_JELLY_BEAN_Map.put(NetworkInfo.DetailedState.CONNECTED, WIFI_NET_STATE.CONNECTED);
        stateHight_JELLY_BEAN_Map.put(NetworkInfo.DetailedState.SUSPENDED, WIFI_NET_STATE.SUSPENDED);
        stateHight_JELLY_BEAN_Map.put(NetworkInfo.DetailedState.DISCONNECTING, WIFI_NET_STATE.DISCONNECTING);
        stateHight_JELLY_BEAN_Map.put(NetworkInfo.DetailedState.DISCONNECTED, WIFI_NET_STATE.DISCONNECTED);
        stateHight_JELLY_BEAN_Map.put(NetworkInfo.DetailedState.FAILED, WIFI_NET_STATE.FAILED);
        stateHight_JELLY_BEAN_Map.put(NetworkInfo.DetailedState.BLOCKED, WIFI_NET_STATE.BLOCKED);
    }
}
