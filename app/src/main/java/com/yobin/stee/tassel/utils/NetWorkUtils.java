package com.yobin.stee.tassel.utils;

import com.yobin.stee.tassel.utils.api.GalleryApi;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yobin_he on 2017/2/15.
 */

public class NetWorkUtils {
    private static GalleryApi galleryApi;
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    public static GalleryApi getGalleryApi(){
        if(galleryApi == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(Constants.BASE_URL_GALLERY)
                    .addConverterFactory(gsonConverFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            galleryApi = retrofit.create(GalleryApi.class);
        }
        return galleryApi;
    }
}
