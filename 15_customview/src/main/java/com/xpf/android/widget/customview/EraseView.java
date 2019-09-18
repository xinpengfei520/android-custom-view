package com.xpf.android.widget.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class EraseView extends View {

    private boolean isMove = false;
    private Bitmap bitmap = null;
    private Bitmap frontBitmap = null;
    private Path path;
    private Canvas mCanvas;
    private Paint paint;

    public EraseView(Context context) {
        this(context, null);
    }

    public EraseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EraseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCanvas == null) {
            createEraseBitmap();
        }
        canvas.drawBitmap(bitmap, 0, 0, null);
        mCanvas.drawPath(path, paint);
    }

    public void createEraseBitmap() {
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_4444);
        frontBitmap = createBitmap(Color.GRAY, getWidth(), getHeight());

        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(60);

        path = new Path();

        mCanvas = new Canvas(bitmap);
        mCanvas.drawBitmap(frontBitmap, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float ax = event.getX();
        float ay = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            isMove = false;
            path.reset();
            path.moveTo(ax, ay);
            invalidate();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            isMove = true;
            path.lineTo(ax, ay);
            invalidate();
            return true;
        }
        return super.onTouchEvent(event);
    }

    public Bitmap createBitmap(int color, int width, int height) {
        int[] rgb = new int[width * height];

        for (int i = 0; i < rgb.length; i++) {
            rgb[i] = color;
        }

        return Bitmap.createBitmap(rgb, width, height, Config.ARGB_8888);
    }

}
