package com.xpf.attributeview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by xpf on 2016/9/23 10:41
 * 作用：自定义属性类
 */
public class MyAttributeView extends View {

    private Bitmap bitmap;
    private int ageInt;
    private String nameStr;
    private static final String TAG = MyAttributeView.class.getSimpleName();

    public MyAttributeView(Context context) {
        this(context, null);
    }

    public MyAttributeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyAttributeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        //得到属性有三种方式
        //1.使用命名空间取属性
        String name = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "name");
        String age = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "age");
        String bg = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "bg");
        Log.e(TAG, "name==" + name + ",age==" + age + ",bg==" + bg);

        //2.使用循环
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            Log.e(TAG, attrs.getAttributeName(i) + "," + attrs.getAttributeValue(i));
        }

        //3.使用系统工具
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyAttributeView);
        for (int i = 0; i < array.getIndexCount(); i++) {
            int index = array.getIndex(i);//id
            switch (index) {
                case R.styleable.MyAttributeView_name:
                    nameStr = array.getString(index);
                    break;
                case R.styleable.MyAttributeView_age:
                    ageInt = array.getInt(index, 0);
                    break;
                case R.styleable.MyAttributeView_bg:
                    Drawable drawable = array.getDrawable(index);
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                    bitmap = bitmapDrawable.getBitmap();
                    break;
            }
        }

        // 3.回收
        array.recycle();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(30);
        paint.setColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        canvas.drawText(nameStr + ":" + ageInt, 20, 40, paint);
    }
}
