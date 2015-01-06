package com.enterprayz.urec.wifiexplorerlib.items;

/**
 * Created by ura on 08.12.14.
 */
public class WifiScanResultsItem {
    /*
        BSSID	        The address of the access point.
       	SSID	        The network name.
        capabilities	Describes the authentication, key management, and encryption schemes supported by the access point.
        frequency	    The frequency in MHz of the channel over which the client is communicating with the access point.
        level	        The detected signal level in dBm, also known as the RSSI.
        isConnected     Is device now connected to WIFI*/

    /**
     *The address of the access point.
     * */
    private String BSSID;
    /**
     *The network name.
     * */
    private String SSID;
    /**
     *Describes the authentication, key management, and encryption schemes supported by the access point.
     * */
    private String capabilities;
    /**
     *The frequency in MHz of the channel over which the client is communicating with the access point.
     * */
    private int frequency;
    /**
     * The detected signal level in dBm, also known as the RSSI.
     * */
    private int level;
    /**
     * Is device now connected to WIFI
     * */
    private boolean isConnected;

    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }

    public String getBSSID() {
        return BSSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getSSID() {
        return SSID;
    }

    public void setCapabilities(String capabilities) {
        this.capabilities = capabilities;
    }

    public String getCapabilities() {
        return capabilities;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public boolean isConnected() {
        return isConnected;
    }
}
