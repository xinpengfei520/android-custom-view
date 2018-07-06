package com.xpf.eventtest.utils;

/**
 * Created by xpf on 2016/4/17 14:07
 * 作用：Logger
 */
public class LogUtil {

    public static void d(String tag, String msg) {
        android.util.Log.e(tag,msg);
    }

    public static void w(String tag, String msg) {
        android.util.Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        android.util.Log.e(tag, msg);
    }

    public static void i(String tag, String msg) {
        android.util.Log.i(tag, msg);
    }

}
