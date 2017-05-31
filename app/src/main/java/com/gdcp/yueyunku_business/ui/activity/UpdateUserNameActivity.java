package com.gdcp.yueyunku_business.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gdcp.yueyunku_business.R;
import com.gdcp.yueyunku_business.presenter.UpdatePhonePresenter;
import com.gdcp.yueyunku_business.presenter.UpdateUserNamePresenter;
import com.gdcp.yueyunku_business.presenter.impl.UpdatePhoneImpl;
import com.gdcp.yueyunku_business.presenter.impl.UpdateUserNameImpl;
import com.gdcp.yueyunku_business.view.UpdatePhoneView;
import com.gdcp.yueyunku_business.view.UpdateUserNameView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

public class UpdateUserNameActivity extends BaseActivity implements UpdateUserNameView{


    @BindView(R.id.tv_tool_title)
    TextView mTvToolTitle;
    @BindView(R.id.tv_send)
    TextView tvCommit;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.edt_update_name_user)
    EditText edtUpdateNameUser;
    private UpdateUserNamePresenter updateUserNamePresenter;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_update_user_name;
    }

    @Override
    protected void init() {
        super.init();
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        edtUpdateNameUser.setText(getIntent().getStringExtra("username"));
        mTvToolTitle.setText(getString(R.string.name_user));
        tvCommit.setText(getString(R.string.commit));
        tvCommit.setVisibility(View.VISIBLE);
        initPresenter();
    }

    private void initPresenter() {
        updateUserNamePresenter=new UpdateUserNameImpl(this);
    }


    @OnClick({R.id.tv_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send:
                String name=edtUpdateNameUser.getText().toString();
                BmobUser user=BmobUser.getCurrentUser();
                updateUserNamePresenter.updateUserName(name,user.getObjectId());
                break;
        }
    }

    @Override
    public void invalidLength() {
        toast("没有输入有效的长度，请重新填写");
    }

    @Override
    public void updateUserNameSucc() {
        toast("修改商家用户名成功");
        finish();
    }

    @Override
    public void updateUserNameNameFailed() {
        toast("修改商家用户名失败");
        finish();
    }
}
