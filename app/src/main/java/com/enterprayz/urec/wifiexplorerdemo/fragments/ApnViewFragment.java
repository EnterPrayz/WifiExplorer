package com.enterprayz.urec.wifiexplorerdemo.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.enterprayz.urec.wifiexplorerdemo.R;
import com.enterprayz.urec.wifiexplorerdemo.adapters.ApnListAdapter;
import com.enterprayz.urec.wifiexplorerdemo.items.APNClientListItem;
import com.enterprayz.urec.wifiexplorerdemo.utils.Converter;
import com.enterprayz.urec.wifiexplorerlib.core.WifiClientModel;
import com.enterprayz.urec.wifiexplorerlib.enum_state.WIFI_APN_STATE;
import com.enterprayz.urec.wifiexplorerlib.items.ClientScanResultItem;

import java.util.ArrayList;

/**
 * Created by con on 02.01.15.
 */
public class ApnViewFragment extends Fragment {
    private WifiClientModel localWificlient;
    private ListView lvApnItemsApnViewFragment;
    private LinearLayout llContainerToHideApnViewFragment;
    private ApnListAdapter adapter;
    public static final int CONNECT_FLAG = 1;
    public static final int DISSCONNECT_FLAG = 2;
    private String hostname;
    private TextView tvApnSsidTitleApnViewFragment;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }


    @Override
    public void onResume() {
        super.onResume();
        iniWifiClient();
        localWificlient.getAPNUsers();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apn_view, container, false);
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
        tvApnSsidTitleApnViewFragment = (TextView) getActivity().findViewById(R.id.tv_apn_ssid_title_apn_view_fragment);
        llContainerToHideApnViewFragment = (LinearLayout) getActivity().findViewById(R.id.ll_container_to_hide_apn_view_fragment);
        lvApnItemsApnViewFragment = (ListView) getActivity().findViewById(R.id.lv_apn_items_apn_view_fragment);
        ArrayList<APNClientListItem> apnlist = new ArrayList<>();
        adapter = new ApnListAdapter(apnlist, getActivity());
        lvApnItemsApnViewFragment.setAdapter(adapter);
    }

    private void iniListeners() {
        lvApnItemsApnViewFragment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

            }
        });
    }

    private void iniWifiClient() {
        localWificlient = WifiClientModel.getInstance(getActivity());
    }

    public void onGetAPNUsers(final ArrayList<ClientScanResultItem> clients) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.setItems(Converter.convertClientScanResultItemToApnListItem(clients));
                if (adapter.getCount() > 0) {
                    showSearchContainer(false);
                } else {
                    showSearchContainer(true);
                }
            }
        });
    }

    public void onAPNUsersChangeState(final ClientScanResultItem userItem, final int flag) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (flag) {
                    case CONNECT_FLAG: {
                        boolean isPresent = false;
                        for (APNClientListItem item : adapter.getItems()) {
                            if (item.getHWAddr().equals(userItem.getHWAddr())) {
                                isPresent = true;
                                break;
                            }
                        }
                        if (!isPresent) {
                            adapter.addItem(new APNClientListItem(
                                    userItem.getIpAddr(),
                                    userItem.getHWAddr(),
                                    userItem.getDevice(),
                                    userItem.isReachable()
                            ));
                        }
                        break;
                    }
                    case DISSCONNECT_FLAG: {
                        boolean isPresent = false;
                        for (APNClientListItem item : adapter.getItems()) {
                            if (item.getHWAddr().equals(userItem.getHWAddr())) {
                                isPresent = true;
                                break;
                            }
                        }
                        if (isPresent) {
                            adapter.removeItem(userItem.getHWAddr());
                        }
                        break;
                    }
                }
                if (adapter.getCount() > 0) {
                    showSearchContainer(false);
                } else {
                    showSearchContainer(true);
                }
            }
        });


    }

    public void onAPNChangeCheck(WIFI_APN_STATE state) {

    }

    public void showSearchContainer(boolean visible) {
        if (llContainerToHideApnViewFragment == null) {
            llContainerToHideApnViewFragment = (LinearLayout) getActivity().findViewById(R.id.ll_container_to_hide_apn_view_fragment);
        }

        if (visible) {
            tvApnSsidTitleApnViewFragment.getHandler().post(new Runnable() {
                public void run() {
                    tvApnSsidTitleApnViewFragment.setText(localWificlient.getLastApnConfiguration().SSID);
                }
            });
            llContainerToHideApnViewFragment.getHandler().post(new Runnable() {
                public void run() {
                    llContainerToHideApnViewFragment.setVisibility(View.VISIBLE);
                }
            });
        } else {
            llContainerToHideApnViewFragment.getHandler().post(new Runnable() {
                public void run() {
                    llContainerToHideApnViewFragment.setVisibility(View.GONE);
                }
            });
        }
    }
}