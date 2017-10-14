package com.gdcp.yueyunku_business.ui.fragment;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gdcp.yueyunku_business.R;
import com.gdcp.yueyunku_business.adapter.SpaceCommentAdapter;
import com.gdcp.yueyunku_business.callback.EmptyCallback;
import com.gdcp.yueyunku_business.callback.LoadingCallback;
import com.gdcp.yueyunku_business.model.Space;
import com.gdcp.yueyunku_business.model.SpaceComment;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.Convertor;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by asus- on 2017/10/13.
 */

public class SpaceCommentFragment extends Fragment{
    private View view;
    private ListView listView;
    private String name;
    private SpaceCommentAdapter spaceCommentAdapter;
    private List<SpaceComment>spaceComments=new ArrayList<>();
    private LoadService loadService;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         if (view==null){
             view= View.inflate(getActivity(), R.layout.fragment_space_comment, null);
             listView= (ListView) view.findViewById(R.id.listview);
             loadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
                 @Override
                 public void onReload(View v) {
                     //loadService.showCallback(LoadingCallback.class);
                     //reloadData();

                 }}, new Convertor<Integer>() {
                 @Override
                 public Class<? extends Callback> map(Integer integer) {
                     Class<? extends Callback> resultCode = SuccessCallback.class;
                     switch (integer) {
                         case 100://成功回调
                             resultCode = SuccessCallback.class;
                             break;
                         case 0:
                             resultCode = EmptyCallback.class;
                             break;
                     }
                     return resultCode;
                 }
             });
             initData();
         }
        return loadService.getLoadLayout();
    }

    private void initData() {
        spaceCommentAdapter=new SpaceCommentAdapter(getActivity(),spaceComments);
        listView.setAdapter(spaceCommentAdapter);
        getData();
    }

    private void getData() {
        BmobQuery<Space> query = new BmobQuery<Space>();
        query.addWhereEqualTo("type",  name);
        query.addWhereEqualTo("business",  BmobUser.getCurrentUser());    // 查询当前用户的所有帖子
        query.include("type");
        query.include("business");// 希望在查询帖子信息的同时也把发布人的信息查询出来
        query.findObjects(new FindListener<Space>() {
            @Override
            public void done(List<Space> list, BmobException e) {
                if(e==null){
                    if (list.size()!=0){
                        getSpaceComment(list.get(0));
                    }else {
                        loadService.showWithConvertor(0);
                    }

                }else{
                    loadService.showWithConvertor(0);
                }
            }
        });
    }

    private void getSpaceComment(Space space) {
            BmobQuery<SpaceComment> query = new BmobQuery<SpaceComment>();
            query.addWhereEqualTo("space",  space);
            query.include("commenter");
            query.findObjects(new FindListener<SpaceComment>() {
                @Override
                public void done(List<SpaceComment> list, BmobException e) {
                    if(e==null){
                        if (list.size()!=0){
                            loadService.showWithConvertor(100);
                            spaceComments.addAll(list);
                            spaceCommentAdapter.notifyDataSetChanged();
                        }else {
                            loadService.showWithConvertor(0);
                        }

                    }else{
                        loadService.showWithConvertor(0);
                    }
                }
            });

    }

    public void setChannelName(String name){
      this.name=name;
    }

}
