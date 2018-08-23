package com.xpf.android.loadingview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private View activity_main;
    private LoadingView mLoadingView;
    private LoadingView.OnLoadingListener listener = new LoadingView.OnLoadingListener() {
        @Override
        public void onDismiss() {
            Toast.makeText(MainActivity.this, "Loading 消失了!", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity_main = findViewById(R.id.activity_main);

        // 1.最基本的用法
        // 因为 PopupWindow 依赖于Activity，所以必须要调用 setDropView 方法设置要挂载的 View，
        // 一般是 Activity 或 Fragment 的根 View，其他参数可根据需求进行设置。
        mLoadingView = new LoadingView.Builder(this)
                .setDropView(activity_main)
                .build();

        // 2.自定义设置各种参数
        mLoadingView = new LoadingView.Builder(this)
                .setText("拼命加载中...") // 设置文案
                .setTextSize(12) // 设置字体大小(sp)
                .setTextColor("#FFFFFF") // 设置字体颜色(#RGB & #ARGB)
                .setTextMarginTop(10) // 设置文字距上的距离(dp)
                .setCornerRadius(4) // 设置圆角半径(dp)
                .setLoadingBgColor("#CC000000") // 设置背景颜色(#RGB & #ARGB)
                .setLoadingWidth(120) // 设置 loading 的宽(dp)
                .setLoadingHeight(100) // 设置 loading 的高(dp)
                .setListener(listener) // 设置监听
                .setDropView(activity_main) // 设置要挂载的 View(必须要设置)，一般是 Activity 或 Fragment 的根 View
                .setGifDrawable(R.drawable.loading4) // 设置 gif 资源
                .setFocusable(false) // 为 true 时，响应返回键消失，为 false 时响应 activity 返回操作，默认为 false
                .setGifWidth(16) // 设置 gif 的宽(dp)
                .setGifHeight(16) // 设置 gif 的高(dp)
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mLoadingView != null) {
            mLoadingView.show();
        }

        startThread();
    }

    private void startThread() {
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mLoadingView != null) {
                            mLoadingView.dismiss();
                        }
                    }
                });
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoadingView != null) {
            mLoadingView.dispose();
            mLoadingView = null;
        }
    }
}
