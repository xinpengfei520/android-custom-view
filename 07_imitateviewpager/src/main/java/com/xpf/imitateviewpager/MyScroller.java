package com.xpf.imitateviewpager;

import android.os.SystemClock;

/**
 * 作者：尚硅谷-杨光福 on 2016/9/23 15:20
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：xxxx
 */
public class MyScroller {


    private float startX;
    private float startY;
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
     * @return
     */
    public float getCurrX() {
        return currX;
    }

    /**
     * 开始记时
     * @param startX
     * @param startY
     * @param distanceX 在X轴要移动的距离
     * @param distanceY 在Y轴要移动的距离
     */
    public void startScroll(float startX,float startY,int distanceX,int distanceY){
        this.startX = startX;
        this.startY = startY;
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
    public boolean computeScrollOffset(){
        if(isFinish){
            return  false;
        }


        //这一小段结束的时刻
        long endTime = SystemClock.uptimeMillis();
        //这一小段所花的时间
        long passTime = endTime - startTime;

        if(passTime < totalTime){
            //还在移动
            //平均速度
//            float voicity = distanceX/totalTime;

            //这一小段移动的距离 = 时间*速度
             float distancSmallX = passTime* distanceX/totalTime;
            //移动这一小段对应的坐标
             currX = startX + distancSmallX;

        }else{
            currX = startX + distanceX;
            //移动结束了
            isFinish = true;
        }

        return true;
    }
}
