package com.jielkko.lphotopreview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;


/**
 * Created by Administrator on 2017/3/15.
 */

public class SlideCloseTouTiaoLayout extends FrameLayout {
    private static final String TAG = "SlideCloseTouTiaoLayout";
    private enum Direction {
        UP_DOWN,
        LEFT_RIGHT,
        NONE
    }

    private Direction direction = Direction.NONE;
    private int previousY;
    private int previousX;
    private boolean isScrollingUp;
    private boolean isLocked = false;
    private LayoutScrollListener mScrollListener;
    private Drawable mBackground;

    //触碰点数量
    private int mode = 0;

    private View mChildView;

    /**
     * 设置拖拽关闭的view
     *
     * @param childV
     */
    public void setImageViews(View childV) {
        this.mChildView = childV;
    }

    public SlideCloseTouTiaoLayout(Context context) {
        this(context, null);
    }

    public SlideCloseTouTiaoLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideCloseTouTiaoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLayoutScrollListener(LayoutScrollListener listener) {
        mScrollListener = listener;
    }

    /**
     * 设置渐变的背景
     *
     * @param drawable
     */
    public void setGradualBackground(Drawable drawable) {
        this.mBackground = drawable;
    }

    public void lock() {
        isLocked = true;
    }

    public void unLock() {
        isLocked = false;
    }


    /**
     * 当垂直滑动时，拦截子view的滑动事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isLocked) {
            return false;
        } else {
            final int y = (int) ev.getRawY();
            final int x = (int) ev.getRawX();
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    previousX = x;
                    previousY = y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    int diffY = y - previousY;
                    int diffX = x - previousX;

                    if (Math.abs(diffX) + 50 < Math.abs(diffY)) {
                        return true;
                    }
                    break;
            }
            return false;
        }

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent ev) {
        if (!isLocked) {
            final int y = (int) ev.getRawY();
            final int x = (int) ev.getRawX();
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Log.d(TAG, "MotionEvent.ACTION_DOWN");
                    mode = 1;
                    previousX = x;
                    previousY = y;
                    break;

                case MotionEvent.ACTION_POINTER_DOWN:
                    Log.d(TAG, "MotionEvent.ACTION_POINTER_DOWN");
                    //当屏幕上已经有一个点被按住，此时再按下其他点时触发
                    mode += 1;
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    Log.d(TAG, "MotionEvent.ACTION_POINTER_UP");
                    //当屏幕上有多个点被按住，松开其中一个点时触发（即非最后一个点被放开时）。
                    mode -= 1;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mode > 1) {
                        return true;
                    }
                    Log.d(TAG, "MotionEvent.ACTION_MOVE");
                    int diffY = y - previousY;
                    int diffX = x - previousX;

                    float currentRawY = ev.getRawY();
                    float currentRawX = ev.getRawX();
                    //判断方向
                    if (direction == Direction.NONE) {
                        if (Math.abs(diffX) > Math.abs(diffY)) {
                            direction = Direction.LEFT_RIGHT;
                        } else if (Math.abs(diffX) < Math.abs(diffY)) {
                            direction = Direction.UP_DOWN;
                        } else {
                            direction = Direction.NONE;
                        }
                    }
                    //当方向为垂直方向时，移动布局并改变透明度
                    if (direction == Direction.UP_DOWN) {
                        isScrollingUp = diffY <= 0;
                        this.setTranslationY(diffY);
                        if (mBackground != null) {
                            int alpha = (int) (255 * Math.abs(diffY * 1f)) / getHeight();
                            mBackground.setAlpha(255 - alpha);
                        }

                        if (mScrollListener != null) {
                            mScrollListener.onMove();
                        }

//                        if (mChildView != null) {
//                            int h2 = getHeight() / 2;
//                            Log.d("TAG", "h2: " + h2);
//
//
//                            float bili = (float) CountUtil.sub(1f, CountUtil.div(Math.abs(diffY), h2));
//
//                            Log.d("TAG", "diffY: " + diffY);
//                            Log.d("TAG", "getHeight(): " + getHeight());
//                            Log.d("TAG", "bili: " + bili);
//
//                            if (bili > 0.5f) {
//                                mChildView.setScaleX(bili);
//                                mChildView.setScaleY(bili);
//                            } else {
//                                mChildView.setScaleX(0.5f);
//                                mChildView.setScaleY(0.5f);
//                            }
//
//
//                        }

                        return true;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    Log.d(TAG, "MotionEvent.ACTION_UP");
                    mode = 0;
                    if (direction == Direction.UP_DOWN) {
                        int height = this.getHeight();
                        //判断滑动距离是否大于height/7
                        if (Math.abs(getTranslationY()) > (height / 7)) {
                            //执行退出动画
                            layoutExitAnim(300, true);
                        } else {
                            //执行恢复动画
                            if (mScrollListener != null) {
                                mScrollListener.onCancel();
                            }
                            layoutRecoverAnim();
                        }
                        direction = Direction.NONE;
                        return true;
                    }
                    direction = Direction.NONE;

            }
            return true;
        }
        return false;
    }

    /**
     * 退出布局的动画
     *
     * @param duration       动画时长
     * @param isFingerScroll 是否手指滑动触发
     */
    public void layoutExitAnim(long duration, boolean isFingerScroll) {
        ObjectAnimator exitAnim;
        if (isFingerScroll) {
            exitAnim = ObjectAnimator.ofFloat(this, "translationY", getTranslationY(), isScrollingUp ? -getHeight() : getHeight());
        } else {
            exitAnim = ObjectAnimator.ofFloat(this, "translationY", 0, getHeight());
        }
        exitAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mBackground != null) {
                    mBackground.setAlpha(0);
                }
                if (mScrollListener != null) {
                    mScrollListener.onLayoutClosed();
                }

            }
        });
        exitAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (mBackground != null) {
                    int alpha = (int) (255 * Math.abs(getTranslationY() * 1f)) / getHeight();
                    mBackground.setAlpha(255 - alpha);
                }
            }
        });
        exitAnim.setDuration(duration);
        exitAnim.start();
    }

    /**
     * 恢复动画
     */
    private void layoutRecoverAnim() {
        ObjectAnimator recoverAnim = ObjectAnimator.ofFloat(this, "translationY", this.getTranslationY(), 0);
        recoverAnim.setDuration(100);
        recoverAnim.start();
        if (mBackground != null) {
            mBackground.setAlpha(255);
        }
        if (mChildView != null) {
            mChildView.setScaleX(1f);
            mChildView.setScaleY(1f);
        }
    }

    public interface LayoutScrollListener {
        //移动布局
        void onMove();
        //取消移动布局
        void onCancel();
        //关闭布局
        void onLayoutClosed();
    }

}
