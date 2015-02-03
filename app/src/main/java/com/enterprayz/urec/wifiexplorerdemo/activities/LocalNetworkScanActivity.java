package com.enterprayz.urec.wifiexplorerdemo.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.enterprayz.urec.wifiexplorerdemo.R;
import com.enterprayz.urec.wifiexplorerdemo.fragments.LocalNetViewFragment;
import com.enterprayz.urec.wifiexplorerdemo.fragments.dialog.CreateAPNDialog;
import com.enterprayz.urec.wifiexplorerlib.enum_state.WIFI_APN_STATE;
import com.enterprayz.urec.wifiexplorerlib.utils.WifiOptions.WifiKeyOptions;

public class LocalNetworkScanActivity extends Activity {

    private LocalNetViewFragment localNetViewFragment;
    private int fragmentCase = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_network_scan);
        iniFragments();
        iniActionBar();
        startFragment(choiseFragment(0));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                close();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        close();
    }

    public void close() {
        switch (fragmentCase) {
            case 0: {
                boolean needCloseActivity = localNetViewFragment.onBackPressed();
                if (needCloseActivity) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(500);
                                finish();

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                break;
            }
        }
    }

    private void iniActionBar() {
        ActionBar ab = getActionBar();
        ab.setIcon(R.drawable.ic_launcher);
        ab.setDisplayHomeAsUpEnabled(true);


    }

    private void iniFragments() {
        localNetViewFragment = new LocalNetViewFragment();
    }

    private Fragment choiseFragment(int mode) {
        Fragment choicenFragment = null;
        switch (mode) {
            case 0: {
                choicenFragment = localNetViewFragment;
                fragmentCase = 0;
            }
        }
        return choicenFragment;
    }

    private void startFragment(Fragment fragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }
}
