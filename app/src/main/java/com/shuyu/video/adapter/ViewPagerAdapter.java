package com.shuyu.video.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shuyu.video.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by zhangleilei on 7/19/16.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments = new ArrayList<>();

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
        return mFragments.get(position).getArguments().getString(Constants.BANNEL_TITLE);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

}
