package com.yobin.stee.tassel.wideget;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by yobin_he on 2017/2/16.
 */

public class GlideModelConfig implements GlideModule {
    private int memorySize = (int) (Runtime.getRuntime().maxMemory() / 8);// 取1/8最大内存作为最大缓存

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //定义缓存大小和位置
        int diskSize = 1024 * 1024 * 250;
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, diskSize)); //手机磁盘
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, diskSize)); //sd卡磁盘

        // 默认内存和图片池大小
      /*MemorySizeCalculator calculator = new MemorySizeCalculator(context);
      int defaultMemoryCacheSize = calculator.getMemoryCacheSize(); // 默认内存大小
      int defaultBitmapPoolSize = calculator.getBitmapPoolSize(); // 默认图片池大小
      builder.setMemoryCache(new LruResourceCache(defaultMemoryCacheSize)); // 该两句无需设置，是默认的
      builder.setBitmapPool(new LruBitmapPool(defaultBitmapPoolSize));*/

        //自定义内存和图片池大小
        builder.setMemoryCache(new LruResourceCache(memorySize));
        builder.setBitmapPool(new LruBitmapPool(memorySize));

        //定义图片格式
        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
