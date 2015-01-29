package com.enterprayz.urec.wifiexplorerlib.utils.ScannerOptions;

import com.enterprayz.urec.wifiexplorerlib.utils.NetInfo;

/**
 * Created by con on 11.01.15.
 */
public class DefaultBaseScannerOptions extends BaseScannerOptions {

    /**
     * Default Scanner options
     * DPORTS - delaults ports for scan
     * TIMEOUT_SCAN - in that time scan must be stop
     * TIMEOUT_SHUTDOWN - the maximum time to wait before blocks until all tasks have completed execution after a shutdown request.
     * THREADS - max scans threads
     * mRateMult - max ping threads
     * Pt_move - 1 (move back like 192.168.1.103, ... 102.... 101 .... 100), 2 (move forward like 192.168.1.103, ... 104.... 105 .... 106)
     * Start - scan start ip (-1062731519 = 192.168.1.1)
     * End - scan end ip (-1062731266 = 192.168.1.254)
     * DoRateControl -is need ping ip.
     * */

    public DefaultBaseScannerOptions (String startIP, String endIp, String gateIp){
        setGateIP(gateIp);
        setStartIP(startIP);
        setEndIP(endIp);

    }

    @Override
    public BaseScannerConfiguration getConfig() {
        BaseScannerConfiguration configuration = new BaseScannerConfiguration();
        configuration.setGateIp(getGateIP());
        configuration.setDPORTS(new int[]{ 20, 21,22, 23, 25, 53, 80, 110, 137, 138 ,139 ,8000, 8080, 3128, 3389, 6588, 1080, 5900, 8888 });
        configuration.setTIMEOUT_SCAN(30);
        configuration.setTIMEOUT_SHUTDOWN(10);
        configuration.setTHREADS(10);
        configuration.setmRateMult(5);
        configuration.setPt_move(2);
        configuration.setStart(NetInfo.getUnsignedLongFromIp(getStartIP()));
        configuration.setEnd(NetInfo.getUnsignedLongFromIp(getEndIP()));
        configuration.setDoRateControl(true);
        return configuration;
    }
}
