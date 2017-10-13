package com.gdcp.yueyunku_business.ui.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gdcp.yueyunku_business.R;

public class WelcomeActivity extends BaseActivity{


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void init() {
        super.init();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(MainActivity.class,true);
            }
        },2000);
    }
}
