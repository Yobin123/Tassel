package com.yobin.stee.tassel.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.yobin.stee.tassel.R;
import com.yobin.stee.tassel.adapter.ViewPagerAdapter;
import com.yobin.stee.tassel.base.BaseActivity;
import com.yobin.stee.tassel.view.fragment.GalleryFragment;
import com.yobin.stee.tassel.view.fragment.VideoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar_home)
    Toolbar toolbarHome;
    @BindView(R.id.tab_layout_home)
    TabLayout tabLayoutHome;
    @BindView(R.id.viewpager_content_home)
    ViewPager viewpagerContentHome;
    @BindView(R.id.nav_view_home)
    NavigationView navViewHome;
    @BindView(R.id.drawer_layout_home)
    DrawerLayout drawerLayoutHome;
    private Context mContext = this;


    //    private String[] titles = new String[]{getResources().getString(R.string.tab_gallery),"视频"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        initToolbar();
        initView();
    }

    private void initToolbar() {
        //初始化toolbar
        toolbarHome.setTitle("Home");
        toolbarHome.setNavigationIcon(R.mipmap.ic_menu_black_24dp);
        setSupportActionBar(toolbarHome);

    }

    private void initView() {
        //设置抽屉监听
        navViewHome.setNavigationItemSelectedListener(this);

        //实例化相应的Fragment对象
        GalleryFragment galleryFragment = new GalleryFragment().newInstance();
        VideoFragment videoFragment = new VideoFragment().newInstance();

        //进行相应的添加Fragment
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(galleryFragment, getResources().getString(R.string.tab_gallery));
        adapter.addFragment(videoFragment, getResources().getString(R.string.tab_video));
        viewpagerContentHome.setAdapter(adapter);
        tabLayoutHome.setupWithViewPager(viewpagerContentHome);

    }

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayoutHome.isDrawerOpen(GravityCompat.START)) {
            drawerLayoutHome.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        switch (id) {
            case android.R.id.home:
                drawerLayoutHome.openDrawer(GravityCompat.START);
                break;
            case R.id.action_settings:
                Toast.makeText(mContext, "您按了设置按钮", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 重写，监听相应的drawer中的item
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        drawerLayoutHome.closeDrawers();
        switch (item.getItemId()) {
            case R.id.nav_gallery:
                Toast.makeText(this, "跳转到图片的tab", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_slideshow:
                Toast.makeText(this, "跳转到视频的tab", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "跳转到图片的tab", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
