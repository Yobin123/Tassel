package com.yobin.stee.tassel.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.yobin.stee.tassel.R;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by yobin_he on 2017/2/16.
 * @描述 : Glide加载图片的封装，圆形、圆角，模糊等处理操作用到了jp.wasabeef:glide-transformations:2.0.1
 *         Glide默认使用httpurlconnection协议，可以配置为OkHttp
 *http://blog.csdn.net/wyb112233/article/details/52337392
 * http://www.cnblogs.com/whoislcj/p/5558168.html
 *
 */

public class GlideUtil {
    private static GlideUtil mInstance;
    private GlideUtil(){};
    public static GlideUtil getInstance(){
        if(mInstance == null){
            synchronized (GlideUtil.class){
                if(mInstance == null){
                    mInstance = new GlideUtil();
                }
            }
        }
        return mInstance;
    }
    /**
     * 常量
     */
    static class Constants{
        public static final int BLUR_VALUE = 20;//模糊
        public static final int CORNER_RADIUS = 20; //圆角
        public static final float THUMB_SIZE = 0.5f; //0-1之间  10%原图的大小
    }

    /**
     * 常规加载图片
     * @param context
     * @param imageView 图片容器
     * @param imageUrl  图片地址
     * @param isFade 是否需要动画
     */
    public void loadImage(Context context , ImageView imageView,String imageUrl,boolean isFade){
        if(isFade){
            Glide.with(context).load(imageUrl).error(R.mipmap.error)
                    .crossFade()
                    .priority(Priority.NORMAL)
                    //all:缓存源资源和转换后的资源 none:不作任何磁盘缓存
                    //source:缓存源资源   result：缓存转换后的资源
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存策略
                    .into(imageView);
        }else {
            Glide.with(context).load(imageUrl).error(R.mipmap.error)
                   .into(imageView);
        }
    }

    /**
     * 加载缩略图
     * @param context
     * @param imageView  图片容器
     * @param  imageUrl  图片地址
     */
    public void loadThumbnailImage(Context context,ImageView imageView,String imageUrl){
        Glide.with(context).load(imageUrl).error(android.R.drawable.stat_notify_error).crossFade().priority(Priority.NORMAL).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(Constants.THUMB_SIZE)
                    .into(imageView);
    }

    /**
     * 加载图片并设置为指定大小
     * @param context
     * @param imageView
     * @param imageUrl
     * @param widthSize
     * @param heightSize
     */
    public void loadOverrideImage(Context context ,ImageView imageView,String imageUrl,int widthSize,int heightSize){
        Glide.with(context).load(imageUrl).error(android.R.drawable.stat_notify_error).crossFade().priority(Priority.NORMAL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(widthSize,heightSize)
                .into(imageView);
    }

    /**
     * 加载图片并对其进行模糊处理
     * @param context
     * @param imageView
     * @param imageUrl
     */
    public void loadBlurImage(Context context,ImageView imageView,String imageUrl){
         Glide.with(context).load(imageUrl).error(android.R.drawable.stat_notify_error).crossFade().priority(Priority.NORMAL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new BlurTransformation(context,Constants.BLUR_VALUE))
                .into(imageView);
    }

    /**
     * 加载圆角
     * @param context
     * @param imageView
     * @param imageUrl
     */
    public void loadCircleImage(Context context,ImageView imageView,String imageUrl){
        Glide.with(context).load(imageUrl).error(android.R.drawable.stat_notify_error).crossFade()
                .priority(Priority.NORMAL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(imageView);
    }

    /**
     * 加载模糊的圆角的图片
     * @param context
     * @param imageView
     * @param imageUrl
     */

    public void loadBlurCirleImage(Context context,ImageView imageView,String imageUrl){
        Glide.with(context).load(imageUrl).error(android.R.drawable.stat_notify_error).crossFade().priority(Priority.NORMAL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new BlurTransformation(context,Constants.BLUR_VALUE),new CropCircleTransformation(context))
                .into(imageView);
    }

    /**
     * 加载圆角图片
     * @param context
     * @param imageView
     * @param imageUrl
     */
    public void loadCornerImage(Context context,ImageView imageView,String imageUrl){
        Glide.with(context).load(imageUrl).error(android.R.drawable.stat_notify_error).crossFade()
                .priority(Priority.NORMAL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new RoundedCornersTransformation(context,Constants.CORNER_RADIUS,Constants.CORNER_RADIUS))
                .into(imageView);
    }

    /**
     * 加载模糊的圆角图片
     * @param context
     * @param imageView
     * @param imageUrl
     */
    public void loadBlurCornerImage(Context context,ImageView imageView,String imageUrl){
        Glide.with(context).load(imageUrl).error(android.R.drawable.stat_notify_error).crossFade().priority(Priority.NORMAL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new BlurTransformation(context,Constants.BLUR_VALUE),
                        new RoundedCornersTransformation(context,Constants.CORNER_RADIUS,Constants.CORNER_RADIUS))
                .into(imageView);
    }

    /**
     * 同步加载图片
     * @param context
     * @param imageUrl
     * @param target
     */
    private void loadBitmapSync(Context context, String imageUrl, SimpleTarget<Bitmap> target){
        Glide.with(context).load(imageUrl)
                .asBitmap()
                .priority(Priority.NORMAL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(target);
    }

    /**
     * 加载gif
     * @param context
     * @param imageView
     * @param imageUrl
     */
    public void loadGifImage(Context context,ImageView imageView,String imageUrl){
        Glide.with(context).load(imageUrl)
                .asGif()
                .crossFade()
                .priority(Priority.NORMAL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(android.R.drawable.stat_notify_error)
                .into(imageView);
    }

    /**
     * 加载gif的缩略图
     * @param context
     * @param imageView
     * @param imageUrl
     */
    public void loadGifThumbnailImage(Context context,ImageView imageView,String imageUrl){
        Glide.with(context).load(imageUrl)
                .asGif()
                .crossFade()
                .priority(Priority.NORMAL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(Constants.THUMB_SIZE)
                .into(imageView);
    }

    /**
     * 恢复请求，一般在停止滚动的时候
     * @param context
     */
    public void resumeRequests(Context context){
        Glide.with(context).resumeRequests();
    }

    /**
     * 暂停请求 正在滚动的时候
     * @param context
     */
    public void pauseRequests(Context context){
        Glide.with(context).pauseRequests();
    }
    public void clearDiskCache(final  Context context){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();//清理磁盘缓存 需要在子线程中执行
            }
        }).start();
    }

    /**
     * 清除内存缓存
     * @param context
     */
    public void clearMemory(Context context){
        Glide.get(context).clearMemory(); //清理内存缓存  可以在UI主线程中进行
    }


//    private  DrawableRequestBuilder<String> initGlide(Context context,String imageUrl){
//       return Glide.with(context).load(imageUrl).error(android.R.drawable.stat_notify_error);
//    }

//     enum Priority {
//        IMMEDIATE, //中等
//        HIGH,//最高
//        NORMAL,//默认
//        LOW, priority, //最低
//    }

}
