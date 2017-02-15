package com.yobin.stee.tassel.view.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yobin.stee.tassel.R;
import com.yobin.stee.tassel.adapter.QuickAdapter;
import com.yobin.stee.tassel.base.BaseFragment;
import com.yobin.stee.tassel.base.MyApplication;
import com.yobin.stee.tassel.beans.Item;
import com.yobin.stee.tassel.utils.GankBeautyResultToItemsMapper;
import com.yobin.stee.tassel.utils.ImageUtils;
import com.yobin.stee.tassel.utils.NetWorkUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class GalleryFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    private  GalleryFragment fragment;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView rvGallery;
    private List<Item> allList = new ArrayList<>();
    private QuickAdapter<Item> adapter = null;
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
        rvGallery = initResId(mContextView,R.id.rv_gallery);
    }

    @Override
    protected void doBusiness(Context mContext) {
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_red_light,android.R.color.holo_orange_light);

        mSwipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) getContext());

        loadPage(1);

        adapter = new QuickAdapter<Item>(allList) {
            @Override
            public int getLayoutId(int ViewType) {
                return R.layout.item_rv_gallery;
            }

            @Override
            public void convert(QuickAdapter.VH holder, Item data, int position) {
                ImageView mImageView = holder.getView(R.id.iv_rv_Image);
                TextView mTextView = holder.getView(R.id.tv_rv_time);
                //进行相应的赋值


            }

            @Override
            public int getItemViewType(int position) {
                return super.getItemViewType(position);
            }
        };
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rvGallery.setLayoutManager(manager);
        rvGallery.setHasFixedSize(true);
        rvGallery.setAdapter(adapter);

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
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(MyApplication.getContext(),getResources().getString(R.string.load_failure), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(List<Item> items) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        adapter.setmDatas(items);
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

    @Override
    public void onRefresh() {

    }
}
