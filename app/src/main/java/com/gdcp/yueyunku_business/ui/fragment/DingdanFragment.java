package com.gdcp.yueyunku_business.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.gdcp.yueyunku_business.R;
import com.gdcp.yueyunku_business.adapter.MyFragmentPageAdapter;
import com.gdcp.yueyunku_business.app.Common;
import com.gdcp.yueyunku_business.factory.FragmentFactory;
import com.gdcp.yueyunku_business.ui.activity.LoginActivity;
import com.gdcp.yueyunku_business.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobUser;

/**
 * Created by Asus on 2017/5/22.
 */

public class DingdanFragment extends BaseFragment {
    @BindView(R.id.viewPager)
    ViewPager mPager;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    private MyFragmentPageAdapter adapter;
    private List<Fragment> mFragments=new ArrayList<>();
    @Override
    protected void init() {
        mFragments.add(FragmentFactory.getInstance().getNodoFragment());
        mFragments.add(FragmentFactory.getInstance().getDoingFragment());
        mFragments.add(FragmentFactory.getInstance().getHadoverFragment());
        adapter=new MyFragmentPageAdapter(getActivity().getSupportFragmentManager(),mFragments);
        mPager.setAdapter(adapter);
        mPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mPager);
    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_dingdan;
    }
}
