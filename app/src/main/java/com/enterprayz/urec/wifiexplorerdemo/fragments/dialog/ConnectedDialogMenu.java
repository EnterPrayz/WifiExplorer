package com.enterprayz.urec.wifiexplorerdemo.fragments.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.enterprayz.urec.wifiexplorerdemo.R;

/**
 * Created by con on 29.01.15.
 */
public class ConnectedDialogMenu extends DialogFragment {
    private View rootView;
    private OnConnectedDialogMenuListener listener;
    private Button disconnectButton;
    private Button wifiInfoButton;
    private Button scanWifiButton;

    public static final int DISSCONNECT_ITEM = 0;
    public static final int WIFI_INFO_ITEM = 1;
    public static final int SCAN_WIFI_ITEM = 2;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_corners);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_connected_menu, null);
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
        disconnectButton = (Button) rootView.findViewById(R.id.disconnect_connected_menu_dialog);
        wifiInfoButton = (Button) rootView.findViewById(R.id.wifi_info_connected_menu_dialog);
        scanWifiButton = (Button) rootView.findViewById(R.id.scan_wifi_connected_menu_dialog);
    }

    private void iniListeners() {
        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemSelect(DISSCONNECT_ITEM);
                }
            }
        });
        wifiInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemSelect(WIFI_INFO_ITEM);
                }
            }
        });
        scanWifiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemSelect(SCAN_WIFI_ITEM);
                }
            }
        });
    }

    public void setOnConnectedDialogMenuListener(OnConnectedDialogMenuListener listener) {
        this.listener = listener;
    }

    public interface OnConnectedDialogMenuListener {
        void onItemSelect(int id);
    }

}