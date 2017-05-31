package com.gdcp.yueyunku_business.presenter;

import android.content.Context;



/**
 * Created by yls on 2016/12/29.
 */

public interface RegisterPresenter {
    void register(String phoneNumber, String password, String confirmpassword,String verificationCode);

    void isPhoneNumber(String phoneNumber);

    void requestSMSCode(Context context, String phoneNumber);
}
