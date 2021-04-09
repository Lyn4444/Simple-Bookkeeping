package com.example.simplebookkeeping.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.simplebookkeeping.R;
import com.example.simplebookkeeping.adapter.ChartItemAdapter;
import com.example.simplebookkeeping.db.ChartBean;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * The Chart Base Fragment Of Detail Fragment.
 */
public abstract class ChartBaseFragment extends Fragment {

    View view, headerView;

    ListView chartListView;

    int year, month, dayNum;

    List<ChartBean> chartBeanList, beans;
    ChartItemAdapter adapter;

    LineChart lineChart;
    TextView dataEmptyView;

    Calendar calendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chart_base, container, false);

        initView(view);

        chartBeanList = new ArrayList<>();

        adapter = new ChartItemAdapter(getContext(), chartBeanList);
        chartListView.setAdapter(adapter);

        initLineChartView();

        return view;
    }

    @SuppressLint("InflateParams")
    private void initView(View view) {
        chartListView = view.findViewById(R.id.chart_listView);

        Bundle bundle = getArguments();
        assert bundle != null;
        year = bundle.getInt("year");
        month = bundle.getInt("month");

        //        布局转View对象
        headerView = LayoutInflater.from(getActivity()).inflate(R.layout.chart_item_top, null);

        chartListView.addHeaderView(headerView);
        lineChart = headerView.findViewById(R.id.chart_item_LineChart);
        dataEmptyView = headerView.findViewById(R.id.chart_item_data_empty);

    }


    private void initLineChartView() {

//        不显示注释
        lineChart.getDescription().setEnabled(false);
        lineChart.setExtraBottomOffset(10);

        lineChart.clear();
        lineChart.invalidate();

        calendar = Calendar.getInstance();

        dayNum = maxDayInMonth(year, month);

        setAxis(year, month, dayNum);

        setAxisData(year, month, dayNum);

    }

    protected abstract void setAxisData(int i, int year, int month);

    public int maxDayInMonth(int year, int month) {
        int max = 30;
        if (month == 1 | month == 3 | month == 5 | month == 7 | month == 8 | month == 10 | month == 12)
            max = 31;
        else if (month == 2) max = 28;
        else if (year % 400 == 0) max = 29;
        return max;
    }

    /*
     *
     * 设置柱状图坐标轴的显示
     */
    private void setAxis(int year, int month, int dayNum) {
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(dayNum);
        xAxis.setTextSize(12f);

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int val = (int) value;
                if (val == 0) {
                    return String.valueOf(month) + "-1";
                }
                if ((val + 1) % 5 == 0) {
                    return String.valueOf(month) + "-" + String.valueOf(val + 1);
                }
                if (month % 2 == 1 && (val + 1) == 31) {
                    return String.valueOf(month) + "-31";
                }
                return "";
            }
        });

        xAxis.setXOffset(10);

        setYAxis(year, month);

    }

    protected abstract void setYAxis(int year, int month);

    @Override
    public void onResume() {
        super.onResume();

        loadData(year, month);
    }

    public void loadData(int year, int month) {
    }

    @SuppressLint("InflateParams")
    public void setSelectedDate(int year, int month) {
        this.year = year;
        this.month = month;

        lineChart.clear();
        lineChart.invalidate();

        dayNum = maxDayInMonth(year, month);

        setAxis(year, month, dayNum);

        setAxisData(year, month, dayNum);

    }

}