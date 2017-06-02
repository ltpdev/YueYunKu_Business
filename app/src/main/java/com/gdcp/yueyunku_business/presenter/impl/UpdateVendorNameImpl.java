package com.gdcp.yueyunku_business.presenter.impl;

import com.gdcp.yueyunku_business.model.User;
import com.gdcp.yueyunku_business.presenter.UpdateVendorNamePresenter;
import com.gdcp.yueyunku_business.view.UpdateVendorNameView;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by asus- on 2017/5/24.
 */

public class UpdateVendorNameImpl implements UpdateVendorNamePresenter{
    private UpdateVendorNameView updateVendorNameView;
    public UpdateVendorNameImpl(UpdateVendorNameView updateVendorNameView){
        this.updateVendorNameView=updateVendorNameView;
    }


    @Override
    public void updateVendorName(String name,String objectId) {
        if (name.length()==0){
            updateVendorNameView.invalidLength();
            return;
        }
        User user=new User();
        user.setUsername(name);
        user.update(objectId,new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    updateVendorNameView.updateVendorNameSucc();
                }else{
                    updateVendorNameView.updateVendorNameFailed();
                }
            }
        });
    }
}
