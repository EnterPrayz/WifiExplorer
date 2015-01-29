package com.enterprayz.urec.wifiexplorerdemo.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enterprayz.urec.wifiexplorerdemo.R;
import com.enterprayz.urec.wifiexplorerlib.core.WifiClientModel;

import java.lang.reflect.Field;

/**
 * Created by con on 04.01.15.
 */
public class MainFragment extends Fragment {
    private WifiClientModel localWificlient;


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
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void iniUi() {
    }

    private void iniListeners() {

    }

    private void iniWifiClient() {
        localWificlient = WifiClientModel.getInstance(getActivity());
    }


}
