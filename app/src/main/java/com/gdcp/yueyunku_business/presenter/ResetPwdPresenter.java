package com.gdcp.yueyunku_business.presenter;

import android.content.Context;


/**
 * Created by yls on 2016/12/29.
 */

public interface ResetPwdPresenter {
    void isPhoneNumber(String phoneNumber);
    void requestSMSCode(Context context, String phoneNumber);
    void resetPwd(String phoneNumber, String pwd, String code);
}
