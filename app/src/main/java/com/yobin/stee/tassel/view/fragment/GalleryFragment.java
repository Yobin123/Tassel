package com.yobin.stee.tassel.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yobin.stee.tassel.R;
import com.yobin.stee.tassel.base.BaseFragment;



public class GalleryFragment extends BaseFragment {
    private  GalleryFragment fragment;
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_gallery, container, false);
//    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_gallery;
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

    public  GalleryFragment newInstance() {
        if(fragment == null){
            fragment = new GalleryFragment();
        }
        return fragment;
    }
}
