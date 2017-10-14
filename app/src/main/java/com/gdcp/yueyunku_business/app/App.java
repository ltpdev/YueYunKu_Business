package com.gdcp.yueyunku_business.app;

import android.app.Application;
import android.widget.Toast;

import com.gdcp.yueyunku_business.callback.EmptyCallback;
import com.gdcp.yueyunku_business.callback.LoadingCallback;
import com.gdcp.yueyunku_business.model.Order;
import com.kingja.loadsir.core.LoadSir;

import org.litepal.LitePalApplication;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.sms.BmobSMS;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.b.I;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;

/**
 * Created by asus- on 2017/5/21.
 */

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "fc17e13ac860610cf084c738f247acad");
        LitePalApplication.initialize(this);
        LoadSir.beginBuilder()
                .addCallback(new LoadingCallback())
                .addCallback(new EmptyCallback())
                .setDefaultCallback(LoadingCallback.class)//设置默认状态页
                .commit();

    }

 /*   private void updateManyData() {
        new BmobBatch().updateBatch(orders).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> o, BmobException e) {
                if(e==null){
                    for(int i=0;i<o.size();i++){
                        BatchResult result = o.get(i);
                        BmobException ex =result.getError();
                        if(ex==null){
                            //log("第"+i+"个数据批量更新成功："+result.getUpdatedAt());
                        }else{
                            //log("第"+i+"个数据批量更新失败："+ex.getMessage()+","+ex.getErrorCode());
                        }
                    }
                }else{
                    //Toast.makeText(App.this, "失败:"+e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }




    private void updateData() {
        Toast.makeText(this, objectIdList.size()+"dd", Toast.LENGTH_SHORT).show();
        for (int i = 0; i <objectIdList.size(); i++) {
            Order order=new Order();
            order.setObjectId(objectIdList.get(i));
            order.setState_type(3);
            orders.add(order);
            Toast.makeText(this, orders.size()+"dd", Toast.LENGTH_SHORT).show();
        }

        updateManyData();
    }

    private void queryData() {
        BmobQuery<Order> query = new BmobQuery<Order>();
        query.addWhereNotEqualTo("state_type", 3);
        query.findObjects(new FindListener<Order>() {

            @Override
            public void done(List<Order> object,BmobException e) {
                if(e==null){
                   *//* objectIdList.clear();*//*
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String dateNowStr = sdf.format(new Date());
                    Toast.makeText(App.this, dateNowStr, Toast.LENGTH_SHORT).show();
                    for (int i = 0; i <object.size(); i++) {
                        try {
                            String bookTime=object.get(i).getBook_time();
                            Date dNowTime = sdf.parse(dateNowStr);
                            Date dBookTime = sdf.parse(bookTime);
                            if (dNowTime.getTime()>dBookTime.getTime()){
                                objectIdList.add(object.get(i).getObjectId());
                            }
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    }


                }else{

                }
            }

        });
    }*/
}
