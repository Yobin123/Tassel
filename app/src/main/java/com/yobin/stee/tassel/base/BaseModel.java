package com.yobin.stee.tassel.base;

import com.yobin.stee.tassel.helper.RetrofitManager;

/**
 * Created by yobin_he on 2017/2/16.
 * 业务对象的基类
 * 由于我的Api比较杂糅所以这就不好使用BaseModel
 */

public class BaseModel {
    //retrofit请求数据的管理类
    public RetrofitManager retrofitManager;
    public BaseModel(){
        retrofitManager = RetrofitManager.builder();
    }
}
