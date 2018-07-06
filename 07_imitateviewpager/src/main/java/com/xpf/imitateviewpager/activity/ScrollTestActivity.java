package com.xpf.imitateviewpager.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.xpf.imitateviewpager.R;
import com.xpf.imitateviewpager.widget.MyImageView;

public class ScrollTestActivity extends AppCompatActivity {

    private MyImageView iv_main;
    private static final String TAG = ScrollTestActivity.class.getSimpleName();

    /*
        1. 瞬间移动视图的内容: 利用View的scroll方法
            1). scrollBy(int x, int y) : 滑动指定的偏移量(从当前位置瞬间)
                x: x轴上的偏移量, x>0内容向左滑动, x<0内容向右滑动, x=0水平方向不滑动
                y: y轴上的偏移量, y>0内容向上滑动, y<0内容向下滑动, y=0垂直方向不滑动
            2). scrollTo(int x, int y) : 滑动到指定的偏移量(从当前位置瞬间)
                x: 目标位置x轴上的偏移量, x>0移动到原始位置的左侧, x<0移动到原始位置的右侧,x=0移动到水平原始位置,
                y: 目标位置y轴上的偏移量, y>0移动到原始位置的上侧, y<0移动到原始位置的下侧, y=0移动到垂直原始位置
        2. 平滑移动视图的内容: 利用Scoller和View的scroll方法
            1). Scoller是实现View平滑移动的帮助类, 它本身并不能实现对View的移动
            2). 平滑移动的基本原理: 将整个从起始位置到结束位置的移动分解成多个小的距离, 循环调用scrollTo()实现平滑移动
            3). 相关API:
                a. Scoller类:
                    -->Scoller(Context context) : 创建对象的构造方法
                    -->startScroll(int startX, int startY, int dx, int dy, int duration) : 开始平滑移动视图(这个方法本身不会产生滑动)
                        startX : 起始位置的X偏移量
                        startY : 起始位置的Y偏移量
                        dx: 滑动多大的X偏移量(如果是0,X方向不会滑动)
                        dy: 滑动多大的Y偏移量(如果是0,Y方向不会滑动)
                        duration : 整个过程持续的时间(ms)
                    -->startScroll(int startX, int startY, int dx, int dy): 开始平滑移动视图(时间为250ms)
                    -->boolean computeScrollOffset() : 计算当前移动的偏移量, 并将其保存到Scoller对象中, 如果滑动还没有完成返回true
                    -->int getCurrX() : 得到计算出的X偏移量
                    -->int getCurrY() : 得到计算出的Y偏移量
                b. View类
                    -->invalidate() : 强制重绘, 导致draw()-->computeScroll()
                        在scoller.startScroll()后必须执行此方法
                    -->computeScroll() : 需要重写此方法, 用于计算移动, 此方法在draw()中调用
                        调用scoller计算移动偏移量
                        调用view对象scrollTo()到计算出的偏移量
                        调用View对象invalidate()强制重绘, 导致computeScroll()再次执行
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_test);
        iv_main = findViewById(R.id.iv_main);
    }

    public void scrollLeft(View v) {
        iv_main.scrollBy(10, 0);
        Log.e(TAG, iv_main.getScrollX() + "-");
    }

    public void scrollRight(View v) {
        iv_main.scrollBy(-10, 0);
        Log.e(TAG, iv_main.getScrollX() + "-");
    }

    public void scrollUp(View v) {
        iv_main.scrollBy(0, 10);
        Log.e(TAG, iv_main.getScrollX() + "-");
    }

    public void scrollDown(View v) {
        iv_main.scrollBy(0, -10);
        Log.e(TAG, iv_main.getScrollX() + "-");
    }

    public void reset1(View v) {
        iv_main.scrollTo(0, 0);
    }

    public void reset2(View v) {
        iv_main.reset();
    }
}
