package com.yobin.stee.tassel.utils.api;

import com.yobin.stee.tassel.beans.GalleryResult;
import com.yobin.stee.tassel.help.RetrofitManager;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by yobin_he on 2017/2/15.
 */

public interface GalleryApi {
    @Headers(RetrofitManager.CACHE_CONTROL_AGE + RetrofitManager.CACHE_STALE_SHORT)
    @GET("data/福利/{number}/{page}")
    Observable<GalleryResult> getBeauties(@Path("number") int number, @Path("page") int page);

}
