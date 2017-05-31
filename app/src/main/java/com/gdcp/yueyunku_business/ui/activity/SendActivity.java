package com.gdcp.yueyunku_business.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gdcp.yueyunku_business.R;
import com.gdcp.yueyunku_business.event.PublishSuccessEvent;
import com.gdcp.yueyunku_business.presenter.SendPresenter;
import com.gdcp.yueyunku_business.presenter.impl.SendPresenterImpl;
import com.gdcp.yueyunku_business.view.SendView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;

/**
 * Created by Asus on 2017/5/22.
 */

public class SendActivity extends BaseActivity implements SendView {
    @BindView(R.id.tv_tool_title)
    TextView tvToolTitle;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_addImg)
    ImageView ivAddImg;
    @BindView(R.id.edt_name_activity)
    EditText edtNameActivity;
    @BindView(R.id.edt_jiancheng_huodong)
    EditText edtJianchengHuodong;
    @BindView(R.id.edt_rule_activity)
    EditText edtRuleActivity;
    TextView tvTimeBegin;
    @BindView(R.id.tv_time_end)
    TextView tvTimeEnd;
    @BindView(R.id.switchButton)
    Switch switchButton;
    @BindView(R.id.gone_linearLayout)
    LinearLayout goneLinearLayout;
    @BindView(R.id.tv_time_kaijiang)
    TextView tvTimeKaijiang;
    @BindView(R.id.edt_type_jiangping)
    EditText edtTypeJiangping;
    @BindView(R.id.edt_num_jiangping)
    EditText edtNumJiangping;
    @BindView(R.id.tv_danwei_activity)
    TextView tvDanweiActivity;
    private SendPresenter sendPresenter;
    private String picPath = null;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_send;
    }

    @Override
    protected void init() {
        initToolbar();
        /*初始化文本组件*/
        initText();
        //初始化P层
        initPresenter();
        initListener();
    }

    private void initListener() {
        switchButton.setOnCheckedChangeListener(onCheckedChangeListener);

    }

    private void initPresenter() {
        sendPresenter = new SendPresenterImpl(this, this);
    }

    private void initText() {
        String vendorname=getIntent().getStringExtra("vendorname");
        tvToolTitle.setText(getString(R.string.activity_fabu));
        tvSend.setVisibility(View.VISIBLE);
        tvSend.setText(getString(R.string.fabu));
        tvDanweiActivity.setText(vendorname);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    @OnClick({R.id.iv_addImg, R.id.tv_send, R.id.tv_time_begin, R.id.tv_time_end, R.id.tv_time_kaijiang})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_addImg:
                //选择图片
                sendPresenter.selectPhoto(this);
                break;
            case R.id.tv_send:
                //先上传图片，再上传文字信息
                sendPresenter.uploadPhotoToBmob(picPath);
                break;
            case R.id.tv_time_begin:
                showDatePickerDialog(tvTimeBegin);
                break;
            case R.id.tv_time_end:
                showDatePickerDialog(tvTimeEnd);
                break;
            case R.id.tv_time_kaijiang:
                showDatePickerDialog(tvTimeKaijiang);
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
                Glide.with(this).load(photos.get(0)).into(ivAddImg);
            }
        }
    }

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                goneLinearLayout.setVisibility(View.VISIBLE);
            } else {
                goneLinearLayout.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void uploadPhotoSuccess(String fileUrl) {
        String unit = tvDanweiActivity.getText().toString();
        String name = edtNameActivity.getText().toString();
        String intro = edtJianchengHuodong.getText().toString();
        String beginTime = tvTimeBegin.getText().toString();
        String endTime = tvTimeEnd.getText().toString();
        String rule = edtRuleActivity.getText().toString();
        //switchButton选中
        String typeJiangping = edtTypeJiangping.getText().toString();
        String jiangpingNum = edtNumJiangping.getText().toString();
        String kaijiangTime = tvTimeKaijiang.getText().toString();

        if (switchButton.isChecked()) {
            sendPresenter.publishActivityWithJiangli(unit, name, intro, fileUrl, beginTime, endTime, rule, typeJiangping, jiangpingNum, kaijiangTime);
        } else {
            sendPresenter.publishActivity(unit, name, intro, fileUrl, beginTime, endTime, rule);
        }


    }

    @Override
    public void uploadPhotoFailed() {
        toast(getString(R.string.failed_upload));
    }

    @Override
    public void unitError() {
        toast(getString(R.string.error_unit));
    }

    @Override
    public void nameError() {
        toast(getString(R.string.error_name));
    }

    @Override
    public void introError() {
        toast(getString(R.string.error_intro));
    }

    @Override
    public void publishSucc() {
        EventBus.getDefault().post(new PublishSuccessEvent(getString(R.string.succ_publish)));
        finish();
    }

    @Override
    public void publishFailed() {
        toast(getString(R.string.fail_publish));
    }

    @Override
    public void beginTimeError() {
        toast(getString(R.string.error_beginTime));
    }

    @Override
    public void endTimeError() {
        toast(getString(R.string.error_endTime));
    }

    @Override
    public void ruleError() {
        toast(getString(R.string.error_rule));
    }

    @Override
    public void typeJiangpingError() {
        toast(getString(R.string.error_typeJiangping));
    }

    @Override
    public void jiangpingNumError() {
        toast(getString(R.string.error_jiangpingNum));
    }

    @Override
    public void kaijiangTimeError() {
        toast(getString(R.string.error_kaijiangTime));
    }

    @Override
    public void beginTimeFail() {
        toast(getString(R.string.fail_beginTime));
    }

    @Override
    public void endTimeFail() {
        toast(getString(R.string.fail_endTime));
    }

    @Override
    public void kaijiangTimeFail() {
        toast(getString(R.string.fail_kaijiangTime));
    }


    @Override
    public void selected(int year, int month, int dayOfMonth, TextView textView) {
        super.selected(year, month, dayOfMonth, textView);
        textView.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
    }



}
