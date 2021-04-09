package com.example.simplebookkeeping.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;


/*
 *
 *
 * Main Tabs Adapter
 */
public class MainPagerAdapter extends FragmentPagerAdapter {


    private final List<Fragment> fragmentList;
    private final String[] main_tab_titles = {"主页", "账单记录", "账单详情", "设置"};

    public MainPagerAdapter(@NonNull FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return main_tab_titles[position];
    }

}
