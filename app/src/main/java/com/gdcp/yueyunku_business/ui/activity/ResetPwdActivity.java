package com.gdcp.yueyunku_business.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gdcp.yueyunku_business.R;
import com.gdcp.yueyunku_business.model.Constant;
import com.gdcp.yueyunku_business.presenter.ResetPwdPresenter;
import com.gdcp.yueyunku_business.presenter.impl.ResetPwdPresenterImpl;
import com.gdcp.yueyunku_business.utils.CountDownTimerUtils;
import com.gdcp.yueyunku_business.view.ResetPwdView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetPwdActivity extends BaseActivity implements ResetPwdView{


    @BindView(R.id.tv_tool_title)
    TextView mTvToolTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_set_new_pwd)
    EditText edtSetNewPwd;
    @BindView(R.id.edt_verification_code)
    EditText edtVerificationCode;
    @BindView(R.id.btn_acq_code)
    Button btnAcqCode;
    @BindView(R.id.btn_reset_pwd)
    Button btnResetPwd;
    private ResetPwdPresenter resetPwdPresenter;
    private String phoneNumber;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_reset_pwd;
    }

    @Override
    protected void init() {
        super.init();
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mTvToolTitle.setText(getString(R.string.pwd_reset));
        initPresenter();
    }

    private void initPresenter() {
        resetPwdPresenter=new ResetPwdPresenterImpl(this);
    }


    @OnClick({R.id.btn_acq_code, R.id.btn_reset_pwd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_acq_code:
                phoneNumber=edtPhone.getText().toString();
                resetPwdPresenter.isPhoneNumber(phoneNumber);
                break;
            case R.id.btn_reset_pwd:
                phoneNumber=edtPhone.getText().toString();
                String pwd=edtSetNewPwd.getText().toString();
                String code=edtVerificationCode.getText().toString();
                resetPwdPresenter.resetPwd(phoneNumber,pwd,code);
                break;
        }
    }

    @Override
    public void onPasswordError() {
        toast(getString(R.string.error_password));
    }

    @Override
    public void onStartRegister() {
        showProgress(getString(R.string.reseting));
    }

    @Override
    public void isPhoneNumber(String phoneNumber) {
        //获取验证码
        resetPwdPresenter.requestSMSCode(ResetPwdActivity.this,phoneNumber);
        //开启定时器
        CountDownTimerUtils cs=new CountDownTimerUtils(Constant.MILLISINFUTURE,Constant.COUNTDOWNINTERVAL,btnAcqCode,this);
        cs.start();
    }

    @Override
    public void isNotPhoneNumber() {
        toast(getString(R.string.error_not_phonenumber));
    }

    @Override
    public void onSendMsgSuccess() {
        toast(getString(R.string.success_sendmsg));
    }

    @Override
    public void onSendMsgFailed(String message) {
        toast(message);
    }

    @Override
    public void resetPwdSucc() {
        hideProgress();
        toast(getString(R.string.reset_pwd_succ));
        finish();
    }

    @Override
    public void resetPwdFail(String message) {
        toast(getString(R.string.reset_pwd_fail)+message);
        hideProgress();
    }

    @Override
    public void codeLengthError() {
        toast(getString(R.string.code_error));
    }
}
