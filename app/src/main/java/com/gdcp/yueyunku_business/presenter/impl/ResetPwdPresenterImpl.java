package com.gdcp.yueyunku_business.presenter.impl;

import android.content.Context;
import android.util.Log;

import com.gdcp.yueyunku_business.model.User;
import com.gdcp.yueyunku_business.presenter.RegisterPresenter;
import com.gdcp.yueyunku_business.presenter.ResetPwdPresenter;
import com.gdcp.yueyunku_business.utils.StringUtils;
import com.gdcp.yueyunku_business.view.RegisterView;
import com.gdcp.yueyunku_business.view.ResetPwdView;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by asus- on 2017/5/21.
 */

public class ResetPwdPresenterImpl implements ResetPwdPresenter{
private ResetPwdView resetPwdView;
   public ResetPwdPresenterImpl(ResetPwdView resetPwdView){
        this.resetPwdView=resetPwdView;
    }

    @Override
    public void isPhoneNumber(String phoneNumber) {
        if (StringUtils.isPhoneNumber(phoneNumber)){
            resetPwdView.isPhoneNumber(phoneNumber);
        }else {
            resetPwdView.isNotPhoneNumber();
        }
    }

    @Override
    public void requestSMSCode(Context context, String phoneNumber) {
        BmobSMS.requestSMSCode(phoneNumber, "悦运酷",new QueryListener<Integer>() {

            @Override
            public void done(Integer integer, cn.bmob.v3.exception.BmobException e) {
                if(e==null){
                    resetPwdView.onSendMsgSuccess();
                }else {
                    resetPwdView.onSendMsgFailed(e.getMessage());
                }
            }


        });
    }

    @Override
    public void resetPwd(String phoneNumber, String pwd, String code) {
        if (StringUtils.isPhoneNumber(phoneNumber)){
            if (StringUtils.isVailedPassword(pwd)) {
                if (code.length()!=0){
                    resetPwdView.onStartRegister();
                    resetPwdToBmob(pwd,code);
                }else {
                    resetPwdView.codeLengthError();
                }

            } else {
                resetPwdView.onPasswordError();
            }
        }else {
            resetPwdView.isNotPhoneNumber();
        }
    }

    private void resetPwdToBmob(String pwd, String code) {
        BmobUser.resetPasswordBySMSCode(code,pwd, new UpdateListener() {

            @Override
            public void done(cn.bmob.v3.exception.BmobException e) {
                if(e==null){
                    resetPwdView.resetPwdSucc();
                }else{
                    resetPwdView.resetPwdFail(e.getMessage());
                }
            }
        });
    }
}
