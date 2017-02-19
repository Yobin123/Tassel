package com.yobin.stee.tassel.view;


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
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.yobin.stee.tassel.R;
import com.yobin.stee.tassel.adapter.GalleryPageAdapter;
import com.yobin.stee.tassel.base.BaseActivity;
import com.yobin.stee.tassel.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Yobin_He on 2017/2/19.
 */

public class GalleryDetailActivity extends BaseActivity {

    @BindView(R.id.vg_detail_gallery)
    ViewPager vgDetailGallery;
    @BindView(R.id.toolbar_detail_gallery)
    Toolbar toolbarDetailGallery;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    private List<String> imgList;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_gallery);
        ButterKnife.bind(this);
        initExtra();
        initToolbar();
        doBusiness();

    }



    /**
     * 初始化相应的toolbar
     */
    private void initToolbar() {
        LogUtils.i("--->>" + mPosition);
        toolbarDetailGallery.setTitle( mPosition+ "/10");
        setSupportActionBar(toolbarDetailGallery);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbarDetailGallery.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 初始化获取的数据
     */
    private void initExtra() {
        mPosition = getIntent().getExtras().getInt("position");
        imgList = getIntent().getExtras().getStringArrayList("imgList");
    }

    private void doBusiness() {
        LogUtils.i("--->>imgList" + imgList.toString() );
        GalleryPageAdapter adapter = new GalleryPageAdapter(this,imgList);
        vgDetailGallery.setAdapter(adapter);
        vgDetailGallery.setCurrentItem(mPosition);
        vgDetailGallery.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mPosition = position;
                toolbarDetailGallery.setTitle( mPosition+1+ "/10");
            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
                toolbarDetailGallery.setTitle((mPosition+1)+ "/10");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public void initParams(Bundle bundle) {
        //这个位置是onResume的时候进行的相应的处理

    }

    @Override
    public void widgetClick(View v) {
        int viewId = v.getId();

    }

    /**
     * 启动本activity所要处理的调用的方法
     * @param context
     * @param imgList
     * @param  position
     *
     */
    public static void actionStart(Context context, List<String> imgList , int position) {
        Intent intent = new Intent(context, GalleryDetailActivity.class);
        intent.putStringArrayListExtra("imgList", (ArrayList<String>) imgList);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }
}
