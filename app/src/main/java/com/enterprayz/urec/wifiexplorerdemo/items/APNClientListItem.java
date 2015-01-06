package com.enterprayz.urec.wifiexplorerdemo.items;

/**
 * Created by con on 03.01.15.
 */
public class APNClientListItem {
    private String IpAddr;
    private String HWAddr;
    private String Device;
    private boolean isReachable;

    public APNClientListItem(String ipAddr, String hWAddr, String device, boolean isReachable) {
        super();
        this.IpAddr = ipAddr;
        this.HWAddr = hWAddr;
        this.Device = device;
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


    public String getDevice() {
        return Device;
    }

    public void setDevice(String device) {
        Device = device;
    }


    public boolean isReachable() {
        return isReachable;
    }

    public void setReachable(boolean isReachable) {
        this.isReachable = isReachable;
    }
}
