package com.yobin.stee.tassel.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yobin.stee.tassel.R;
import com.yobin.stee.tassel.base.BaseFragment;

/**
 * Created by yobin_he on 2017/2/14.
 *
 */

public class VideoFragment extends BaseFragment {
    private  VideoFragment fragment;
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_video, container, false);
//    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initView(View mContextView) {

    }

    @Override
    protected void doBusiness(Context mContext) {

    }

    @Override
    public void widgetClick(View v) {

    }

    public  VideoFragment newInstance() {
        if(fragment == null){
            fragment = new VideoFragment();
        }
        return fragment;
    }
}
