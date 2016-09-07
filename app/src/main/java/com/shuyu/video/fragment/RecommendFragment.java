package com.shuyu.video.fragment;


import android.os.Bundle;

import com.shuyu.core.BaseFragment;
import com.shuyu.video.R;

public class RecommendFragment extends BaseFragment {

    public RecommendFragment() {

    }


    public static RecommendFragment newInstance() {
        RecommendFragment fragment = new RecommendFragment();
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
