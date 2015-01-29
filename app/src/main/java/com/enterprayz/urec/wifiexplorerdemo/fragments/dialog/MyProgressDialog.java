package com.enterprayz.urec.wifiexplorerdemo.fragments.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;

import com.enterprayz.urec.wifiexplorerdemo.R;

/**
 * Created by con on 12.01.15.
 */
public class MyProgressDialog extends DialogFragment {
    private View rootView;
    private Button btnCancelScan;
    private ProgressBar progressBar;
    private int lastProgress = -1;
    private OnProgressDialogListener listener;

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
        rootView = inflater.inflate(R.layout.dialg_progress, null);
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
        btnCancelScan = (Button) rootView.findViewById(R.id.btn_cancel_scan);
        progressBar = (ProgressBar) rootView.findViewById(R.id.pb_on_load);
        progressBar.setMax(100);
    }

    private void iniListeners() {

        btnCancelScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCancel();
                getDialog().dismiss();
            }
        });
    }

    public void setProgressBarProgress(int progress) {
        if (progress != lastProgress) {
            lastProgress = progress;
            progressBar.setProgress(progress);
        }
    }

    public void setListener(OnProgressDialogListener listener) {
        this.listener = listener;
    }

    public interface OnProgressDialogListener{
        public void onCancel();
    }

}