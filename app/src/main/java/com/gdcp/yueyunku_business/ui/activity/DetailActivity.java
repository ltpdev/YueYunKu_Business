package com.gdcp.yueyunku_business.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gdcp.yueyunku_business.R;
import com.gdcp.yueyunku_business.event.PublishSuccessEvent;
import com.gdcp.yueyunku_business.model.Activity;
import com.gdcp.yueyunku_business.model.User;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class DetailActivity extends BaseActivity {


    @BindView(R.id.tv_tool_title)
    TextView tvToolTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_activity)
    ImageView ivActivity;
    @BindView(R.id.tv_name_activity)
    TextView tvNameActivity;
    @BindView(R.id.tv_intro_activity)
    TextView tvIntroActivity;
    @BindView(R.id.tv_time_activity)
    TextView tvTimeActivity;
    @BindView(R.id.tv_rule_activity)
    TextView tvRuleActivity;
    @BindView(R.id.tv_type_jiangping)
    TextView tvTypeJiangping;
    @BindView(R.id.tv_num_jiangping)
    TextView tvNumJiangping;
    @BindView(R.id.tv_time_kaijiang)
    TextView tvTimeKaijiang;
    @BindView(R.id.gone_linearLayout)
    LinearLayout goneLinearLayout;
    @BindView(R.id.tv_send)
    TextView tvSend;
    private Activity activity;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void init() {
        super.init();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        User user=BmobUser.getCurrentUser(User.class);
        activity= (Activity) getIntent().getSerializableExtra("activity");
        if (user!=null){
            if (activity.getBusiness().getObjectId().equals(user.getObjectId())){
                tvSend.setText(getString(R.string.delete));
                tvSend.setVisibility(View.VISIBLE);
            }
        }

        tvToolTitle.setText(activity.getBusiness().getUsername());
        tvNameActivity.setText(activity.getName());
        tvIntroActivity.setText(activity.getActivityIntro());
        tvTimeActivity.setText(activity.getBeginTime() + "-" + activity.getEndTime());
        tvRuleActivity.setText(activity.getRule());
        String type = activity.getTypeJiangping();
        String jiangpingNum = activity.getJiangpingNum();
        String kaijiangTime = activity.getKaijiangTime();
        if (type != null) {
            goneLinearLayout.setVisibility(View.VISIBLE);
            tvTypeJiangping.setText(type);
            tvNumJiangping.setText(jiangpingNum);
            tvTimeKaijiang.setText(kaijiangTime);
        }
        Glide.with(this).load(activity.getPicUrl()).into(ivActivity);
    }



    @OnClick({R.id.tv_send})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_send:
                showAlertDialog("确定删除该活动?");
                break;
        }
    }

    @Override
    protected void doSomeThing() {
        super.doSomeThing();
        Activity activityDelete=new Activity();
        activityDelete.delete(activity.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    EventBus.getDefault().post(new PublishSuccessEvent("删除成功"));
                    finish();
                }else {
                    toast("删除失败");
                    finish();
                }
            }
        });
    }
}
