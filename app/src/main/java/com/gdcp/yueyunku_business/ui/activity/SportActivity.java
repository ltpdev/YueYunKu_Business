package com.gdcp.yueyunku_business.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gdcp.yueyunku_business.R;
import com.gdcp.yueyunku_business.model.Space;
import com.gdcp.yueyunku_business.model.User;
import com.gdcp.yueyunku_business.presenter.SportPresenter;
import com.gdcp.yueyunku_business.presenter.impl.SportImpl;
import com.gdcp.yueyunku_business.view.SportView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import me.iwf.photopicker.PhotoPicker;

public class SportActivity extends BaseActivity implements SportView{


    @BindView(R.id.tv_tool_title)
    TextView mTvToolTitle;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.edt_price_sport)
    EditText edtPriceSport;
    @BindView(R.id.edt_num_sport)
    EditText edtNumSport;
    @BindView(R.id.iv_sport)
    ImageView ivSport;
    private boolean isSaved = false;
    private User user = BmobUser.getCurrentUser(User.class);
    private String objectId;
    private String type;
    private String picPath=null;
    private SportPresenter sportPresenter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_sport;
    }

    @Override
    protected void init() {
        super.init();
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        type = getIntent().getStringExtra("type");
        mTvToolTitle.setText(type);
        tvSend.setText(getString(R.string.commit));
        tvSend.setVisibility(View.VISIBLE);
        initPresenter();
        BmobQuery<Space> query = new BmobQuery<Space>();
        query.addWhereEqualTo("business", user);    // 查询当前用户的所有帖子
        query.findObjects(new FindListener<Space>() {

            @Override
            public void done(List<Space> object, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < object.size(); i++) {
                        if (object.get(i).getType().equals(type)) {
                            isSaved = true;
                            objectId = object.get(i).getObjectId();
                            edtPriceSport.setText(String.valueOf(object.get(i).getPrice()));
                            edtNumSport.setText(String.valueOf(object.get(i).getNum()));
                            Glide.with(SportActivity.this).load(object.get(i).getPicUrl()).into(ivSport);
                            break;
                        }
                    }
                } else {

                }
            }

        });

    }

    private void initPresenter() {
        sportPresenter=new SportImpl(this);
    }


    @OnClick({R.id.tv_send,R.id.iv_sport})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send:
                if (picPath!=null){
                    sportPresenter.uploadPhotoToBmob(picPath);
                }else {
                    String num =edtNumSport.getText().toString();
                    String price = edtPriceSport.getText().toString();
                    if (isSaved) {
                        sportPresenter.updateWithoutPic(num,price,user,type,objectId);
                    } else {
                        sportPresenter.saveWithoutPic(num,price,user,type);

                    }
                }

                break;

            case R.id.iv_sport:
                sportPresenter.pickPhoto(SportActivity.this);
                break;
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                picPath = photos.get(0);
                Glide.with(this).load(photos.get(0)).into(ivSport);
            }
        }
    }

    @Override
    public void uploadPhotoSuccess(String fileUrl) {
        String num =edtNumSport.getText().toString();
        String price = edtPriceSport.getText().toString();
                if (isSaved) {
                    sportPresenter.update(num,price,fileUrl,user,type,objectId);
                } else {
                    sportPresenter.save(num,price,fileUrl,user,type);

                }
    }

    @Override
    public void uploadPhotoFailed() {
       toast("上传失败");
    }

    @Override
    public void numError() {
        toast("数量的内容不能为空");
    }

    @Override
    public void priceError() {
        toast("价格的内容不能为空");
    }

    @Override
    public void saveSucc() {
        toast("保存成功");
        finish();
    }

    @Override
    public void saveFail() {
        toast("保存失败");
    }

    @Override
    public void updateSucc() {
        toast("更新成功");
        finish();
    }

    @Override
    public void updateFail() {
        toast("更新失败");
    }
}
