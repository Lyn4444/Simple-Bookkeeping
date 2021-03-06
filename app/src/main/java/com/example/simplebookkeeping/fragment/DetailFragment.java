package com.example.simplebookkeeping.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simplebookkeeping.R;
import com.example.simplebookkeeping.adapter.ChartPagerAdapter;
import com.example.simplebookkeeping.db.DBManager;
import com.example.simplebookkeeping.utils.CalenderDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;


public class DetailFragment extends Fragment implements View.OnClickListener {

    Button inBtn, outBtn;
    TextView dataTextView, inTextView, outTextView;
    ViewPager chartViewPager;
    ImageView calender;

    int year, month, selectYear, selectMonth;

    List<Fragment> fragmentList;
    InChartFragment inChartFragment;
    OutChartFragment outChartFragment;
    ChartPagerAdapter chartAdapter;

    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        initView(view);

        initTime();

        initData(year, month);

        initFrag();

        setFragmentSelectedListener();

        return view;
    }

    private void initView(View view) {
        inBtn = view.findViewById(R.id.detail_select_btn_in);
        outBtn = view.findViewById(R.id.detail_select_btn_out);
        dataTextView = view.findViewById(R.id.detail_box_title);
        outTextView = view.findViewById(R.id.detail_box_out);
        inTextView = view.findViewById(R.id.detail_box_in);
        chartViewPager = view.findViewById(R.id.detail_chart);
        calender = view.findViewById(R.id.detail_calender);

        inBtn.setOnClickListener(this);
        outBtn.setOnClickListener(this);
        calender.setOnClickListener(this);
    }

    private void initTime() {
        Calendar instance = Calendar.getInstance();
        year = instance.get(Calendar.YEAR);
        month = instance.get(Calendar.MONTH) + 1;
    }

    /*
     *
     * ???????????????????????????
     */
    @SuppressLint("SetTextI18n")
    private void initData(int year, int month) {
        float monthSumMoney_out = DBManager.getMonthSumMoney(year, month, 0);
        float monthSumMoney_in = DBManager.getMonthSumMoney(year, month, 1);

        int monthNumMoney_out = DBManager.getMonthNumMoney(year, month, 0);
        int monthNumMoney_in = DBManager.getMonthNumMoney(year, month, 1);

        dataTextView.setText(year + "/" + month + " ???????????????");
        outTextView.setText("?????????????????????" + monthNumMoney_out + "???, ???????????????" + monthSumMoney_out);
        inTextView.setText("?????????????????????" + monthNumMoney_in + "???, ???????????????" + monthSumMoney_in);
    }


    private void initFrag() {
        fragmentList = new ArrayList<>();

        outChartFragment = new OutChartFragment();
        inChartFragment = new InChartFragment();

//        ???????????????Fragment
        bundle = new Bundle();
        bundle.putInt("year", year);
        bundle.putInt("month", month);
        outChartFragment.setArguments(bundle);
        inChartFragment.setArguments(bundle);

//        ??????Fragment???????????????
        fragmentList.add(outChartFragment);
        fragmentList.add(inChartFragment);

//        ???????????????,???fragmentList??????
        /*
         *
         * fragment ????????????fragment????????????getChildFragmentManager()??????
         */
        chartAdapter = new ChartPagerAdapter(getChildFragmentManager(), fragmentList);
        chartViewPager.setAdapter(chartAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_calender:
                showCalenderDialog();
                break;
            case R.id.detail_select_btn_in:
                setButtonStyle(1);
//                ??????fragment??????
                chartViewPager.setCurrentItem(1);
                break;
            case R.id.detail_select_btn_out:
                setButtonStyle(0);
//                ??????fragment??????
                chartViewPager.setCurrentItem(0);
                break;

        }
    }

    @SuppressLint("SetTextI18n")
    private void showCalenderDialog() {
        CalenderDialog dialog = new CalenderDialog(Objects.requireNonNull(getContext()));
        dialog.show();
        dialog.setCalenderDialog();
        dialog.setOnEnsureListener(() -> {
            selectYear = dialog.year;
            selectMonth = dialog.month;
            initData(selectYear, selectMonth);
            inChartFragment.setSelectedDate(selectYear, selectMonth);
            outChartFragment.setSelectedDate(selectYear, selectMonth);
        });
    }

    /*
     *
     * ??????button???????????????????????????
     *
     * ????????????????????? ???kind = 0???
     *
     * ????????????blue, ??????grey_record
     *
     */
    @SuppressLint("ResourceAsColor")
    public void setButtonStyle(int kind) {
        if (kind == 0) {
            outBtn.setTextColor(this.getResources().getColor(R.color.green));
            inBtn.setTextColor(this.getResources().getColor(R.color.grey_record));
        } else {
            outBtn.setTextColor(this.getResources().getColor(R.color.grey_record));
            inBtn.setTextColor(this.getResources().getColor(R.color.green));
        }
    }

    private void setFragmentSelectedListener() {
        chartViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                setButtonStyle(position);
            }
        });
    }

}