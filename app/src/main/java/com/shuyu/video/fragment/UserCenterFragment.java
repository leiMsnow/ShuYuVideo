package com.shuyu.video.fragment;


import android.os.Bundle;

import com.shuyu.core.BaseFragment;
import com.shuyu.video.R;

public class UserCenterFragment extends BaseFragment {

    public UserCenterFragment() {

    }


    public static UserCenterFragment newInstance() {
        UserCenterFragment fragment = new UserCenterFragment();
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
