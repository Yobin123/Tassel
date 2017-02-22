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
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.yobin.stee.tassel.R;
import com.yobin.stee.tassel.adapter.GalleryPageAdapter;
import com.yobin.stee.tassel.base.BaseActivity;
import com.yobin.stee.tassel.interfaces.ImageDownLoadCallback;
import com.yobin.stee.tassel.service.DownLoadImageService;
import com.yobin.stee.tassel.utils.LogUtils;

import java.io.File;
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

    private boolean mIsHidden = false;
    private Context mContext = this;
    public static final int MSG_SAVE = 0;
    public static final int MSG_ERROR = 1;
    public static final int DELAY_TIME = 2000;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
          switch (msg.what){
              case MSG_SAVE:
                  Toast.makeText(mContext, getResources().getString(R.string.save_success), Toast.LENGTH_SHORT).show();
                  break;
              case MSG_ERROR:
                  Toast.makeText(mContext, getResources().getString(R.string.save_failure), Toast.LENGTH_SHORT).show();
                  break;
          }
        }
    };

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

    /**
     * 进行业务的处理
     */
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
//        vgDetailGallery.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()){
//                    case MotionEvent.ACTION_DOWN:
//                        hideOrShowToolbar();
//                        break;
//                }
//                return true;
//            }
//        });



    }

    private void hideOrShowToolbar() {
        appBarLayout.animate()
                .translationY(mIsHidden ? 0 : -appBarLayout.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        mIsHidden = !mIsHidden;
    }

    @Override
    public void initParams(Bundle bundle) {
        //这个位置是onResume的时候进行的相应的处理

    }

    @Override
    public void widgetClick(View v) {
        int viewId = v.getId();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gallery,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_share:
                Toast.makeText(this, "你点了分享的按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_save:
//                if(mPosition == imgList.get())
                if(imgList.get(mPosition) != null){
                    saveImageToGallery(imgList.get(mPosition));
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 将相应的图片保存到相应的图片集中
     */
    private void saveImageToGallery(String url) {
        LogUtils.i("-----url::",url);
        DownLoadImageService service = new DownLoadImageService(getApplicationContext(), url, new ImageDownLoadCallback() {
            @Override
            public void onDownLoadSuccess(File file) {

            }

            @Override
            public void onDownLoadSuccess(Bitmap bitmap) {
                //在这里执行图片保存的方法
                Message message = new Message();
                message.what = MSG_SAVE;
                handler.sendMessageDelayed(message,DELAY_TIME);
            }

            @Override
            public void onDownLoadFailed() {
                //图片保存失败
                Message message = new Message();
                message.what = MSG_ERROR;
                handler.sendMessageDelayed(message,DELAY_TIME);
            }
        });
        new Thread(service).start();
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
