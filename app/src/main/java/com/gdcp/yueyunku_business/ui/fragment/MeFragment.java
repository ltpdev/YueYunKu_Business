package com.gdcp.yueyunku_business.ui.fragment;

/**
 * Created by Asus on 2017/5/21.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gdcp.yueyunku_business.R;
import com.gdcp.yueyunku_business.event.UploadHeadSuccessEvent;
import com.gdcp.yueyunku_business.ui.activity.LoginActivity;
import com.gdcp.yueyunku_business.ui.activity.UpdatePhoneActivity;
import com.gdcp.yueyunku_business.ui.activity.UpdatePlaceAddressActivity;
import com.gdcp.yueyunku_business.ui.activity.UpdatePlaceInfoActivity;
import com.gdcp.yueyunku_business.ui.activity.UpdateUserNameActivity;
import com.gdcp.yueyunku_business.ui.activity.UpdateVendorNameActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;
import me.iwf.photopicker.PhotoPicker;


public class MeFragment extends BaseFragment {


    @BindView(R.id.cv_user_head)
    CircleImageView cvUserHead;
    @BindView(R.id.tv_name_user)
    TextView tvNameUser;
    @BindView(R.id.tv_phone_user)
    TextView tvPhoneUser;
    @BindView(R.id.linearLayout_logout)
    LinearLayout linearLayoutLogout;
    @BindView(R.id.tv_tool_title)
    TextView tvToolTitle;
    @BindView(R.id.linearLayout_login)
    LinearLayout linearLayoutLogin;
    @BindView(R.id.tv_name_vendor)
    TextView tvNameVendor;
    @BindView(R.id.tv_address_place)
    TextView tvAddressPlace;
    @BindView(R.id.linearLayout_vendor_name)
    LinearLayout linearLayoutVendorName;
    @BindView(R.id.linearLayout_address_place)
    LinearLayout linearLayoutAddressPlace;
    @BindView(R.id.linearLayout_info_place)
    LinearLayout linearLayoutInfoPlace;
    private BmobUser bmobUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void init() {
        tvToolTitle.setText(getString(R.string.center_person));
        bmobUser = BmobUser.getCurrentUser();
        if (bmobUser != null) {
            String username = (String) BmobUser.getObjectByKey("username");
            String num = (String) BmobUser.getObjectByKey("mobilePhoneNumber");
            String url = (String) BmobUser.getObjectByKey("head");
            String vendorName = (String) BmobUser.getObjectByKey("vendorName");
            String area = (String) BmobUser.getObjectByKey("area");
            if (url != null) {
                Glide.with(getActivity()).load(url).into(cvUserHead);
            }
            tvNameVendor.setText(vendorName);
            tvNameUser.setText(username);
            tvPhoneUser.setText(getString(R.string.phone_contact) + num);
            tvAddressPlace.setText(area);
            linearLayoutLogout.setVisibility(View.VISIBLE);
        } else {
            tvNameUser.setText(getString(R.string.click_login));
            tvPhoneUser.setText("");
            tvNameVendor.setText("");
            tvAddressPlace.setText("");
            Glide.with(getActivity()).load(R.mipmap.content_head).into(cvUserHead);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_me;
    }


    @OnClick({R.id.cv_user_head, R.id.tv_name_user, R.id.tv_phone_user,
            R.id.linearLayout_logout, R.id.linearLayout_login,
            R.id.linearLayout_vendor_name, R.id.linearLayout_address_place,R.id.linearLayout_info_place})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cv_user_head:
                if (bmobUser != null) {
                    PhotoPicker.builder()
                            .setPhotoCount(1)
                            .setShowCamera(true)
                            .setShowGif(false)
                            .setPreviewEnabled(false)
                            .start(getActivity(), PhotoPicker.REQUEST_CODE);
                }
                break;
            case R.id.tv_name_user:
                if (bmobUser != null) {
                    startActivity(UpdateUserNameActivity.class, "username", (String) BmobUser.getObjectByKey("username"), false);
                }
                break;
            case R.id.tv_phone_user:
                if (bmobUser != null) {
                    startActivity(UpdatePhoneActivity.class, "phone", (String) BmobUser.getObjectByKey("mobilePhoneNumber"), false);
                }
                break;
            case R.id.linearLayout_login:
                if (bmobUser == null) {
                    startActivity(LoginActivity.class, false);
                }

                break;
            case R.id.linearLayout_logout:
                BmobUser.logOut();
                toast(getString(R.string.succ_logout));
                linearLayoutLogout.setVisibility(View.GONE);
                init();
                break;

            case R.id.linearLayout_vendor_name:
                if (bmobUser != null) {
                    startActivity(UpdateVendorNameActivity.class, "vendorname", (String) BmobUser.getObjectByKey("vendorName"), false);
                }
                break;
            case R.id.linearLayout_address_place:
                if (bmobUser != null) {
                    startActivity(UpdatePlaceAddressActivity.class, "address", tvAddressPlace.getText().toString(), false);
                }
                break;
             case R.id.linearLayout_info_place:
                 if (bmobUser != null) {
                     startActivity(UpdatePlaceInfoActivity.class, false);
                 }
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUploadHeadSuccessEvent(UploadHeadSuccessEvent event) {
        if (event != null && event.getUrl() != null)
            Glide.with(getActivity()).load(event.getUrl()).into(cvUserHead);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }




}
