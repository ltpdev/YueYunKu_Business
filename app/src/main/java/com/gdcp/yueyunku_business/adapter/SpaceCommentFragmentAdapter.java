package com.gdcp.yueyunku_business.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by asus- on 2017/10/13.
 */

public class SpaceCommentFragmentAdapter extends FragmentPagerAdapter{
    private List<Fragment>fragmentList;
    private List<String>titles;
    public SpaceCommentFragmentAdapter(FragmentManager fm, List<Fragment>fragmentList,List<String>titles) {
        super(fm);
        this.fragmentList=fragmentList;
        this.titles=titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
