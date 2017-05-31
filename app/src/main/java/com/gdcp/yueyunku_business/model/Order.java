package com.gdcp.yueyunku_business.model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by Asus on 2017/5/30.
 */

public class Order extends BmobObject implements Serializable{
    private String sport_type;//球类
    private User joiner;
    private User business;
    private Integer state_type;//审核状态(未审核0，已通过1，未通过2，已经结束3)
    private String book_time;//订阅时间
    private Integer price;//价格
    private String orderUrl;//场地图片
    private Space space;//订单场所

    public void setSpace(Space space) {
        this.space = space;
    }

    public Space getSpace() {
        return space;
    }

    public String getOrderUrl() {
        return orderUrl;
    }

    public void setOrderUrl(String orderUrl) {
        this.orderUrl = orderUrl;
    }

    public String getSport_type() {
        return sport_type;
    }

    public void setSport_type(String sport_type) {
        this.sport_type = sport_type;
    }

    public User getJoiner() {
        return joiner;
    }

    public void setJoiner(User joiner) {
        this.joiner = joiner;
    }

    public User getBusiness() {
        return business;
    }

    public void setBusiness(User business) {
        this.business = business;
    }

    public Integer getState_type() {
        return state_type;
    }

    public void setState_type(Integer state_type) {
        this.state_type = state_type;
    }

    public String getBook_time() {
        return book_time;
    }

    public void setBook_time(String book_time) {
        this.book_time = book_time;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
