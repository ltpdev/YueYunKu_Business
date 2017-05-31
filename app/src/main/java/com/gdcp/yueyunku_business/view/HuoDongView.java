package com.gdcp.yueyunku_business.view;

import com.gdcp.yueyunku_business.model.Activity;

import java.util.List;

/**
 * Created by asus- on 2017/5/24.
 */

public interface HuoDongView {

    void querySucc(List<Activity> object);

    void queryFail();

    void loadMoreSucc(List<Activity> object);

    void loadMoreFail();
}
