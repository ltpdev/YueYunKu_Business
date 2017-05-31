package com.gdcp.yueyunku_business.view;

/**
 * Created by asus- on 2017/5/21.
 */

public interface MainView {

    void curUserExist(boolean b);

    void uploadPhotoSuccess(String fileUrl);

    void uploadPhotoFailed();

    void updatePhotoSucc(String fileUrl);

    void updatePhotoFailed();
}
