package com.example.simplebookkeeping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.example.simplebookkeeping.adapter.RecordPagerAdapter;
import com.example.simplebookkeeping.fragment.InRecordFragment;
import com.example.simplebookkeeping.fragment.OutRecordFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
//        查找控件
        tabLayout = findViewById(R.id.record_tab);
        viewPager = findViewById(R.id.record_viewPage);
//        加载页面初始化设置
        initPager();
    }

    private void initPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        OutRecordFragment fragmentOut = new OutRecordFragment();
        InRecordFragment fragmentIn = new InRecordFragment();

        fragmentList.add(fragmentOut);
        fragmentList.add(fragmentIn);
//        创建adapter
        RecordPagerAdapter recordPagerAdapter = new RecordPagerAdapter(getSupportFragmentManager(), fragmentList);
//        设置adapter
        viewPager.setAdapter(recordPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.record_return:
                finish();
                break;
        }
    }
}