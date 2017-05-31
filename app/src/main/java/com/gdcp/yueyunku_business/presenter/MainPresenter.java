package com.gdcp.yueyunku_business.presenter;

import android.app.Activity;

/**
 * Created by asus- on 2017/5/21.
 */

public interface MainPresenter {


    void judgeCurrentUser();

    void uploadPhoto(String s);

    void updatePhoto(String fileUrl, String objectId);
}
