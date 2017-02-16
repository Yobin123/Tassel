package com.yobin.stee.tassel.base;

/**
 * Created by yobin_he on 2017/2/16.
 */

public interface IBaseView<T> {

    /**
     * 通知toast提示用户
     * @param msg
     * @param requestTag
     */
    void toast(String msg,int requestTag);

    /**
     * 显示进度
     * @param requestTag
     */
    void showProgress(int requestTag);

    /**
     * 隐藏进度
     * @param requestTag
     */
    void hideProgress(int requestTag);

    /**
     * 基础的请求的返回
     * @param data
     * @param RequestTag
     */
    void loadDataSuccess(T data,int RequestTag);

    /**
     * 基础请求的错误
     * @param e
     * @param requestTag
     */
    void loadDataError(Throwable e,int requestTag);

}
