package com.xpf.imitateviewpager.widget;

import android.os.SystemClock;

/**
 * Created by xpf  on 2016/9/23 15:20
 * 作用：
 */
public class MyScroller {

    // 开始的 xy 坐标
    private float startX;
    private float startY;
    // 在 xy 轴要移动距离
    private int distanceX;
    private int distanceY;

    private long startTime;
    /**
     * 是否移动完成
     * true:移动结束
     * false:还在移动
     */
    private boolean isFinish = false;

    /**
     * 总时间
     */
    private long totalTime = 500;
    private float currX;

    /**
     * 得到移动这一小段后对应的坐标
     *
     * @return
     */
    public float getCurrX() {
        return currX;
    }

    /**
     * 开始记时
     *
     * @param startX
     * @param startY
     * @param distanceX 在X轴要移动的距离
     * @param distanceY 在Y轴要移动的距离
     */
    public void startScroll(float startX, float startY, int distanceX, int distanceY) {
        this.startX = startX;
        this.startY = startY;
        // 需要移动的总距离
        this.distanceX = distanceX;
        this.distanceY = distanceY;
        this.startTime = SystemClock.uptimeMillis();//开机到现在的时刻
        this.isFinish = false;
    }

    /**
     * 是否在移动
     * true:在移动
     * false:停止移动了
     *
     * @return
     */
    public boolean computeScrollOffset() {
        if (isFinish) {
            return false;
        }

        // 这一小段结束的时刻
        long endTime = SystemClock.uptimeMillis();
        // 这一小段所花的时间
        long passTime = endTime - startTime;

        if (passTime < totalTime) {
            //还在移动
            // 1.计算平均速度
            float vx = distanceX / totalTime;
            // 2.这一小段移动的距离 = 时间*速度
            float distanceSmallX = passTime * vx;
            // 3.计算移动这一小段对应的坐标
            currX = startX + distanceSmallX;

        } else {
            currX = startX + distanceX;
            //移动结束了
            isFinish = true;
            return false;
        }

        return true;
    }
}
