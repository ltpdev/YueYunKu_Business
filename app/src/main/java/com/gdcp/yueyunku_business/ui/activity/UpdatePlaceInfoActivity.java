package com.gdcp.yueyunku_business.ui.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gdcp.yueyunku_business.R;
import com.gdcp.yueyunku_business.model.User;
import com.gdcp.yueyunku_business.presenter.UpdatePlaceInfoPresenter;
import com.gdcp.yueyunku_business.presenter.impl.UpdatePlaceInfoImpl;
import com.gdcp.yueyunku_business.view.UpdatePlaceInfoView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import me.iwf.photopicker.PhotoPicker;

public class UpdatePlaceInfoActivity extends BaseActivity implements UpdatePlaceInfoView{


    @BindView(R.id.tv_tool_title)
    TextView mTvToolTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_place)
    ImageView ivPlace;
    @BindView(R.id.edt_info_place)
    EditText edtInfoPlace;
    @BindView(R.id.relativeLayout_basketball)
    RelativeLayout relativeLayoutBasketball;
    @BindView(R.id.relativeLayout_volleyball)
    RelativeLayout relativeLayoutVolleyball;
    @BindView(R.id.relativeLayout_tennis)
    RelativeLayout relativeLayoutTennis;
    @BindView(R.id.relativeLayout_pingpong)
    RelativeLayout relativeLayoutPingpong;
    @BindView(R.id.relativeLayout_football)
    RelativeLayout relativeLayoutFootball;
    @BindView(R.id.relativeLayout_badminton)
    RelativeLayout relativeLayoutBadminton;
    @BindView(R.id.relativeLayout_yoga)
    RelativeLayout relativeLayoutYoga;
    @BindView(R.id.activity_update_place_info)
    LinearLayout activityUpdatePlaceInfo;
    @BindView(R.id.tv_send)
    TextView tvSend;
    private String picPath = null;
    private UpdatePlaceInfoPresenter updatePlaceInfoPresenter;
    private User user;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_update_place_info;
    }

    @Override
    protected void init() {
        super.init();
        user= BmobUser.getCurrentUser(User.class);
        initData();
        initPresenter();


    }

    private void initPresenter() {
        updatePlaceInfoPresenter=new UpdatePlaceInfoImpl(this);
    }


    private void initData() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mTvToolTitle.setText(getString(R.string.update_info_place));
        tvSend.setVisibility(View.VISIBLE);
        tvSend.setText(getString(R.string.commit));
        Glide.with(this).load(user.getBackgroundUrl()).into(ivPlace);
        edtInfoPlace.setText(user.getPlaceIntro());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                picPath = photos.get(0);
                Glide.with(this).load(photos.get(0)).into(ivPlace);
            }
        }
    }


    @OnClick({R.id.iv_place, R.id.relativeLayout_basketball,
            R.id.relativeLayout_volleyball, R.id.relativeLayout_tennis,
            R.id.relativeLayout_pingpong, R.id.relativeLayout_football,
            R.id.relativeLayout_badminton, R.id.relativeLayout_yoga, R.id.activity_update_place_info,R.id.tv_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_place:
                updatePlaceInfoPresenter.selectPhoto(UpdatePlaceInfoActivity.this);
                break;
            case R.id.relativeLayout_basketball:
                //打开具体类型的activity
                startActivity(SportActivity.class, "type", "篮球", false);
                break;
            case R.id.relativeLayout_volleyball:
                startActivity(SportActivity.class, "type", "排球", false);
                break;
            case R.id.relativeLayout_tennis:
                startActivity(SportActivity.class, "type", "羽毛球", false);
                break;
            case R.id.relativeLayout_pingpong:
                startActivity(SportActivity.class, "type", "乒乓球", false);
                break;
            case R.id.relativeLayout_football:
                startActivity(SportActivity.class, "type", "足球", false);
                break;
            case R.id.relativeLayout_badminton:
                startActivity(SportActivity.class, "type", "网球", false);
                break;
            case R.id.relativeLayout_yoga:
                startActivity(SportActivity.class, "type", "瑜伽", false);
                break;
            case R.id.tv_send:
                if (picPath!=null){
                    updatePlaceInfoPresenter.uploadPhotoToBmob(picPath);
                }else {
                    String intro=edtInfoPlace.getText().toString();
                    updatePlaceInfoPresenter.updateWithoutPic(intro);
                }

                break;

        }
    }




    @Override
    public void updateSucc() {
        toast("更新成功");
        finish();
    }

    @Override
    public void updateFail(String message) {
        toast("更新失败:"+message);
    }

    @Override
    public void uploadPhotoFailed() {
           toast("上传图片失败");
    }

    @Override
    public void uploadPhotoSuccess(String fileUrl) {
        String intro=edtInfoPlace.getText().toString();
        updatePlaceInfoPresenter.update(fileUrl,intro);
    }

    @Override
    public void infoPlaceError() {
        toast("简介的内容不能为空");
    }
}
