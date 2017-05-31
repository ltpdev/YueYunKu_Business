package com.gdcp.yueyunku_business.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.gdcp.yueyunku_business.R;
import com.gdcp.yueyunku_business.model.Order;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HadOverActivity extends BaseActivity {


    @BindView(R.id.tv_tool_title)
    TextView tvToolTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_name_place)
    TextView tvNamePlace;
    @BindView(R.id.tv_type_sport)
    TextView tvTypeSport;
    @BindView(R.id.icon_free_money)
    ImageView iconFreeMoney;
    @BindView(R.id.tv_price_place)
    TextView tvPricePlace;
    @BindView(R.id.tv_time_begin)
    TextView tvTimeBegin;
    @BindView(R.id.tv_label_address_place)
    TextView tvLabelAddressPlace;
    @BindView(R.id.tv_address_place)
    TextView tvAddressPlace;
    @BindView(R.id.tv_name_participant)
    TextView tvNameParticipant;
    @BindView(R.id.tv_phone_participant)
    TextView tvPhoneParticipant;
    @BindView(R.id.tv_age_participant)
    TextView tvAgeParticipant;
    private Order order = null;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_had_over;
    }

    @Override
    protected void init() {
        super.init();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        tvToolTitle.setText(getString(R.string.look_dingdan_hadover));
        order = (Order) getIntent().getSerializableExtra("order");
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



}
