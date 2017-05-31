package com.gdcp.yueyunku_business.event;

/**
 * Created by asus- on 2017/5/30.
 */

public class DingDanEvent {
    String msg;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public DingDanEvent(String msg) {
        this.msg = msg;
    }
}
