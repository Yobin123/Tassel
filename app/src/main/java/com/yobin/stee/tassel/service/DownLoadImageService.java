package com.yobin.stee.tassel.service;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.yobin.stee.tassel.interfaces.ImageDownLoadCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by yobin_he on 2017/2/20.
 */

public class DownLoadImageService implements Runnable {
    private String url;
    private Context context;
    private ImageDownLoadCallback callback;
    private File currentFile;
    public DownLoadImageService(Context context,String url ,ImageDownLoadCallback callback){
        this.url = url;
        this.callback = callback;
        this.context = context;
    }

    @Override
    public void run() {
        File file = null;
        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(context)
                    .load(url)
                    .asBitmap().into(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
                    .get();

            if(bitmap!=null){
                //在这里保存图片的方法
                saveImageToGallery(context,bitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(bitmap != null && currentFile.exists()){
                callback.onDownLoadSuccess(bitmap);
            }else {
                callback.onDownLoadFailed();
            }
        }

    }

    private void saveImageToGallery(Context context, Bitmap bitmap) {
        //首先保存图片
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();
        //注意小米手机必须这样获得绝对路径
        String pathName = "新建文件夹";
        File appDir = new File(file,pathName);
        if(!appDir.exists()){
            appDir.mkdirs();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        currentFile = new File(appDir,fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(currentFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(fos!=null){
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        //其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),currentFile.getAbsolutePath(),fileName,null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        //最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(currentFile.getPath()))));

    }
}
