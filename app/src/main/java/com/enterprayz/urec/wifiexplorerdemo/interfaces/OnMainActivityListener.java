package com.enterprayz.urec.wifiexplorerdemo.interfaces;

import com.enterprayz.urec.wifiexplorerlib.items.WifiScanResultsItem;

import java.util.ArrayList;

/**
 * Created by con on 26.12.14.
 */
public interface OnMainActivityListener {
    public void onNetworkScanResult(ArrayList<WifiScanResultsItem> results);
}
