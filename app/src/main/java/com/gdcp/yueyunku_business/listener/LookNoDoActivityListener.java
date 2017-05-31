package com.gdcp.yueyunku_business.listener;

import com.gdcp.yueyunku_business.model.Order;
import com.gdcp.yueyunku_business.ui.activity.HadOverActivity;
import com.gdcp.yueyunku_business.ui.activity.NoDoActivity;

/**
 * Created by asus- on 2017/5/29.
 */

public interface LookNoDoActivityListener {

    void lookActivity(Order order);
}
