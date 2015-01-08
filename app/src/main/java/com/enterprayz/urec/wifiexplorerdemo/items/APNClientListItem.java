package com.enterprayz.urec.wifiexplorerdemo.items;

/**
 * Created by con on 03.01.15.
 */
public class APNClientListItem {
    private String IpAddr;
    private String HWAddr;
    private String DeviceVendor;
    private boolean isReachable;

    public APNClientListItem(String ipAddr, String hWAddr, String deviceVendor, boolean isReachable) {
        super();
        this.IpAddr = ipAddr;
        this.HWAddr = hWAddr;
        this.DeviceVendor = deviceVendor;
        this.isReachable = isReachable;
    }

    public String getIpAddr() {
        return IpAddr;
    }

    public void setIpAddr(String ipAddr) {
        IpAddr = ipAddr;
    }


    public String getHWAddr() {
        return HWAddr;
    }

    public void setHWAddr(String hWAddr) {
        HWAddr = hWAddr;
    }


    public String getDeviceVendor() {
        return DeviceVendor;
    }

    public void setDeviceVendor(String deviceVendor) {
        DeviceVendor = deviceVendor;
    }


    public boolean isReachable() {
        return isReachable;
    }

    public void setReachable(boolean isReachable) {
        this.isReachable = isReachable;
    }
}
