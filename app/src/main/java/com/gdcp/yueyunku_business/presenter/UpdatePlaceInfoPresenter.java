package com.gdcp.yueyunku_business.presenter;

import android.app.Activity;
import android.widget.TextView;

import com.gdcp.yueyunku_business.model.Places;
import com.gdcp.yueyunku_business.ui.activity.UpdatePlaceInfoActivity;

import java.util.List;

/**
 * Created by asus- on 2017/5/21.
 */

public interface UpdatePlaceInfoPresenter {



    void update(String picUrl,String introPlace);

    void selectPhoto(Activity activity);

    void uploadPhotoToBmob(String picPath);


    void updateWithoutPic(String introPlace);
}
