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
import com.gdcp.yueyunku_business.listener.LookDoingActivityListener;
import com.gdcp.yueyunku_business.listener.LookNoDoActivityListener;
import com.gdcp.yueyunku_business.model.Order;

import java.util.List;

/**
 * Created by Asus on 2017/5/22.
 */

public class DoingAdapter extends RecyclerView.Adapter<DoingAdapter.ViewHolder>{
    private List<Order> orderList;
    private Context context;

    public DoingAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }


    private LookDoingActivityListener lookDoingActivityListener;

    @Override
    public DoingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_doing, parent, false);
        DoingAdapter.ViewHolder holder = new DoingAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DoingAdapter.ViewHolder holder, int position) {
        final Order order=orderList.get(position);
        Glide.with(context).load(order.getOrderUrl()).into(holder.ivPlaceSport);
        holder.tvNameParticipant.setText("发起人："+order.getJoiner().getUsername());
        holder.tvPhoneParticipant.setText("手机号码:"+order.getJoiner().getMobilePhoneNumber());
        holder.tvPricePlace.setText("价格："+order.getPrice()+"元/h");
        holder.tvTimeSport.setText("运动时间:"+order.getBook_time());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lookDoingActivityListener.lookActivity(order);
            }
        });
    }

    public void setLookDoingActivityListener(LookDoingActivityListener lookDoingActivityListener) {
        this.lookDoingActivityListener = lookDoingActivityListener;
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPlaceSport;
        TextView tvNameParticipant;
        TextView tvPhoneParticipant;
        TextView tvTimeSport;
        TextView tvPricePlace;
        public ViewHolder(View itemView) {
            super(itemView);
            ivPlaceSport= (ImageView) itemView.findViewById(R.id.iv_place_sport);
            tvNameParticipant= (TextView) itemView.findViewById(R.id.tv_name_participant);
            tvPhoneParticipant= (TextView) itemView.findViewById(R.id.tv_phone_participant);
            tvTimeSport= (TextView) itemView.findViewById(R.id.tv_time_sport);
            tvPricePlace= (TextView) itemView.findViewById(R.id.tv_price_place);

        }
    }
}
