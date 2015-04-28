package com.enterprayz.urec.wifiexplorerdemo.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.enterprayz.urec.wifiexplorerdemo.R;
import com.enterprayz.urec.wifiexplorerdemo.fragments.ApnViewFragment;
import com.enterprayz.urec.wifiexplorerdemo.fragments.MainFragment;
import com.enterprayz.urec.wifiexplorerdemo.fragments.NetViewFragment;
import com.enterprayz.urec.wifiexplorerdemo.fragments.dialog.CreateAPNDialog;
import com.enterprayz.urec.wifiexplorerdemo.utils.AccelerateOvershootInterpolator;
import com.enterprayz.urec.wifiexplorerlib.core.WifiClientModel;
import com.enterprayz.urec.wifiexplorerlib.enum_state.WIFI_APN_STATE;
import com.enterprayz.urec.wifiexplorerlib.enum_state.WIFI_MODULE_MODE;
import com.enterprayz.urec.wifiexplorerlib.enum_state.WIFI_MODULE_STATE;
import com.enterprayz.urec.wifiexplorerlib.enum_state.WIFI_NET_STATE;
import com.enterprayz.urec.wifiexplorerlib.interfaces.OnAPNCheckListener;
import com.enterprayz.urec.wifiexplorerlib.interfaces.OnAPNUsersChangeStateListener;
import com.enterprayz.urec.wifiexplorerlib.interfaces.OnFinishAPNScanUsersListener;
import com.enterprayz.urec.wifiexplorerlib.interfaces.OnNETCheckListener;
import com.enterprayz.urec.wifiexplorerlib.interfaces.OnWifiModuleCheckListener;
import com.enterprayz.urec.wifiexplorerlib.items.ClientScanResultItem;
import com.enterprayz.urec.wifiexplorerlib.items.WifiScanResultsItem;
import com.enterprayz.urec.wifiexplorerlib.utils.WifiOptions.WifiKeyOptions;

import java.util.ArrayList;


public class ActivityMain extends Activity implements
        OnWifiModuleCheckListener,
        OnNETCheckListener,
        OnAPNUsersChangeStateListener,
        OnFinishAPNScanUsersListener,
        OnAPNCheckListener {

    private Switch swcCheckWifiModule;
    private Switch swcCheckApn;
    private WifiClientModel localWificlient;
    private NetViewFragment netViewFragment;
    private ApnViewFragment apnViewFragment;
    private MainFragment mainFragment;
    private boolean needEnableWifi = false;
    private ImageView ivScanLocalNetwork;


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniWifiClient();
        iniActionBar();
        iniFragments();
        startFragment(choiseFragment(localWificlient.getWifiModuleMode()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        iniWifiClient();
        if (swcCheckWifiModule != null) {
            swcCheckWifiModule.setChecked(localWificlient.isWifiModuleEnabled());
        }
        if (ivScanLocalNetwork != null) {
            if (localWificlient.getNETState().equals(WIFI_NET_STATE.CONNECTED)) {
                ivScanLocalNetwork.setVisibility(View.VISIBLE);
            } else {
                ivScanLocalNetwork.setVisibility(View.GONE);
            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        localWificlient.onDestroy();
        super.onDestroy();
    }



    @Override
    public void onBackPressed() {
        this.finish();
    }

    private void iniWifiClient() {
        localWificlient = WifiClientModel.getInstance();
        localWificlient.setOnNetCheckListener(this);
        localWificlient.setWifiModuleCheckListener(this);
        localWificlient.setAPNUserChangeStateListener(this);
        localWificlient.setOnApnCheckListener(this);
        localWificlient.setOnAPNScanUsersListener(this);
    }

    private Fragment choiseFragment(WIFI_MODULE_MODE mode) {
        Fragment choicenFragment = null;
        switch (mode) {
            case APN_MODE: {
                choicenFragment = apnViewFragment;
                apnViewFragment = (ApnViewFragment) choicenFragment;
                swcCheckApn.setChecked(true);
                swcCheckWifiModule.startAnimation(getSwypeLeftToRightOUTAnimation(swcCheckWifiModule));
                swcCheckWifiModule.setChecked(false);
                break;
            }
            case NET_MODE: {
                choicenFragment = netViewFragment;
                netViewFragment = (NetViewFragment) choicenFragment;
                swcCheckApn.setChecked(false);
                swcCheckApn.startAnimation(getSwypeRightToLeftOUTAnimation(swcCheckApn));
                swcCheckWifiModule.setChecked(true);
                break;
            }
            case OFF_MODE: {
                choicenFragment = mainFragment;
                mainFragment = (MainFragment) choicenFragment;
                swcCheckApn.setChecked(false);
                swcCheckWifiModule.setChecked(false);
                break;
            }
        }
        return choicenFragment;
    }

    private void iniFragments() {
        apnViewFragment = new ApnViewFragment();
        netViewFragment = new NetViewFragment();
        mainFragment = new MainFragment();
    }

    private void startFragment(Fragment fragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    private void iniActionBar() {
        ActionBar ab = getActionBar();
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ab.setIcon(R.color.transparent);
        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.action_bar_top, null);
        swcCheckWifiModule = (Switch) v.findViewById(R.id.swc_check_wifi_module);
        swcCheckWifiModule.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked != localWificlient.isWifiModuleEnabled()) {
                    if (localWificlient.getAPNState().equals(WIFI_APN_STATE.WIFI_AP_STATE_ENABLED)
                            && !localWificlient.isWifiModuleEnabled()) {
                        localWificlient.closeAP();
                        needEnableWifi = true;
                    } else {
                        localWificlient.setWifiModuleEnable(isChecked);
                    }
                }
            }
        });
        swcCheckApn = (Switch) v.findViewById(R.id.swc_check_apn);
        swcCheckApn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (localWificlient.getAPNState().equals(WIFI_APN_STATE.WIFI_AP_STATE_DISABLED)
                        && isChecked) {
                    CreateAPNDialog dialog = new CreateAPNDialog();
                    dialog.setOnCreateAPNDialogListener(new CreateAPNDialog.OnAPNDialogListener() {
                        @Override
                        public void onGetAPNOConfiguration(WifiKeyOptions configuration) {
                            localWificlient.createAp(configuration);
                        }

                        @Override
                        public void onCancelDialog() {
                            if (swcCheckApn.isChecked()) {
                                swcCheckApn.setChecked(false);
                            }
                        }
                    });
                    dialog.show(getFragmentManager(), "CreateAPNDialog");

                } else if ((localWificlient.getAPNState().equals(WIFI_APN_STATE.WIFI_AP_STATE_ENABLED)
                        && !isChecked)) {
                    localWificlient.closeAP();
                }
            }
        });
        ivScanLocalNetwork = (ImageView) v.findViewById(R.id.iv_scan_local_network);
        ivScanLocalNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LocalNetworkScanActivity.class));
            }
        });
        ab.setCustomView(v);
    }

    @Override
    public void onWifiModuleChangeCheck(WIFI_MODULE_STATE state) {
        Log.d(this.getClass().getName(), localWificlient.getWifiModuleMode().name());
        switch (state) {
            case WIFI_MODULE_STATE_DISABLED: {
                swcCheckApn.startAnimation(getSwypeLeftToRightINAnimation(swcCheckApn));
                startFragment(choiseFragment(WIFI_MODULE_MODE.OFF_MODE));
                break;
            }
            case WIFI_MODULE_STATE_ENABLED: {

                startFragment(choiseFragment(WIFI_MODULE_MODE.NET_MODE));
                break;
            }
            case WIFI_MODULE_STATE_DISABLING: {

                break;
            }
            case WIFI_MODULE_STATE_ENABLING: {
                break;
            }
        }
        if (netViewFragment != null) {
            netViewFragment.onWifiModuleChangeCheck(state);
        }
    }

    @Override
    public void onNetCheckChange(WIFI_NET_STATE state) {
        switch (state) {
            case CONNECTED: {
                ivScanLocalNetwork.startAnimation(getSwypeLeftToRightINAnimation(ivScanLocalNetwork));
                break;
            }

            case DISCONNECTED: {
                ivScanLocalNetwork.startAnimation(getSwypeRightToLeftOUTAnimation(ivScanLocalNetwork));
                break;
            }
        }
    }

    @Override
    public void onNetworkScanResult(ArrayList<WifiScanResultsItem> results) {
        if (netViewFragment != null && results != null) {
            netViewFragment.onNetworkScanResult(results);
        }
    }

    @Override
    public void onAPNUsersChangeState(ClientScanResultItem userItem, int flag) {
        if (apnViewFragment != null) {
            apnViewFragment.onAPNUsersChangeState(userItem, flag);
        }
    }

    @Override
    public void onGetAPNUsers(ArrayList<ClientScanResultItem> clients) {
        apnViewFragment.onGetAPNUsers(clients);
    }

    @Override
    public void onAPNChangeCheck(WIFI_APN_STATE state) {
        switch (state) {
            case WIFI_AP_STATE_DISABLED: {
                swcCheckWifiModule.startAnimation(getSwypeRightToLeftINAnimation(swcCheckApn));
                startFragment(choiseFragment(WIFI_MODULE_MODE.OFF_MODE));
                break;
            }
            case WIFI_AP_STATE_ENABLED: {
                startFragment(choiseFragment(WIFI_MODULE_MODE.APN_MODE));
                break;
            }
        }
        if (apnViewFragment != null) {
            apnViewFragment.onAPNChangeCheck(state);
        }
    }

    private Animation getSwypeLeftToRightINAnimation(final View view) {
        Animation animationSwype = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_to_right_in_swype);
        animationSwype.setFillAfter(true);
        AccelerateOvershootInterpolator interpolator = new AccelerateOvershootInterpolator(2.0f, 1.7f);
        animationSwype.setInterpolator(interpolator);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).start();
        return animationSwype;
    }

    private Animation getSwypeRightToLeftOUTAnimation(final View view) {
        Animation animationSwype = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_to_left_swipe_out);
        animationSwype.setFillAfter(false);
        AccelerateOvershootInterpolator interpolator = new AccelerateOvershootInterpolator(2.0f, 1.7f);
        animationSwype.setInterpolator(interpolator);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.setVisibility(View.GONE);
                    }
                });
            }
        }).start();
        return animationSwype;
    }

    private Animation getSwypeRightToLeftINAnimation(final View view) {
        Animation animationSwype = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_to_left_swipe_in);
        animationSwype.setFillAfter(true);
        AccelerateOvershootInterpolator interpolator = new AccelerateOvershootInterpolator(2.0f, 1.7f);
        animationSwype.setInterpolator(interpolator);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).start();
        return animationSwype;
    }

    private Animation getSwypeLeftToRightOUTAnimation(final View view) {
        Animation animationSwype = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_to_right_out_swype);
        animationSwype.setFillAfter(false);
        AccelerateOvershootInterpolator interpolator = new AccelerateOvershootInterpolator(2.0f, 1.7f);
        animationSwype.setInterpolator(interpolator);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();
        return animationSwype;
    }


}