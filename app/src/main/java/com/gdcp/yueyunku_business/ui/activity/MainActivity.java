package com.gdcp.yueyunku_business.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gdcp.yueyunku_business.R;
import com.gdcp.yueyunku_business.app.App;
import com.gdcp.yueyunku_business.event.DingDanEvent;
import com.gdcp.yueyunku_business.event.PublishSuccessEvent;
import com.gdcp.yueyunku_business.event.UploadHeadSuccessEvent;
import com.gdcp.yueyunku_business.factory.FragmentController;
import com.gdcp.yueyunku_business.factory.FragmentFactory;
import com.gdcp.yueyunku_business.model.Constant;
import com.gdcp.yueyunku_business.model.Order;
import com.gdcp.yueyunku_business.model.User;
import com.gdcp.yueyunku_business.presenter.MainPresenter;
import com.gdcp.yueyunku_business.presenter.impl.MainPresenterImpl;
import com.gdcp.yueyunku_business.view.MainView;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.ValueEventListener;
import me.iwf.photopicker.PhotoPicker;

public class MainActivity extends FragmentActivity implements MainView{
    private FragmentManager fragmentManager;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;
    private FragmentController controller;
    private MainPresenter mainPresenter;
    private BmobUser bmobUser;
    BmobRealTimeData data = new BmobRealTimeData();
    private List<String> objectIdList;
    private List<BmobObject> orders;
   /* @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        super.init();
        fragmentManager = getSupportFragmentManager();
        bottomBar.setOnTabSelectListener(onTabSelectListener);

    }

*/


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        objectIdList=new ArrayList<>();
        orders =new ArrayList<BmobObject>();
        //查询类型不等于3的订单，判断是否过期
        queryData();
        //updateData();
        init();
        listto();
    }

    private void listto() {
        data.start(new ValueEventListener(){

            @Override
            public void onConnectCompleted(Exception e) {
                   if (data.isConnected()){
                       data.subTableUpdate("Order");
                   }
            }

            @Override
            public void onDataChange(JSONObject jsonObject) {
                 EventBus.getDefault().post(new DingDanEvent("ddd"));
            }
        });
    }

    private void init() {
        bmobUser=BmobUser.getCurrentUser();
        initPresenter();
        controller = FragmentController.getInstance(this, R.id.fragment_contain);
        controller.showFragment(0);
        fragmentManager = getSupportFragmentManager();
        bottomBar.setOnTabSelectListener(onTabSelectListener);
    }

    private void initPresenter() {
        mainPresenter=new MainPresenterImpl(this);
    }

    private OnTabSelectListener onTabSelectListener = new OnTabSelectListener() {
        @Override
        public void onTabSelected(@IdRes int tabId) {

            switch (tabId) {
                case R.id.tab_activity:
                    //两个Fragment相互切换
                    controller.showFragment(0);
                    break;
                case R.id.tab_dingdan:
                    mainPresenter.judgeCurrentUser();
                    break;
                case R.id.tab_me:
                    controller.showFragment(3);
                    break;
            }
           /* FragmentTransaction transaction=fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_contain, FragmentFactory.getInstance().getFragment(tabId));
            transaction.commit();*/
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FragmentController.destoryController();
        data.unsubTableUpdate("Order");
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void curUserExist(boolean b) {
        if (b){
            controller.showFragment(1);
        }else {
            controller.showFragment(2);
        }
    }

    @Override
    public void uploadPhotoSuccess(String fileUrl) {
        Toast.makeText(this, "上传图片成功", Toast.LENGTH_SHORT).show();
        //修改用户信息
        mainPresenter.updatePhoto(fileUrl,bmobUser.getObjectId());

    }

    @Override
    public void uploadPhotoFailed() {
        Toast.makeText(this, "上传图片失败", Toast.LENGTH_SHORT).show();
    }
    //修改成功后返回图片地址
    @Override
    public void updatePhotoSucc(String fileUrl) {
        //发送enentbus
        EventBus.getDefault().post(new UploadHeadSuccessEvent(fileUrl));
    }

    @Override
    public void updatePhotoFailed() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Constant.LOGINACTIVITY_RESULT_CODE){
            String type=data.getStringExtra(Constant.KEY_TWO);
            if (type.equals(Constant.VALUE_TWO)){
                controller.showFragment(1);
            }
        }

        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                mainPresenter.uploadPhoto(photos.get(0));
            }
        }
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
                    //Toast.makeText(App.this, "失败:"+e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }




    private void updateData() {
        for (int i = 0; i <objectIdList.size(); i++) {
            Order order=new Order();
            order.setObjectId(objectIdList.get(i));
            order.setState_type(3);
            orders.add(order);
        }
//批量更新
        updateManyData();
    }

    private void queryData() {
        BmobQuery<Order> query = new BmobQuery<Order>();
        query.addWhereNotEqualTo("state_type", 3);
        query.findObjects(new FindListener<Order>() {

            @Override
            public void done(List<Order> object,BmobException e) {
                if(e==null){
                   /* objectIdList.clear();*/
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String dateNowStr = sdf.format(new Date());
                    for (int i = 0; i <object.size(); i++) {
                        try {
                            String bookTime=object.get(i).getBook_time();
                            Date dNowTime = sdf.parse(dateNowStr);
                            Date dBookTime = sdf.parse(bookTime);
                            if (dNowTime.getTime()-dBookTime.getTime()>0){
                                objectIdList.add(object.get(i).getObjectId());
                            }
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    }
                    //修改了类型不等于3为3
                    updateData();
                }else{

                }
            }

        });
    }



}
