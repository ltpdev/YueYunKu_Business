package com.gdcp.yueyunku_business.presenter;

import android.app.Activity;
import android.widget.TextView;

import com.gdcp.yueyunku_business.model.Places;
import com.gdcp.yueyunku_business.model.User;
import com.gdcp.yueyunku_business.ui.activity.SportActivity;

import java.util.List;

/**
 * Created by asus- on 2017/5/21.
 */

public interface SportPresenter {

    void pickPhoto(Activity activity);

    void uploadPhotoToBmob(String picPath);

    void save(String num, String price, String fileUrl, User user,String type);

    void update(String num, String price, String fileUrl, User user, String type,String id);

    void updateWithoutPic(String num, String price, User user, String type, String objectId);

    void saveWithoutPic(String num, String price, User user, String type);
}
