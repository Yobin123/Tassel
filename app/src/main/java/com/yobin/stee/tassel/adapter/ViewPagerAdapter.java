package com.yobin.stee.tassel.adapter;




import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yobin_he on 2017/2/14.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

//    @Override
//    public Fragment getItem(int position) {
//        return fragmentList.get(position);
//    }
//


    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
    //将相应的Fragment添加到相应的ViewPager;
    public void addFragment(Fragment fragment,String title){
        fragmentList.add(fragment);
        titleList.add(title);
    }
}
