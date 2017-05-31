package com.gdcp.yueyunku_business.view;

/**
 * Created by yls on 2016/12/29.
 */

public interface RegisterView {
    void onRegisterSuccessed();

    void onRegisterFailed(String message);

    

    void onUserNameError();

    void onPasswordError();

    void onConfirmpasswordError();

    void onStartRegister();

    void isPhoneNumber(String phoneNumber);

    void isNotPhoneNumber();

    void onSendMsgSuccess();

    void onSendMsgFailed(String message);
}
