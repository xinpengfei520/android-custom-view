package com.xpf.eventtest.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.xpf.eventtest.R;
import com.xpf.eventtest.utils.LogUtil;
import com.xpf.eventtest.utils.TouchEventUtil;

public class TouchEventActivity extends Activity {

    private static final String TAG = TouchEventActivity.class.getSimpleName();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    /**
     * 事件分发
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.e(TAG, "Activity | dispatchTouchEvent --> " + TouchEventUtil.getTouchAction(ev.getAction()));
        return super.dispatchTouchEvent(ev);
        //return true;
    }

    /**
     * 触摸事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.w(TAG, "Activity | onTouchEvent --> " + TouchEventUtil.getTouchAction(event.getAction()));
        return super.onTouchEvent(event);
    }

}