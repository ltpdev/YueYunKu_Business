package com.gdcp.yueyunku_business.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by asus- on 2017/5/30.
 */

public class Space extends BmobObject{
    private String type;
    private Integer price;
    private Integer num;
    //订阅数量
    private Integer hadBooks;
    private User business;
    private String picUrl;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getHadBooks() {
        return hadBooks;
    }

    public void setHadBooks(Integer hadBooks) {
        this.hadBooks = hadBooks;
    }

    public User getBusiness() {
        return business;
    }

    public void setBusiness(User business) {
        this.business = business;
    }
}
