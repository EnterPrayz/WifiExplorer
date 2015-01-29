package com.enterprayz.urec.wifiexplorerdemo.fragments.dialog;

/**
 * Created by con on 29.12.14.
 */

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.enterprayz.urec.wifiexplorerdemo.R;


/**
 * Created by ura on 14.11.14.
 */


public class OpenWifiDialog extends DialogFragment {
    private View rootView;
    private Button btnOkWifiOpenWifi;
    private Button btnCancelWifiOpenWifi;
    private EditText etInputPassWifiOpenWifi;
    private OnOpenWifiDialogListener openWifiDialogListener;

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
        rootView = inflater.inflate(R.layout.dialog_open_wifi, null);
        iniUi(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setCancelable(false);
        iniListeners();
    }

    private void iniUi(View rootView) {
        etInputPassWifiOpenWifi = (EditText) rootView.findViewById(R.id.et_input_pass_wifi_open_wifi);
        btnOkWifiOpenWifi = (Button) rootView.findViewById(R.id.btn_ok_wifi_open_wifi);
        btnCancelWifiOpenWifi = (Button) rootView.findViewById(R.id.btn_cancel_wifi_open_wifi);
    }

    private void iniListeners() {
        btnOkWifiOpenWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (openWifiDialogListener != null) {
                    if (etInputPassWifiOpenWifi.getText().toString().length() > 7) {
                        openWifiDialogListener.onInputPassword(etInputPassWifiOpenWifi.getText().toString());
                        getDialog().dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Password should be more than " + etInputPassWifiOpenWifi.getText().toString().length() + " characters", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        btnCancelWifiOpenWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
    }

    public void setOnOpenWifiDialogListener(OnOpenWifiDialogListener openWifiDialogListener) {
        this.openWifiDialogListener = openWifiDialogListener;
    }

    public interface OnOpenWifiDialogListener {
        void onInputPassword(String password);
    }

}