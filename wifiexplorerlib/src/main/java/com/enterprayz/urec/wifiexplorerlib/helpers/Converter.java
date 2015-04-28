package com.enterprayz.urec.wifiexplorerlib.helpers;

import android.net.wifi.ScanResult;

import com.enterprayz.urec.wifiexplorerlib.items.WifiScanResultsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by con on 28.12.14.
 */
public final class Converter {

    public static ArrayList<WifiScanResultsItem> convertScanResult(List<ScanResult> scanResults, String SSIDofConnectedWifi) {
        ArrayList<WifiScanResultsItem> wifiScanResultsItems = new ArrayList<>();
        if (scanResults != null) {
            for (ScanResult scanResult : scanResults) {
                WifiScanResultsItem item = new WifiScanResultsItem();
                item.setSSID(scanResult.SSID);
                item.setBSSID(scanResult.BSSID);
                item.setCapabilities(scanResult.capabilities);
                item.setFrequency(scanResult.frequency);
                item.setLevel(scanResult.level);
                item.setConnected(false);
                if (scanResult.SSID.equals(SSIDofConnectedWifi)) {
                    item.setConnected(true);
                }
                wifiScanResultsItems.add(item);
            }
        }
        return wifiScanResultsItems;
    }

}
