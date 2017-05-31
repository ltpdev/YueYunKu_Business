package com.gdcp.yueyunku_business.view;

import android.widget.TextView;

/**
 * Created by asus- on 2017/5/24.
 */

public interface UpdatePlaceInfoView {

    void updateSucc();

    void updateFail(String message);

    void uploadPhotoFailed();

    void uploadPhotoSuccess(String fileUrl);

    void infoPlaceError();
}
