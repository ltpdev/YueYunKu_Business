package com.gdcp.yueyunku_business.model;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by asus- on 2017/5/28.
 */

public class Place extends BmobObject{
    private List<Places>placesList;
    private String placePicUrl;
    private String placeInfo;
    //商家
    private User user;

    public List<Places> getPlacesList() {
        return placesList;
    }

    public void setPlacesList(List<Places> placesList) {
        this.placesList = placesList;
    }

    public String getPlacePicUrl() {
        return placePicUrl;
    }

    public void setPlacePicUrl(String placePicUrl) {
        this.placePicUrl = placePicUrl;
    }

    public String getPlaceInfo() {
        return placeInfo;
    }

    public void setPlaceInfo(String placeInfo) {
        this.placeInfo = placeInfo;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
