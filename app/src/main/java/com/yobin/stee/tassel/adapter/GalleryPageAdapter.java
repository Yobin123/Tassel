package com.yobin.stee.tassel.adapter;


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
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bm.library.PhotoView;
import com.yobin.stee.tassel.R;
import com.yobin.stee.tassel.utils.GlideUtil;

import java.util.List;

/**
 * Created by Yobin_He on 2017/2/19.
 */

public class GalleryPageAdapter extends PagerAdapter {
    private List<String> imgList;
    private List<View> views;
    private Context mContext;

    public GalleryPageAdapter(Context context, List<String> imgList) {
        this.mContext = context;
        this.imgList = imgList;

    }

    @Override
    public int getCount() {
        return imgList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_gallery_img, null);
        PhotoView imageView = (PhotoView) view.findViewById(R.id.iv_vg_gallery);
        imageView.enable();
        GlideUtil.getInstance().loadImage(mContext, imageView, imgList.get(position), true);
        container.addView(view);
        return view;
    }
}
