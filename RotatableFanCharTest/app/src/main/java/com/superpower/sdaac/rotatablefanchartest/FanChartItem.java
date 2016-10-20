package com.superpower.sdaac.rotatablefanchartest;

/**
 * Created by santa on 16/8/24.
 */
public class FanChartItem {
    private String name; //名称
    private int color; //颜色
    private float count; //数量
    private static float countSum = 0; //总共数量
    private float startAngle; //开始角度
    private float endAngle; //结束角度



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public static synchronized void addCount(float cnt){
        countSum += cnt;
    }

    public static synchronized float getCountSum() {
        return countSum;
    }

    public static synchronized void setCountSum(float countSum) {
        FanChartItem.countSum = countSum;
    }

    public float getStartAngle() {
        return startAngle;
    }

    public void setAngle(float startAngle, float endAngle) {
        this.startAngle = startAngle;
        this.endAngle = endAngle;
    }

    public float getEndAngle() {
        return endAngle;
    }

}
