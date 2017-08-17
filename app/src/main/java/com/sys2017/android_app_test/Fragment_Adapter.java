package com.sys2017.android_app_test;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by N552VW on 2017-06-15.
 */

public class Fragment_Adapter extends FragmentPagerAdapter {

    Fragment[] fragments = new Fragment[3];
    String[] title = new String[]{"앨범","뉴스","업데이트중"};

    public Fragment_Adapter(FragmentManager fm) {
        super(fm);

        fragments[0] = new Page01Fragment();
        fragments[1] = new Page02Fragment();
        fragments[2] = new Page03Fragment();

    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
