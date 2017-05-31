package com.gdcp.yueyunku_business.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gdcp.yueyunku_business.R;
import com.gdcp.yueyunku_business.presenter.UpdatePhonePresenter;
import com.gdcp.yueyunku_business.presenter.impl.UpdatePhoneImpl;
import com.gdcp.yueyunku_business.view.UpdatePhoneView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

public class UpdatePhoneActivity extends BaseActivity implements UpdatePhoneView{


    @BindView(R.id.tv_tool_title)
    TextView mTvToolTitle;
    @BindView(R.id.tv_send)
    TextView tvCommit;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.edt_update_phone_user)
    EditText edtUpdatePhoneUser;
    private UpdatePhonePresenter updatePhonePresenter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_update_phone;
    }

    @Override
    protected void init() {
        super.init();
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        edtUpdatePhoneUser.setText(getIntent().getStringExtra("phone"));
        mTvToolTitle.setText(getString(R.string.phone_user));
        tvCommit.setText(getString(R.string.commit));
        tvCommit.setVisibility(View.VISIBLE);
        initPresenter();
    }

    private void initPresenter() {
        updatePhonePresenter=new UpdatePhoneImpl(this);
    }


    @OnClick({R.id.tv_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send:
                String phone=edtUpdatePhoneUser.getText().toString();
                BmobUser user=BmobUser.getCurrentUser();
                updatePhonePresenter.updatePhone(phone,user.getObjectId());
                break;
        }
    }

    @Override
    public void invalidLength() {
        toast("没有输入有效的长度，请重新填写");
    }

    @Override
    public void updatePhoneSucc() {
        toast("修改用户联系电话成功");
        finish();
    }

    @Override
    public void updatePhoneFailed() {
        toast("修改用户联系电话失败");
        finish();
    }

    @Override
    public void isNotPhoneNumber() {
        toast("输入的号码不是手机号码");
    }
}
