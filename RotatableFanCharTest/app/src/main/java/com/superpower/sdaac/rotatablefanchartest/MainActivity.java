package com.superpower.sdaac.rotatablefanchartest;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends Activity {

    private FanChart fanChart;
    private List<FanChartItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fanChart= (FanChart) findViewById(R.id.fanChart);
        initdata();

    }

    private void initdata(){
        list = getChartItem();
        fanChart.setCharts(list);
    }

    private List<FanChartItem> getChartItem() {
        List<FanChartItem> charts = new LinkedList<>();
        FanChartItem.setCountSum(0);
        charts.add(createItem("数码", 10548.90f, getResources().getColor(R.color.color1)));
        charts.add(createItem("服饰", 318, getResources().getColor(R.color.color11)));
        charts.add(createItem("食材", 403.60f, getResources().getColor(R.color.color8)));
        charts.add(createItem("宠物", 360f, getResources().getColor(R.color.color9)));
        charts.add(createItem("交通", 3646, getResources().getColor(R.color.color4)));
        charts.add(createItem("家居", 2800, getResources().getColor(R.color.color5)));
        charts.add(createItem("住房", 3869, getResources().getColor(R.color.color2)));
        charts.add(createItem("用餐", 3653.50f, getResources().getColor(R.color.color3)));
        charts.add(createItem("信用卡",2400, getResources().getColor(R.color.color6)));
        charts.add(createItem("零食", 871.10f, getResources().getColor(R.color.color7)));
        charts.add(createItem("通讯", 330, getResources().getColor(R.color.color10)));
        return charts;
    }

    private FanChartItem createItem(String name, float count, int color) {
        FanChartItem item = new FanChartItem();
        item.setName(name);
        item.setCount(count);
        item.setColor(color);
        FanChartItem.addCount(count);
        return item;
    }
}
