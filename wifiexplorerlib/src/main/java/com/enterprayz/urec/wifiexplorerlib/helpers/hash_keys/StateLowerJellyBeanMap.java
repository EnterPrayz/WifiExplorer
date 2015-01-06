package com.enterprayz.urec.wifiexplorerlib.helpers.hash_keys;

import android.net.NetworkInfo;

import com.enterprayz.urec.wifiexplorerlib.enum_state.WIFI_NET_STATE;

import java.util.EnumMap;

/**
 * Created by con on 24.12.14.
 */
public class StateLowerJellyBeanMap {
    public static EnumMap<NetworkInfo.DetailedState, WIFI_NET_STATE> stateLower_JELLY_BEAN_Map =
            new EnumMap<>(NetworkInfo.DetailedState.class);

    static {
        stateLower_JELLY_BEAN_Map.put(NetworkInfo.DetailedState.IDLE, WIFI_NET_STATE.IDLE);
        stateLower_JELLY_BEAN_Map.put(NetworkInfo.DetailedState.SCANNING, WIFI_NET_STATE.SCANNING);
        stateLower_JELLY_BEAN_Map.put(NetworkInfo.DetailedState.CONNECTING, WIFI_NET_STATE.CONNECTING);
        stateLower_JELLY_BEAN_Map.put(NetworkInfo.DetailedState.AUTHENTICATING, WIFI_NET_STATE.AUTHENTICATING);
        stateLower_JELLY_BEAN_Map.put(NetworkInfo.DetailedState.OBTAINING_IPADDR, WIFI_NET_STATE.OBTAINING_IPADDR);
        stateLower_JELLY_BEAN_Map.put(NetworkInfo.DetailedState.CONNECTED, WIFI_NET_STATE.CONNECTED);
        stateLower_JELLY_BEAN_Map.put(NetworkInfo.DetailedState.SUSPENDED, WIFI_NET_STATE.SUSPENDED);
        stateLower_JELLY_BEAN_Map.put(NetworkInfo.DetailedState.DISCONNECTING, WIFI_NET_STATE.DISCONNECTING);
        stateLower_JELLY_BEAN_Map.put(NetworkInfo.DetailedState.DISCONNECTED, WIFI_NET_STATE.DISCONNECTED);
        stateLower_JELLY_BEAN_Map.put(NetworkInfo.DetailedState.FAILED, WIFI_NET_STATE.FAILED);
        stateLower_JELLY_BEAN_Map.put(NetworkInfo.DetailedState.BLOCKED, WIFI_NET_STATE.BLOCKED);
    }

}
