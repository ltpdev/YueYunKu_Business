package com.gdcp.yueyunku_business.view;

/**
 * Created by yls on 2016/12/29.
 */

public interface SendView {

    void uploadPhotoSuccess(String fileUrl);

    void uploadPhotoFailed();

    void unitError();

    void nameError();

    void introError();

    void publishSucc();

    void publishFailed();

    void beginTimeError();

    void endTimeError();

    void ruleError();

    void typeJiangpingError();

    void jiangpingNumError();

    void kaijiangTimeError();

    void beginTimeFail();

    void endTimeFail();

    void kaijiangTimeFail();

    void picPathError();
}
