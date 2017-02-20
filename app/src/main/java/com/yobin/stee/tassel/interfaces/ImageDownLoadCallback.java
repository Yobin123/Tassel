package com.yobin.stee.tassel.interfaces;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by yobin_he on 2017/2/20.
 */

public interface ImageDownLoadCallback {
    void onDownLoadSuccess(File file);
    void onDownLoadSuccess(Bitmap bitmap);
    void onDownLoadFailed();
}
