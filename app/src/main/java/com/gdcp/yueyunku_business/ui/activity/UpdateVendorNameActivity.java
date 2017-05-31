package com.gdcp.yueyunku_business.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gdcp.yueyunku_business.R;
import com.gdcp.yueyunku_business.presenter.UpdateVendorNamePresenter;
import com.gdcp.yueyunku_business.presenter.impl.UpdateVendorNameImpl;
import com.gdcp.yueyunku_business.view.UpdateVendorNameView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

public class UpdateVendorNameActivity extends BaseActivity implements UpdateVendorNameView{

    @BindView(R.id.tv_tool_title)
    TextView mTvToolTitle;
    @BindView(R.id.tv_send)
    TextView tvCommit;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.edt_update_name_vendor)
    EditText edtUpdateNameVendor;
    private UpdateVendorNamePresenter updateVendorNamePresenter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_update_vendor_name;
    }

    @Override
    protected void init() {
        super.init();
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        edtUpdateNameVendor.setText(getIntent().getStringExtra("vendorname"));
        mTvToolTitle.setText(getString(R.string.name_vendor));
        tvCommit.setText(getString(R.string.commit));
        tvCommit.setVisibility(View.VISIBLE);
        initPresenter();
    }

    private void initPresenter() {
        updateVendorNamePresenter=new UpdateVendorNameImpl(this);
    }


    @OnClick({R.id.tv_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send:
                    String name=edtUpdateNameVendor.getText().toString();
                    BmobUser user=BmobUser.getCurrentUser();
                    updateVendorNamePresenter.updateVendorName(name,user.getObjectId());
                break;
        }
    }

    @Override
    public void updateVendorNameSucc() {
        toast("修改商家名称成功");
        finish();
    }

    @Override
    public void updateVendorNameFailed() {
        toast("修改商家名称失败");
        finish();
    }

    @Override
    public void invalidLength() {
        toast("没有输入有效的长度，请重新填写");
    }
}
