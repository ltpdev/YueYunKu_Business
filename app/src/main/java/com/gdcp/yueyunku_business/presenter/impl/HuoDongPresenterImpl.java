package com.gdcp.yueyunku_business.presenter.impl;

import com.gdcp.yueyunku_business.model.Activity;
import com.gdcp.yueyunku_business.model.User;
import com.gdcp.yueyunku_business.presenter.HuoDongPresenter;
import com.gdcp.yueyunku_business.presenter.UpdateUserNamePresenter;
import com.gdcp.yueyunku_business.view.HuoDongView;
import com.gdcp.yueyunku_business.view.UpdateUserNameView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by asus- on 2017/5/24.
 */

public class HuoDongPresenterImpl implements HuoDongPresenter{
    private HuoDongView huoDongView;
    public HuoDongPresenterImpl(HuoDongView huoDongView){
        this.huoDongView=huoDongView;
    }

    @Override
    public void query() {
         BmobQuery<Activity> query = new BmobQuery<Activity>();
        query.order("-updatedAt");
        query.setLimit(5);
        query.include("business");
        query.findObjects(new FindListener<Activity>() {
            @Override
            public void done(List<Activity> object, BmobException e) {
                if(e==null){
                    huoDongView.querySucc(object);
                }else{
                    huoDongView.queryFail();
                }
            }
        });
    }

    @Override
    public void loadMoreData(String lastTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=null;
        try {
            date=sdf.parse(lastTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        BmobQuery<Activity> query = new BmobQuery<Activity>();
        query.addWhereLessThan("updatedAt",new BmobDate(date));
        query.order("-updatedAt");
        query.include("business");
        query.setLimit(5);
        query.findObjects(new FindListener<Activity>() {
            @Override
            public void done(List<Activity> object, BmobException e) {
                if(e==null){
                    huoDongView.loadMoreSucc(object);
                }else{
                    huoDongView.loadMoreFail();
                }
            }
        });
    }
}
