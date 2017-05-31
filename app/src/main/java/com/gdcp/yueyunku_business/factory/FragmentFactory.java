package com.gdcp.yueyunku_business.factory;

import android.support.v4.app.Fragment;

import com.gdcp.yueyunku_business.R;
import com.gdcp.yueyunku_business.ui.fragment.DingdanFragment;
import com.gdcp.yueyunku_business.ui.fragment.DoingFragment;
import com.gdcp.yueyunku_business.ui.fragment.HadoverFragment;
import com.gdcp.yueyunku_business.ui.fragment.HuodongFragment;
import com.gdcp.yueyunku_business.ui.fragment.NodoFragment;


public class FragmentFactory {
    private static FragmentFactory sFragmentFactory;
    private HuodongFragment huodongFragment;
    private DingdanFragment dingdanFragment;
    private DoingFragment doingFragment;
    private HadoverFragment hadoverFragment;
    private NodoFragment nodoFragment;
     private FragmentFactory (){

    }
    public static FragmentFactory getInstance() {
        if (sFragmentFactory == null) {
            synchronized (FragmentFactory.class) {
                if (sFragmentFactory == null) {
                    sFragmentFactory = new FragmentFactory();
                }
            }
        }
        return sFragmentFactory;
    }

    public Fragment getFragment(int tabId) {
        switch (tabId) {
            case R.id.tab_activity:
                return getHuodongFragment();
            case R.id.tab_dingdan:
                return getDingDanFragment();
            case R.id.tab_me:
                return getMyFragment();
        }
        return null;
    }


    public Fragment getHuodongFragment() {
        if (huodongFragment == null) {
            huodongFragment = new HuodongFragment();
        }
        return huodongFragment;
    }


    public Fragment getDingDanFragment() {
        if (dingdanFragment==null){
            dingdanFragment=new DingdanFragment();
        }
        return dingdanFragment;
    }

    public Fragment getMyFragment() {
        return null;
    }

    public DoingFragment getDoingFragment(){
        if (doingFragment==null){
            doingFragment=new DoingFragment();
        }
        return doingFragment;
    }

    public HadoverFragment getHadoverFragment(){
        if (hadoverFragment==null){
            hadoverFragment=new HadoverFragment();
        }
        return hadoverFragment;
    }

    public NodoFragment getNodoFragment(){
        if (nodoFragment==null){
            nodoFragment=new NodoFragment();
        }
        return nodoFragment;
    }
}
