package com.gdcp.yueyunku_business.presenter.impl;

import android.app.Activity;

import com.gdcp.yueyunku_business.model.Space;
import com.gdcp.yueyunku_business.model.User;
import com.gdcp.yueyunku_business.presenter.SportPresenter;
import com.gdcp.yueyunku_business.view.SportView;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import me.iwf.photopicker.PhotoPicker;

/**
 * Created by asus- on 2017/5/24.
 */

public class SportImpl implements SportPresenter{
private SportView sportView;
    public SportImpl(SportView sportView) {
        this.sportView=sportView;
    }

    @Override
    public void pickPhoto(Activity activity) {
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
                    sportView.uploadPhotoSuccess(bmobFile.getFileUrl());
                }else{
                    sportView.uploadPhotoFailed();
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });
    }

    @Override
    public void save(String num, String price, String fileUrl, User user,String type) {
        if (num.length()==0){
            sportView.numError();
            return;
        }
        if (price.length()==0){
            sportView.priceError();
            return;
        }

        Integer number=Integer.parseInt(num);
        Integer jiege=Integer.parseInt(price);
         Space space = new Space();
                    space.setNum(number);
                    space.setPrice(jiege);
                    space.setBusiness(user);
                    space.setType(type);
                    space.setPicUrl(fileUrl);
                    space.save(new SaveListener<String>() {

                        @Override
                        public void done(String objectId, BmobException e) {
                            if (e == null) {
                                sportView.saveSucc();
                            } else {
                                sportView.saveFail();
                            }
                        }
                    });
    }

    @Override
    public void update(String num, String price, String fileUrl, User user, String type,String objectId) {
        if (num.length()==0){
            sportView.numError();
            return;
        }
        if (price.length()==0){
            sportView.priceError();
            return;
        }
        Integer number=Integer.parseInt(num);
        Integer jiege=Integer.parseInt(price);
        Space space = new Space();
                    space.setNum(number);
                    space.setPrice(jiege);
                    space.setBusiness(user);
                    space.setType(type);
                    space.setPicUrl(fileUrl);
                    space.update(objectId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                sportView.updateSucc();
                            } else {
                                sportView.updateFail();
                            }
                        }

                    });
    }

    @Override
    public void updateWithoutPic(String num, String price, User user, String type, String objectId) {
        if (num.length()==0){
            sportView.numError();
            return;
        }
        if (price.length()==0){
            sportView.priceError();
            return;
        }
        Integer number=Integer.parseInt(num);
        Integer jiege=Integer.parseInt(price);
        Space space = new Space();
        space.setNum(number);
        space.setPrice(jiege);
        space.setBusiness(user);
        space.setType(type);
        space.update(objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    sportView.updateSucc();
                } else {
                    sportView.updateFail();
                }
            }

        });
    }

    @Override
    public void saveWithoutPic(String num, String price, User user, String type) {
        if (num.length()==0){
            sportView.numError();
            return;
        }
        if (price.length()==0){
            sportView.priceError();
            return;
        }

        Integer number=Integer.parseInt(num);
        Integer jiege=Integer.parseInt(price);
        Space space = new Space();
        space.setNum(number);
        space.setPrice(jiege);
        space.setBusiness(user);
        space.setType(type);
        space.save(new SaveListener<String>() {

            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    sportView.saveSucc();
                } else {
                    sportView.saveFail();
                }
            }
        });
    }
}
