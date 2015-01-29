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
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;

import com.enterprayz.urec.wifiexplorerdemo.R;

import java.util.ArrayList;

public class NetScannerLayout extends ViewGroup {
    private OnNetScannerLayoutListener listener;
    private ArrayList<Integer> viewHeightAdapter;

    private static int firstItemPositionXbeforeTranslate = 0;
    private static int firstItemPositionYbeforeTranslate = 0;
    private static boolean wasItemDetailTranslate = false;
    private static int itemDetailExpandedId = -1;
    private static boolean mExpanded = false;

    public static final float DEFAULT_FROM_DEGREES = 0.0f;
    public static final float DEFAULT_TO_DEGREES = 360.0f;
    private static final int DEFAULT_DETAIL_ITEM_POSITION_X = 0;
    private static final int DEFAULT_DETAIL_ITEM_POSITION_Y = 0;
    private static final int DEFAULT_CHILD_PADDING = 15;
    private static final int DEFAULT_LAYOUT_PADDING = 0;
    private static final int DEFAULT_ITEM_DETAIL_ZOOM = 2;


    private static final int DEFAULT_CHILD_SIZE = 44;
    private static final int DEFAULT_MIN_RADIUS = DEFAULT_CHILD_SIZE * 2;

    private float mFromDegrees;
    private float mToDegrees;
    private int mChildSize;
    private int mMinRadius;
    private int mDetailItemPositionX;
    private int mDetailItemPositionY;
    private int mChildPadding;
    private int mLayoutPadding;
    private int mZoomIn;
    /* the distance between the layout's center and any child's center */
    private int mMaxRadius;


    public NetScannerLayout(Context context) {
        super(context);
    }

    public NetScannerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.NetScannerLayout, 0, 0);
            mFromDegrees = a.getFloat(R.styleable.NetScannerLayout_fromDegrees, DEFAULT_FROM_DEGREES);
            mToDegrees = a.getFloat(R.styleable.NetScannerLayout_toDegrees, DEFAULT_TO_DEGREES);
            mChildSize = a.getDimensionPixelSize(R.styleable.NetScannerLayout_childSize, DEFAULT_CHILD_SIZE) / 2 * 2;
            mMinRadius = Math.max(a.getDimensionPixelSize(R.styleable.NetScannerLayout_minRadius, DEFAULT_MIN_RADIUS), mChildSize * 2);
            mDetailItemPositionX = a.getDimensionPixelSize(R.styleable.NetScannerLayout_detailItemPositionX, DEFAULT_DETAIL_ITEM_POSITION_X);
            mDetailItemPositionY = a.getDimensionPixelSize(R.styleable.NetScannerLayout_detailItemPositionY, DEFAULT_DETAIL_ITEM_POSITION_Y);
            mChildPadding = a.getDimensionPixelSize(R.styleable.NetScannerLayout_childPadding, DEFAULT_CHILD_PADDING);
            mLayoutPadding = a.getDimensionPixelSize(R.styleable.NetScannerLayout_layoutPadding, DEFAULT_LAYOUT_PADDING);
            mZoomIn = a.getInteger(R.styleable.NetScannerLayout_detailItemZoomValue, DEFAULT_ITEM_DETAIL_ZOOM);
            viewHeightAdapter = new ArrayList<>();
            a.recycle();
        }
    }

    private static int computeRadius(final float arcDegrees, final int childCount, final int childSize,
                                     final int childPadding, final int minRadius) {

        if (childCount < 2) {
            return minRadius;
        }

        final float perDegrees = arcDegrees / (childCount);
        final float perHalfDegrees = perDegrees / 2;
        final int perSize = childSize + childPadding;

        final int radius = (int) ((perSize / 2) / Math.sin(Math.toRadians(perHalfDegrees)));

        return Math.max(radius, minRadius);
    }

    public void addViewHeight(int percentage) {
        viewHeightAdapter.add(percentage);
    }

    private static Rect computeExpandChildFrame(final int centerX, final int centerY, final int radius, final float degrees,
                                                final int size) {

        final double childCenterX = centerX + radius * Math.cos(Math.toRadians(degrees));
        final double childCenterY = centerY + radius * Math.sin(Math.toRadians(degrees));

        return new Rect((int) (childCenterX - size / 2), (int) (childCenterY - size / 2),
                (int) (childCenterX + size / 2), (int) (childCenterY + size / 2));
    }

    private Rect computeExpandDetailChildFrame(final int[] newStartXY) {
        int childStartX;
        int childStartY;
        Rect rect;
        if (mExpanded) {
            childStartX = wasItemDetailTranslate ? newStartXY[0] : firstItemPositionXbeforeTranslate;
            childStartY = wasItemDetailTranslate ? newStartXY[1] : firstItemPositionYbeforeTranslate;
            rect = new Rect(
                    childStartX,
                    childStartY,
                    childStartX + (mChildSize * mZoomIn),
                    childStartY + (mChildSize * mZoomIn));
        } else {
            childStartX = firstItemPositionXbeforeTranslate;
            childStartY = firstItemPositionYbeforeTranslate;
            itemDetailExpandedId = -1;
            rect = new Rect(
                    childStartX,
                    childStartY,
                    childStartX + mChildSize,
                    childStartY + mChildSize);
        }
        return rect;
    }

    private float computeZoomOut(int zoomIn) {
        int defaultVal = 1;
        return defaultVal / zoomIn;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mSize = 0;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        if (width > height) {
            mSize = height;
        } else {
            mSize = width;
        }

        mMaxRadius = (mSize / 2) - (mChildSize / 2) - mChildPadding - mLayoutPadding - mMinRadius;


        setMeasuredDimension(mSize, mSize);

        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            getChildAt(i).measure(MeasureSpec.makeMeasureSpec(mChildSize, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(mChildSize, MeasureSpec.EXACTLY));
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int centerX = getWidth() / 2;
        final int centerY = getHeight() / 2;


        final int childCount = getChildCount();
        final float perDegrees = (mToDegrees - mFromDegrees) / childCount;//72 if count 5

        float degrees = mFromDegrees;
        for (int i = 0; i < childCount; i++) {
            int heigthPercentage = viewHeightAdapter.get(i);
            float cof = heigthPercentage / 100f;
            float dim = (float) mMaxRadius - (mMaxRadius * cof);
            int itemRadius = mExpanded ? (int) dim + mMinRadius : 0;
            Rect frame;
            frame = computeExpandChildFrame(centerX, centerY, itemRadius, degrees, mChildSize);

            if (wasItemDetailTranslate) {
                if (i == itemDetailExpandedId) {
                    int[] coordinates = {mDetailItemPositionX, mDetailItemPositionY};
                    frame = computeExpandDetailChildFrame(coordinates);
                } else {
                    final int radius = getWidth() > getHeight() ? getWidth() : getHeight();
                    frame = computeExpandChildFrame(centerX, centerY, radius, mFromDegrees + i * perDegrees, mChildSize);
                    frame.right = 0;
                    frame.bottom = 0;
                }
            }

            degrees += perDegrees;

            getChildAt(i).layout(frame.left, frame.top, frame.right, frame.bottom);
        }


    }


    private static long computeStartOffset(final int childCount, final boolean expanded, final int index,
                                           final float delayPercent, final long duration, Interpolator interpolator) {
        final float delay = delayPercent * duration;
        final long viewDelay = (long) (getTransformedIndex(expanded, childCount, index) * delay);
        final float totalDelay = delay * childCount;

        float normalizedDelay = viewDelay / totalDelay;
        normalizedDelay = interpolator.getInterpolation(normalizedDelay);

        return (long) (normalizedDelay * totalDelay);
    }

    private static int getTransformedIndex(final boolean expanded, final int count, final int index) {
        if (expanded) {
            return count - 1 - index;
        }
        return index;
    }

    //open anim
    private static Animation createExpandAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta,
                                                   long startOffset, long duration, Interpolator interpolator) {
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.setFillAfter(true);

        final long preDuration = duration / 2;

        Animation translateAnimation = new TranslateAnimation(0, toXDelta, 0, toYDelta, 720, 360);
        translateAnimation.setStartOffset(startOffset + preDuration);
        translateAnimation.setDuration(duration - preDuration);
        translateAnimation.setInterpolator(interpolator);
        translateAnimation.setFillAfter(true);

        animationSet.addAnimation(translateAnimation);

        return animationSet;
    }

    //close anim
    private static Animation createShrinkAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta,
                                                   long startOffset, long duration, Interpolator interpolator) {
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.setFillAfter(true);

        final long preDuration = duration / 2;

        Animation translateAnimation = new TranslateAnimation(0, toXDelta, 0, toYDelta, 360, 720);
        translateAnimation.setStartOffset(startOffset + preDuration);
        translateAnimation.setDuration(duration - preDuration);
        translateAnimation.setInterpolator(interpolator);
        translateAnimation.setFillAfter(true);

        animationSet.addAnimation(translateAnimation);

        return animationSet;
    }

    private Animation createBezierTranslateAnimation(final View view, boolean wasItemDetailTranslate, final int index) {

        BezierTranslateAnimation anim;
        if (!wasItemDetailTranslate) {
            firstItemPositionXbeforeTranslate = view.getLeft();
            firstItemPositionYbeforeTranslate = view.getTop();
            anim = new BezierTranslateAnimation(
                    0, 0 - firstItemPositionXbeforeTranslate + ((mChildSize) / 2) + mDetailItemPositionX,
                    0, 0 - firstItemPositionYbeforeTranslate + ((mChildSize) / 2) + mDetailItemPositionY,
                    0, -200);
        } else {
            if (firstItemPositionYbeforeTranslate < mDetailItemPositionY) {
                anim = new BezierTranslateAnimation(
                        0, firstItemPositionXbeforeTranslate,
                        0, firstItemPositionYbeforeTranslate,
                        0, 200);
            } else {
                anim = new BezierTranslateAnimation(
                        0, firstItemPositionXbeforeTranslate - (mChildSize / 2) - mDetailItemPositionX,
                        0, firstItemPositionYbeforeTranslate - (mChildSize / 2) - mDetailItemPositionY,
                        0, -200);
            }
        }
        anim.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (listener != null) {
                    listener.onItemDetailStartTranslate(isWasItemDetailTranslate(), view, index);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (listener != null) {
                    listener.onItemDetailEndTranslate(isWasItemDetailTranslate(), view, index);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        anim.setFillAfter(true);
        return anim;
    }

    public void createAlphaScaleTranslateAnimation(View view, int index, boolean wasItemDetailTranslate, boolean isClicked, float degrees, AnimationSet animationColection) {
        Animation alphaScaleAnimation;
        Animation translateAnimation = null;
        if (!wasItemDetailTranslate) {
            if (isClicked) {
                alphaScaleAnimation = new ScaleAnimation(1.0f, (float) mZoomIn, 1.0f, (float) mZoomIn,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            } else {
                alphaScaleAnimation = new AlphaAnimation(1, 0);

                final int centerX = getWidth() / 2;
                final int centerY = getHeight() / 2;
                final int radius = getWidth() > getHeight() ? getWidth() : getHeight();
                final int duration = 100;
                final int childCount = getChildCount();
                final float perDegrees = (mToDegrees - mFromDegrees) / childCount;
                Rect frame = computeExpandChildFrame(centerX, centerY, radius, mFromDegrees + index * perDegrees, mChildSize);
                final int toXDelta = frame.left - view.getLeft();
                final int toYDelta = frame.top - view.getTop();
                Interpolator interpolator = new AccelerateInterpolator(1.0f);

                translateAnimation = createExpandAnimation(0, toXDelta, 0, toYDelta, 1, duration, interpolator);
            }
            alphaScaleAnimation.setInterpolator(new DecelerateInterpolator(0.5f));
        } else {
            if (isClicked) {
                alphaScaleAnimation = new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            } else {
                alphaScaleAnimation = new AlphaAnimation(0, 1);
                alphaScaleAnimation.setFillBefore(true);

                int heigthPercentage = viewHeightAdapter.get(index);
                float cof = heigthPercentage / 100f;
                float dim = (float) mMaxRadius - ((float) mMaxRadius * cof);
                int itemRadius = mExpanded ? (int) dim + mMinRadius : 0;

                final int centerX = getWidth() / 2;
                final int centerY = getHeight() / 2;

                Rect frame = computeExpandChildFrame(centerX, centerY, itemRadius, degrees, mChildSize);
                view.layout(frame.left, frame.top, frame.right, frame.bottom);
            }
            alphaScaleAnimation.setInterpolator(new AccelerateInterpolator(0.5f));
        }
        alphaScaleAnimation.setFillAfter(true);
        animationColection.addAnimation(alphaScaleAnimation);
        if (translateAnimation != null) {
            animationColection.addAnimation(translateAnimation);
        }
    }

    private void bindChildAnimation(final View child, int radius, final int index, final long duration) {
        final boolean expanded = mExpanded;
        final int centerX = getWidth() / 2;
        final int centerY = getHeight() / 2;

        final int childCount = getChildCount();
        final float perDegrees = (mToDegrees - mFromDegrees) / childCount;
        Rect frame = computeExpandChildFrame(centerX, centerY, radius, mFromDegrees + index * perDegrees, mChildSize);

        final int toXDelta = frame.left - child.getLeft();
        final int toYDelta = frame.top - child.getTop();

        Interpolator interpolator = mExpanded ? new AccelerateInterpolator() : new AccelerateInterpolator(0.5f);
        final long startOffset = computeStartOffset(childCount, mExpanded, index, 0.1f, duration, interpolator);

        Animation animation = mExpanded ? createShrinkAnimation(0, toXDelta, 0, toYDelta, startOffset, duration,
                interpolator) : createExpandAnimation(0, toXDelta, 0, toYDelta, startOffset, duration, interpolator);

        final boolean isLast = getTransformedIndex(expanded, childCount, index) == childCount - 1;
        animation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isLast) {
                    postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            onAllAnimationsEnd();
                        }
                    }, 0);
                    if (listener != null) {
                        listener.onAnimationEndExpand(isExpanded());
                    }
                }
            }
        });

        child.setAnimation(animation);
    }

    private void bindDetailChildAnimation(final View child, boolean isClick, long duration, int index, float degrees) {
        final int childCount = getChildCount();

        AnimationSet animationCollection = new AnimationSet(false);
        createAlphaScaleTranslateAnimation(child, index, wasItemDetailTranslate, isClick, degrees, animationCollection);
        if (isClick) {
            Animation bezierTranslateAnimation = createBezierTranslateAnimation(child, wasItemDetailTranslate, index);
            animationCollection.addAnimation(bezierTranslateAnimation);
        }
        final boolean isLast = getTransformedIndex(wasItemDetailTranslate, childCount, index) == childCount - 1;
        animationCollection.setFillAfter(true);
        animationCollection.setDuration(duration);
        animationCollection.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isLast) {
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onAllAnimationsEnd();
                        }
                    }, 0);

                }
            }
        });

        child.setAnimation(animationCollection);

    }

    public boolean isExpanded() {
        return mExpanded;
    }

    public int getmChildSize() {
        return mChildSize;
    }

    public boolean isWasItemDetailTranslate() {
        return wasItemDetailTranslate;
    }

    public void switchExpandState(final boolean showAnimation) {
        if (showAnimation) {
            final int childCount = getChildCount();
            if (listener != null) {
                listener.onAnimationStartExpand(isExpanded());
            }
            for (int i = 0; i < childCount; i++) {
                int heigthPercentage = viewHeightAdapter.get(i);
                float cof = heigthPercentage / 100f;
                float dim = (float) mMaxRadius - ((float) mMaxRadius * cof);
                int itemRadius = mExpanded ? 0 : (int) dim + mMinRadius;
                bindChildAnimation(getChildAt(i), itemRadius, i, 300);
            }
        }

        mExpanded = !mExpanded;

        if (wasItemDetailTranslate) {
            wasItemDetailTranslate = false;
        }

        if (!showAnimation) {
            requestLayout();
        }

        invalidate();
    }

    public void switchItemDetailState(View view, final boolean showAnimation) {
        if (showAnimation) {
            final int childCount = getChildCount();
            final float perDegrees = (mToDegrees - mFromDegrees) / childCount;//72 if count 5
            float degrees = mFromDegrees;
            for (int i = 0; i < childCount; i++) {
                boolean isClickView = view == getChildAt(i);
                if (isClickView) {
                    itemDetailExpandedId = i;
                }
                bindDetailChildAnimation(getChildAt(i), isClickView, 600, i, degrees);
                degrees += perDegrees;
            }
        }

        wasItemDetailTranslate = !wasItemDetailTranslate;


        if (!showAnimation) {
            requestLayout();
        }

        invalidate();
    }

    public void onAllAnimationsEnd() {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).clearAnimation();
        }

        requestLayout();
    }

    public void setListener(OnNetScannerLayoutListener listener) {
        this.listener = listener;
    }

    public interface OnNetScannerLayoutListener {
        public void onAnimationStartExpand(boolean listExpandState);
        public void onAnimationEndExpand(boolean listExpandState);

        public void onItemDetailStartTranslate(boolean state, View view, int position);
        public void onItemDetailEndTranslate(boolean state, View view, int position);
    }

}
