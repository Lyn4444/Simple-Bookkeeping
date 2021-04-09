package com.example.simplebookkeeping.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.simplebookkeeping.R;
import com.example.simplebookkeeping.adapter.MainListAdapter;
import com.example.simplebookkeeping.db.AccountBean;
import com.example.simplebookkeeping.db.DBManager;
import com.example.simplebookkeeping.utils.CalenderDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * The List Fragment Of Main Fragment.
 */
public class ListFragment extends Fragment implements View.OnClickListener {
    ListView listView;
    TextView historyView, nowView;
    ImageView calender;

    List<AccountBean> accountBeanList;
    MainListAdapter listAdapter;

    int year, month, historyYear, historyMonth;


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        listView = view.findViewById(R.id.list_listView);
        historyView = view.findViewById(R.id.list_history);
        calender = view.findViewById(R.id.list_calender);
        nowView = view.findViewById(R.id.list_now);

        calender.setOnClickListener(this);
        nowView.setOnClickListener(this);

        accountBeanList = new ArrayList<>();
        listAdapter = new MainListAdapter(getContext(), accountBeanList);
        listView.setAdapter(listAdapter);

        initTime();

        historyView.setText("账单时间：" + year + "/" + month);

        getMonthDataFromDB(year, month);

        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();

        historyView.setText("账单时间：" + year + "/" + month);

        getMonthDataFromDB(year, month);
    }

    private void getMonthDataFromDB(int year, int month) {
        List<AccountBean> resList = DBManager.getDayAccountBeanFromAccountTable(year, month);
        accountBeanList.clear();
        accountBeanList.addAll(resList);
        listAdapter.notifyDataSetChanged();
    }

    /*
     *
     * 获取当前时间
     */
    private void initTime() {
        Calendar instance = Calendar.getInstance();
        year = instance.get(Calendar.YEAR);
        month = instance.get(Calendar.MONTH) + 1;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.list_calender:
                showCalenderDialog();
                break;
            case R.id.list_now:
                initTime();
                historyView.setText("账单时间：" + year + "/" + month);
                getMonthDataFromDB(year, month);
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void showCalenderDialog() {
        CalenderDialog dialog = new CalenderDialog(Objects.requireNonNull(getContext()));
        dialog.show();
        dialog.setCalenderDialog();
        dialog.setOnEnsureListener(() -> {
            historyYear = dialog.year;
            historyMonth = dialog.month;
            historyView.setText("账单时间：" + historyYear + "/" + historyMonth);
            year = historyYear;
            month = historyMonth;
            getMonthDataFromDB(historyYear, historyMonth);
        });
    }
}