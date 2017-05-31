package com.gdcp.yueyunku_business.event;

/**
 * Created by asus- on 2017/5/24.
 */

public class PublishSuccessEvent {
   private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public PublishSuccessEvent(String msg){
        this.msg=msg;
    }
}
