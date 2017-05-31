package com.gdcp.yueyunku_business.presenter.impl;

import com.gdcp.yueyunku_business.event.UploadHeadSuccessEvent;
import com.gdcp.yueyunku_business.model.User;
import com.gdcp.yueyunku_business.presenter.LoginPresenter;
import com.gdcp.yueyunku_business.presenter.MainPresenter;
import com.gdcp.yueyunku_business.view.LoginView;
import com.gdcp.yueyunku_business.view.MainView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by asus- on 2017/5/21.
 */

public class MainPresenterImpl implements MainPresenter{
    private MainView mainView;
    public MainPresenterImpl(MainView mainView){
        this.mainView=mainView;
    }

    @Override
    public void judgeCurrentUser() {
        BmobUser bmobUser = BmobUser.getCurrentUser();
        if (bmobUser != null) {
            mainView.curUserExist(true);
        } else {
            mainView.curUserExist(false);
        }
    }

    @Override
    public void uploadPhoto(String picPath) {
        final BmobFile bmobFile = new BmobFile(new File(picPath));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    mainView.uploadPhotoSuccess(bmobFile.getFileUrl());
                }else{
                    mainView.uploadPhotoFailed();
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });

    }

    @Override
    public void updatePhoto(final String fileUrl, String objectId) {
        User user=new User();
        user.setHead(fileUrl);
        user.update(objectId,new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    mainView.updatePhotoSucc(fileUrl);
                }else{
                    mainView.updatePhotoFailed();
                }
            }
        });
    }
}
