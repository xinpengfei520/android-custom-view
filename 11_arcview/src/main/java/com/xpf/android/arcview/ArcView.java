package com.xpf.android.arcview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by x-sir on 2018/8/10 :)
 * Function:自定义弧形 View
 */
public class ArcView extends View {

    private int mWidth;
    private int mHeight;
    private int mArcHeight; // 弧形高度
    private int mBgColor; // 背景颜色
    private Paint mPaint;

    public ArcView(Context context) {
        this(context, null);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcView);
        mArcHeight = typedArray.getDimensionPixelSize(R.styleable.ArcView_arcHeight, 0);
        mBgColor = typedArray.getColor(R.styleable.ArcView_bgColor, Color.parseColor("#1E90FF"));
        typedArray.recycle();
        mPaint = new Paint();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mBgColor);
        mPaint.setAntiAlias(true);
        Rect rect = new Rect(0, 0, mWidth, mHeight - mArcHeight);
        canvas.drawRect(rect, mPaint);
        Path path = new Path();
        path.moveTo(0, mHeight - mArcHeight);
        path.quadTo(mWidth / 2, mHeight, mWidth, mHeight - mArcHeight);
        canvas.drawPath(path, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }
        setMeasuredDimension(mWidth, mHeight);
    }
}
