package com.gdcp.yueyunku_business.view;

/**
 * Created by yls on 2016/12/29.
 */

public interface ResetPwdView {
    void onPasswordError();
    void onStartRegister();
    void isPhoneNumber(String phoneNumber);
    void isNotPhoneNumber();
    void onSendMsgSuccess();
    void onSendMsgFailed(String message);

    void resetPwdSucc();

    void resetPwdFail(String message);

    void codeLengthError();
}
