package com.shuyu.video.activity;

import com.shuyu.core.BaseActivity;
import com.shuyu.video.R;
import com.shuyu.video.fragment.VideoFragment;


public class VideoActivity extends BaseActivity {


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_video;
    }

    @Override
    protected void initData() {

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, VideoFragment.newInstance(getIntent().getExtras()))
                .commit();

    }


}
