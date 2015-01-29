package com.enterprayz.urec.wifiexplorerlib.interfaces;

import com.enterprayz.urec.wifiexplorerlib.items.HostBean;

/**
 * Created by con on 11.01.15.
 */
public interface OnNetDeviceScanListener {
    final public static int ON_START = 0;
    final public static int ON_PROGRESS = 1;
    final public static int ON_FIND_DEVICE = 2;
    final public static int ON_FINISH = 3;
    final public static int ON_ERROR = 4;
    final public static int ON_CANCELED = 5;

    public void onStartScan();

    public void onProgress(int percentage);

    public void onFindDevice(HostBean user);

    public void onCanceled ();

    public void onFinishScan ();

    public void onErrorScan ();
}
