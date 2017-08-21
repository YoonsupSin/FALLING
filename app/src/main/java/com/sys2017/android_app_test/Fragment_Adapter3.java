package com.sys2017.android_app_test;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by N552VW on 2017-06-15.
 */

public class Fragment_Adapter3 extends FragmentPagerAdapter {

    Fragment[] fragments = new Fragment[3];
    String[] title = new String[]{"","",""};

    public Fragment_Adapter3(FragmentManager fm) {
        super(fm);

        fragments[0] = new Fragment_confirm_00();
        fragments[1] = new Fragment_confirm_01();
        fragments[2] = new Fragment_confirm_02();

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
