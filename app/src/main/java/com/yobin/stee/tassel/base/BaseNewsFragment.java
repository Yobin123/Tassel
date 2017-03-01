package com.yobin.stee.tassel.base;


////////////////////////////////////////////////////////////////////
//                          _ooOoo_                               //
//                         o8888888o                              //
//                         88" . "88                              //
//                         (| ^_^ |)                              //
//                         O\  =  /O                              //
//                      ____/`---'\____                           //
//                    .'  \\|     |//  `.                         //
//                   /  \\|||  :  |||//  \                        //
//                  /  _||||| -:- |||||-  \                       //
//                  |   | \\\  -  /// |   |                       //
//                  | \_|  ''\---/''  |   |                       //
//                  \  .-\__  `-`  ___/-. /                       //
//                ___`. .'  /--.--\  `. . ___                     //
//              ."" '<  `.___\_<|>_/___.'  >'"".                  //
//            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
//            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
//      ========`-.____`-.___\_____/___.-`____.-'========         //
//                           `=---='                              //
//      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
//         佛祖保佑       永无BUG     永不修改                  //
////////////////////////////////////////////////////////////////////


import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yobin.stee.tassel.utils.LogUtils;

import butterknife.ButterKnife;

/**
 * Created by Yobin_He on 2017/2/22.
 */

public  abstract class BaseNewsFragment extends Fragment implements View.OnClickListener{

    public static final String TAG = BaseNewsFragment.class.getSimpleName();
    protected View mRootView;
    private boolean isDebug;
    private String APP_NAME;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isDebug = MyApplication.isDebug;
        APP_NAME = TAG;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(),container,false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);  //运用黄油刀进行相应的绑定布局
        afterCreated(savedInstanceState);
    }



    //用于初始化布局
    protected abstract int getLayoutId();

    //用于保存原始的数据
    protected abstract void afterCreated(Bundle savedInstanceState);

    /** View点击 **/
    public abstract void widgetClick(View v);

    /**
     * 日志输出
     * @param msg
     */
    protected void $Log(String msg){
        if (isDebug){
            LogUtils.i("---->>>"+APP_NAME+"::", msg);
        }
    }


    @Override
    public void onClick(View view) {
        if(fastClick()){
            widgetClick(view);
        }
    }


    /** * [防止快速点击] * * @return */
    private boolean fastClick() {
        long lastClick = 0;
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

}
