package com.shuyu.video.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 *
 * Created by zhangleilei on 7/19/16.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    public static final String ARG_TITLE = "title";

    private List<Fragment> mFragments;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(List<Fragment> fragments) {
        mFragments = fragments;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getArguments().getString(ARG_TITLE);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

}
