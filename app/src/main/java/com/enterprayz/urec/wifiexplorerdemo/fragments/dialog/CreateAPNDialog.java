package com.enterprayz.urec.wifiexplorerdemo.fragments.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.enterprayz.urec.wifiexplorerdemo.R;
import com.enterprayz.urec.wifiexplorerlib.core.WifiClientModel;
import com.enterprayz.urec.wifiexplorerlib.utils.WifiOptions.NoKeyApOptions;
import com.enterprayz.urec.wifiexplorerlib.utils.WifiOptions.WifiKeyOptions;
import com.enterprayz.urec.wifiexplorerlib.utils.WifiOptions.Wpa2APNKeyOption;
import com.enterprayz.urec.wifiexplorerlib.utils.WifiOptions.WpaAPNKeyOption;

/**
 * Created by con on 03.01.15.
 */
public class CreateAPNDialog extends DialogFragment {
    private View rootView;
    private EditText etInputApnName;
    private EditText etInputApnPass;
    private CheckBox chbxNeedToHide;
    private Spinner spSecurityList;
    private Button btnOkWifiOpenApn;
    private Button btnCancelWifiOpenApn;
    private LinearLayout llContainerToHide;
    private OnAPNDialogListener onCreateAPNDialogListener;
    private WifiClientModel localWifiClient;
    private int secureId = 0;
    String[] secureListdata = {"Open", "WPA PSK", "WPA2 PSK"};


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_corners);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_create_apn, null);
        iniUi(rootView);
        iniSecurityList();
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setCancelable(false);
        iniListeners();
    }

    private void iniUi(View rootView) {
        localWifiClient = WifiClientModel.getInstance();
        etInputApnName = (EditText) rootView.findViewById(R.id.et_input_apn_name);
        etInputApnPass = (EditText) rootView.findViewById(R.id.et_input_apn_pass);
        chbxNeedToHide = (CheckBox) rootView.findViewById(R.id.chbx_need_to_hide);
        spSecurityList = (Spinner) rootView.findViewById(R.id.sp_security_list);
        btnOkWifiOpenApn = (Button) rootView.findViewById(R.id.btn_ok_wifi_open_apn);
        btnCancelWifiOpenApn = (Button) rootView.findViewById(R.id.btn_cancel_wifi_open_apn);
        llContainerToHide = (LinearLayout) rootView.findViewById(R.id.ll_container_to_hide);
    }

    private void iniSecurityList() {
        if (spSecurityList != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, secureListdata);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spSecurityList.setAdapter(adapter);
            spSecurityList.setSelection(0);
            spSecurityList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    secureId = position;
                    if (position > 0) {
                        llContainerToHide.setVisibility(View.VISIBLE);
                    } else {
                        llContainerToHide.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });
        }
    }

    private void iniListeners() {
        btnOkWifiOpenApn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCreateAPNDialogListener != null) {
                    if (checkAllFields()) {
                        onCreateAPNDialogListener.onGetAPNOConfiguration(getConfiguration(
                                etInputApnName.getText().toString(),
                                chbxNeedToHide.isChecked(),
                                secureId,
                                etInputApnPass.getText().toString()
                        ));
                        getDialog().dismiss();
                    }
                }
            }
        });
        btnCancelWifiOpenApn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCreateAPNDialogListener != null) {
                    onCreateAPNDialogListener.onCancelDialog();
                }
                getDialog().dismiss();
            }
        });
    }

    private boolean checkAllFields() {
        boolean isOk = true;
        if (etInputApnName.getText().toString().length() < 1) {
            isOk = false;
            Toast.makeText(getActivity(), "Please input APN name", Toast.LENGTH_LONG).show();
        }
        if (secureId > 0 && etInputApnPass.getText().toString().length() <= 7) {
            isOk = false;
            Toast.makeText(getActivity(), "Password should be more than " + etInputApnPass.getText().toString().length() + " characters", Toast.LENGTH_LONG).show();
        }
        return isOk;
    }

    private WifiKeyOptions getConfiguration(String apnName, boolean needToHide, int secure, String password) {
        WifiKeyOptions configuration = null;
        switch (secure) {
            case 0: {//Open apn
                configuration = new NoKeyApOptions(apnName, needToHide);
                break;
            }
            case 1: {//WPA secure
                configuration = new WpaAPNKeyOption(apnName, password, needToHide);
                break;
            }
            case 2: {//WPA2 secure
                configuration = new Wpa2APNKeyOption(apnName, password, needToHide);
                break;
            }
        }
        return configuration;
    }

    public void setOnCreateAPNDialogListener(OnAPNDialogListener onCreateAPNDialogListener) {
        this.onCreateAPNDialogListener = onCreateAPNDialogListener;
    }

    public interface OnAPNDialogListener {
        void onGetAPNOConfiguration(WifiKeyOptions configuration);

        void onCancelDialog();
    }

}
