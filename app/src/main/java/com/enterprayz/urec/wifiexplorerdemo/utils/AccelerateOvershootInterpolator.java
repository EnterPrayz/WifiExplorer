package com.enterprayz.urec.wifiexplorerdemo.utils;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by ura on 23.11.14.
 */
public class AccelerateOvershootInterpolator implements Interpolator
{
    private AccelerateInterpolator accelerate;
    private OvershootInterpolator overshoot;

    public AccelerateOvershootInterpolator(float factor, float tension)
    {
        accelerate = new AccelerateInterpolator(factor);
        overshoot = new OvershootInterpolator(tension);
    }

    @Override
    public float getInterpolation(float input)
    {
        return overshoot.getInterpolation(accelerate.getInterpolation(input));
    }

}