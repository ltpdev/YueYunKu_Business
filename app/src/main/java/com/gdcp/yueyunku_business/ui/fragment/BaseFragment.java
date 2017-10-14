package com.gdcp.yueyunku_business.ui.fragment;

/**
 * Created by Asus on 2017/5/21.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gdcp.yueyunku_business.callback.EmptyCallback;
import com.gdcp.yueyunku_business.callback.LoadingCallback;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.Convertor;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import butterknife.ButterKnife;

/**
 * 创建者:   Leon
 * 创建时间:  2016/10/17 21:52
 * 描述：    TODO
 */
public abstract class BaseFragment extends Fragment {
    public static final String TAG = "BaseFragment";

    private ProgressDialog mProgressDialog;
    private View root;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root==null){
            root = inflater.inflate(getLayoutResId(), null);
            ButterKnife.bind(this, root);
            //第二步：注册布局View
            init();

        }
        return root;
    }

    protected abstract void init();


    protected abstract int getLayoutResId();

    protected void showProgress(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setCancelable(true);
        }
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    protected void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    protected void toast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void startActivity(Class activity) {
        startActivity(activity, false);
    }

    protected void startActivity(Class activity, String key, String extra,int requestCode,boolean finish) {
        Intent intent = new Intent(getActivity(), activity);
        intent.putExtra(key, extra);
        startActivityForResult(intent,requestCode);
        if (finish) {
            getActivity().finish();
        }
    }
    protected void startActivity(Class activity, String key, String extra,boolean finish) {
        Intent intent = new Intent(getActivity(), activity);
        intent.putExtra(key, extra);
        startActivity(intent);
        if (finish) {
            getActivity().finish();
        }
    }


    protected void startActivity(Class activity, boolean finish) {
        Intent intent = new Intent(getContext(), activity);
        startActivity(intent);
        if (finish) {
            getActivity().finish();
        }
    }

    /**
     *  Reset ProgressDialog when finish activity, since we will reuse the fragment created before,
     *  and the dialog refer to the finished activity if we not reset here.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mProgressDialog = null;
    }
}
