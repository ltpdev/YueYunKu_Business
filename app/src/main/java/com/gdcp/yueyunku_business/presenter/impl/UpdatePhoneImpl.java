package com.gdcp.yueyunku_business.presenter.impl;

import com.gdcp.yueyunku_business.model.User;
import com.gdcp.yueyunku_business.presenter.UpdatePhonePresenter;
import com.gdcp.yueyunku_business.presenter.UpdateVendorNamePresenter;
import com.gdcp.yueyunku_business.utils.StringUtils;
import com.gdcp.yueyunku_business.view.UpdatePhoneView;
import com.gdcp.yueyunku_business.view.UpdateVendorNameView;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by asus- on 2017/5/24.
 */

public class UpdatePhoneImpl implements UpdatePhonePresenter{
    private UpdatePhoneView updatePhoneView;
    public UpdatePhoneImpl(UpdatePhoneView updatePhoneView){
        this.updatePhoneView=updatePhoneView;
    }


    @Override
    public void updatePhone(String phone, String objectId) {
        if (phone.length()==0){
            updatePhoneView.invalidLength();
            return;
        }
        if (!StringUtils.isPhoneNumber(phone)){
            updatePhoneView.isNotPhoneNumber();
            return;
        }
        User user=new User();
        user.setMobilePhoneNumber(phone);
        user.update(objectId,new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    updatePhoneView.updatePhoneSucc();
                }else{
                    updatePhoneView.updatePhoneFailed();
                }
            }
        });
    }
    }

