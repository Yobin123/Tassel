package com.yobin.stee.tassel.model.gallery;

import android.content.Context;

import com.yobin.stee.tassel.base.BaseModel;
import com.yobin.stee.tassel.base.IBaseRequestCallBack;
import com.yobin.stee.tassel.beans.Item;
import com.yobin.stee.tassel.utils.GankBeautyResultToItemsMapper;
import com.yobin.stee.tassel.utils.api.GalleryApi;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yobin_he on 2017/2/16.
 */

public class GalleryModelImpl extends BaseModel{
    private Context mContext;
    private GalleryApi galleryApi;
    public GalleryModelImpl(Context context){
        super(); //这里相当于初始化了RetrofitManager;
        this.mContext = context;
        galleryApi = retrofitManager.getGalleryApi();
    }

    public void loadGalleryInfo(int page, final IBaseRequestCallBack<List<Item>> callBack, final int requestTag){
        //从相应的服务器获取相应的数据
        galleryApi.getBeauties(10,page)
                    .map(GankBeautyResultToItemsMapper.getInstance())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<Item>>() {
                        @Override
                        public void onCompleted() {
                            callBack.requestComplete(requestTag);
                        }

                        @Override
                        public void onError(Throwable e) {
                            callBack.requestError(e,requestTag);
                        }
                        @Override
                        public void onNext(List<Item> items) {
                            if(items != null){
                                callBack.requestSuccess(items,requestTag);
                            }
                        }
                    });
    }
}
