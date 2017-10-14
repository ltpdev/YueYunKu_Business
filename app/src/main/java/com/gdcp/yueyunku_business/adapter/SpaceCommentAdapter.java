package com.gdcp.yueyunku_business.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gdcp.yueyunku_business.R;
import com.gdcp.yueyunku_business.model.SpaceComment;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by asus- on 2017/10/13.
 */

public class SpaceCommentAdapter extends BaseAdapter{
    private List<SpaceComment>spaceComments;
    private Context context;
    public SpaceCommentAdapter(Context context,List<SpaceComment>spaceComments){
        this.context=context;
        this.spaceComments=spaceComments;
    }

    @Override
    public int getCount() {
        return spaceComments.size();
    }

    @Override
    public Object getItem(int position) {
        return spaceComments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SpaceComment spaceComment=spaceComments.get(position);
        SpaceCommentViewHolder viewHolder;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.item_space_comment,null);
            viewHolder=new SpaceCommentViewHolder();
            viewHolder.head= (CircleImageView) convertView.findViewById(R.id.head_comment);
            viewHolder.commentName= (TextView) convertView.findViewById(R.id.name_comment);
            viewHolder.commentTime= (TextView) convertView.findViewById(R.id.time_comment);
            viewHolder.commentContent= (TextView) convertView.findViewById(R.id.content_comment);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (SpaceCommentViewHolder) convertView.getTag();
        }
        Glide.with(context).load(spaceComment.getCommenter().getHead()).into(viewHolder.head);
        viewHolder.commentName.setText(spaceComment.getCommenter().getUsername());
        viewHolder.commentTime.setText(spaceComment.getCreatedAt());
        viewHolder.commentContent.setText(spaceComment.getContent());
        return convertView;
    }

    class SpaceCommentViewHolder{
        CircleImageView head;
        TextView commentName;
        TextView commentTime;
        TextView commentContent;

    }


}
