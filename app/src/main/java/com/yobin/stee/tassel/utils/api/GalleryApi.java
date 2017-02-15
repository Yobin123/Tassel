package com.yobin.stee.tassel.utils.api;

import com.yobin.stee.tassel.beans.GalleryResult;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by yobin_he on 2017/2/15.
 */

public interface GalleryApi {
    @GET("data/福利/{number}/{page}")
    Observable<GalleryResult> getBeauties(@Path("number") int number, @Path("page") int page);

}
