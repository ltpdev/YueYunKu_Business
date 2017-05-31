package com.gdcp.yueyunku_business.presenter.impl;

import com.gdcp.yueyunku_business.model.User;
import com.gdcp.yueyunku_business.presenter.UpdatePlaceAddressPresenter;
import com.gdcp.yueyunku_business.presenter.UpdateUserNamePresenter;
import com.gdcp.yueyunku_business.view.UpdatePlaceAddressView;
import com.gdcp.yueyunku_business.view.UpdateUserNameView;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by asus- on 2017/5/24.
 */

public class UpdatePlaceAddressImpl implements UpdatePlaceAddressPresenter{
    private UpdatePlaceAddressView updatePlaceAddressView;
    public UpdatePlaceAddressImpl(UpdatePlaceAddressView updatePlaceAddressView){
        this.updatePlaceAddressView=updatePlaceAddressView;
    }


    @Override
    public void updatePlaceAddress(String areaName, String detailAreaName, String objectId) {
        if (detailAreaName.length()==0){
            updatePlaceAddressView.invalidLength();
            return;
        }
        User user=new User();
        user.setArea(areaName+"-"+detailAreaName);
        user.update(objectId,new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    updatePlaceAddressView.updatePlaceAddressSucc();
                }else{
                    updatePlaceAddressView.updatePlaceAddressFailed();
                }
            }
        });
    }
}
