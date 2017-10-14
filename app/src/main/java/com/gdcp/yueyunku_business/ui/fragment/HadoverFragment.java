package com.gdcp.yueyunku_business.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gdcp.yueyunku_business.R;
import com.gdcp.yueyunku_business.adapter.HadOverAdapter;
import com.gdcp.yueyunku_business.adapter.NodoAdapter;
import com.gdcp.yueyunku_business.callback.EmptyCallback;
import com.gdcp.yueyunku_business.event.DingDanEvent;
import com.gdcp.yueyunku_business.listener.LookHadOverActivityListener;
import com.gdcp.yueyunku_business.model.Order;
import com.gdcp.yueyunku_business.model.User;
import com.gdcp.yueyunku_business.ui.activity.HadOverActivity;
import com.gdcp.yueyunku_business.ui.activity.NoDoActivity;
import com.gdcp.yueyunku_business.utils.PostUtil;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.Convertor;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

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

public class HadoverFragment extends BaseFragment implements LookHadOverActivityListener{
    private HadOverAdapter hadOverAdapter;
    @BindView(R.id.recycler_view_had_over)
    RecyclerView mRecyclerViewHadOver;
    private List<Order> orderList;
    private LoadService loadService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void init() {
        initLoadSir();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewHadOver.setLayoutManager(layoutManager);
        orderList=new ArrayList<>();
        hadOverAdapter = new HadOverAdapter(orderList,getActivity());
        hadOverAdapter.setLookHadOverActivityListener(this);
        mRecyclerViewHadOver.setAdapter(hadOverAdapter);
        initData();
    }
    private void initLoadSir() {
        loadService = LoadSir.getDefault().register(mRecyclerViewHadOver, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                //loadService.showCallback(LoadingCallback.class);
                //reloadData();

            }}, new Convertor<Integer>() {
            @Override
            public Class<? extends Callback> map(Integer integer) {
                Class<? extends Callback> resultCode = SuccessCallback.class;
                switch (integer) {
                    case 100://成功回调
                        resultCode = SuccessCallback.class;
                        break;
                    case 0:
                        resultCode = EmptyCallback.class;
                        break;
                }
                return resultCode;
            }
        });
    }
    private void initData() {
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Order> query = new BmobQuery<Order>();
        query.addWhereEqualTo("business", user);    // 查询当前用户的所有帖子
        query.addWhereEqualTo("state_type", 3);
        query.include("business,joiner");
        query.order("-updatedAt");
        query.findObjects(new FindListener<Order>() {

            @Override
            public void done(List<Order> object,BmobException e) {
                if(e==null){
                    if (object.size()!=0){
                        PostUtil.postCodeDelayed(loadService,100,1000);
                        orderList.clear();
                        orderList.addAll(object);
                        hadOverAdapter.notifyDataSetChanged();
                    }else {
                        PostUtil.postCodeDelayed(loadService,0,1000);
                    }

                }else{

                }
            }

        });

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_had_over;
    }


    @Override
    public void lookActivity(Order order) {
        Intent intent = new Intent(getActivity(),HadOverActivity.class);
        intent.putExtra("order",order);
        startActivity(intent);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DingDanEvent event) {
        initData();
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
