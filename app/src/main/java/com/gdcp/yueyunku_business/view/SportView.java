package com.gdcp.yueyunku_business.view;

import android.widget.TextView;

/**
 * Created by asus- on 2017/5/24.
 */

public interface SportView {


    void uploadPhotoSuccess(String fileUrl);

    void uploadPhotoFailed();

    void numError();

    void priceError();

    void saveSucc();

    void saveFail();

    void updateSucc();

    void updateFail();
}
