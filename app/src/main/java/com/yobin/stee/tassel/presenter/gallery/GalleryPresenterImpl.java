package com.yobin.stee.tassel.presenter.gallery;

import android.content.Context;

import com.yobin.stee.tassel.base.BasePresenterImpl;
import com.yobin.stee.tassel.beans.Item;
import com.yobin.stee.tassel.model.gallery.GalleryModelImpl;
import com.yobin.stee.tassel.utils.LogUtils;
import com.yobin.stee.tassel.view.IGalleryView;

import java.util.List;

/**
 * Created by yobin_he on 2017/2/16.
 */

public class GalleryPresenterImpl extends BasePresenterImpl<IGalleryView,List<Item>>{

    private GalleryModelImpl galleryModel;
    private Context mContext;

    public GalleryPresenterImpl(IGalleryView view,Context context) {
        super(view);  //初始化了View
        this.mContext = context;
        galleryModel = new GalleryModelImpl(mContext);
    }

    public void getGalleryInfo(int page,int requestTag){
        onResume();
        beforeRequest(requestTag); //请求之前的准备
        LogUtils.i("---->>>page",page+"");
        galleryModel.loadGalleryInfo(page,this,requestTag);
    }
}
