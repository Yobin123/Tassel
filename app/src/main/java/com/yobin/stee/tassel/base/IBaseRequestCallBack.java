package com.yobin.stee.tassel.base;

/**
 * Created by yobin_he on 2017/2/16.
 */

public interface IBaseRequestCallBack<T> {

    /**
     * 开始请求之前的处理
     * @param requestTag
     */
    void beforeRequest(int requestTag);

    /**
     * 请求失败
     * @param e
     * @param requestTag
     */
    void requestError(Throwable e,int requestTag);

    /**
     * 请求结束
     * @param requestTag
     */
    void requestComplete(int requestTag);

    /**
     * 请求成功
     * @param callBack
     * @param requestTag
     */
    void requestSuccess(T callBack,int requestTag);
}
