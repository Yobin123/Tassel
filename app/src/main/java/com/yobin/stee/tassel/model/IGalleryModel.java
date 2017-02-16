package com.yobin.stee.tassel.model;

import com.yobin.stee.tassel.presenter.IGalleryPresenter;

/**
 * Created by yobin_he on 2017/2/16.
 */

public interface IGalleryModel {
    /**
     * 通过网络请求加载图片信息
     */
    void loadGalleryImageUrl(int page, IGalleryPresenter iGalleryPresenter);
}
