package com.yobin.stee.tassel.base;

/**
 * Created by yobin_he on 2017/2/16.
 */

public class BasePresenterImpl<T extends IBaseView,V> implements IBasePresenter,IBaseRequestCallBack<V> {

    private IBaseView iView;

   public BasePresenterImpl(T view){
       this.iView = view;
   }
    @Override
    public void onResume() {


    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void beforeRequest(int requestTag) {
        //显示Loading
        iView.showProgress(requestTag);
    }

    @Override
    public void requestError(Throwable e, int requestTag) {
        //通知UI具体的错误信息
        iView.loadDataError(e,requestTag);
    }

    @Override
    public void requestComplete(int requestTag) {
        //隐藏loading
        iView.hideProgress(requestTag);
    }

    @Override
    public void requestSuccess(V callBack, int requestTag) {
        //将获取的数据调给UI(activity或者fragment)
        //通过callBack对象进行传出
        iView.loadDataSuccess(callBack,requestTag);
    }
}
