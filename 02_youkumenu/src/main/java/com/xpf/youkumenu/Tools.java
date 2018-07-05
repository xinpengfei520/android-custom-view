package com.xpf.youkumenu;

import android.animation.ObjectAnimator;
import android.view.ViewGroup;

/**
 * Created by xpf on 2016/9/21 :)
 * Function:负责显示和隐藏View
 */
public class Tools {
    /**
     * 旋转到隐藏View
     *
     * @param view
     */
    public static void hideView(ViewGroup view) {
        hideView(view, 0);
    }

    /**
     * 旋转到显示View
     *
     * @param view
     */
    public static void showView(ViewGroup view) {
        showView(view, 0);
    }

    public static void hideView(ViewGroup view, int startOffset) {
        /*RotateAnimation ra = new RotateAnimation(0, 180, view.getWidth() / 2, view.getHeight());
        //设置动画播放持续时间
        ra.setDuration(500);
        //动画停留在播放完成后的状态
        ra.setFillAfter(true);
        //多久时间后开始播放动画
        ra.setStartOffset(startOffset);
        //开始播放动画
        view.startAnimation(ra);

        for (int i = 0; i < view.getChildCount(); i++) {
            view.getChildAt(i).setEnabled(false);
        }

        view.setEnabled(false);
        //view.setRotation();*/

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0, 180);
        //持续的时间
        animator.setDuration(500);
        //延迟播放
        animator.setStartDelay(startOffset);
        animator.start();//开始播放
        view.setPivotX(view.getWidth() / 2);
        view.setPivotY(view.getHeight());
    }

    public static void showView(ViewGroup view, int startOffset) {
        /*RotateAnimation ra = new RotateAnimation(180, 360, view.getWidth() / 2, view.getHeight());
        //设置动画播放持续时间
        ra.setDuration(500);
        //动画停留在播放完成后的状态
        ra.setFillAfter(true);
        ra.setStartOffset(startOffset);
        //开始播放动画
        view.startAnimation(ra);
        for (int i = 0; i < view.getChildCount(); i++) {
            view.getChildAt(i).setEnabled(true);
        }
        view.setEnabled(true);*/

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 180, 360);
        //持续的时间
        animator.setDuration(500);
        //延迟播放
        animator.setStartDelay(startOffset);
        animator.start();//开始播放
        view.setPivotX(view.getWidth() / 2);
        view.setPivotY(view.getHeight());
    }
}
