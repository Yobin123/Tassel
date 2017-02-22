package com.yobin.stee.tassel.wideget;


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

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.system.ErrnoException;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Yobin_He on 2017/2/21.
 */

public class CustomViewPager extends ViewPager{
    private boolean isLocked;
    public CustomViewPager(Context context) {
        this(context,null);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        isLocked = false;
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        if(!isLocked){
            try {
                return super.onInterceptHoverEvent(event);
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }

        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return !isLocked&&super.onTouchEvent(ev);
    }
    public void toggleLock(){
        isLocked = !isLocked;
    }
    public void setLocked(boolean isLocked){
        this.isLocked = isLocked;
    }
    public boolean isLocked(){
        return isLocked;
    }
}
