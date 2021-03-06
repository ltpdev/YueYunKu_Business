package com.gdcp.yueyunku_business.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gdcp.yueyunku_business.R;
import com.gdcp.yueyunku_business.listener.LookHadOverActivityListener;
import com.gdcp.yueyunku_business.ui.activity.HadOverActivity;

/**
 * Created by Asus on 2017/5/22.
 */

public class HadOverDingDanAdapter extends RecyclerView.Adapter<HadOverDingDanAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dingdan_had_over, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    public void setLookHadOverActivityListener(LookHadOverActivityListener lookHadOverActivityListener) {

    }


    @Override
    public int getItemCount() {
        return 8;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
