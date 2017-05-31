package com.gdcp.yueyunku_business.ui.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import butterknife.ButterKnife;
import cn.bmob.sms.BmobSMS;
import cn.bmob.v3.Bmob;

/**
 * Created by Asus on 2017/5/10.
 */

public abstract class BaseActivity extends AppCompatActivity{

    private ProgressDialog mProgressDialog;

    private InputMethodManager mInputMethodManager;
    private int year;
    private int month;
    private int day;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWindowFuture();
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        init();
    }

    protected void setWindowFuture() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    protected void startActivity(Class activity,boolean isFinish){
        Intent intent=new Intent(this,activity);
        startActivity(intent);
        if (isFinish){
            finish();
        }
    }

    protected void startActivity(Class activity,String key,String value,boolean isFinish){
        Intent intent=new Intent(this,activity);
        intent.putExtra(key,value);
        startActivity(intent);
        if (isFinish){
            finish();
        }
    }


    protected void showProgress(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
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
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void hideKeyBoard() {
        if (mInputMethodManager == null) {
            mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    protected void init(){}

    protected abstract int getLayoutResId();



    public void showDatePickerDialog(final TextView textView){
        Calendar mycalendar=Calendar.getInstance();
        year=mycalendar.get(Calendar.YEAR); //获取Calendar对象中的年
        month=mycalendar.get(Calendar.MONTH);//获取Calendar对象中的月
        day=mycalendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天
        DatePickerDialog dpd=new DatePickerDialog(BaseActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                     selected(year,month,dayOfMonth,textView);
            }
        }, year, month, day);
        dpd.show();//显示DatePickerDialog组件
    }

    public void selected(int year, int month, int dayOfMonth,TextView textView) {

    }


}
