package com.xpf.eventtest.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.xpf.eventtest.utils.LogUtil;
import com.xpf.eventtest.utils.TouchEventUtil;

/**
 * Created by xpf on on 2016/9/24 11:01
 * 作用：
 */
public class MyImageView extends AppCompatImageView {

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        getParent().requestDisallowInterceptTouchEvent(true);
        LogUtil.w("eventTest", "MyImageView | onTouchEvent --> " + TouchEventUtil.getTouchAction(ev.getAction()));
//        return super.onTouchEvent(ev);
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.w("eventTest", "MyImageView | dispatchTouchEvent --> " + TouchEventUtil.getTouchAction(ev.getAction()));
//        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
