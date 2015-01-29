/*
 * Copyright (C) 2012 Capricorn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.enterprayz.urec.wifiexplorerdemo.LocalNetScannerView;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.*;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.enterprayz.urec.wifiexplorerdemo.R;
import com.enterprayz.urec.wifiexplorerdemo.utils.AccelerateOvershootInterpolator;


public class NetScannerList extends RelativeLayout {
    private NetScannerLayout mNetScannerLayout;

    private ImageView mHintView;
    private ViewGroup controlLayout;
    private OnNetScannerListListener onListListener;

    private View expandedView;
    private OnClickListener expandedListener;

    private boolean isTranslateViewBlock = false;

    public NetScannerList(Context context) {
        super(context);
        init(context);
    }

    public NetScannerList(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        final Rect frame = computareControlFrame(mNetScannerLayout.isWasItemDetailTranslate(), controlLayout);
        controlLayout.layout(frame.left, frame.top, frame.right, frame.bottom);
    }


    private void init(Context context) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        li.inflate(R.layout.item_net_scanner_view, this);
        mNetScannerLayout = (NetScannerLayout) findViewById(R.id.net_scanner_view_item);
        mNetScannerLayout.setListener(new NetScannerLayout.OnNetScannerLayoutListener() {
            @Override
            public void onAnimationStartExpand(boolean listExpandState) {

            }

            @Override
            public void onAnimationEndExpand(boolean listExpandState) {
                if (onListListener != null) {
                    onListListener.onListBind(listExpandState);
                }
            }

            @Override
            public void onItemDetailStartTranslate(boolean state, View view, int position) {
                isTranslateViewBlock = true;
            }

            @Override
            public void onItemDetailEndTranslate(boolean state, View view, int position) {
                isTranslateViewBlock = false;
                if (onListListener != null) {
                    onListListener.onItemDetailBind(state, view, position);
                }
            }
        });
        controlLayout = (ViewGroup) findViewById(R.id.fl_control_layout);
        controlLayout.setClickable(true);
        controlLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //      changeExpandState();
                }
                return false;
            }
        });

        mHintView = (ImageView) findViewById(R.id.control_hint);
    }

    public void changeExpandState() {
        mHintView.startAnimation(createHintSwitchAnimation(mNetScannerLayout.isExpanded(), 1000));
        mNetScannerLayout.switchExpandState(true);
    }

    public void addItem(View item, int itemHeigtPercentage, OnClickListener listener) {
        mNetScannerLayout.addView(item);
        mNetScannerLayout.addViewHeight(itemHeigtPercentage);
        item.setOnClickListener(getItemClickListener(listener));
    }

    //On Item click
    private OnClickListener getItemClickListener(final OnClickListener onClickListener) {
        return new OnClickListener() {
            @Override
            public void onClick(final View viewClicked) {
                if (!isTranslateViewBlock) {
                    expandedListener = onClickListener;
                    expandedView = viewClicked;
                    changeTranslateState();

                }
            }
        };
    }

    public void changeTranslateState() {
        mNetScannerLayout.switchItemDetailState(expandedView, true);
        controlLayout.startAnimation(createControlSwitchAnimation(mNetScannerLayout.isWasItemDetailTranslate(), 1000, controlLayout));
        if (expandedListener != null) {
            expandedListener.onClick(expandedView);
        }
    }


    private static Animation createHintSwitchAnimation(final boolean expanded, int duration) {
        Animation animation = new RotateAnimation(expanded ? 45 : 0, expanded ? 0 : 45, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setStartOffset(0);
        animation.setDuration(duration);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setFillAfter(true);

        return animation;
    }

    private Animation createControlSwitchAnimation(final boolean expanded, int duration, final View view) {
        Animation alphaAnim;
        if (expanded) {
            alphaAnim = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out);
        } else {
            alphaAnim = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);
        }
        alphaAnim.setFillAfter(true);
        return alphaAnim;
    }

    private Rect computareControlFrame(final boolean wasItemExpand, final View view) {
        return new Rect(
                wasItemExpand ? view.getLeft() - (view.getWidth() * 2) : view.getLeft(),
                wasItemExpand ? view.getTop() - (view.getHeight() * 2) : view.getTop(),
                wasItemExpand ? 0 : view.getLeft() + view.getWidth(),
                wasItemExpand ? 0 : view.getTop() + view.getHeight());
    }

    public void setOnListListener(OnNetScannerListListener onListListener) {
        this.onListListener = onListListener;
    }

    public boolean isExpanded() {
        return mNetScannerLayout.isExpanded();
    }

    public boolean isTranslated() {
        return mNetScannerLayout.isWasItemDetailTranslate();
    }

    public interface OnNetScannerListListener {
        public void onListBind(boolean listExpandState);

        public void onItemDetailBind(boolean itemExpandState, View view, int position);
    }
}
