package com.xpf.imitateviewpager.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Scroller;

public class MyImageView extends AppCompatImageView {

    private Scroller mScroller;
    private static final String TAG = MyImageView.class.getSimpleName();

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    public void reset() {
        mScroller.startScroll(getScrollX(), getScrollY(), -getScrollX(), -getScrollY(), 1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {//滑动还没有完成
            Log.e(TAG, "CurrX=" + mScroller.getCurrX());
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
