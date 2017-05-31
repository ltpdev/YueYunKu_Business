package com.gdcp.yueyunku_business.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Asus on 2017/5/22.
 */

public class MyFragmentPageAdapter extends FragmentPagerAdapter{

    private String[] titles={"还未处理的订单","正在进行的订单","已经结束的订单"};
    private List<Fragment> mFragments;
    public MyFragmentPageAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        mFragments=fragments;

    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
