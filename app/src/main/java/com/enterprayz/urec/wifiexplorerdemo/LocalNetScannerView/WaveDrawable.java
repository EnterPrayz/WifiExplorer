package com.enterprayz.urec.wifiexplorerdemo.LocalNetScannerView;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.Interpolator;

public class WaveDrawable extends Drawable {

    private Paint wavePaint;
    private int color;
    private int radius;
    private long animationTime = 2000;
    private int startX;
    private int startY;


    protected float waveScale;
    protected int alpha;


    private Interpolator waveInterpolator;
    private Interpolator alphaInterpolator;

    private Animator animator;
    private AnimatorSet animatorSet;
    private Animator.AnimatorListener listener;

    /**
     * @param color         color
     * @param radius        radius
     * @param animationTime time
     */
    public WaveDrawable(int color, int radius, long animationTime, int[] startXY) {
        this(color, radius);
        this.animationTime = animationTime;
        this.startX = startXY[0];
        this.startY = startXY[1];
    }

    /**
     * @param color  colro
     * @param radius radius
     */
    public WaveDrawable(int color, int radius) {
        this.color = color;
        this.radius = radius;
        this.waveScale = 0f;
        this.alpha = 50;

        wavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        animatorSet = new AnimatorSet();

    }

    @Override
    public void draw(Canvas canvas) {

        final Rect bounds = getBounds();

        // circle
        wavePaint.setStyle(Paint.Style.FILL);
        wavePaint.setColor(color);
        wavePaint.setAlpha(alpha);
        canvas.drawCircle(startX, startY, radius * waveScale, wavePaint);

    }

    /**
     * @param interpolator interpolator
     */
    public void setWaveInterpolator(Interpolator interpolator) {
        this.waveInterpolator = interpolator;
    }

    /**
     * @param interpolator interpolator
     */
    public void setAlphaInterpolator(Interpolator interpolator) {
        this.alphaInterpolator = interpolator;
    }


    public void startAnimation(Animator.AnimatorListener listener) {
        animator = generateAnimation();
        animator.addListener(listener);
        animator.start();
    }

    public void stopAnimation() {
        if (animator.isRunning()) {
            animator.end();
        }
    }

    public void setListener(Animator.AnimatorListener listener) {
        this.listener = listener;
    }

    public boolean isAnimationRunning() {
        if (animator != null) {
            return animator.isRunning();
        }
        return false;
    }

    @Override
    public void setAlpha(int alpha) {
        this.alpha = alpha;
        invalidateSelf();
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        wavePaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return wavePaint.getAlpha();
    }


    protected void setWaveScale(float waveScale) {
        this.waveScale = waveScale;
        invalidateSelf();
    }

    protected float getWaveScale() {
        return waveScale;
    }


    private Animator generateAnimation() {

        //Wave animation

        ObjectAnimator waveAnimator = ObjectAnimator.ofFloat(this, "waveScale", 0f, 1f);
        waveAnimator.setDuration(animationTime);
        if (waveInterpolator != null) {
            waveAnimator.setInterpolator(waveInterpolator);
        }

        animatorSet.playTogether(waveAnimator);

        return animatorSet;
    }
}
