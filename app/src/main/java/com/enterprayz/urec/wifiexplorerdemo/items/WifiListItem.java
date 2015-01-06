package com.enterprayz.urec.wifiexplorerdemo.items;

import android.net.Uri;

import com.enterprayz.urec.wifiexplorerlib.items.WifiScanResultsItem;

/**
 * Created by con on 26.12.14.
 */
public class WifiListItem {

    private long id;
    private String title, subtitle;
    private Uri signalLevelImagePath;
    private Uri lockWifiImagePath;
    private WifiScanResultsItem infoItem;

    public WifiListItem() {

    }

    public WifiListItem(long id, String title, String subtitle, Uri signalLevelImagePath, Uri lockWifiImagePath, WifiScanResultsItem infoItem) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.signalLevelImagePath = signalLevelImagePath;
        this.lockWifiImagePath = lockWifiImagePath;
        this.infoItem = infoItem;
    }
    //add getters and setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Uri getSignalLevelImagePath() {
        return signalLevelImagePath;
    }

    public void setSignalLevelImagePath(Uri signalLevelImagePath) {
        this.signalLevelImagePath = signalLevelImagePath;
    }

    public Uri getLockWifiImagePath() {
        return lockWifiImagePath;
    }

    public void setLockWifiImagePath(Uri lockWifiImagePath) {
        this.lockWifiImagePath = lockWifiImagePath;
    }

    public WifiScanResultsItem getInfoItem() {
        return infoItem;
    }

    public void setInfoItem(WifiScanResultsItem infoItem) {
        this.infoItem = infoItem;
    }
}