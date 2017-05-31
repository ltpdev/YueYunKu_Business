package com.gdcp.yueyunku_business.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gdcp.yueyunku_business.R;
import com.gdcp.yueyunku_business.listener.LookActivityListener;
import com.gdcp.yueyunku_business.model.Activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Asus on 2017/5/21.
 */

public class HuodongAdapter extends RecyclerView.Adapter<HuodongAdapter.ViewHolder>{
    private List<Activity>activityList;
    private Context context;
    private LookActivityListener lookActivityListener;
    public void setLookActivityListener(LookActivityListener lookActivityListener){
        this.lookActivityListener=lookActivityListener;
    }
    public HuodongAdapter(List<Activity> activityList, Context context) {
        this.activityList = activityList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_huodong,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Activity activity=activityList.get(position);
        int days = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateNowStr = sdf.format(new Date());
            //Date begDate = sdf.parse(activity.getBeginTime());
            Date endDate = sdf.parse(activity.getEndTime());
            Date nowDate = sdf.parse(dateNowStr);
            if (nowDate.getTime()<=endDate.getTime()){
                days =(int) ((endDate.getTime() - nowDate.getTime()) / (24 * 60 * 60 * 1000)) + 1;
                holder.tvTimeRemain.setText("剩余"+days+"天0个小时");
            }else {
                holder.tvTimeRemain.setText("已结束");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tvActivityName.setText(activity.getName());
        holder.tvActivityIntro.setText(activity.getActivityIntro());
        holder.tvTimeActivity.setText(activity.getBeginTime()+"--"+activity.getEndTime());
        Glide.with(context).load(activity.getPicUrl()).into(holder.ivActivity);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lookActivityListener.lookActivity(activity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivActivity;
        TextView tvActivityName;
        TextView tvActivityIntro;
        TextView tvTimeActivity;
        TextView tvTimeRemain;
        public ViewHolder(View itemView) {
            super(itemView);
            ivActivity= (ImageView) itemView.findViewById(R.id.iv_activity);
            tvActivityName= (TextView) itemView.findViewById(R.id.tv_name_activity);
            tvActivityIntro= (TextView) itemView.findViewById(R.id.tv_intro_activity);
            tvTimeActivity= (TextView) itemView.findViewById(R.id.tv_time_activity);
            tvTimeRemain= (TextView) itemView.findViewById(R.id.tv_time_remain);

        }
    }
}
