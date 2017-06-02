package com.gdcp.yueyunku_business.ui.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.gdcp.yueyunku_business.R;
import com.gdcp.yueyunku_business.model.Constant;
import com.gdcp.yueyunku_business.presenter.LoginPresenter;
import com.gdcp.yueyunku_business.presenter.impl.LoginPresenterImpl;
import com.gdcp.yueyunku_business.view.LoginView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Asus on 2017/5/10.
 */

public class LoginActivity extends BaseActivity implements LoginView{
    @BindView(R.id.edt_phone)
    EditText mEdtPhone;
    @BindView(R.id.edt_pwd)
    EditText mEdtPwd;
    @BindView(R.id.tv_forgetpwd)
    TextView mTvForgetpwd;
    @BindView(R.id.tv_register)
    TextView mTvRegister;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.tv_tool_title)
    TextView mTvToolTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private LoginPresenter mPresenter;

    @Override
    protected void init() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mTvToolTitle.setText(getString(R.string.login));
        initPresenter();

    }

    private void initPresenter() {
        mPresenter=new LoginPresenterImpl(this);
    }

    @Override
    protected void setWindowFuture() {
        super.setWindowFuture();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }




    @OnClick({R.id.btn_login, R.id.tv_forgetpwd, R.id.tv_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String number=mEdtPhone.getText().toString();
                String pwd=mEdtPwd.getText().toString();
                showProgress(getString(R.string.logining));
                hideKeyBoard();
                mPresenter.login(number,pwd);
                break;
            case R.id.tv_forgetpwd:
                break;
            case R.id.tv_register:
                startActivity(RegisterActivity.class,false);
                break;
        }
    }

    @Override
    public void onLoginSuccess() {
        toast(getString(R.string.success_login));
        hideProgress();
       /* startActivity(MainActivity.class,true);*/
        String key=getIntent().getStringExtra(Constant.KEY_ONE);
        Intent intent=new Intent();
        intent.putExtra(Constant.KEY_TWO,key);
        setResult(Constant.LOGINACTIVITY_RESULT_CODE,intent);
        finish();
        //需要判断

    }

    @Override
    public void onLoginFailed(String message) {
          toast(getString(R.string.failed_login)+message);

          hideProgress();
    }

    @Override
    public void notBusiness() {
        hideProgress();
        toast(getString(R.string.notBusiness));
    }
}
