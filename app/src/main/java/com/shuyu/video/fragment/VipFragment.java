package com.shuyu.video.fragment;


import android.os.Bundle;

import com.shuyu.core.BaseFragment;
import com.shuyu.video.R;

public class VipFragment extends BaseFragment {

    public VipFragment() {

    }


    public static VipFragment newInstance() {
        VipFragment fragment = new VipFragment();
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

    }
}
