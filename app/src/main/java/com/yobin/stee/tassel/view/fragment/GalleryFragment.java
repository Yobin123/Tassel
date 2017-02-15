package com.yobin.stee.tassel.view.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yobin.stee.tassel.R;
import com.yobin.stee.tassel.base.BaseFragment;
import com.yobin.stee.tassel.beans.Item;
import com.yobin.stee.tassel.utils.GankBeautyResultToItemsMapper;
import com.yobin.stee.tassel.utils.NetWorkUtils;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class GalleryFragment extends BaseFragment {
    private  GalleryFragment fragment;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView rvGallary;

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
        mSwipeRefreshLayout = initResId(mContextView, R.id.swipe_refresh_gallery);
        rvGallary = initResId(mContextView,R.id.rv_gallery);
    }

    @Override
    protected void doBusiness(Context mContext) {
        //进行相应的联网操作
        

    }

    private void loadPage(int page){
        mSwipeRefreshLayout.setRefreshing(true);
        unSubscribe();
        subscription = NetWorkUtils.getGalleryApi()
                .getBeauties(10,page)
                .map(GankBeautyResultToItemsMapper.getInstance())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Item>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Item> items) {

                    }
                });

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
