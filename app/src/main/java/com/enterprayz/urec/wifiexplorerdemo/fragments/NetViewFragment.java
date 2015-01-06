package com.enterprayz.urec.wifiexplorerdemo.fragments;

/**
 * Created by con on 25.12.14.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.enterprayz.urec.wifiexplorerdemo.R;
import com.enterprayz.urec.wifiexplorerdemo.adapters.WifiListAdapter;
import com.enterprayz.urec.wifiexplorerdemo.fragments.dialog.OpenWifiDialog;
import com.enterprayz.urec.wifiexplorerdemo.items.WifiListItem;
import com.enterprayz.urec.wifiexplorerdemo.utils.Converter;
import com.enterprayz.urec.wifiexplorerlib.core.WifiClientModel;
import com.enterprayz.urec.wifiexplorerlib.enum_state.WIFI_MODULE_STATE;
import com.enterprayz.urec.wifiexplorerlib.items.WifiScanResultsItem;
import com.enterprayz.urec.wifiexplorerlib.utils.WifiOptions.NoNETKeyOptions;
import com.enterprayz.urec.wifiexplorerlib.utils.WifiOptions.WepNETKeyOption;
import com.enterprayz.urec.wifiexplorerlib.utils.WifiOptions.WifiKeyOptions;
import com.enterprayz.urec.wifiexplorerlib.utils.WifiOptions.Wpa2NETKeyOption;
import com.enterprayz.urec.wifiexplorerlib.utils.WifiOptions.WpaNETKeyOption;
import com.enterprayz.urec.wifiexplorerlib.utils.WifiOptionsBuilder;

import java.util.ArrayList;


public class NetViewFragment extends Fragment {
    private ListView lvWifiNETItems;
    private WifiListAdapter adapter;
    private WifiClientModel localWificlient;


    @Override
    public void onResume() {
        super.onResume();
        iniWifiClient();
        localWificlient.autoScanNetwork(2000);
    }

    @Override
    public void onPause() {
        super.onPause();
        localWificlient.stopAutoScanNetwork();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_net_view, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        iniUi();
        iniListeners();
        iniWifiClient();
    }

    private void iniUi() {
        lvWifiNETItems = (ListView) getActivity().findViewById(R.id.lv_wifi_net_items_net_view_fragment);
        ArrayList<WifiListItem> wifilist = new ArrayList<>();
        adapter = new WifiListAdapter(wifilist, getActivity());
        lvWifiNETItems.setAdapter(adapter);
    }

    private void iniListeners() {
        lvWifiNETItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public int keySecure;
            public String SSID;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WifiListItem item = adapter.getItem(position);
                if (item.getInfoItem().isConnected()) {
                    WifiClientModel.getInstance(getActivity()).dissconnectFromNetwork();
                } else {
                    SSID = item.getInfoItem().getSSID();
                    keySecure = WifiOptionsBuilder.getWifiSecure(item.getInfoItem().getCapabilities())[0];
                    if (keySecure != WifiOptionsBuilder.NO_KEY_NET_ATTR) {
                        OpenWifiDialog dialog = new OpenWifiDialog();
                        dialog.setOnOpenWifiDialogListener(new OpenWifiDialog.OnOpenWifiDialogListener() {
                            @Override
                            public void onInputPassword(String password) {
                                WifiKeyOptions secureconfig;
                                switch (keySecure) {
                                    case WifiOptionsBuilder.WPA_KEY_NET_ATTR: {
                                        secureconfig = new WpaNETKeyOption(SSID, password);
                                        break;
                                    }
                                    case WifiOptionsBuilder.WPA2_KEY_NET_ATTR: {
                                        secureconfig = new Wpa2NETKeyOption(SSID, password);
                                        break;
                                    }
                                    case WifiOptionsBuilder.WEP_KEY_NET_ATTR: {
                                        secureconfig = new WepNETKeyOption(SSID, password);
                                        break;
                                    }
                                    default: {
                                        secureconfig = null;
                                    }
                                }
                                if (secureconfig != null) {
                                    localWificlient.connectToNetwork(secureconfig);
                                }
                            }
                        });
                        dialog.show(getFragmentManager(), "OpenWifiDialog");
                    } else {
                        localWificlient.connectToNetwork(new NoNETKeyOptions(SSID));
                    }
                }
            }
        });
    }

    private void iniWifiClient() {
        localWificlient = WifiClientModel.getInstance(getActivity());
    }

    public void onNetworkScanResult(ArrayList<WifiScanResultsItem> results) {
        adapter.removeAll();
        adapter.addItems(Converter.convertScanResListItemsToWifiListItems(results, getActivity()));
    }

    public void onWifiModuleChangeCheck(WIFI_MODULE_STATE state) {
        switch (state) {
            case WIFI_MODULE_STATE_DISABLED: {
                if (localWificlient == null) {
                    iniWifiClient();
                }
                if (localWificlient.getAutoScanCheck()) {
                    localWificlient.stopAutoScanNetwork();
                }
                adapter.removeAll();
                break;
            }
            case WIFI_MODULE_STATE_ENABLED: {
                if (localWificlient == null) {
                    iniWifiClient();
                }
                if (!localWificlient.getAutoScanCheck()) {
                    localWificlient.autoScanNetwork(2000);
                }

                break;
            }
            case WIFI_MODULE_STATE_DISABLING: {
                break;
            }
            case WIFI_MODULE_STATE_ENABLING: {
                break;
            }
        }
    }

}