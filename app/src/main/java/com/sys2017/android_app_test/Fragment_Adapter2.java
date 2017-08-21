package com.sys2017.android_app_test;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by N552VW on 2017-06-15.
 */

public class Fragment_Adapter2 extends FragmentPagerAdapter {

    Fragment[] fragments = new Fragment[3];
    String[] title = new String[]{"보낸편지함","받은편지함","편지보내기"};

    public Fragment_Adapter2(FragmentManager fm) {
        super(fm);

        fragments[0] = new Page02Fragment_Fragment01();
        fragments[1] = new Page02Fragment_Fragment02();
        fragments[2] = new Page02Fragment_Fragment03();

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
