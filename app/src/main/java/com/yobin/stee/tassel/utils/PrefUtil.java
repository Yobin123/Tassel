package com.yobin.stee.tassel.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.yobin.stee.tassel.base.MyApplication;


/**
 * Created by laucherish on 16/3/30.
 */
public class PrefUtil {

    private static final String PRE_NAME = "com.yobin.stee.tassel_preferences";
    private static final String PRE_NIGHT = "night";

    private static SharedPreferences getSharedPreferences() {
        return MyApplication.getContext()
                .getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);
    }

    public static void setNight(){
        getSharedPreferences().edit().putBoolean(PRE_NIGHT, true).commit();
    }

    public static void setDay(){
        getSharedPreferences().edit().putBoolean(PRE_NIGHT, false).commit();
    }

    public static void changeDayNight(){
        boolean change = !getSharedPreferences().getBoolean(PRE_NIGHT, false);
        getSharedPreferences().edit().putBoolean(PRE_NIGHT, change).commit();
    }

    public static boolean isNight(){
        return getSharedPreferences().getBoolean(PRE_NIGHT, false);
    }

//    public static int getThemeRes(){
//        if (!isNight()) {
//            return Constant.RESOURCES_DAYTHEME;
//        } else {
//            return Constant.RESOURCES_NIGHTTHEME;
//        }
//    }
}
