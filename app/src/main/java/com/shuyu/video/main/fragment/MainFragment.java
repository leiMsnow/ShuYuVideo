package com.shuyu.video.main.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.shuyu.core.BaseFragment;
import com.shuyu.core.widget.HorizontalIndicatorView;
import com.shuyu.video.R;
import com.shuyu.video.main.adapter.ViewPagerAdapter;

import java.util.ArrayList;

import butterknife.Bind;

public class MainFragment extends BaseFragment {

    @Bind(R.id.vp_indicator)
    HorizontalIndicatorView mVpIndicator;
    @Bind(R.id.vp_container)
    ViewPager mVpContainer;

    private ViewPagerAdapter mPagerAdapter;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initData() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        Fragment fragment = ItemFragment.newInstance();
        fragments.add(fragment);

        mPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), fragments);
        mVpContainer.setAdapter(mPagerAdapter);
        mVpIndicator.setViewPager(mVpContainer);
    }
}
