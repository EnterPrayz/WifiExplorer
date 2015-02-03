package com.enterprayz.urec.wifiexplorerdemo.fragments.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.enterprayz.urec.wifiexplorerdemo.R;
import com.enterprayz.urec.wifiexplorerdemo.items.WifiListItem;
import com.enterprayz.urec.wifiexplorerlib.utils.NetInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by con on 29.01.15.
 */
public class WifiInfoDialog extends DialogFragment {
    private View rootView;
    private OnConnectedDialogMenuListener listener;
    private ListView lvWifiInfoList;
    private NetInfo netInfo;
    private SimpleAdapter adapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().setBackgroundDrawableResource(R.color.black);
        dialog.setTitle(netInfo.ssid);
        return dialog;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_net_info, null);
        iniUi(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setCancelable(true);
        iniListeners();
    }

    private void iniUi(View rootView) {
        lvWifiInfoList = (ListView) rootView.findViewById(R.id.lv_wifi_info_list_wifi_info_dialog);
        lvWifiInfoList.setAdapter(adapter);

    }

    public void iniList(Context context, NetInfo netInfo, WifiListItem item) {
        this.netInfo = netInfo;
        ArrayList<Map<String, ?>> infoList = new ArrayList<>();

        Map<String, String> map;

        if (!netInfo.ssid.equals("")) {
            map = new HashMap<>();
            map.put("title", "SSID");
            map.put("subTitle", netInfo.ssid);
            map.put("imageSignal", String.valueOf(item.getSignalLevelImagePath()));
            infoList.add(map);
        }

        if (!netInfo.bssid.equals("")) {
            map = new HashMap<>();
            map.put("title", "BSID");
            map.put("subTitle", netInfo.bssid);
            infoList.add(map);
        }
        if (!netInfo.ip.equals(NetInfo.NOIP)) {
            map = new HashMap<>();
            map.put("title", "My IP address");
            map.put("subTitle", netInfo.ip);
            infoList.add(map);
        }
        if (!netInfo.macAddress.equals(NetInfo.NOMAC)) {
            map = new HashMap<>();
            map.put("title", "My MAC address");
            map.put("subTitle", netInfo.macAddress);
            infoList.add(map);
        }

        if (!netInfo.gatewayIp.equals(NetInfo.NOIP)) {
            map = new HashMap<>();
            map.put("title", "Access point IP address");
            map.put("subTitle", netInfo.gatewayIp);
            infoList.add(map);
        }

        if (!netInfo.netmaskIp.equals(NetInfo.NOMASK)) {
            map = new HashMap<>();
            map.put("title", "Mask address");
            map.put("subTitle", netInfo.netmaskIp);
            infoList.add(map);
        }
        if (netInfo.speed != 0) {
            map = new HashMap<>();
            map.put("title", "Link speed");
            map.put("subTitle", netInfo.speed + "Mbs");
            infoList.add(map);
        }


        String[] from = {"title", "subTitle", "imageSignal"};
        int[] to = {R.id.tv_item_title_item_net_info_list,
                R.id.tv_item_sub_title_item_net_info_list,
                R.id.iv_wifi_strength_indicator_item_net_info_list
        };

        adapter = new SimpleAdapter(context, infoList, R.layout.item_net_info_list, from, to);

    }


    private void iniListeners() {

    }

    public void setOnConnectedDialogMenuListener(OnConnectedDialogMenuListener listener) {
        this.listener = listener;
    }

    public interface OnConnectedDialogMenuListener {
        void onItemSelect(int id);
    }

}
