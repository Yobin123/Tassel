package com.yobin.stee.tassel.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yobin_he on 2017/1/16.
 * 对所有活动进行相应的管理，从而可以实现随时随地退出程序
 * 用法：在基的activity中的onCreate中添加相应的activity;
 * 在onDestroy中销毁相应的activity;
 * 也可以在需要退出程序的界面使用SingleTask来退出程序；
 * 参考资料：第一行代码
 *
 */

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();
    public static void  addActivity(Activity activity){
        activities.add(activity);
    }
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }
    public static void finishAll(){
        for (Activity activity : activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
