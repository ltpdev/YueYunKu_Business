package com.gdcp.yueyunku_business.ui.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.gdcp.yueyunku_business.R;
import com.gdcp.yueyunku_business.adapter.DoingAdapter;
import com.gdcp.yueyunku_business.event.DingDanEvent;
import com.gdcp.yueyunku_business.listener.LookDoingActivityListener;
import com.gdcp.yueyunku_business.model.Order;
import com.gdcp.yueyunku_business.model.User;
import com.gdcp.yueyunku_business.ui.activity.DoingActivity;
import com.gdcp.yueyunku_business.ui.fragment.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Asus on 2017/5/22.
 */

public class DoingFragment extends BaseFragment implements LookDoingActivityListener{
    private DoingAdapter doingAdapter;
    @BindView(R.id.recycler_view_doing)
    RecyclerView mRecyclerViewDoing;
    private List<Order> orderList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewDoing.setLayoutManager(layoutManager);
        orderList=new ArrayList<>();
        doingAdapter = new DoingAdapter(orderList,getActivity());
        doingAdapter.setLookDoingActivityListener(this);
        mRecyclerViewDoing.setAdapter(doingAdapter);
        initData();
    }

    private void initData() {
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Order> query = new BmobQuery<Order>();
        query.addWhereEqualTo("business", user);    // 查询当前用户的所有帖子
        query.addWhereEqualTo("state_type", 1);
        query.include("business,joiner,space");
        query.order("-updatedAt");
        query.findObjects(new FindListener<Order>() {

            @Override
            public void done(List<Order> object,BmobException e) {
                if(e==null){
                    orderList.clear();
                    orderList.addAll(object);
                    doingAdapter.notifyDataSetChanged();
                }else{

                }
            }

        });

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_doing;
    }


    @Override
    public void lookActivity(Order order) {
        Intent intent = new Intent(getActivity(),DoingActivity.class);
        intent.putExtra("order",order);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DingDanEvent event) {
        initData();
    };
}