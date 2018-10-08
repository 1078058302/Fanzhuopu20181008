package com.example.fanzhuopu20181008;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/*
Author:樊卓璞
date:2018/10/8
*/
//水波纹
public class WaterView extends View {

    private Paint paintBottom;
    private Paint paintTop;
    private Path pathTop;
    private Path pathBottom;
    private float あ;
    private int bottomcolor;
    private int topcolor;

    public WaterView(Context context) {
        super(context);
        init(context, null);
    }

    public WaterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public WaterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ty = context.obtainStyledAttributes(attrs, R.styleable.WaterView);
        topcolor = ty.getColor(R.styleable.WaterView_colorTop, Color.WHITE);
        bottomcolor = ty.getColor(R.styleable.WaterView_colorBottom, Color.WHITE);
        ty.recycle();
        paintTop = new Paint();
        paintTop.setColor(topcolor);
//        paintTop.setAntiAlias(true);
        paintBottom = new Paint();
        paintBottom.setColor(bottomcolor);
//        paintBottom.setAntiAlias(true);
        paintBottom.setAlpha(30);

        pathTop = new Path();
        pathBottom = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        pathTop.reset();
        pathBottom.reset();
        //起始路径
        pathTop.moveTo(getLeft(), getBottom());
        pathBottom.moveTo(getLeft(), getBottom());
        double v = Math.PI * 2 / getWidth();
        あ -= 0.1f;

        for (float i = 0; i < getWidth(); i += 20) {
            float y = (float) (10 * Math.cos(v * i + あ) + 10);
            pathTop.lineTo(i, y);
            pathBottom.lineTo(i, (float) (10 * Math.sin(v * i + あ)));
            listener.success(y);
        }
        pathTop.lineTo(getRight() + 2000, getBottom());
        pathBottom.lineTo(getRight() + 2000, getBottom());
        canvas.drawPath(pathTop, paintTop);
        canvas.drawPath(pathBottom, paintBottom);
        postInvalidateDelayed(20);
    }

    private WaterViewListener listener;

    public void result(WaterViewListener listener) {
        this.listener = listener;
    }


    public interface WaterViewListener {
        void success(float y);
    }

}
