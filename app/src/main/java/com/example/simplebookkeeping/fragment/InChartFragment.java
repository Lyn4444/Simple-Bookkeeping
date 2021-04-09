package com.example.simplebookkeeping.fragment;


import android.content.Intent;
import android.view.View;

import com.example.simplebookkeeping.R;
import com.example.simplebookkeeping.adapter.ChartItemAdapter;
import com.example.simplebookkeeping.db.DBManager;
import com.example.simplebookkeeping.db.LineChartBean;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class InChartFragment extends ChartBaseFragment {

    @Override
    protected void setAxisData(int year, int month, int dayNum) {
        List<ILineDataSet> sets = new ArrayList<>();

        List<LineChartBean> dataList = DBManager.getDaySumMoneyInMonth(year, month, 1);

        if (dataList.size() == 0) {
            lineChart.setVisibility(View.GONE);
            dataEmptyView.setVisibility(View.VISIBLE);
        } else {
            lineChart.setVisibility(View.VISIBLE);
            dataEmptyView.setVisibility(View.GONE);

            List<Entry> entries1 = new ArrayList<>();

            if (1 != dataList.get(0).getDay()) {
                entries1.add(new Entry(0, 0));
            }

            for (LineChartBean bean : dataList) {
                entries1.add(new Entry(bean.getDay() - 1, bean.getMoney()));
            }

            if (dayNum != dataList.get(dataList.size() - 1).getDay()) {
                entries1.add(new Entry(dayNum - 1, 0));
            }

            LineDataSet dataSet1 = new LineDataSet(entries1, "");
            dataSet1.setValueTextSize(15f);
            dataSet1.setDrawValues(true); // 不显示值
            dataSet1.setColor(getResources().getColor(R.color.blue));  //折线的颜色
            dataSet1.setCircleColor(getResources().getColor(R.color.blue));
            dataSet1.setLineWidth(2);        //折线的粗细
//            dataSet1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            sets.add(dataSet1);

            LineData lineData = new LineData(sets);
            lineChart.setData(lineData);
//            lineChart.setExtraOffsets(5, 5, 5, 5);
            lineChart.setScaleEnabled(false); // 设置能缩放
            lineChart.setTouchEnabled(false); // 设置不能触摸
        }
    }

    @Override
    protected void setYAxis(int year, int month) {
        float maxMoney = DBManager.getMaxMoneyByDayInMonth(year, month, 1);

        float max = (float) Math.ceil(maxMoney);

        YAxis yAxis_right = lineChart.getAxisRight();
        yAxis_right.setAxisMinimum(0f);
        yAxis_right.setAxisMaximum(max + 100);
        yAxis_right.setTextSize(12f);
        yAxis_right.setEnabled(false);

        YAxis yAxis_left = lineChart.getAxisLeft();
        yAxis_left.setAxisMinimum(0f);
        yAxis_left.setAxisMaximum(max + 100);
        yAxis_left.setTextSize(12f);
        yAxis_left.setEnabled(true);

        yAxis_left.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return "￥" + (int) value;
            }
        });

//        不显示图例
        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);
    }


    @Override
    public void loadData(int year, int month) {
        super.loadData(year, month);

        beans = DBManager.getMothTotalMoney(year, month, 1);
        chartBeanList.clear();
        chartBeanList.addAll(beans);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void setSelectedDate(int year, int month) {
        super.setSelectedDate(year, month);

        loadData(year, month);
    }

}
