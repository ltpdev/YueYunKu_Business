package com.gdcp.yueyunku_business.presenter.impl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.gdcp.yueyunku_business.R;
import com.gdcp.yueyunku_business.model.User;
import com.gdcp.yueyunku_business.presenter.SendPresenter;
import com.gdcp.yueyunku_business.view.SendView;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import me.iwf.photopicker.PhotoPicker;

/**
 * Created by asus- on 2017/5/21.
 */

public class SendPresenterImpl implements SendPresenter{
    private SendView sendView;
    private Context context;
    public SendPresenterImpl(SendView sendView,Context context) {
        this.sendView=sendView;
        this.context=context;
    }
    private ProgressDialog mProgressDialog;
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
        if (picPath==null){
            sendView.picPathError();
            return;
        }
        final BmobFile bmobFile = new BmobFile(new File(picPath));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    sendView.uploadPhotoSuccess(bmobFile.getFileUrl());
                }else{
                    sendView.uploadPhotoFailed();
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });
    }

    public void showProgress(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setCancelable(true);
        }
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    public void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }




    @Override
    public void publishActivity(String name, String intro, String fileUrl, String beginTime, String endTime, String rule) {

        if (name.length()==0){
            sendView.nameError();
            return;
        }
        if (intro.length()==0){
            sendView.introError();
            return;
        }
        if (beginTime.length()==0){
            sendView.beginTimeError();
            return;
        }
        if (endTime.length()==0){
            sendView.endTimeError();
            return;
        }
        if (rule.length()==0){
            sendView.ruleError();
            return;
        }

        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date dt1 = df.parse(beginTime);
            Date dt2 = df.parse(endTime);
            Date dt3 = df.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            if (dt3.getTime()>dt1.getTime()) {
                sendView.beginTimeFail();
                return;
            }
            if (dt2.getTime()< dt1.getTime()) {
                sendView.endTimeFail();
                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        showProgress(context.getString(R.string.publishing));
        com.gdcp.yueyunku_business.model.Activity activity=new com.gdcp.yueyunku_business.model.Activity();
        activity.setName(name);
        activity.setActivityIntro(intro);
        activity.setPicUrl(fileUrl);
        activity.setBeginTime(beginTime);
        activity.setEndTime(endTime);
        activity.setRule(rule);
        activity.setBusiness(BmobUser.getCurrentUser(User.class));
        activity.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    hideProgress();
                    sendView.publishSucc();
                }else {
                    sendView.publishFailed();
                }
            }
        });
    }

    @Override
    public void publishActivityWithJiangli(String name, String intro, String fileUrl, String beginTime, String endTime, String rule, String typeJiangping, String jiangpingNum, String kaijiangTime) {
        if (name.length()==0){
            sendView.nameError();
            return;
        }
        if (intro.length()==0){
            sendView.introError();
            return;
        }
        if (beginTime.length()==0){
            sendView.beginTimeError();
            return;
        }
        if (endTime.length()==0){
            sendView.endTimeError();
            return;
        }
        if (rule.length()==0){
            sendView.ruleError();
            return;
        }
        if (typeJiangping.length()==0){
            sendView.typeJiangpingError();
            return;
        }
        if (jiangpingNum.length()==0){
            sendView.jiangpingNumError();
            return;
        }
        if (kaijiangTime.length()==0){
            sendView.kaijiangTimeError();
            return;
        }
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date dt1 = df.parse(beginTime);
            Date dt2 = df.parse(endTime);
            Date dt3 = df.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            Date dt4 = df.parse(kaijiangTime);
            if (dt3.getTime()>dt1.getTime()) {
                sendView.beginTimeFail();
                return;
            }
            if (dt2.getTime()< dt1.getTime()) {
                sendView.endTimeFail();
                return;
            }

            if(!(dt4.getTime()>dt1.getTime()&&dt4.getTime()<dt2.getTime())){
                sendView.kaijiangTimeFail();
                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        showProgress(context.getString(R.string.publishing));
        com.gdcp.yueyunku_business.model.Activity activity=new com.gdcp.yueyunku_business.model.Activity();
        activity.setName(name);
        activity.setBusiness(BmobUser.getCurrentUser(User.class));
        activity.setActivityIntro(intro);
        activity.setPicUrl(fileUrl);

        //
        activity.setBeginTime(beginTime);
        activity.setEndTime(endTime);
        activity.setJiangpingNum(jiangpingNum);
        activity.setRule(rule);
        activity.setKaijiangTime(kaijiangTime);
        activity.setTypeJiangping(typeJiangping);


        activity.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    hideProgress();
                    sendView.publishSucc();
                }else {
                    sendView.publishFailed();
                }
            }
        });
    }
}
