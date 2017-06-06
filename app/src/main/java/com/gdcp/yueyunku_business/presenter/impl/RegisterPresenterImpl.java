package com.gdcp.yueyunku_business.presenter.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.gdcp.yueyunku_business.model.User;
import com.gdcp.yueyunku_business.presenter.RegisterPresenter;

import com.gdcp.yueyunku_business.utils.StringUtils;
import com.gdcp.yueyunku_business.view.RegisterView;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import static android.R.attr.name;

/**
 * Created by asus- on 2017/5/21.
 */

public class RegisterPresenterImpl implements RegisterPresenter{
    private RegisterView registerView;
    public RegisterPresenterImpl(RegisterView registerView){
        this.registerView=registerView;
    }
    @Override
    public void register(String phoneNumber, String password, String confirmpassword, String verificationCode) {
            if (StringUtils.isPhoneNumber(phoneNumber)){
                if (StringUtils.isVailedPassword(password)) {
                    if (password.equals(confirmpassword)) {
                        if (verificationCode.length()!=0){
                            registerView.onStartRegister();
                            registerToBmob(phoneNumber, password,verificationCode);
                        }else{
                            registerView.code_error();
                        }

                    } else {
                        registerView.onConfirmpasswordError();
                    }
                } else {
                    registerView.onPasswordError();
                }
            }else {
                registerView.isNotPhoneNumber();
            }


    }

    private void registerToBmob(String phoneNumber, final String password,final String verificationCode) {
        User user = new User();
        user.setUsername(phoneNumber);
        user.setType(1);
        user.setMobilePhoneNumber(phoneNumber);
        user.setMobilePhoneNumberVerified(true);
        user.setPassword(password);
        user.signOrLogin(verificationCode, new SaveListener<User>() {
            @Override
            public void done(User user, cn.bmob.v3.exception.BmobException e) {
                if(e==null){
                    registerView.onRegisterSuccessed();
                }else{
                    registerView.onRegisterFailed(e.getMessage());
                }

            }
        });

    }

    @Override
    public void isPhoneNumber(String phoneNumber) {
        if (StringUtils.isPhoneNumber(phoneNumber)){
            registerView.isPhoneNumber(phoneNumber);
        }else {
            registerView.isNotPhoneNumber();
        }
    }

    @Override
    public void requestSMSCode(final Context context, String phoneNumber) {

        BmobSMS.requestSMSCode(phoneNumber, "悦运酷",new QueryListener<Integer>(){

            @Override
            public void done(Integer integer, cn.bmob.v3.exception.BmobException e) {
                if(e==null){
                    registerView.onSendMsgSuccess();
                }else {
                    registerView.onSendMsgFailed(e.getMessage());
                }
            }


        });
    }


}
