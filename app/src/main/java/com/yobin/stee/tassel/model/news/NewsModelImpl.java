package com.yobin.stee.tassel.model.news;


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

import com.yobin.stee.tassel.base.MyApplication;
import com.yobin.stee.tassel.beans.News;
import com.yobin.stee.tassel.beans.NewsList;
import com.yobin.stee.tassel.db.dao.NewDao;
import com.yobin.stee.tassel.helper.RetrofitManager;
import com.yobin.stee.tassel.presenter.news.NewsPresenterImpl;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Yobin_He on 2017/2/22.
 */

public class NewsModelImpl implements INewsModel{

    private OnLoadDataListener listener;

    /**
     * 获取相应的最近的信息
     * @param listener
     */
    @Override
    public void loadLatesedData(OnLoadDataListener listener) {

    }

    /**
     * 获取以前的信息
     * @param date
     * @param listener
     */
    @Override
    public void loadBeforeData(String date, final OnLoadDataListener listener) {
        //获取带有日期的数据
        RetrofitManager.builder().getBeforeNews(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<NewsList, NewsList>() {
                    @Override
                    public NewsList call(NewsList newsList) {
//                        cacheAllDetail(newsList.getStories());
                        return changeReadState(newsList);
                    }
                })
                .subscribe(new Action1<NewsList>() {
                    @Override
                    public void call(NewsList newsList) {
//                        mAutoLoadOnScrollListener.setLoading(false);  //停止刷新
//                        mLoadBeforeSnackbar.dismiss();

//                        $Log(newsList.getStories().toString());

//                        mNewsListAdapter.addData(newsList.getStories()); //没有日期的加入的数据
//                        curDate = newsList.getDate();
                        if(newsList.getStories() != null || newsList !=null){
                            //通过接口来回调相应的数据
                            listener.success(newsList.getStories());
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
//                        mAutoLoadOnScrollListener.setLoading(false); //停止刷新
//                        mLoadBeforeSnackbar.show();
                          listener.onFailure("load list failure", (Exception) throwable);
                    }
                });
    }

    //可以通过接口来进行相应的传值
   public interface OnLoadDataListener{
        void success(List<News> list);
        void onFailure(String msg,Exception ex);
    }



    public NewsList changeReadState(NewsList newsList) {
        List<String> allReadId = MyApplication.dao.getAllReadNew();
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
}
