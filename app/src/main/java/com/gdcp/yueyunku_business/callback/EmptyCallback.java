package com.gdcp.yueyunku_business.callback;

import android.content.Context;
import android.view.View;

import com.gdcp.yueyunku_business.R;
import com.kingja.loadsir.callback.Callback;

/**
 * Created by asus- on 2017/10/14.
 */

public class EmptyCallback extends Callback{

    @Override
    protected int onCreateView() {
        return R.layout.layout_empty;
    }

    @Override
    protected boolean onRetry(Context context, View view) {
        return true;
    }
}
