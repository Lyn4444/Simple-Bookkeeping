package com.example.simplebookkeeping.fragment;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.simplebookkeeping.R;
import com.example.simplebookkeeping.RecordActivity;
import com.example.simplebookkeeping.SearchActivity;
import com.example.simplebookkeeping.adapter.MainListAdapter;
import com.example.simplebookkeeping.db.AccountBean;
import com.example.simplebookkeeping.db.DBManager;
import com.example.simplebookkeeping.utils.BudgetDialog;
import com.example.simplebookkeeping.utils.FloatDecimalFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * The Home Fragment Of Main Fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    ImageView searchView;
    ImageButton editBtn;

    ListView mainListView;
    List<AccountBean> accountBeanList;
    MainListAdapter mainListAdapter;
    int year, month, day;
    View mainListHeaderView, view;

    TextView headerOutView, headerInView, headerSetBudgetView, headerBudgetView, headerTodayView, visualView;
    ImageView isShowView;

    //    共享参数保存预算数值
    SharedPreferences preferences;

    boolean isShow = true;

    OnMainListViewClick onMainListViewClick;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);


        initView(view);

//        preferences = getSharedPreferences("budget", Context.MODE_PRIVATE);
//        不在main页面，需要context.getSharedPreferences()
        preferences = Objects.requireNonNull(getContext()).getSharedPreferences("budget", Context.MODE_PRIVATE);

        addMainListHeaderView();
        accountBeanList = new ArrayList<>();
        initTime();
//        ListView内容显示适配器
        mainListAdapter = new MainListAdapter(getContext(), accountBeanList);
        mainListView.setAdapter(mainListAdapter);

        return view;
    }


    private void initView(View view) {
        initComponent(view);

    }

    private void initComponent(View view) {
        mainListView = view.findViewById(R.id.main_list);
        searchView = view.findViewById(R.id.main_search);
        editBtn = view.findViewById(R.id.main_btn_edit);

        searchView.setOnClickListener(this);
        editBtn.setOnClickListener(this);
    }

    /*
     *
     * 获取当前时间
     */
    private void initTime() {
        Calendar instance = Calendar.getInstance();
        year = instance.get(Calendar.YEAR);
        month = instance.get(Calendar.MONTH) + 1;
        day = instance.get(Calendar.DAY_OF_MONTH);
    }

    /*
     *
     * 在主页面添加main_item_top
     */
    @SuppressLint("InflateParams")
    private void addMainListHeaderView() {
////        将布局转化成View对象
        mainListHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.main_item_top, null);
        mainListView.addHeaderView(mainListHeaderView);

        headerOutView = mainListHeaderView.findViewById(R.id.main_item_top_cost_out);
        headerInView = mainListHeaderView.findViewById(R.id.main_item_top_cost_in);
        headerSetBudgetView = mainListHeaderView.findViewById(R.id.main_item_top_month_setBudget);
        headerBudgetView = mainListHeaderView.findViewById(R.id.main_item_top_budget);
        headerTodayView = mainListHeaderView.findViewById(R.id.main_item_top_today);
        isShowView = mainListHeaderView.findViewById(R.id.main_item_top_ih_show);
        visualView = mainListHeaderView.findViewById(R.id.main_item_top_visual);

        mainListHeaderView.setOnClickListener(this);
        headerSetBudgetView.setOnClickListener(this);
        isShowView.setOnClickListener(this);
        visualView.setOnClickListener(this);

        setMainListViewLongClickListener();
    }

    /*
     *
     * 设置MainListView长按事件
     *
     * 删除或者以后添加其他功能
     */
    private void setMainListViewLongClickListener() {
        mainListView.setOnItemLongClickListener((parent, view, position, id) -> {
            if ((position == 0)) {
                return false;
            }
//                获取被点击的AccountBean类型对象
            int pos = position - 1;
            AccountBean delete_bean = accountBeanList.get(pos);

            showDeleteDialog(delete_bean);
            return false;
        });
    }

    /*
     *
     * 弹出删除记录的Dialog
     */
    private void showDeleteDialog(AccountBean accountBean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));

        builder.setTitle("提示").setMessage("确定删除该记录？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", (dialog, which) -> {
//                        删除数据库中这条记录
//                        更新MainListView中数据
//                        adapter.notifyDataSetChanged()
                    int i = DBManager.deleteFromAccountTableById(accountBean.getId());
                    accountBeanList.remove(accountBean);
                    refreshListData();
                    refreshHeaderData();
                });
//        显示Dialog
        builder.create().show();
    }

    /*
     *
     * MainActivity获取焦点时调用
     *
     * 每次回到主页面都要执行查找并刷新
     *
     * refreshListData: 从数据库查找记录
     * refreshHeaderData: 从数据库查找并更新mainListHeaderView中的数据
     *
     * Transfer: List<AccountBean>
     */
    @Override
    public void onResume() {
        super.onResume();

        refreshListData();
        refreshHeaderData();
    }

    @SuppressLint("SetTextI18n")
    private void refreshHeaderData() {
        float dayMoney_out = DBManager.getDaySumMoney(year, month, day, 0);
        float dayMoney_in = DBManager.getDaySumMoney(year, month, day, 1);
        String dayInfo = "今日支出 ￥" + dayMoney_out + "  今日收入 ￥" + dayMoney_in;
        headerTodayView.setText(dayInfo);

        float monthMoney_out = DBManager.getMonthSumMoney(year, month, 0);
        float monthMoney_in = DBManager.getMonthSumMoney(year, month, 1);
        headerInView.setText("￥" + monthMoney_in);
        headerOutView.setText("￥" + monthMoney_out);

        float budget = preferences.getFloat("budget", 0);
        if ((budget == 0)) {
            headerSetBudgetView.setText("￥0.0");
            headerBudgetView.setText("￥0.0");
        } else {
            headerSetBudgetView.setText("￥" + budget);
            float budget_res = budget - monthMoney_out;
            headerBudgetView.setText("￥" + budget_res);
        }

    }

    private void refreshListData() {

        List<AccountBean> tmpList = DBManager.getDayAccountBeanFromAccountTable(year, month, day);
        this.accountBeanList.clear();
        this.accountBeanList.addAll(tmpList);
        mainListAdapter.notifyDataSetChanged();

    }

    public void setOnMainListViewClick(OnMainListViewClick onMainListViewClick) {
        this.onMainListViewClick = onMainListViewClick;
    }

    public interface OnMainListViewClick {
        public void onClick();
    }

    @Override
    public void onClick(View v) {
        /*
         *
         * 接口回调，把跳转方法交MainActivity重写
         */

        switch (v.getId()) {
            case R.id.main_search:
                Intent searchIntent = new Intent(getContext(), SearchActivity.class);
                startActivity(searchIntent);
                break;
            case R.id.main_btn_edit:
//                跳转页面操作
                Intent mainToRecordIntent = new Intent(getContext(), RecordActivity.class);
                startActivity(mainToRecordIntent);
                break;
            case R.id.main_item_top_month_setBudget:
                showBudgetDialog();
                break;
            case R.id.main_item_top_ih_show:
                isShowData();
                break;
            case R.id.main_item_top_visual:
                if (onMainListViewClick != null) {
                    onMainListViewClick.onClick();
                }
                break;
        }
    }

    /*
     *
     * 显示设置预算BudgetDialog
     */
    @SuppressLint("SetTextI18n")
    private void showBudgetDialog() {
        String budget_tmp = headerSetBudgetView.getText().toString().replace("￥", "");
        BudgetDialog budgetDialog = new BudgetDialog(Objects.requireNonNull(getContext()));
        budgetDialog.show();
        budgetDialog.setBudgetDialog();
        budgetDialog.setEditText(budget_tmp);
        budgetDialog.setOnEnsureListener((float budget) -> {
//            预算数值存储
            SharedPreferences.Editor editor = preferences.edit();
            editor.putFloat("budget", budget);
            editor.apply();
//            计算预算剩余
            float monthMoney_out = DBManager.getMonthSumMoney(year, month, 0);
            float budget_res = budget - monthMoney_out;
            headerBudgetView.setText("￥" + budget_res);
            headerSetBudgetView.setText("￥" + budget);
        });
    }

    /*
     *
     * 是否显示
     */
    private void isShowData() {

        if (isShow) {
//            得到密文对象
            PasswordTransformationMethod method = PasswordTransformationMethod.getInstance();
            headerInView.setTransformationMethod(method);
            headerOutView.setTransformationMethod(method);
            headerSetBudgetView.setTransformationMethod(method);
            headerBudgetView.setTransformationMethod(method);
            isShowView.setImageResource(R.mipmap.ih_hide);
            isShow = false;
        } else {
            HideReturnsTransformationMethod method = HideReturnsTransformationMethod.getInstance();
            headerInView.setTransformationMethod(method);
            headerOutView.setTransformationMethod(method);
            headerSetBudgetView.setTransformationMethod(method);
            headerBudgetView.setTransformationMethod(method);
            isShowView.setImageResource(R.mipmap.ih_show);
            isShow = true;
        }

    }
}