package com.enterprayz.urec.wifiexplorerlib.utils.ScannerOptions;


/**
 * Created by con on 11.01.15.
 */
public abstract class BaseScannerOptions extends BaseScannerConfiguration {

    String startIP;
    String  endIP;
    String _gateIP;

    public void setStartIP(String  startIP) {
        this.startIP = startIP;
    }

    public String getStartIP() {
        return startIP;
    }

    public void setEndIP(String endIP) {
        this.endIP = endIP;
    }

    public String getEndIP() {
        return endIP;
    }

    public void setGateIP(String _gateIP) {
        this._gateIP = _gateIP;
    }

    public String getGateIP() {
        return _gateIP;
    }

    public abstract BaseScannerConfiguration getConfig();

}
