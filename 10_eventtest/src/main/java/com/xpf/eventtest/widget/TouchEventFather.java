package com.xpf.eventtest.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.xpf.eventtest.utils.TouchEventUtil;

public class TouchEventFather extends LinearLayout {

    private static final String TAG = TouchEventFather.class.getSimpleName();

    public TouchEventFather(Context context) {
        super(context);
    }

    public TouchEventFather(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("eventTest", "Father | dispatchTouchEvent --> " + TouchEventUtil.getTouchAction(ev.getAction()));
        return super.dispatchTouchEvent(ev);
//		return true;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("eventTest", "Father | onInterceptTouchEvent --> " + TouchEventUtil.getTouchAction(ev.getAction()));
        return super.onInterceptTouchEvent(ev);
//		return true;
    }

    public boolean onTouchEvent(MotionEvent ev) {
//		super.onTouchEvent(ev);
        Log.d("eventTest", "Father | onTouchEvent --> " + TouchEventUtil.getTouchAction(ev.getAction()));
//		return false;
        return super.onTouchEvent(ev);
    }

}
