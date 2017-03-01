package com.yobin.stee.tassel.presenter.news;


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

import com.yobin.stee.tassel.beans.News;
import com.yobin.stee.tassel.model.news.INewsModel;
import com.yobin.stee.tassel.model.news.NewsModelImpl;
import com.yobin.stee.tassel.view.interfaces.INewsView;

import java.util.List;

/**
 * Created by Yobin_He on 2017/2/22.
 */

public class NewsPresenterImpl implements INewsPresenter {

    private INewsModel model;
    private INewsView newsView;

    public NewsPresenterImpl(INewsView view){
        this.newsView = view;
        this.model = new NewsModelImpl();
    }

    @Override
    public void loadLatestData() {
        model.loadLatesedData(new NewsModelImpl.OnLoadDataListener() {
            @Override
            public void success(List<News> list) {

            }

            @Override
            public void onFailure(String msg, Exception ex) {

            }
        });

    }


    @Override
    public void loadBeforeData(String date) {
        model.loadBeforeData(date, new NewsModelImpl.OnLoadDataListener() {
            @Override
            public void success(List<News> list) {

            }

            @Override
            public void onFailure(String msg, Exception ex) {

            }
        });
    }
}
