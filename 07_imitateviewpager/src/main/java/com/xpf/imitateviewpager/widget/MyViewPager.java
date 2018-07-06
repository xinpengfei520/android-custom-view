package com.xpf.imitateviewpager.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by xpf on 2016/9/23 11:37
 * 作用：自定义仿 ViewPager
 */
public class MyViewPager extends ViewGroup {

    private static final String TAG = MyViewPager.class.getSimpleName();
    /**
     * 1.定义
     * 2.实例化手势识别器
     * 3.从onTouchEvent()接收事件
     */
    private GestureDetector detector;
    private int currentIndex; // 当前页面的下标
    private float startX;
    private Scroller scroller;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
        detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            /**
             *
             * @param e1 按下
             * @param e2 离开
             * @param distanceX 在X轴产生的距离
             * @param distanceY 在Y轴产生的距离
             * @return
             */
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                /*
                android系统提供移动控件内容的两个方法之一
                 x:在x轴要移动的距离；
                 y:在Y轴移动的距离
                 */
                scrollBy((int) distanceX, 0);
                Log.e("TAG", "distanceX==" + distanceX);
                /**
                 * 移动的是坐标
                 */
//                scrollTo(x,y);
                return super.onScroll(e1, e2, distanceX, distanceY);
            }
        });
    }

    /**
     * 测量每个孩子
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec widthMeasureSpec :包含父类该当前控件的宽和模式
     *                          三种模式：EXACTLY,AT_MOST,UNSPECIFIED
     *                          系统的onMesaure中所干的事：
     *                           1、根据 widthMeasureSpec 求得宽度width，和父view给的模式
     *                           2、根据自身的宽度width 和自身的padding 值，相减，求得子view可以拥有的宽度newWidth
     *                           3、根据 newWidth 和模式求得一个新的MeasureSpec值:
     *                            MeasureSpec.makeMeasureSpec(newSize, newmode);
     *                           4、用新的MeasureSpec来计算子view
     */

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        Log.e(TAG,"widthMeasureSpec=="+widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
//        MeasureSpec.makeMeasureSpec(width,mode);
        Log.e(TAG, "width==" + width + ",height==" + height + ",mode==" + mode);

        for (int i = 0; i < getChildCount(); i++) {
            View children = getChildAt(i);
            children.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**
     * 指定孩子的位置
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View children = getChildAt(i);
            //指定第0个页面的坐标
            children.layout(i * getWidth(), 0, (i + 1) * getWidth(), getHeight());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        //3.把事件传递给手势识别器
        detector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //1.记录坐标
                startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                //2.来到新的坐标
                float endX = event.getX();
                //3.判断滑动方向
                int tempIndex = currentIndex;

                if ((endX - startX) > getWidth() / 2) {
                    //显示上一个页面
                    tempIndex--;
                } else if ((startX - endX) > getWidth() / 2) {
                    //显示下一个页面
                    tempIndex++;
                }

                //根据位置移动到对应的页面
                moveToPager(tempIndex);
                //重新记录起始值
                startX = event.getX();
                break;
        }
        return true;
    }

    /**
     * 屏蔽非法下标位置和移动对应下标位置的页面
     *
     * @param tempIndex
     */
    public void moveToPager(int tempIndex) {
        //屏蔽非法值
        if (tempIndex < 0) {
            tempIndex = 0;
        }

        if (tempIndex > getChildCount() - 1) {
            tempIndex = getChildCount() - 1;
        }

        currentIndex = tempIndex;
        //根据坐标移动控件的内容

        if (changeListener != null) {
            changeListener.onChangePager(currentIndex);
        }

        int distanceX = currentIndex * getWidth() - getScrollX();

//        scrollTo(currentIndex*getWidth(),0);

        //开始记时
//        scroller.startScroll(getScrollX(), getScrollY(), distanceX, 0);
        scroller.startScroll(getScrollX(), getScrollY(), distanceX, 0, Math.abs(distanceX));

        invalidate();//会导致onDraw执行，并且还会导致computeScroll执行
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            //一小段对应的坐标
            float currX = scroller.getCurrX();
            scrollTo((int) currX, 0);
            invalidate();//导致computeScroll执行
        }
    }


    /**
     * 当滑动页面改变的监听者
     */
    public interface OnPagerChangeListener {
        /**
         * 当页面改变的时候回调这个方法
         *
         * @param index 页面的下标位置
         */
        void onChangePager(int index);
    }

    private OnPagerChangeListener changeListener;

    /**
     * 设置监听页面改变 -by Afu
     *
     * @param l
     */
    public void setOnPagerChangeListener(OnPagerChangeListener l) {
        this.changeListener = l;
    }

}
