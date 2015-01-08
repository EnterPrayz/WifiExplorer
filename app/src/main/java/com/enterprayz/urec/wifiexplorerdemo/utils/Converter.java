package com.enterprayz.urec.wifiexplorerdemo.utils;

import android.content.Context;
import android.net.Uri;

import com.enterprayz.urec.wifiexplorerdemo.R;
import com.enterprayz.urec.wifiexplorerdemo.items.APNClientListItem;
import com.enterprayz.urec.wifiexplorerdemo.items.WifiListItem;
import com.enterprayz.urec.wifiexplorerlib.items.ClientScanResultItem;
import com.enterprayz.urec.wifiexplorerlib.items.WifiScanResultsItem;
import com.enterprayz.urec.wifiexplorerlib.utils.WifiOptionsBuilder;

import java.util.ArrayList;

/**
 * Created by con on 28.12.14.
 */
public class Converter {
    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FORESLASH = "/";

    public static ArrayList<WifiListItem> convertScanResListItemsToWifiListItems(ArrayList<WifiScanResultsItem> wifiScanResultsItems,
                                                                                 Context context) {
        ArrayList<WifiListItem> wifiListItems = new ArrayList<>();
        for (WifiScanResultsItem scanResultsItem : wifiScanResultsItems) {
            wifiListItems.add(convertScanResItemListToWifiListItem(scanResultsItem, context));
        }
        return wifiListItems;
    }

    public static WifiListItem convertScanResItemListToWifiListItem(WifiScanResultsItem scanResultsItem, Context context) {
        WifiListItem result = new WifiListItem();
        result.setTitle(scanResultsItem.getSSID());
        Context con = context;
        if (con != null) {
            Uri signalLevelImagePath = null;

            int difference = 0 - scanResultsItem.getLevel();
            String imageResPath = ANDROID_RESOURCE + context.getPackageName() + FORESLASH;
            if (difference >= 80) {
                signalLevelImagePath = Uri.parse(imageResPath + R.drawable.wifi_level_1);
            } else if (difference >= 70) {
                signalLevelImagePath = Uri.parse(imageResPath + R.drawable.wifi_level_2);
            } else if (difference >= 60) {
                signalLevelImagePath = Uri.parse(imageResPath + R.drawable.wifi_level_3);
            } else if (difference > 50) {
                signalLevelImagePath = Uri.parse(imageResPath + R.drawable.wifi_level_4);
            } else if (difference <= 50) {
                signalLevelImagePath = Uri.parse(imageResPath + R.drawable.wifi_level_5);
            }
            result.setSignalLevelImagePath(signalLevelImagePath);

            String subTitletext = "";

            int[] secureKey = WifiOptionsBuilder.getWifiSecure(scanResultsItem.getCapabilities());

            switch (secureKey[0]) {
                case WifiOptionsBuilder.NO_KEY_NET_ATTR: {
                    subTitletext = "Open Wifi.";
                    break;
                }
                case WifiOptionsBuilder.WEP_KEY_NET_ATTR: {
                    subTitletext = "Wifi secureb by WEP.";
                    break;
                }
                case WifiOptionsBuilder.WPA_KEY_NET_ATTR: {
                    subTitletext = "Wifi secured by WPA.";
                    break;
                }
                case WifiOptionsBuilder.WPA2_KEY_NET_ATTR: {
                    subTitletext = "Wifi secured by WPA2.";
                    break;
                }
            }

            if (secureKey[1] == WifiOptionsBuilder.WPS_KEY_NET_ATTR) {
                subTitletext += "WPS is enabled";
            }

            if (scanResultsItem.isConnected()) {
                subTitletext = "Is connected";
            }

            result.setSubtitle(subTitletext);

            Uri lockWifiImagePath = Uri.parse(imageResPath + R.drawable.lock);
            result.setLockWifiImagePath(lockWifiImagePath);

            result.setInfoItem(scanResultsItem);
        }
        return result;
    }


    public static ArrayList<APNClientListItem> convertClientScanResultItemToApnListItem(ArrayList<ClientScanResultItem> clients) {
        ArrayList<APNClientListItem> list = new ArrayList<>();
        if (clients != null) {
            for (ClientScanResultItem item : clients) {
                if (item.isReachable()) {
                    list.add(new APNClientListItem(
                            item.getIpAddr(),
                            item.getHWAddr(),
                            item.getDeviceVendorName(),
                            item.isReachable()
                    ));
                }
            }

        }
        return list;
    }
}
