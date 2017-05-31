package com.gdcp.yueyunku_business.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gdcp.yueyunku_business.R;
import com.gdcp.yueyunku_business.presenter.UpdatePlaceAddressPresenter;
import com.gdcp.yueyunku_business.presenter.impl.UpdatePlaceAddressImpl;
import com.gdcp.yueyunku_business.view.UpdatePlaceAddressView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

public class UpdatePlaceAddressActivity extends BaseActivity implements UpdatePlaceAddressView{


    @BindView(R.id.tv_tool_title)
    TextView tvToolTitle;
    @BindView(R.id.tv_send)
    TextView tvCommit;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.linearLayout_area)
    LinearLayout linearLayoutArea;
    @BindView(R.id.edt_detail_area)
    EditText edtDetailArea;
    private UpdatePlaceAddressPresenter updatePlaceAddressPresenter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_update_place_address;
    }

    @Override
    protected void init() {
        super.init();
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        String address=getIntent().getStringExtra("address");
        if (address.length()!=0){
            int position=address.lastIndexOf("-");
            String area=address.substring(0,position);
            String areaDetail=address.substring(position+1,address.length());
            tvArea.setText(area);
            edtDetailArea.setText(areaDetail);
        }
        tvToolTitle.setText(getString(R.string.address_place));
        tvCommit.setText(getString(R.string.commit));
        tvCommit.setVisibility(View.VISIBLE);
        initPresenter();

    }

    private void initPresenter() {
        updatePlaceAddressPresenter=new UpdatePlaceAddressImpl(this);
    }


    @OnClick({R.id.tv_send, R.id.linearLayout_area})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send:
                String areaName=tvArea.getText().toString();
                String detailAreaName=edtDetailArea.getText().toString();
                BmobUser user=BmobUser.getCurrentUser();
                updatePlaceAddressPresenter.updatePlaceAddress(areaName,detailAreaName,user.getObjectId());
                break;
            case R.id.linearLayout_area:
                Intent intent=new Intent(this,ChooseAreaActivity.class);
                startActivityForResult(intent,1001);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==7001){
            String address=data.getStringExtra("address");
            tvArea.setText(address);
        }
    }

    @Override
    public void invalidLength() {
        toast("详细地址的内容不能为空");
    }

    @Override
    public void updatePlaceAddressSucc() {
        toast("更新场馆地址成功");
        finish();
    }

    @Override
    public void updatePlaceAddressFailed() {
        toast("更新场馆地址失败");
    }
}
