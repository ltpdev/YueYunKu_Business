package com.gdcp.yueyunku_business.ui.activity;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gdcp.yueyunku_business.R;
import com.gdcp.yueyunku_business.event.DingDanEvent;
import com.gdcp.yueyunku_business.model.Order;
import com.gdcp.yueyunku_business.model.Space;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class NoDoActivity extends BaseActivity {


    @BindView(R.id.tv_tool_title)
    TextView tvToolTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_name_place)
    TextView tvNamePlace;
    @BindView(R.id.tv_type_sport)
    TextView tvTypeSport;
    @BindView(R.id.tv_price_place)
    TextView tvPricePlace;
    @BindView(R.id.btn_agree)
    Button btnAgree;
    @BindView(R.id.tv_time_begin)
    TextView tvTimeBegin;
    @BindView(R.id.tv_address_place)
    TextView tvAddressPlace;
    @BindView(R.id.btn_disagree)
    Button btnDisagree;
    @BindView(R.id.tv_name_participant)
    TextView tvNameParticipant;
    @BindView(R.id.tv_phone_participant)
    TextView tvPhoneParticipant;
    @BindView(R.id.tv_age_participant)
    TextView tvAgeParticipant;
    private Order order =null;
   /* private Integer hasBooks=0;*/

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_no_do;
    }

    @Override
    protected void init() {
        super.init();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        tvToolTitle.setText(getString(R.string.look_dingdan_nodo));
        order = (Order) getIntent().getSerializableExtra("order");
     /*   if (order.getSpace().getHadBooks()!=null){
            hasBooks=order.getSpace().getHadBooks();
        }*/

        if (order != null) {
            tvNamePlace.setText(order.getBusiness().getVendorName());
            tvTypeSport.setText(order.getSport_type());
            tvPricePlace.setText(order.getPrice() + "");
            tvTimeBegin.setText(order.getBook_time());
            tvAddressPlace.setText(order.getBusiness().getArea());
            tvNameParticipant.setText(order.getJoiner().getUsername());
            tvPhoneParticipant.setText(order.getJoiner().getMobilePhoneNumber());
            tvAgeParticipant.setText(order.getJoiner().getAge() + "");
        }
    }


    @OnClick({R.id.btn_agree, R.id.btn_disagree})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_agree:
                final Order order1 = new Order();
                order1.setState_type(1);
                order1.update(order.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                           // EventBus.getDefault().post(new DingDanEvent("成功执行操作"));
                           /* hasBooks++;*/
                            updateSpace(order.getSpace().getObjectId());
                        } else {

                        }
                    }

                });
                break;
            case R.id.btn_disagree:
                Order order2 = new Order();
                order2.setState_type(2);
                order2.update(order.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            //EventBus.getDefault().post(new DingDanEvent("成功执行操作"));
                            finish();
                        } else {

                        }
                    }

                });
                break;
        }
    }

    private void updateSpace(String objectId) {
        Space space=new Space();
        space.increment("hadBooks");
        space.update(objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    finish();
                }else {
                    toast(e.getMessage());
                }
            }
        });


    }
}
