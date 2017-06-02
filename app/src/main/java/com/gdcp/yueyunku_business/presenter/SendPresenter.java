package com.gdcp.yueyunku_business.presenter;

import android.app.Activity;

/**
 * Created by asus- on 2017/5/21.
 */

public interface SendPresenter {

    void selectPhoto(Activity activity);

    void uploadPhotoToBmob(String picPath);

    void publishActivity(String unit, String name, String intro, String fileUrl, String beginTime, String endTime);

    void publishActivityWithJiangli(String unit, String name, String intro, String fileUrl, String beginTime, String endTime, String rule, String typeJiangping, String jiangpingNum);
}
