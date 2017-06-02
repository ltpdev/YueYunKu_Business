package com.gdcp.yueyunku_business.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gdcp.yueyunku_business.R;
import com.gdcp.yueyunku_business.adapter.HuodongAdapter;
import com.gdcp.yueyunku_business.event.PublishSuccessEvent;
import com.gdcp.yueyunku_business.listener.LookActivityListener;
import com.gdcp.yueyunku_business.model.Activity;
import com.gdcp.yueyunku_business.model.User;
import com.gdcp.yueyunku_business.presenter.HuoDongPresenter;
import com.gdcp.yueyunku_business.presenter.impl.HuoDongPresenterImpl;
import com.gdcp.yueyunku_business.ui.activity.DetailActivity;
import com.gdcp.yueyunku_business.ui.activity.LoginActivity;
import com.gdcp.yueyunku_business.ui.activity.SendActivity;
import com.gdcp.yueyunku_business.view.HuoDongView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.sms.listener.InitListener;
import cn.bmob.v3.BmobUser;

/**
 * Created by Asus on 2017/5/21.
 */

public class HuodongFragment extends BaseFragment implements HuoDongView, LookActivityListener{

    @BindView(R.id.tv_tool_title)
    TextView tvToolTitle;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.recycler_view_huodong)
    XRecyclerView recyclerViewHuodong;
    private HuodongAdapter mAdapter;
    private List<Activity> activityList;
    private HuoDongPresenter huoDongPresenter;
    private String lastTime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_huodong;
    }

    @Override
    protected void init() {
        initToolbar();
        initPresenter();
        activityList = new ArrayList<Activity>();
        mAdapter = new HuodongAdapter(activityList, getActivity());
        mAdapter.setLookActivityListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewHuodong.setLayoutManager(layoutManager);
        recyclerViewHuodong.setAdapter(mAdapter);
        initListener();
        initData();
    }

    private void initListener() {
        recyclerViewHuodong.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        recyclerViewHuodong.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerViewHuodong.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        initData();
                        recyclerViewHuodong.refreshComplete();
                    }

                }, 1000);
            }

            @Override
            public void onLoadMore() {
                // load more data here
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        huoDongPresenter.loadMoreData(lastTime);
                        recyclerViewHuodong.loadMoreComplete();
                    }

                }, 1000);

            }
        });
    }

    private void initPresenter() {
        huoDongPresenter = new HuoDongPresenterImpl(this);
    }

    private void initData() {
        huoDongPresenter.query();
    }

    private void initToolbar() {
        tvToolTitle.setText(getString(R.string.my_activity));
        tvSend.setVisibility(View.VISIBLE);
        tvSend.setText(getString(R.string.fabu));
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUploadHeadSuccessEvent(PublishSuccessEvent event) {
        if (event != null) {
            toast(event.getMsg());
            initData();
        }
    }


    @OnClick({R.id.tv_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send:
                BmobUser user = BmobUser.getCurrentUser();
                if (user != null) {
                    startActivity(SendActivity.class,false);
                } else {
                    toast("你还没登录");
                    startActivity(LoginActivity.class, false);
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void querySucc(List<Activity> object) {
        activityList.clear();
        if (object.size()!=0){
            lastTime=object.get(object.size()-1).getUpdatedAt();
            for (Activity activity : object) {
                activityList.add(activity);
            }
        }else {
            toast(getString(R.string.data_empty));
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void queryFail() {
        toast(getString(R.string.queryFail));
    }

    @Override
    public void loadMoreSucc(List<Activity> object) {
        if (object.size()!=0) {
            lastTime = object.get(object.size() - 1).getUpdatedAt();
            for (Activity activity : object) {
                activityList.add(activity);
            }
            mAdapter.notifyDataSetChanged();
        }else {
            toast(getString(R.string.data_end));
        }
    }

    @Override
    public void loadMoreFail() {
        toast(getString(R.string.Fail_loadMore));
    }

    @Override
    public void lookActivity(Activity activity) {
         /* startActivity(DetailActivity.class,false);*/
        Intent intent = new Intent();
        intent.setClass(getActivity(), DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("activity", activity);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
