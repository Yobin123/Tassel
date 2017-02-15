package com.yobin.stee.tassel.base;

import android.app.Application;
import android.content.Context;

import com.yobin.stee.tassel.utils.AppContextUtil;

/**
 * Created by yobin_he on 2017/2/14.
 * 用于初始化一些数据
 */

public class MyApplication extends Application {
    private static Context mApplicationContext;
    public static boolean isDebug = true;
    public static String APP_NAME;
    @Override
    public void onCreate() {
        super.onCreate();
        AppContextUtil.init(this);
        mApplicationContext = this;
        APP_NAME = this.getClass().getSimpleName();
    }
    //获取ApplicationContext;
    public static Context getContext(){
        return mApplicationContext;
    }

}
