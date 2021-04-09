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
 * Record Tabs Adapter
 */
public class RecordPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragmentList;
    private final String[] record_tab_titles = {"支出", "收入"};

    public RecordPagerAdapter(@NonNull FragmentManager fm, List<Fragment> fragmentList) {
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
        return record_tab_titles[position];
    }
}
