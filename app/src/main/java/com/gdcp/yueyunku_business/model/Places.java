package com.gdcp.yueyunku_business.model;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by asus- on 2017/5/28.
 */

public class Places{
    private String type;
    private Integer price;
    private Integer num;
    //订阅数量
    private Integer hadBooks;
    private List<String> usersIdList;


    public Places(String type, Integer price, Integer num) {
        this.type = type;
        this.price = price;
        this.num = num;
        usersIdList=new ArrayList<>();
    }

    public List<String> getUsersId() {
        return usersIdList;
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

    public void setHadBooks(Integer hadBooks) {
        this.hadBooks = hadBooks;
    }

    public Integer getHadBooks() {
        return hadBooks;
    }
}
