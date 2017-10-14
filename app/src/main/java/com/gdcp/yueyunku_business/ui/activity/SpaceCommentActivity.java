package com.gdcp.yueyunku_business.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.gdcp.yueyunku_business.R;
import com.gdcp.yueyunku_business.adapter.SpaceCommentFragmentAdapter;
import com.gdcp.yueyunku_business.ui.fragment.SpaceCommentFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpaceCommentActivity extends BaseActivity {
/*先不用mvp模式
*
* */


    @BindView(R.id.titleLayout)
    TabLayout titleLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tv_tool_title)
    TextView tvToolTitle;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private List<Fragment> fragmentList;
    private List<String> titles;
    private SpaceCommentFragmentAdapter spaceCommentFragmentAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_space_comment;
    }

    @Override
    protected void init() {
        super.init();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        tvToolTitle.setText("场地评论");
        tvSend.setVisibility(View.GONE);
        fragmentList = new ArrayList<>();
        titles = new ArrayList<>();
        String[] strings = {"篮球", "排球", "羽毛球", "乒乓球", "足球", "网球", "瑜伽"};
        for (int i = 0; i < strings.length; i++) {
            SpaceCommentFragment spaceCommentFragment = new SpaceCommentFragment();
            spaceCommentFragment.setChannelName(strings[i]);
            fragmentList.add(spaceCommentFragment);
            titles.add(strings[i]);
        }
        spaceCommentFragmentAdapter = new SpaceCommentFragmentAdapter(getSupportFragmentManager(), fragmentList, titles);
        titleLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(spaceCommentFragmentAdapter);

    }


}
