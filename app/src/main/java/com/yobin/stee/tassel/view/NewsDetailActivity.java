package com.yobin.stee.tassel.view;


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



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.yobin.stee.tassel.R;
import com.yobin.stee.tassel.base.BaseNewsActivity;
import com.yobin.stee.tassel.beans.News;
import com.yobin.stee.tassel.view.fragment.NewsDetailFragment;

/**
 * Created by Yobin_He on 2017/2/22.
 */

public class NewsDetailActivity extends BaseNewsActivity {
    public static final String KEY_NEWS = "key_news";

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mSwipeBackLayout.setEdgeDp(120); //触动距离
        News news = getIntent().getParcelableExtra(KEY_NEWS);
        showNewsDetailFragment(news);
    }
    private void showNewsDetailFragment(News news) {
        NewsDetailFragment fragment = NewsDetailFragment.newInstance(news);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        //这里必须用相应得到app的Fragment，否则会出现错误
        transaction.replace(R.id.fl_container,fragment,NewsDetailFragment.TAG);
        transaction.commit();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    //从相应的跳转出进行跳转，这里可以更好的解决跳转页面的问题
    public static void startAction(Context context, News news) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(KEY_NEWS, news);
        context.startActivity(intent);
    }
}
