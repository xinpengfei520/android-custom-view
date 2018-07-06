package com.xpf.slidingmenu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by xpf on on 2016/9/24 16:23
 * 作用：侧滑菜单
 */
public class SlideLayout extends FrameLayout {

    // TODO: 2018/7/6 解决复用问题，解决上下滑动时复位
    private View contentView;
    private View menuView;

    /**
     * 内容的宽
     */
    private int contentWidth;
    /**
     * menu的宽
     */
    private int menuWidth;
    /**
     * 两个控件的高
     */
    private int viewHeight;
    private Scroller scroller;

    public SlideLayout(@NonNull Context context) {
        this(context, null);
    }

    public SlideLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = getChildAt(0);
        menuView = getChildAt(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //contentWidth = getMeasuredWidth();
        contentWidth = contentView.getMeasuredWidth();
        //得到菜单的宽
        menuWidth = menuView.getMeasuredWidth();
        viewHeight = getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        menuView.layout(contentWidth, 0, contentWidth + menuWidth, viewHeight);
    }

    private float startX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 1.记录起始坐标
                startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                // 2.来到结束坐标
                float endX = event.getX();
                // 3.计算偏移量
                float distanceX = endX - startX;
                // 4.移动视图
                int toScrollX = (int) (getScrollX() - distanceX);
                if (toScrollX < 0) {
                    toScrollX = 0;
                }

                if (toScrollX > menuWidth) {
                    toScrollX = menuWidth;
                }
                scrollTo(toScrollX, getScrollY());
                //5.重新记录坐标
                startX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                int totalScrollX = getScrollX();
                Log.e("TAG", "totalScrollX==" + totalScrollX + ",menuWidth==" + menuWidth);
                if (totalScrollX < menuWidth / 2) {
                    //关闭
                    closeMenu();
                } else {
                    //打开
                    openMenu();
                }
                break;
        }
        return true;
    }

    //-->menuWidth
    private void openMenu() {
        scroller.startScroll(getScrollX(), getScrollY(), menuWidth - getScrollX(), getScrollY());
        invalidate();//会导致重绘制，还会导致computeScroll执行
    }

    //-->0
    private void closeMenu() {
        scroller.startScroll(getScrollX(), getScrollY(), 0 - getScrollX(), getScrollY());
        invalidate();//会导致重绘制，还会导致computeScroll执行
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), getScrollY());
            invalidate();
        }
    }
}
