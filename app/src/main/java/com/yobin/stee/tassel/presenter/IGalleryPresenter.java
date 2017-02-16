package com.yobin.stee.tassel.presenter;

import com.yobin.stee.tassel.beans.Item;

import java.util.List;

/**
 * Created by yobin_he on 2017/2/16.
 */

public interface IGalleryPresenter {
    /**
     * 加载成功
     * @param list
     */
    void loadSuccess(List<Item> list);

    /**
     * 加载失败
     * @param e 失败的原因
     */
    void loadError(Throwable e);

}
