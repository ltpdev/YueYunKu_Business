package com.gdcp.yueyunku_business.event;

/**
 * Created by asus- on 2017/5/24.
 */

public class UploadHeadSuccessEvent {
    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public UploadHeadSuccessEvent(String url){
        this.url=url;
    }
}
