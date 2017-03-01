package com.yobin.stee.tassel.view.fragment;


////////////////////////////////////////////////////////////////////
//                          _ooOoo_                               //
//                         o8888888o                              //
//                         88" . "88                              //
//                         (| ^_^ |)                              //
//                         O\  =  /O                              //
//                      ____/`---'\____                           //
//                    .'  \\|     |//  `.                         //
//                   /  \\|||  :  |||//  \                        //
//                  /  _||||| -:- |||||-  \                       //
//                  |   | \\\  -  /// |   |                       //
//                  | \_|  ''\---/''  |   |                       //
//                  \  .-\__  `-`  ___/-. /                       //
//                ___`. .'  /--.--\  `. . ___                     //
//              ."" '<  `.___\_<|>_/___.'  >'"".                  //
//            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
//            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
//      ========`-.____`-.___\_____/___.-`____.-'========         //
//                           `=---='                              //
//      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
//         佛祖保佑       永无BUG     永不修改                  //
////////////////////////////////////////////////////////////////////

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yobin.stee.tassel.R;
import com.yobin.stee.tassel.base.BaseNewsFragment;
import com.yobin.stee.tassel.beans.News;
import com.yobin.stee.tassel.beans.NewsDetail;
import com.yobin.stee.tassel.helper.RetrofitManager;
import com.yobin.stee.tassel.utils.HtmlUtil;
import com.yobin.stee.tassel.utils.PrefUtil;
import com.yobin.stee.tassel.view.NewsDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Yobin_He on 2017/2/21.
 */

public class NewsDetailFragment extends BaseNewsFragment {

    @BindView(R.id.iv_header)
    ImageView mIvHeader;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_source)
    TextView mTvSource;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.wv_news)
    WebView mWvNews;
    @BindView(R.id.nested_view)
    NestedScrollView mNestedView;
    @BindView(R.id.tv_load_empty)
    TextView mTvLoadEmpty;
    @BindView(R.id.tv_load_error)
    TextView mTvLoadError;
    @BindView(R.id.pb_loading)
    ContentLoadingProgressBar mPbLoading;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private News mNews;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_detail;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {

        mNews = getArguments().getParcelable(NewsDetailActivity.KEY_NEWS);
        setHasOptionsMenu(true);
        init();
        loadData();
    }

    @Override
    public void widgetClick(View v) {

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            case R.id.menu_action_share:
//                share();
                Toast.makeText(getActivity(), "你点击了分享", Toast.LENGTH_SHORT).show();
                return true;
//            case R.id.menu_action_about:
////                AboutActivity.start(getActivity());
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadData() {
        RetrofitManager.builder().getNewsDetail(mNews.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgress();
                    }
                }).subscribe(new Action1<NewsDetail>() {
            @Override
            public void call(NewsDetail newsDetail) {
                hideProgress();
                if (newsDetail == null) {
                    mTvLoadEmpty.setVisibility(View.VISIBLE);
                } else {
                    Glide.with(getActivity()).load(newsDetail.getImage())
                            .into(mIvHeader);
                    mTvTitle.setText(newsDetail.getTitle());
                    mTvSource.setText(newsDetail.getImage_source());
                    mWvNews.setDrawingCacheEnabled(true);
                    boolean isNight = PrefUtil.isNight();
                    String htmlData = HtmlUtil.createHtmlData(newsDetail, isNight);
                    mWvNews.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
                    mTvLoadEmpty.setVisibility(View.GONE);
                }
                mTvLoadError.setVisibility(View.GONE);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                hideProgress();
                mTvLoadError.setVisibility(View.VISIBLE);
                mTvLoadEmpty.setVisibility(View.GONE);
            }
        });
    }

    private void hideProgress() {
        mPbLoading.setVisibility(View.GONE);
    }

    private void showProgress() {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    private void init() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        ActionBar actionBar = activity.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mCollapsingToolbarLayout.setTitleEnabled(true);
    }


    public static NewsDetailFragment newInstance(News news) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(NewsDetailActivity.KEY_NEWS, news);
        NewsDetailFragment fragment = new NewsDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


}
