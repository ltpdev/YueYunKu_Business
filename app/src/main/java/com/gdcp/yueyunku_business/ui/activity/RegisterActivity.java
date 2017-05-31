package com.gdcp.yueyunku_business.ui.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gdcp.yueyunku_business.R;
import com.gdcp.yueyunku_business.model.Constant;
import com.gdcp.yueyunku_business.presenter.RegisterPresenter;
import com.gdcp.yueyunku_business.presenter.impl.RegisterPresenterImpl;
import com.gdcp.yueyunku_business.utils.CountDownTimerUtils;
import com.gdcp.yueyunku_business.view.RegisterView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Asus on 2017/5/10.
 */

public class RegisterActivity extends BaseActivity implements RegisterView{


    @BindView(R.id.edt_phone)
    EditText mEdtPhone;
    @BindView(R.id.edt_set_pwd)
    EditText mEdtSetPwd;
    @BindView(R.id.edt_confirm_pwd)
    EditText mEdtConfirmPwd;
    @BindView(R.id.edt_verification_code)
    EditText mEdtVerificationCode;
    @BindView(R.id.btn_acq_code)
    Button mBtnAcqCode;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.tv_tool_title)
    TextView mTvToolTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.btn_register_business)
    Button btnRegisterBusiness;
    private String phoneNumber;
    private RegisterPresenter mPresenter;

   // private CountDownTimerUtils mTimerUtils;

    @Override
    protected void init() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mTvToolTitle.setText(getString(R.string.register));
        initPresenter();

    }

    private void initPresenter() {
        mPresenter=new RegisterPresenterImpl(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_register;
    }


    @OnClick({R.id.btn_acq_code, R.id.btn_register_business, R.id.tv_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_acq_code:
                //判断输入的号码是否为手机号
                phoneNumber=mEdtPhone.getText().toString();
                mPresenter.isPhoneNumber(phoneNumber);
                break;
            case R.id.btn_register_business:
                phoneNumber=mEdtPhone.getText().toString();
                String pwd=mEdtSetPwd.getText().toString();
                String confirmPassword=mEdtConfirmPwd.getText().toString();
                String code=mEdtVerificationCode.getText().toString();
                mPresenter.register(phoneNumber,pwd,confirmPassword,code);
                break;
            case R.id.tv_login:
                startActivity(LoginActivity.class, true);
                break;
        }
    }

    @Override
    public void onRegisterSuccessed() {
        hideProgress();
        //注册成功
        toast(getString(R.string.successed_register));
        /*startActivity(LoginActivity.class, true);*/
        finish();
    }

    @Override
    public void onRegisterFailed(String message) {
        hideProgress();
        //注册失败
        toast(getString(R.string.failed_register)+message);
    }

    @Override
    public void onUserNameError() {

    }

    @Override
    public void onPasswordError() {
        toast(getString(R.string.error_password));
    }

    @Override
    public void onConfirmpasswordError() {
        toast(getString(R.string.error_confirmpassword));
    }

    @Override
    public void onStartRegister() {
       showProgress(getString(R.string.registering));
    }

    @Override
    public void isPhoneNumber(String phoneNumber) {
        //获取验证码
        mPresenter.requestSMSCode(RegisterActivity.this,phoneNumber);
        //开启定时器
        CountDownTimerUtils cs=new CountDownTimerUtils(Constant.MILLISINFUTURE,Constant.COUNTDOWNINTERVAL,mBtnAcqCode,this);
        cs.start();
    }



    @Override
    public void isNotPhoneNumber() {
         //提示用户该号码为非手机号
        toast(getString(R.string.error_not_phonenumber));
    }

    @Override
    public void onSendMsgSuccess() {
        //成功发送验证码
        toast(getString(R.string.success_sendmsg));
    }

    @Override
    public void onSendMsgFailed(String message) {
        toast(message);
    }
}
