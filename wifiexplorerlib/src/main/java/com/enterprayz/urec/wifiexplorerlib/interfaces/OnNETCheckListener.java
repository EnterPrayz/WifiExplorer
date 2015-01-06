package com.enterprayz.urec.wifiexplorerlib.interfaces;

import com.enterprayz.urec.wifiexplorerlib.enum_state.WIFI_NET_STATE;
import com.enterprayz.urec.wifiexplorerlib.items.WifiScanResultsItem;

import java.util.ArrayList;
import com.enterprayz.urec.wifiexplorerlib.core.WifiNETStateReseirver;
/**
 *  Broadcast message from {@link WifiNETStateReseirver}.
 */
public interface OnNETCheckListener {
    /**
     *  On change {@link WIFI_NET_STATE}
     */
    public void onNetCheckChange (WIFI_NET_STATE state);

    /**
     *  Return ArrayList <{@link WifiScanResultsItem}> after scan wifi network.
     */
    public void onNetworkScanResult (ArrayList <WifiScanResultsItem> results);
}
