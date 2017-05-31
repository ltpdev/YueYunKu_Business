package com.gdcp.yueyunku_business.app;

import android.app.Application;

import com.gdcp.yueyunku_business.model.Order;

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
    private List<String>objectIdList;
    List<BmobObject> orders;
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(getApplicationContext(), "fc17e13ac860610cf084c738f247acad");
        LitePalApplication.initialize(this);
        objectIdList=new ArrayList<>();
        orders =new ArrayList<BmobObject>();
        queryData();
        updateData();
        updateManyData();
    }

    private void updateManyData() {
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
                    /*Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());*/
                }
            }
        });
    }

    private void updateData() {
        orders.clear();
        for (int i = 0; i <objectIdList.size(); i++) {
            Order order=new Order();
            order.setObjectId(objectIdList.get(i));
            order.setState_type(3);
            orders.add(order);
        }

    }

    private void queryData() {
        BmobQuery<Order> query = new BmobQuery<Order>();
        query.addWhereNotEqualTo("state_type", 3);
        query.findObjects(new FindListener<Order>() {

            @Override
            public void done(List<Order> object,BmobException e) {
                if(e==null){
                    objectIdList.clear();
                    for (int i = 0; i <object.size(); i++) {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            String dateNowStr = sdf.format(new Date());
                            String bookTime=object.get(i).getBook_time();
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            Date dNowTime = df.parse(dateNowStr);
                            Date dBookTime = df.parse(bookTime);
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
    }
}