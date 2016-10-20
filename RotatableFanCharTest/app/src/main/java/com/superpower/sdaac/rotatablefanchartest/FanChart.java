package com.superpower.sdaac.rotatablefanchartest;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/10/20.
 */
public class FanChart extends View {

    private int mStrokeWidth = 80; // 扇形宽度
    private List<FanChartItem> list=new ArrayList<>(); //数据源
    private Paint mPaint; //画笔
    DecimalFormat df = new DecimalFormat("###.00"); //保留2位小数
    private float mPointAngle = 90;
    private OnRotationListener mListener;


    public FanChart(Context context) {
        this(context, null);
    }

    public FanChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FanChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        mStrokeWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mStrokeWidth, dm);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FanChart);
        mStrokeWidth = array.getDimensionPixelSize(R.styleable.FanChart_fc_strokeWidth, mStrokeWidth);
        array.recycle();
        initPaint();
    }

    /**
     *  初始化画笔
     *
     */
    private void initPaint(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(Color.GREEN);
    }

    /**
     *  设置数据源
     * @param list
     */
    public void setCharts(List<FanChartItem> list) {
        this.list = list;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //控制圆形大小，防止圆形超出总的控件外面
        int offset = (int) (mPaint.getStrokeWidth()/2);

        float curStartAngle = 0; //每次重画的起始角度

        //遍历list的item，根据item计算度数，循环画扇形
        for (int i=0;i<list.size();i++){
            //设置该区域画笔颜色
            mPaint.setColor(list.get(i).getColor());
            //计算当前区域所占百分比
            float statu=list.get(i).getCount()/FanChartItem.getCountSum();
            //计算当前所画的度数
            float sweepAngle=360*statu;
            //把当前的起始及结束度数填充到item
            list.get(i).setAngle(curStartAngle,curStartAngle+sweepAngle);

            //开始画扇形
            RectF rectF=new RectF(offset,offset,getWidth()-offset,getHeight()-offset);
            canvas.drawArc(rectF,curStartAngle,sweepAngle+2,false,mPaint); //这里不知道为什么空了一点  所以就加上了2
            //画完之后需要改变起始角度
            curStartAngle+=sweepAngle;
        }
    }

    /**
     *  设置移动
     * @param rotation
     */
    @Override
    public void setRotation(float rotation) {
        super.setRotation(rotation);
        if (null != mListener) {
            FanChartItem item = getCurItem(rotation);
            if (null != item) {
                float percent = 100*item.getCount()/FanChartItem.getCountSum();
                mListener.setText(item.getName(), df.format(percent)+"%");
            }
        }
    }

    /**
     *  得到当前所在的item
     * @param rotation
     * @return
     */
    private FanChartItem getCurItem(float rotation) {
        float pointAngleOffset = mPointAngle - rotation;
        pointAngleOffset = pointAngleOffset%360;
        if (pointAngleOffset < 0) {
            pointAngleOffset += 360;
        }
        for (FanChartItem item : list) {
            if (item.getEndAngle() > pointAngleOffset && item.getStartAngle() < pointAngleOffset) {
                return item;
            }
        }
        return null;
    }

    public void setPointAngle(float pointAngle) {
        mPointAngle = pointAngle;
    }

    /**
     *  设置滑动监听，改变text的文本
     * @param listener
     */
    public void setOnRotationListener(OnRotationListener listener) {
        mListener = listener;
    }

    public interface OnRotationListener{
        void setText(String name, String percent);
    }

    /**
     *  每次滑动完成后都让指针指在当前模块的最中间
     *
     */
    public void rotateToMid(){
        float rotation = getRotation();
        FanChartItem item = getCurItem(rotation);
        if (null != item) {
            float start = rotation%360;
            float target = mPointAngle - (item.getStartAngle() + item.getEndAngle())/2;
            if (Math.abs(target - start) > Math.abs(item.getEndAngle() - item.getStartAngle())) {
                if (target > start) {
                    target -= 360;
                } else {
                    target += 360;
                }
            }
            ValueAnimator anim = ObjectAnimator.ofFloat(start, target);
            anim.setDuration(300);
            anim.setInterpolator(new DecelerateInterpolator());
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float val = (float) animation.getAnimatedValue();
                    setRotation(val);
                }
            });
            anim.start();
        }
    }
}
