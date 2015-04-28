package com.enterprayz.urec.wifiexplorerdemo.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.enterprayz.urec.wifiexplorerdemo.LocalNetScannerView.DetailHostBeanList;
import com.enterprayz.urec.wifiexplorerdemo.LocalNetScannerView.NetScannerList;
import com.enterprayz.urec.wifiexplorerdemo.R;
import com.enterprayz.urec.wifiexplorerdemo.fragments.dialog.MyProgressDialog;
import com.enterprayz.urec.wifiexplorerlib.core.WifiClientModel;
import com.enterprayz.urec.wifiexplorerlib.interfaces.OnNetDeviceScanListener;
import com.enterprayz.urec.wifiexplorerlib.items.HostBean;

import java.util.ArrayList;

/**
 * Created by con on 11.01.15.
 */
public class LocalNetViewFragment extends Fragment {
    private static final String TAG = LocalNetViewFragment.class.getName();
    private WifiClientModel localWificlient;
    private NetScannerList cvLocalScanNetList;
    private ArrayList<HostBean> hostBeanArrayList;
    private int MAX_RESPONCE_TIME = 0;
    private MyProgressDialog pd;
    private DetailHostBeanList detailHostBeanList;

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_net_view, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        iniWifiClient();
        cvLocalScanNetList = (NetScannerList) getActivity().findViewById(R.id.cv_local_scan_net_view);
        detailHostBeanList = (DetailHostBeanList) getActivity().findViewById(R.id.detail_host_bean_list);
        iniProgressDialog();
        localWificlient.scanLocalNetwork(new OnNetDeviceScanListener() {
            @Override
            public void onStartScan() {
                hostBeanArrayList = new ArrayList<>();
                pd.show(getFragmentManager(), "ProgressDialog");
                Log.d(TAG, "onStartScan");
            }

            @Override
            public void onProgress(final int percentage) {
                pd.setProgressBarProgress(percentage);
                Log.d(TAG, "onProgress  " + percentage + "%");
            }

            @Override
            public void onFindDevice(HostBean user) {
                hostBeanArrayList.add(user);
                Log.d(TAG, "onFindDevice " + user.nicVendor);

            }

            @Override
            public void onCanceled() {
                pd.dismiss();
                Log.d(TAG, "onCanceled");
            }

            @Override
            public void onFinishScan() {
                if (pd.isVisible()){
                    pd.dismiss();
                }
                initList(cvLocalScanNetList, hostBeanArrayList);
                Log.d(TAG, "onFinishScan");

            }

            @Override
            public void onErrorScan() {
                pd.dismiss();
                Log.d(TAG, "onErrorScan");

            }
        });
    }


    public boolean onBackPressed() {
        boolean needCloseActivity = true;
        if (cvLocalScanNetList.isTranslated()) {
            needCloseActivity = false;
            cvLocalScanNetList.changeTranslateState();
        } else if (cvLocalScanNetList.isExpanded()) {
            needCloseActivity = true;
            cvLocalScanNetList.changeExpandState();
        }
        return needCloseActivity;
    }

    private void iniProgressDialog() {
        pd = new MyProgressDialog();
        pd.setListener(new MyProgressDialog.OnProgressDialogListener() {
            @Override
            public void onCancel() {
                localWificlient.cancelScanLocalNetwork();
            }
        });
    }

    private void initList(final NetScannerList list, final ArrayList<HostBean> hostBeanArrayList) {
        list.setOnListListener(new NetScannerList.OnNetScannerListListener() {
            @Override
            public void onListBind(boolean listExpandState) {

            }

            @Override
            public void onItemDetailBind(boolean itemExpandState, View view, int position) {
                if (itemExpandState) {
                    detailHostBeanList.setVisibility(View.VISIBLE);
                    int[] startXY = new int[2];
                    startXY[0] = 23 + (view.getMeasuredWidth() / 2);
                    startXY[1] = 45 + (view.getMeasuredHeight() / 2);
                    detailHostBeanList.iniAnimation(startXY,
                            (int) Math.sqrt(Math.pow((double) cvLocalScanNetList.getWidth(), (double) 2) + Math.pow((double) cvLocalScanNetList.getHeight(), (double) 2)));
                    detailHostBeanList.setHostBeanItem(hostBeanArrayList.get(position));
                }
            }
        });

        MAX_RESPONCE_TIME = 0;

        for (final HostBean item : hostBeanArrayList) {
            if (item.responseTime > MAX_RESPONCE_TIME) {
                MAX_RESPONCE_TIME = item.responseTime;
            }
        }
        Log.d(TAG, "MAX_RESPONCE_TIME = " + MAX_RESPONCE_TIME);
        for (final HostBean item : hostBeanArrayList) {
            ImageView image = new ImageView(getActivity());
            if (item.deviceType == HostBean.TYPE_GATEWAY) {
                image.setImageResource(R.drawable.router);
            } else if (item.deviceType == HostBean.TYPE_COMPUTER) {
                image.setImageResource(R.drawable.computer);
            }

            int percent = 100 - ((int) item.responseTime * 100 / MAX_RESPONCE_TIME);


            list.addItem(image, percent, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detailHostBeanList.setVisibility(View.GONE);
                }
            });
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            list.changeExpandState();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void iniWifiClient() {
        localWificlient = WifiClientModel.getInstance();
    }

}
