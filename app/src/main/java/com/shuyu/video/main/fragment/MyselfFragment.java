package com.shuyu.video.main.fragment;


import android.os.Bundle;

import com.shuyu.core.BaseFragment;
import com.shuyu.video.R;

public class MyselfFragment extends BaseFragment {

    public MyselfFragment() {

    }


    public static MyselfFragment newInstance() {
        MyselfFragment fragment = new MyselfFragment();
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
