package com.gdcp.yueyunku_business.model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by asus- on 2017/5/25.
 */

public class Activity extends BmobObject implements Serializable{
    private String picUrl;
    //发布单位
    private String publishUnit;
    private String name;
    //活动简称
    private String activityIntro;
    private String beginTime;
    private String endTime;
    private String rule;
    private String typeJiangping;
    private String jiangpingNum;
    private String kaijiangTime;
    private User business;

    public User getBusiness() {
        return business;
    }

    public void setBusiness(User business) {
        this.business = business;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getTypeJiangping() {
        return typeJiangping;
    }

    public void setTypeJiangping(String typeJiangping) {
        this.typeJiangping = typeJiangping;
    }

    public String getJiangpingNum() {
        return jiangpingNum;
    }

    public void setJiangpingNum(String jiangpingNum) {
        this.jiangpingNum = jiangpingNum;
    }

    public String getKaijiangTime() {
        return kaijiangTime;
    }

    public void setKaijiangTime(String kaijiangTime) {
        this.kaijiangTime = kaijiangTime;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPublishUnit() {
        return publishUnit;
    }

    public void setPublishUnit(String publishUnit) {
        this.publishUnit = publishUnit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivityIntro() {
        return activityIntro;
    }

    public void setActivityIntro(String activityIntro) {
        this.activityIntro = activityIntro;
    }
}
