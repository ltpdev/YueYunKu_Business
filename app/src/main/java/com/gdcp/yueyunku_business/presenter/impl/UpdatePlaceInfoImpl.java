package com.gdcp.yueyunku_business.presenter.impl;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import com.gdcp.yueyunku_business.model.Place;
import com.gdcp.yueyunku_business.model.Places;
import com.gdcp.yueyunku_business.model.User;
import com.gdcp.yueyunku_business.presenter.UpdatePlaceInfoPresenter;
import com.gdcp.yueyunku_business.view.UpdatePlaceInfoView;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import me.iwf.photopicker.PhotoPicker;

/**
 * Created by asus- on 2017/5/24.
 */

public class UpdatePlaceInfoImpl implements UpdatePlaceInfoPresenter{
    private UpdatePlaceInfoView updatePlaceInfoView;
    @Override
    public void update(String picUrl,String infoPlace) {
        if (infoPlace.length()==0){
            updatePlaceInfoView.infoPlaceError();
            return;
        }
        BmobUser bmobUser=BmobUser.getCurrentUser();
        User user=new User();
        user.setBackgroundUrl(picUrl);
        user.setPlaceIntro(infoPlace);
        user.update(bmobUser.getObjectId(),new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    updatePlaceInfoView.updateSucc();
                }else {
                    updatePlaceInfoView.updateFail(e.getMessage());
                }
            }
        });
    }

    @Override
    public void selectPhoto(Activity activity) {
        PhotoPicker.builder()
                .setPhotoCount(1)
                .setShowCamera(true)
                .setShowGif(false)
                .setPreviewEnabled(false)
                .start(activity, PhotoPicker.REQUEST_CODE);
    }

    @Override
    public void uploadPhotoToBmob(String picPath) {
        final BmobFile bmobFile = new BmobFile(new File(picPath));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    updatePlaceInfoView.uploadPhotoSuccess(bmobFile.getFileUrl());
                }else{
                    updatePlaceInfoView.uploadPhotoFailed();
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });
    }



    @Override
    public void updateWithoutPic(String infoPlace) {
        if (infoPlace.length()==0){
            updatePlaceInfoView.infoPlaceError();
            return;
        }
        BmobUser bmobUser=BmobUser.getCurrentUser();
        User user=new User();
        user.setPlaceIntro(infoPlace);
        user.update(bmobUser.getObjectId(),new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    updatePlaceInfoView.updateSucc();
                }else {
                    updatePlaceInfoView.updateFail(e.getMessage());
                }
            }
        });
    }


    public UpdatePlaceInfoImpl(UpdatePlaceInfoView updatePlaceInfoView){
        this.updatePlaceInfoView=updatePlaceInfoView;
    }



}
