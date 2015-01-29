package com.enterprayz.urec.wifiexplorerdemo.LocalNetScannerView;

import android.view.animation.*;

/**
 * Created by con on 13.01.15.
 */
public class BezierTranslateAnimation extends android.view.animation.TranslateAnimation {

    private int mFromXType    = ABSOLUTE;
    private int mToXType      = ABSOLUTE;
    private int mFromYType    = ABSOLUTE;
    private int mToYType      = ABSOLUTE;
    private float mFromXValue = 0.0f;
    private float mToXValue   = 0.0f;
    private float mFromYValue = 0.0f;
    private float mToYValue   = 0.0f;
    private float mFromXDelta;
    private float mToXDelta;
    private float mFromYDelta;
    private float mToYDelta;
    private float mBezierXDelta;
    private float mBezierYDelta;

    public BezierTranslateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta, float bezierXDelta, float bezierYDelta) {
        super(fromXDelta, toXDelta, fromYDelta, toYDelta);

        mFromXValue = fromXDelta;
        mToXValue   = toXDelta;
        mFromYValue = fromYDelta;
        mToYValue   = toYDelta;
        mFromXType  = ABSOLUTE;
        mToXType    = ABSOLUTE;
        mFromYType  = ABSOLUTE;
        mToYType    = ABSOLUTE;
        mBezierXDelta = bezierXDelta;
        mBezierYDelta = bezierYDelta;

    }



    @Override
    protected void  applyTransformation(float interpolatedTime, Transformation t) {

        float dx=0, dy=0;

        if (mFromXValue != mToXValue) {

            dx  = (float) ((1.0-interpolatedTime)*(1.0-interpolatedTime)*mFromXValue + 2.0*interpolatedTime*(1.0-interpolatedTime)*mBezierXDelta + interpolatedTime*interpolatedTime*mToXValue);
        }

        if (mFromYValue != mToYValue) {

            dy  = (float) ((1.0-interpolatedTime)*(1.0-interpolatedTime)*mFromYValue + 2.0*interpolatedTime*(1.0-interpolatedTime)*mBezierYDelta + interpolatedTime*interpolatedTime*mToYValue);
        }

        t.getMatrix().setTranslate(dx, dy);

    }

}