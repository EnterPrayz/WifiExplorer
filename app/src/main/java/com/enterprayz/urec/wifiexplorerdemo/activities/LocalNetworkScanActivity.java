package com.enterprayz.urec.wifiexplorerdemo.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.enterprayz.urec.wifiexplorerdemo.R;
import com.enterprayz.urec.wifiexplorerdemo.fragments.LocalNetViewFragment;

public class LocalNetworkScanActivity extends Activity {

    private LocalNetViewFragment localNetViewFragment;
    private int fragmentCase = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_network_scan);
        iniFragments();
        startFragment(choiseFragment(0));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }

    @Override
    public void onBackPressed() {
        switch (fragmentCase) {
            case 0: {
                boolean needCloseActivity = localNetViewFragment.onBackPressed();
                if (needCloseActivity) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
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
