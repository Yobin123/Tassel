package com.yobin.stee.tassel.base;

/**
 * Created by yobin_he on 2017/2/16.
 */

public interface IBasePresenter {
    /**
     * 开始进行一些初始化操作
     */
    void onResume();

    /**
     * 销毁，用于做一些销毁，回收等类型的操作
     */
    void onDestroy();
}
