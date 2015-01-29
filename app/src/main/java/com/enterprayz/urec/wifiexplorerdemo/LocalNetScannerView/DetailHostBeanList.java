package com.enterprayz.urec.wifiexplorerdemo.LocalNetScannerView;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.*;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.enterprayz.urec.wifiexplorerdemo.R;
import com.enterprayz.urec.wifiexplorerdemo.utils.AccelerateOvershootInterpolator;
import com.enterprayz.urec.wifiexplorerlib.database.DbActionsHelper;
import com.enterprayz.urec.wifiexplorerlib.items.HostBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by con on 19.01.15.
 */
public class DetailHostBeanList extends RelativeLayout {
    private TextView tvVendorName;
    private TextView tvIpAddress;
    private TextView tvMacAddress;
    private TextView tvPing;
    private WaveDrawable waveDrawable;
    private LinearLayout llMainContainer;
    private LinearLayout llIpAddressContainer;
    private LinearLayout llMacAddressContainer;
    private LinearLayout llPingContainer;
    private ListView lvOpenedPorts;
    private SimpleAdapter adapter;
    private LinearLayout llPortsContainer;
    private TextView tvIndicatorOpenPorts;

    public DetailHostBeanList(Context context) {
        super(context);
        init(context);
    }

    public DetailHostBeanList(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater rootView = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView.inflate(R.layout.item_detai_host_bean_view, this);
        iniUi();
    }

    private void iniUi() {
        llMainContainer = (LinearLayout) findViewById(R.id.ll_main_container_detail_hostbean_view);
        tvVendorName = (TextView) findViewById(R.id.tv_vendor_name_detail_hostbean_view);
        tvIpAddress = (TextView) findViewById(R.id.tv_ip_address_detail_hostbean_view);
        tvMacAddress = (TextView) findViewById(R.id.tv_mac_address_detail_hostbean_view);
        tvPing = (TextView) findViewById(R.id.tv_ping_detail_hostbean_view);
        lvOpenedPorts = (ListView) findViewById(R.id.lv_opened_ports_detail_hostbean_view);
        llIpAddressContainer = (LinearLayout) findViewById(R.id.ll_ip_address_container_detail_hostbean_view);
        llMacAddressContainer = (LinearLayout) findViewById(R.id.ll_mac_address_container_detail_hostbean_view);
        llPingContainer = (LinearLayout) findViewById(R.id.ll_ping_container_detail_hostbean_view);
        llPortsContainer = (LinearLayout) findViewById(R.id.ll_ports_container_detail_hostbean_view);
        tvIndicatorOpenPorts = (TextView) findViewById(R.id.tv_indicator_open_ports_detail_hostbean_view);
    }

    public void iniAnimation(int[] startXYAnimation, int radius) {
        waveDrawable = new WaveDrawable(Color.parseColor("#20000000"),
                radius,
                600,
                startXYAnimation);
        llMainContainer.setBackgroundDrawable(waveDrawable);
        Interpolator interpolator = new AccelerateOvershootInterpolator(1.5f, 0.5f);
        waveDrawable.setWaveInterpolator(interpolator);
        waveDrawable.startAnimation(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                tvVendorName.startAnimation(getAlphaTranslateAnimation(1));
                llIpAddressContainer.startAnimation(getAlphaTranslateAnimation(2));
                llMacAddressContainer.startAnimation(getAlphaTranslateAnimation(3));
                llPingContainer.startAnimation(getAlphaTranslateAnimation(4));
                llPortsContainer.startAnimation(getAlphaTranslateAnimation(5));
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void setHostBeanItem(HostBean hostBean) {
        tvVendorName.setText(hostBean.nicVendor != null ? hostBean.nicVendor : "Unknown");
        tvIpAddress.setText(hostBean.ipAddress);
        tvMacAddress.setText(hostBean.hardwareAddress);
        tvPing.setText(hostBean.responseTime != 800 ? hostBean.responseTime + "ms" : "Unknown");

        ArrayList<Map<String, String>> portsList = new ArrayList<>();
        for (int item : hostBean.portsOpen) {
            Map<String, String> map = new HashMap<>();
            map.put("port", String.valueOf(item));

            String[] portData = DbActionsHelper.getPortDestination(item, getContext());
            map.put("destination", portData[0]);
            map.put("status", portData[1]);
            portsList.add(map);
        }

        if (portsList.size() < 1) {
            tvIndicatorOpenPorts.setVisibility(GONE);
        }else {
            tvIndicatorOpenPorts.setVisibility(VISIBLE);
        }

        String[] from = {"port", "destination", "status"};
        int[] to = {R.id.tv_item_title_open_ports_lists,
                R.id.tv_item_sub_title_open_ports_lists,
                R.id.tv_item_status_title_open_ports_lists
        };

        adapter = new SimpleAdapter(
                getContext(),
                portsList,
                R.layout.item_open_ports_lists,
                from,
                to
        );

        lvOpenedPorts.setAdapter(adapter);
    }

    private Animation getAlphaTranslateAnimation(int index) {
        AnimationSet animationCollection = new AnimationSet(true);
        AlphaAnimation alpha = new AlphaAnimation(0.0f, 1.0f);
        android.view.animation.TranslateAnimation translateAnimation = new TranslateAnimation(12.0f, 0.0f, 0.0f, 0.0f);
        translateAnimation.setInterpolator(getInterpolator());
        animationCollection.addAnimation(alpha);
        animationCollection.addAnimation(translateAnimation);
        animationCollection.setStartOffset(index * 100);
        animationCollection.setDuration(300);
        return animationCollection;
    }

    private Interpolator getInterpolator() {
        return new DecelerateInterpolator(0.5f);
    }

}
