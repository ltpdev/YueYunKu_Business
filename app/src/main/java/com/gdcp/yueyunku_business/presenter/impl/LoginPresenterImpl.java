package com.gdcp.yueyunku_business.presenter.impl;

import android.util.Log;

import com.gdcp.yueyunku_business.model.User;
import com.gdcp.yueyunku_business.presenter.LoginPresenter;
import com.gdcp.yueyunku_business.view.LoginView;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * Created by asus- on 2017/5/21.
 */

public class LoginPresenterImpl implements LoginPresenter{
    private LoginView loginView;
    public LoginPresenterImpl(LoginView loginView) {
        this.loginView=loginView;
    }


    @Override
    public void login(String number, String pwd) {
        BmobUser.loginByAccount(number, pwd, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if(user!=null){
                    if (user.getType()==1){
                        loginView.onLoginSuccess();
                    }
                    else {
                        BmobUser.logOut();
                        loginView.notBusiness();
                    }
                }else {

                    loginView.onLoginFailed(e.getMessage());
                }
            }
        });
    }
}
