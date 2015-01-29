package com.enterprayz.urec.wifiexplorerlib.utils;

import android.content.Context;
import android.util.Log;

import com.enterprayz.urec.wifiexplorerlib.interfaces.OnNetDeviceScanListener;
import com.enterprayz.urec.wifiexplorerlib.items.HostBean;
import com.enterprayz.urec.wifiexplorerlib.utils.ScannerOptions.BaseScannerConfiguration;

public class NetworkDeviceScanner extends BaseScanner {

    OnNetDeviceScanListener listener;

    public NetworkDeviceScanner(BaseScannerConfiguration configuration, Context context) {
        super(configuration, context);
    }

    public void setListener(OnNetDeviceScanListener listener) {
        this.listener = listener;
    }


    @Override
    protected void onPreExecute() {
        if (listener != null) {
            listener.onStartScan();
        }
    }

    @Override
    protected void onProgressUpdate(HostBean... host) {

        if (listener != null) {
            if (hosts_done == 0){
                listener.onProgress(1);
            }else {
                int res = (int) (hosts_done * 100 / size);
                listener.onProgress(res);
            }

            if (host[0] != null) {
                listener.onFindDevice(host[0]);
            }
        }
    }

    @Override
    protected void onPostExecute(Void unused) {
        if (listener != null) {
            listener.onFinishScan();
        }
    }

    @Override
    protected void onCancelled() {
        if (listener != null) {
            listener.onCanceled();
        }
        super.onCancelled();
    }
}
