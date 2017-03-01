package com.yobin.stee.tassel.view.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yalantis.phoenix.PullToRefreshView;
import com.yobin.stee.tassel.R;
import com.yobin.stee.tassel.adapter.NewsListAdapter;
import com.yobin.stee.tassel.base.BaseNewsFragment;
import com.yobin.stee.tassel.beans.News;
import com.yobin.stee.tassel.beans.NewsDetail;
import com.yobin.stee.tassel.beans.NewsList;
import com.yobin.stee.tassel.db.dao.NewDao;
import com.yobin.stee.tassel.helper.RetrofitManager;
import com.yobin.stee.tassel.utils.NetUtil;
import com.yobin.stee.tassel.view.interfaces.INewsView;
import com.yobin.stee.tassel.wideget.AutoLoadOnScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class NewsFragment1 extends BaseNewsFragment implements PullToRefreshView.OnRefreshListener ,INewsView{

    public static final String EXTRA_POSITION = "position";
    public static final String EXTRA_SCROLL = "scroll";
    public static final String EXTRA_CURDATE = "curdate";

    @BindView(R.id.rv_news_list)
    RecyclerView mRvNewsList;
    @BindView(R.id.ptr_news_list)
    PullToRefreshView mPtrNewsList;
    @BindView(R.id.tv_load_empty)
    TextView mTvLoadEmpty;
    @BindView(R.id.tv_load_error)
    TextView mTvLoadError;
    @BindView(R.id.pb_loading)
    ContentLoadingProgressBar mPbLoading;

    private LinearLayoutManager mLinearManager;
    private NewsListAdapter mNewsListAdapter;
    private NewsListAdapter mExtraAdapter;
    private String curDate;
    private Snackbar mLoadLatestSnackbar;
    private Snackbar mLoadBeforeSnackbar;
    private List<News> mNewsList;
    private AutoLoadOnScrollListener mAutoLoadOnScrollListener;
    private OnRecyclerViewCreated mOnRecyclerViewCreated;

    private boolean move = false;
    //记录顶部显示的项
    private int position = 0;

    //记录顶部项的的偏移
    private int scroll = 0 ;




    @Override
    public void widgetClick(View v) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {
        //用于设置相应的toolbar,此时没有什么用
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        mPtrNewsList.setOnRefreshListener(this);
        //配置相应的RecycleView
        mLinearManager = new LinearLayoutManager(getActivity());
        mRvNewsList.setLayoutManager(mLinearManager);
        mRvNewsList.setHasFixedSize(true);
        mRvNewsList.setItemAnimator(new DefaultItemAnimator());

        //设置相应的适配器
        mNewsListAdapter = new NewsListAdapter(getActivity(),new ArrayList<News>());
        mRvNewsList.setAdapter(mNewsListAdapter);

        //
        loadLatestNews();
        loadBeforeNews(curDate);


        mAutoLoadOnScrollListener = new AutoLoadOnScrollListener(mLinearManager) {
            @Override
            public void onLoadMore(int currentPage) {
                //加载当前日期数据：
                loadBeforeNews(curDate);

            }
        };
        mRvNewsList.addOnScrollListener(mAutoLoadOnScrollListener);

        mRvNewsList.addOnScrollListener(new RecyclerViewListener());

//        if(mExtraAdapter != null){
//            mNewsListAdapter.setAnim(false);
//            mNewsListAdapter.setmNewsList(mExtraAdapter.getmNewsList());
//            mNewsListAdapter.notifyDataSetChanged();
////            move();
//        }

        mLoadLatestSnackbar = Snackbar.make(mRvNewsList, R.string.load_fail, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.refresh, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadLatestNews();
                    }
                });
        mLoadBeforeSnackbar = Snackbar.make(mRvNewsList, R.string.load_more_fail, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.refresh, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadBeforeNews(curDate);
                    }
                });
    }


    //加载前面的数据
    private void loadBeforeNews(String date) {
        //获取带有日期的数据
        RetrofitManager.builder().getBeforeNews(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<NewsList, NewsList>() {
                    @Override
                    public NewsList call(NewsList newsList) {
                        cacheAllDetail(newsList.getStories());
                        return changeReadState(newsList);
                    }
                })
                .subscribe(new Action1<NewsList>() {
                    @Override
                    public void call(NewsList newsList) {
                        mAutoLoadOnScrollListener.setLoading(false);  //停止刷新
                        mLoadBeforeSnackbar.dismiss();

                        $Log(newsList.getStories().toString());

                        mNewsListAdapter.addData(newsList.getStories()); //没有日期的加入的数据
                        curDate = newsList.getDate();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mAutoLoadOnScrollListener.setLoading(false); //停止刷新
                        mLoadBeforeSnackbar.show();
                    }
                });
    }

    //加载最新的数据
    private void loadLatestNews() {
        RetrofitManager.builder().getLatestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgress();
                    }
                })
                .map(new Func1<NewsList, NewsList>() {
                    @Override
                    public NewsList call(NewsList newsList) {
                        cacheAllDetail(newsList.getStories());
                        return changeReadState(newsList);
                    }
                })
                .subscribe(new Action1<NewsList>() {
                    @Override
                    public void call(NewsList newsList) {
                        mPtrNewsList.setRefreshing(false);
                        hideProgress();

                        if (newsList.getStories() == null) {
                            mTvLoadEmpty.setVisibility(View.VISIBLE);
                        } else {
                            mNewsListAdapter.changeData(newsList.getStories());
                            curDate = newsList.getDate();
                            mTvLoadEmpty.setVisibility(View.GONE);
                        }
                        mTvLoadError.setVisibility(View.GONE);
                        mLoadLatestSnackbar.dismiss();
                        if (newsList.getStories().size() < 8) {
                            loadBeforeNews(curDate);
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mPtrNewsList.setRefreshing(false);
                        hideProgress();
                        mLoadLatestSnackbar.show();
                        mTvLoadError.setVisibility(View.VISIBLE);
                        mTvLoadEmpty.setVisibility(View.GONE);
                    }
                });
    }

    public NewsList changeReadState(NewsList newsList) {
        List<String> allReadId = new NewDao(getActivity()).getAllReadNew();
        for (News news : newsList.getStories()) {
            //设置相应的日期，进入新的集合
            news.setDate(newsList.getDate());
            for (String readId : allReadId) {
                //通过保存的在数据库id的状态确定是否读取，如果读取，设置相应数据为已读
                if (readId.equals(news.getId() + "")) {
                    news.setRead(true);
                }
            }
        }
        return newsList;
    }

    private void move() {
        if (position < 0 || position >= mRvNewsList.getAdapter().getItemCount()) {
            return;
        }
        int firstItem = mLinearManager.findFirstVisibleItemPosition();
        int lastItem = mLinearManager.findLastVisibleItemPosition();

        if (position <= firstItem) {
            mRvNewsList.scrollToPosition(position);
            move = true;
        } else if (position <= lastItem) {
            int top = mRvNewsList.getChildAt(position - firstItem).getTop() - scroll;
            mRvNewsList.scrollBy(0, top);
            if (mOnRecyclerViewCreated != null) {
                mOnRecyclerViewCreated.recyclerViewCreated();
            }
        } else {
            mRvNewsList.scrollToPosition(position);
            move = true;
        }
    }




    private void cacheAllDetail(List<News> newsList) {
        if (NetUtil.isWifiConnected()) {
            for (News news : newsList) {
                //获取相应的详情页面的信息
                cacheNewsDetail(news.getId());
            }
        }
    }
    private void cacheNewsDetail(int newsId) {
        RetrofitManager.builder().getNewsDetail(newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Action1<NewsDetail>() {
                    @Override
                    public void call(NewsDetail newsDetail) {
//                        ArrayList<String> imgList = getImgs(newsDetail.getBody());
//                        for (String img : imgList) {
//                            L.d("Cache img: " + img);
//                        }
                    }
                });
    }



    public NewsFragment1() {

    }

    //实现相应的数据化
    public static NewsFragment1 newInstance() {
        NewsFragment1 fragment = new NewsFragment1();
//        Bundle bundle = new Bundle();
//        bundle.putInt(EXTRA_POSITION, position);
//        bundle.putInt(EXTRA_SCROLL, scroll);
//        bundle.putString(EXTRA_CURDATE,curDate);
//        fragment.setArguments(bundle);
        return fragment;
    }






    class RecyclerViewListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (move) {
                move = false;
                int n = position - mLinearManager.findFirstVisibleItemPosition();
                if (0 <= n && n < mRvNewsList.getChildCount()) {
                    int top = mRvNewsList.getChildAt(n).getTop() - scroll;
                    mRvNewsList.smoothScrollBy(0, top);
                }
                //这个地方是起到相应通知的作用
                if (mOnRecyclerViewCreated != null) {
                    mOnRecyclerViewCreated.recyclerViewCreated();
                }
            }
        }
    }

    public interface OnRecyclerViewCreated{
        void recyclerViewCreated();
    }

    public void setmOnRecyclerViewCreated(OnRecyclerViewCreated mOnRecyclerViewCreated) {
        this.mOnRecyclerViewCreated = mOnRecyclerViewCreated;
    }


    @Override
    public void onRefresh() {
        //用于加载最新数据
        loadLatestNews();
    }



    public void showProgress() {
        mPbLoading.setVisibility(View.VISIBLE);
    }


    @Override
    public void hideProgress() {
        mPbLoading.setVisibility(View.GONE);
    }

    @Override
    public void showLoadFailMsg() {

    }

    @Override
    public void loadLatestData(List<News> list) {

    }

    @Override
    public void showLoadBeforeData(List<News> list) {

    }
}
