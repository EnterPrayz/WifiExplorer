package com.enterprayz.urec.wifiexplorerlib.utils.WifiOptions;

import android.net.wifi.WifiConfiguration;

/**
 * Created by ura on 09.12.14.
 */
public abstract class WifiCongigFactory {

    /**
     * Get {@link WifiConfiguration} needed for work with wifi network.
     */
    public abstract WifiConfiguration getConfig(WifiKeyOptions keyOptions);
}
