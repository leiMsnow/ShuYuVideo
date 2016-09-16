package com.shuyu.video.activity;

import com.shuyu.core.BaseActivity;
import com.shuyu.video.R;
import com.shuyu.video.fragment.VideoPlayerFragment;

@Deprecated
public class VideoPlayerActivity extends BaseActivity {

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_video;
    }

    protected void initData() {
        if (getIntent() == null) return;
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, VideoPlayerFragment.newInstance(getIntent().getExtras()))
                .commit();
    }
}
