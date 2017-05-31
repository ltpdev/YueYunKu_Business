package com.gdcp.yueyunku_business.presenter.impl;

import com.gdcp.yueyunku_business.model.User;
import com.gdcp.yueyunku_business.presenter.UpdateUserNamePresenter;
import com.gdcp.yueyunku_business.presenter.UpdateVendorNamePresenter;
import com.gdcp.yueyunku_business.view.UpdateUserNameView;
import com.gdcp.yueyunku_business.view.UpdateVendorNameView;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by asus- on 2017/5/24.
 */

public class UpdateUserNameImpl implements UpdateUserNamePresenter{
    private UpdateUserNameView updateUserNameView;
    public UpdateUserNameImpl(UpdateUserNameView updateUserNameView){
        this.updateUserNameView=updateUserNameView;
    }


    @Override
    public void updateUserName(String name, String objectId) {
        if (name.length()==0){
            updateUserNameView.invalidLength();
            return;
        }
        User user=new User();
        user.setUsername(name);
        user.update(objectId,new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    updateUserNameView.updateUserNameSucc();
                }else{
                    updateUserNameView.updateUserNameNameFailed();
                }
            }
        });
    }
}
