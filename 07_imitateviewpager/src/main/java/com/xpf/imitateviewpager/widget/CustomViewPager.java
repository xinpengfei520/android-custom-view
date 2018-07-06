package com.xpf.imitateviewpager.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by xpf on 2016/7/6 :)
 * GitHub:xinpengfei520
 * Function:自定义ViewPager
 */
public class CustomViewPager extends ViewGroup {

    private static final String TAG = CustomViewPager.class.getSimpleName();
    private int height;
    private int width;
    private int mCurrentIndex = 0;
    private Scroller mScroller;
    /**
     * 计算down和move的时候移动的距离
     */
    private float startX1;
    /**
     * 计算down和up的时候移动的距离
     */
    private float startX2;
    private float startY;

    public CustomViewPager(Context context) {
        this(context, null);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = getMeasuredHeight();
        width = getMeasuredWidth();
        // 使子 View 显示
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            this.getChildAt(i).layout(i * width, 0, i * width + width, height);
        }
    }

    public void moveTo(int temp) {
        if (temp < 0) {
            temp = 0;
        } else if (temp > getChildCount() - 1) {
            temp = getChildCount() - 1;
        }
        mCurrentIndex = temp;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageSelect(mCurrentIndex);
        }
        // 缓慢移动
        int distanceX = mCurrentIndex * getWidth() - getScrollX();
        // 给 MyScroll 计算的类赋初始值
        mScroller.startScroll(getScrollX(), 0, distanceX, 0, Math.abs(distanceX));
        invalidate();
    }

    @Override
    public void computeScroll() {
        // 如果为true说明移动还没结束
        if (mScroller.computeScrollOffset()) {
            // 得到计算的位置，然后移动
            float currX = mScroller.getCurrX();
            scrollTo((int) currX, 0);
            invalidate();
        }
    }

    private onPageChangeListener onPageChangeListener;

    public void setOnPageChangeListener(onPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }

    public interface onPageChangeListener {
        void onPageSelect(int position);
    }

    /*
     * 如果子View没有冲突，执行intercept的down然后Touch的down move up
     * 如果子View有冲突，执行intercept的down move如果父View处理就ToucheEvent的move和up
     * 综上：在down放在intercept中判断冲突的move放在intercept中其它的move和up放在TouchEvent中，这样dector的计算距离就会有错误所以手动计算移动距离
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean result = false;//这个一定要放在里面，不然move的时候result为tre下次再down返回true ListView就没有机会执行了
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "onInterceptTouchEvent ACTION_DOWN result=" + result);
                startX2 = x;
                startX1 = x;
                startY = y;
                break;
            case MotionEvent.ACTION_MOVE://不用startX1，为了防止污染onTouchEvent的startX1
                float disX = x - startX2;
                float disY = y - startY;
                result = Math.abs(disX) > Math.abs(disY);
                startX2 = x;
                startY = y;
                Log.e(TAG, "onInterceptTouchEvent ACTION_MOVE result=" + result);
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "onInterceptTouchEvent ACTION_UP result=" + result);
                break;
            default:
                break;
        }
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "onTouchEvent ACTION_MOVE");
                float disX = x - startX1;
                // scrollBy总是和移动的相反
                CustomViewPager.this.scrollBy((int) -disX, 0);
                startX1 = x;
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "onTouchEvent ACTION_UP");
                if (x - startX2 > getWidth() / 2) {// 移动到上一个
                    moveTo(mCurrentIndex - 1);
                } else if (x - startX2 < -getWidth() / 2) {// 移动到下一个
                    moveTo(mCurrentIndex + 1);
                } else {// 移动到当前页面
                    moveTo(mCurrentIndex);
                }
                break;
            default:
                break;
        }
        return true;
    }
}
