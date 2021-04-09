package com.example.simplebookkeeping;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.simplebookkeeping.adapter.MainPagerAdapter;
import com.example.simplebookkeeping.fragment.DetailFragment;
import com.example.simplebookkeeping.fragment.HomeFragment;
import com.example.simplebookkeeping.fragment.ListFragment;
import com.example.simplebookkeeping.fragment.SettingFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/*
 *
 * 现改动版本：
 *
 * 2.0
 *
 * 改动详情：
 *
 * 底部导航栏
 *
 * "主页", "账单记录", "账单详情", "设置"
 *
 * fragment_home, fragment_list, fragment_detail, fragment_setting
 *
 * 改动详情：
 *
 * @Time: 2021/04/07 01:38
 *
 */
public class MainActivity extends AppCompatActivity {

//public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//
//    ImageView searchView;
//    Button editBtn;
//
//    ListView mainListView;
//    List<AccountBean> accountBeanList;
//    MainListAdapter mainListAdapter;
//    int year, month, day;
//    View mainListHeaderView;
//
//    TextView headerOutView, headerInView, headerSetBudgetView, headerBudgetView, headerTodayView;
//    ImageView isShowView;
//
//    //    共享参数保存预算数值
//    SharedPreferences preferences;
//
//    boolean isShow = true;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        initView();
//        preferences = getSharedPreferences("budget", Context.MODE_PRIVATE);
//
//        addMainListHeaderView();
//        accountBeanList = new ArrayList<>();
//        initTime();
////        ListView内容显示适配器
//        mainListAdapter = new MainListAdapter(this, accountBeanList);
//        mainListView.setAdapter(mainListAdapter);
//
//    }
//
//    private void initView() {
//        mainListView = findViewById(R.id.main_list);
//        searchView = findViewById(R.id.main_search);
//        editBtn = findViewById(R.id.main_btn_edit);
//
//        searchView.setOnClickListener(this);
//        editBtn.setOnClickListener(this);
//    }
//
//    /*
//     *
//     * 在主页面添加main_item_top
//     */
//    private void addMainListHeaderView() {
//
////        将布局转化成View对象
//        mainListHeaderView = getLayoutInflater().inflate(R.layout.main_item_top, null);
//        mainListView.addHeaderView(mainListHeaderView);
//
//        headerOutView = mainListHeaderView.findViewById(R.id.main_item_top_cost_out);
//        headerInView = mainListHeaderView.findViewById(R.id.main_item_top_cost_in);
//        headerSetBudgetView = mainListHeaderView.findViewById(R.id.main_item_top_month_setBudget);
//        headerBudgetView = mainListHeaderView.findViewById(R.id.main_item_top_budget);
//        headerTodayView = mainListHeaderView.findViewById(R.id.main_item_top_today);
//        isShowView = mainListHeaderView.findViewById(R.id.main_item_top_ih_show);
//
//        mainListHeaderView.setOnClickListener(this);
//        headerSetBudgetView.setOnClickListener(this);
//        isShowView.setOnClickListener(this);
//
//        setMainListViewLongClickListener();
//
//    }
//
//    /*
//     *
//     * 设置MainListView长按事件
//     *
//     * 删除或者以后添加其他功能
//     */
//    private void setMainListViewLongClickListener() {
//        mainListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                if ((position == 0)) {
//                    return false;
//                }
////                获取被点击的AccountBean类型对象
//                int pos = position - 1;
//                AccountBean delete_bean = accountBeanList.get(pos);
//
//                showDeleteDialog(delete_bean);
//                return false;
//            }
//        });
//    }
//
//    /*
//     *
//     * 弹出删除记录的Dialog
//     */
//    private void showDeleteDialog(AccountBean accountBean) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        builder.setTitle("提示").setMessage("确定删除该记录？")
//                .setNegativeButton("取消", null)
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
////                        删除数据库中这条记录
////                        更新MainListView中数据
////                        adapter.notifyDataSetChanged()
//                        int i = DBManager.deleteFromAccountTableById(accountBean.getId());
//                        accountBeanList.remove(accountBean);
//                        refreshListData();
//                        refreshHeaderData();
//                    }
//                });
////        显示Dialog
//        builder.create().show();
//    }
//
//    /*
//     *
//     * 获取当前时间
//     */
//    private void initTime() {
//        Calendar instance = Calendar.getInstance();
//        year = instance.get(Calendar.YEAR);
//        month = instance.get(Calendar.MONTH) + 1;
//        day = instance.get(Calendar.DAY_OF_MONTH);
//    }
//
//    /*
//     *
//     * MainActivity获取焦点时调用
//     *
//     * 每次回到主页面都要执行查找并刷新
//     *
//     * refreshListData: 从数据库查找记录
//     * refreshHeaderData: 从数据库查找并更新mainListHeaderView中的数据
//     *
//     * Transfer: List<AccountBean>
//     */
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        refreshListData();
//        refreshHeaderData();
//    }
//
//    @SuppressLint("SetTextI18n")
//    private void refreshHeaderData() {
//        float dayMoney_out = DBManager.getDaySumMoney(year, month, day, 0);
//        float dayMoney_in = DBManager.getDaySumMoney(year, month, day, 1);
//        String dayInfo = "今日支出 ￥" + dayMoney_out + "  今日收入 ￥" + dayMoney_in;
//        headerTodayView.setText(dayInfo);
//
//        float monthMoney_out = DBManager.getMonthSumMoney(year, month, 0);
//        float monthMoney_in = DBManager.getMonthSumMoney(year, month, 1);
//        headerInView.setText("￥" + monthMoney_in);
//        headerOutView.setText("￥" + monthMoney_out);
//
//        float budget = preferences.getFloat("budget", 0);
//        if ((budget == 0)) {
//            headerSetBudgetView.setText("￥0");
//            headerBudgetView.setText("￥0");
//        } else {
//            headerSetBudgetView.setText("￥" + budget);
//            float budget_res = budget - monthMoney_out;
//            headerBudgetView.setText("￥" + budget_res);
//        }
//
//    }
//
//    private void refreshListData() {
//
//        List<AccountBean> tmpList = DBManager.getDayAccountBeanFromAccountTable(year, month, day);
//        this.accountBeanList.clear();
//        this.accountBeanList.addAll(tmpList);
//        mainListAdapter.notifyDataSetChanged();
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (v == mainListHeaderView) {
//
//        }
//        switch (v.getId()) {
//            case R.id.main_search:
//                Intent searchIntent = new Intent(this, SearchActivity.class);
//                startActivity(searchIntent);
//                break;
//            case R.id.main_btn_edit:
////                跳转页面操作
//                Intent mainToRecordIntent = new Intent(this, RecordActivity.class);
//                startActivity(mainToRecordIntent);
//                break;
//            case R.id.main_item_top_month_setBudget:
//                showBudgetDialog();
//                break;
//            case R.id.main_item_top_ih_show:
//                isShowData();
//                break;
//        }
//    }
//
//    /*
//     *
//     * 显示设置预算BudgetDialog
//     */
//    @SuppressLint("SetTextI18n")
//    private void showBudgetDialog() {
//        String budget_tmp = headerSetBudgetView.getText().toString().replace("￥", "");
//        BudgetDialog budgetDialog = new BudgetDialog(this);
//        budgetDialog.show();
//        budgetDialog.setBudgetDialog();
//        budgetDialog.setEditText(budget_tmp);
//        budgetDialog.setOnEnsureListener((float budget) -> {
////            预算数值存储
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.putFloat("budget", budget);
//            editor.apply();
////            计算预算剩余
//            float monthMoney_out = DBManager.getMonthSumMoney(year, month, 0);
//            float budget_res = budget - monthMoney_out;
//            headerBudgetView.setText("￥" + budget_res);
//            headerSetBudgetView.setText("￥" + budget);
//        });
//    }
//
//    /*
//     *
//     * 是否显示
//     */
//    private void isShowData() {
//
//        if (isShow) {
////            得到密文对象
//            PasswordTransformationMethod method = PasswordTransformationMethod.getInstance();
//            headerInView.setTransformationMethod(method);
//            headerOutView.setTransformationMethod(method);
//            headerSetBudgetView.setTransformationMethod(method);
//            headerBudgetView.setTransformationMethod(method);
//            isShowView.setImageResource(R.mipmap.ih_hide);
//            isShow = false;
//        } else {
//            HideReturnsTransformationMethod method = HideReturnsTransformationMethod.getInstance();
//            headerInView.setTransformationMethod(method);
//            headerOutView.setTransformationMethod(method);
//            headerSetBudgetView.setTransformationMethod(method);
//            headerBudgetView.setTransformationMethod(method);
//            isShowView.setImageResource(R.mipmap.ih_show);
//            isShow = true;
//        }
//    }

    /*
     *
     *
     *
     * tally 2.0版本
     *
     */

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private final int[] imageId = {R.mipmap.home, R.mipmap.list, R.mipmap.detail, R.mipmap.setting};
    private final int[] selectImageId = {R.mipmap.home_green, R.mipmap.list_green, R.mipmap.detail_green, R.mipmap.setting_green};

    //    共享参数保存预算数值
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        查找控件
        tabLayout = findViewById(R.id.main_tab);
        viewPager = findViewById(R.id.main_viewPage);

        preferences = getSharedPreferences("previousPos", Context.MODE_PRIVATE);
//        加载页面初始化设置
        initPager();

    }

    private void initPager() {

        List<Fragment> fragmentList = new ArrayList<>();

        HomeFragment homeFragment = new HomeFragment();
        ListFragment listFragment = new ListFragment();
        DetailFragment detailFragment = new DetailFragment();
        SettingFragment settingFragment = new SettingFragment();

        fragmentList.add(homeFragment);
        fragmentList.add(listFragment);
        fragmentList.add(detailFragment);
        fragmentList.add(settingFragment);

//        创建MainPagerAdapter
        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), fragmentList);

        viewPager.setAdapter(mainPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorHeight(0);

//        tab设置图标和字体
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);

            if (tab != null) {
                tab.setCustomView(initTabView(tab, i));
            }
        }

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.main_tab_icon).setBackgroundResource(selectImageId[tab.getPosition()]);
                TextView tittleView = tab.getCustomView().findViewById(R.id.main_tab_title);
                tittleView.setTextColor(getResources().getColor(R.color.dark_green));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.main_tab_icon).setBackgroundResource(imageId[tab.getPosition()]);
                TextView tittleView = tab.getCustomView().findViewById(R.id.main_tab_title);
                tittleView.setTextColor(getResources().getColor(R.color.black));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });



        homeFragment.setOnMainListViewClick(() -> {
             /*
              *
              * 用tabLayout选择tab进行跳转
              */
            viewPager.setCurrentItem(2);
        });

        viewPager.setCurrentItem(0);


    }

    @SuppressLint("ResourceAsColor")
    private View initTabView(TabLayout.Tab tab, int position) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.main_tab_icon_view, null);
        ImageView iconView = view.findViewById(R.id.main_tab_icon);
        TextView textView = view.findViewById(R.id.main_tab_title);
        textView.setText(tab.getText());
        textView.setTextColor(view.getResources().getColor(R.color.black));
        iconView.setBackgroundResource(imageId[position]);
        if (position == 0) {
            iconView.setBackgroundResource(selectImageId[position]);
            textView.setTextColor(view.getResources().getColor(R.color.dark_green));
        }
        return view;
    }


}