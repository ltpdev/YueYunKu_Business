package com.gdcp.yueyunku_business.ui.fragment;

/**
 * Created by Asus on 2017/5/21.
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gdcp.yueyunku_business.R;
import com.gdcp.yueyunku_business.model.Constant;
import com.gdcp.yueyunku_business.ui.activity.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginFragment extends BaseFragment {


    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void init() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_login;
    }



    @OnClick(R.id.btn_login)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                startActivity(LoginActivity.class,Constant.KEY_ONE,Constant.VALUE_TWO, Constant.LOGINFRAGMENT_REQUSET_CODE,false);
            break;
        }
    }
}
