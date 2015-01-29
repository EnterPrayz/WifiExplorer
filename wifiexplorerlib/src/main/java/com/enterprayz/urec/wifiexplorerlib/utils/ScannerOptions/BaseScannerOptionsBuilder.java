package com.enterprayz.urec.wifiexplorerlib.utils.ScannerOptions;

/**
 * Created by con on 11.01.15.
 */
public class BaseScannerOptionsBuilder extends BaseScannerFactory {

    public BaseScannerOptionsBuilder(){

    }


    @Override
    public BaseScannerConfiguration getConfig(BaseScannerOptions options) {
        return options.getConfig();
    }
}
