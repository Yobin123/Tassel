package com.yobin.stee.tassel.view.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yobin.stee.tassel.R;
import com.yobin.stee.tassel.adapter.QuickAdapter;
import com.yobin.stee.tassel.base.BaseFragment;
import com.yobin.stee.tassel.beans.Item;
import com.yobin.stee.tassel.utils.GankBeautyResultToItemsMapper;
import com.yobin.stee.tassel.utils.GlideUtil;
import com.yobin.stee.tassel.utils.NetWorkUtils;
import com.yobin.stee.tassel.view.IGalleryView;
import com.yobin.stee.tassel.wideget.Dividers.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yobin_he on 2017/2/16.
 */

public class GalleryFragment1 extends BaseFragment implements IGalleryView{
    private  GalleryFragment1 fragment;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView rvGallery;
    private List<Item> allList = new ArrayList<>();
    private QuickAdapter<Item> adapter = null;
    private int lastVisibleItemPosition = 0;
    private int page = 1;
    private static final int PRELOAD_SIZE = 6;
    private boolean mIsFirstTimeTouchBottom = true;
//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what){
//                case 0:
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    break;
//            }
//        }
//    };
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
    protected void doBusiness(final Context mContext) {
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_red_light,android.R.color.holo_orange_light);

        loadPage(mContext,page);
        adapter = new QuickAdapter<Item>(allList) {
            @Override
            public int getLayoutId(int ViewType) {
                return R.layout.item_rv_gallery;
            }

            @Override
            public void convert(QuickAdapter.VH holder, Item data, int position) {
                final ImageView mImageView = holder.getView(R.id.iv_rv_Image);
                TextView mTextView = holder.getView(R.id.tv_rv_time);
                ViewGroup.LayoutParams lp;
                if(position == 0){
                    lp= mImageView.getLayoutParams();
                    lp.height = 200;
                    mImageView.setLayoutParams(lp);
                }

                //进行相应的赋值
                mTextView.setText(data.description);
//               mImageView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
//                Glide.with(mContext)
//                        .load(data.imageUrl)
//                        .centerCrop()
//                        .into(mImageView);
                GlideUtil.getInstance().loadImage(mContext,mImageView,data.imageUrl,true);
            }

            @Override
            public int getItemViewType(int position) {
                return super.getItemViewType(position);
            }
        };

        rvGallery.setHasFixedSize(true);
        rvGallery.setItemAnimator(new DefaultItemAnimator());
        rvGallery.addItemDecoration(new SpacesItemDecoration(10));
//        final GridLayoutManager manager = new GridLayoutManager(mContext,2,1,false);
        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rvGallery.setLayoutManager(manager);
        rvGallery.setAdapter(adapter);

        //上拉加载
//        rvGallery.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
////                lastVisibleItemPosition = manager.findLastVisibleItemPosition();
//            }
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if(lastVisibleItemPosition + 1 == adapter.getItemCount() && newState == RecyclerView.SCROLL_STATE_IDLE){
//                    loadPage(mContext,page++);
////                    handler.sendEmptyMessageDelayed(0,3000);
//                }
//            }
//        });
//        rvGallery.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            //用来标记是否正在向最后一个滑动，即是否向下滑动
//            boolean isSlidingToLast = false;
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
//                if(dy > 0){
//                    //大于0表示正在向下滚动
//                    isSlidingToLast = true;
//                }else {
//                    //小于或等于0,表示停止或向上滚动
//                    isSlidingToLast = false;
//                }
//            }
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
////                super.onScrollStateChanged(recyclerView, newState);
//                //当不滚动时:
//                if(newState == RecyclerView.SCROLL_STATE_IDLE){
//                    //获取最后一个完全显示的ItemPosition;
//                    int[] lastVisiblePositisons = manager.findLastVisibleItemPositions(new int[manager.getSpanCount()]);
//                    int lastVisiblePos = getMaxElem(lastVisiblePositisons);
//                    int totalItemCount = manager.getItemCount();
//                    //判断是否活动到底部
//                    if(lastVisiblePos == (totalItemCount-1) && isSlidingToLast){
//                        //加载更多
//                        loadPage(mContext,page++);
//                    }
//                }
//            }
//        });
        rvGallery.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
                boolean isBottom = manager.findLastCompletelyVisibleItemPositions(new int[2])[1] >= adapter.getItemCount() - PRELOAD_SIZE;
                if(!mSwipeRefreshLayout.isRefreshing() && isBottom){
                    if(!mIsFirstTimeTouchBottom){
                        mSwipeRefreshLayout.setRefreshing(true);
                        page += 1;
                        loadPage(mContext,page);
                    }else {
                        mIsFirstTimeTouchBottom = false;
                    }
                }
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                loadPage(mContext,1);
//                handler.sendEmptyMessageDelayed(0,3000);
                closeSwipeRefresh(mSwipeRefreshLayout);
            }
        });


    }

  

    /**
     *进行加载数据
     * @param context
     * @param page
     */
    private void loadPage(final Context context, int page){
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
//                        mSwipeRefreshLayout.setRefreshing(false);
                        closeSwipeRefresh(mSwipeRefreshLayout);
                    }

                    @Override
                    public void onError(Throwable e) {
//                        mSwipeRefreshLayout.setRefreshing(false);
                        closeSwipeRefresh(mSwipeRefreshLayout);
                        Toast.makeText(context,getResources().getString(R.string.load_failure), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(List<Item> items) {

//                        mSwipeRefreshLayout.setRefreshing(false);
                        closeSwipeRefresh(mSwipeRefreshLayout);
                        adapter.setmDatas(items);
                        $Log(items.get(1).description);
                    }
                });

    }

    @Override
    public void widgetClick(View v) {

    }

    /**
     * 关闭相应的SwipeRefreshLayout
     * @param view
     */
    public void closeSwipeRefresh(final SwipeRefreshLayout view){
        if(view!=null && view.isRefreshing()){
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setRefreshing(false);
                }
            },3000);
        }
    }


    /**
     * 碎片的单例模式
     * @return
     */
    public GalleryFragment1 newInstance() {
        if(fragment == null){
            fragment = new GalleryFragment1();
        }
      return fragment;
    }


    @Override
    public void toast(String msg, int requestTag) {

    }

    @Override
    public void showProgress(int requestTag) {

    }

    @Override
    public void hideProgress(int requestTag) {

    }

    @Override
    public void loadDataSuccess(List<Item> data, int RequestTag) {

    }

    @Override
    public void loadDataError(Throwable e, int requestTag) {

    }
}
