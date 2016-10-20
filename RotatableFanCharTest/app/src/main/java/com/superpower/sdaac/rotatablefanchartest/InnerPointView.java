package com.superpower.sdaac.rotatablefanchartest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by pc on 2016/10/20.
 */
public class InnerPointView extends InnerPointViewBase {

    private Paint mPaint;
    private Path mPath = new Path();

    public InnerPointView(Context context) {
        this(context, null);
    }

    public InnerPointView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InnerPointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(0x00000000);

        initPaint();
    }
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int radius = width*2/5; //这里圆的半径不是宽度的一半是因为方便下面绘制突出来的角
        canvas.drawCircle(width / 2, height / 2, radius, mPaint);

        //把画笔移动到底部中间，然后绘制突出来的角
        mPath.moveTo(width/2, height);
        mPath.lineTo(width/2 - width/4, height/2);
        mPath.lineTo(width/2 + width/4, height/2);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }
}
