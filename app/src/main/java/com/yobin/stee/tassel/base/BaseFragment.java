package com.yobin.stee.tassel.base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yobin.stee.tassel.utils.LogUtils;

import rx.Subscription;

/**
 * Created by yobin_he on 2017/2/14.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener{
    private boolean isDebug;
    private String APP_NAME;
    protected final String TAG = this.getClass().getSimpleName();
    private View mContextView = null;


    protected Subscription subscription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isDebug = MyApplication.isDebug;
        APP_NAME = MyApplication.APP_NAME;
        $Log(TAG);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContextView = inflater.inflate(bindLayout(),container,false);
        initView(mContextView);
        doBusiness(getActivity());
        return mContextView;
    }

    /**
     * 绑定布局
     * @return
     */
    protected abstract int bindLayout();

    /**
     * 初始化控件
     * @param mContextView
     */
    protected abstract void initView(View mContextView);

    /**
     * 业务操作
     * @param mContext
     */
    protected abstract void doBusiness(Context mContext);
    /** View点击 **/
    public abstract void widgetClick(View v);

    /**
     * 这是进行findViewById;
     * @param <T>
     * @param view
     * @param resId
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T initResId(View view, int resId){
        return (T) view.findViewById(resId);
    }

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

//    这是不是太过共性的，只有在与rxJava相互结合时才可能产生的

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unSubscribe();

    }
    protected void unSubscribe(){
        if(subscription != null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }
}
