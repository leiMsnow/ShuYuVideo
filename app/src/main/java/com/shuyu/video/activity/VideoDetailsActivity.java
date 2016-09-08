package com.shuyu.video.activity;

import com.shuyu.core.BaseActivity;
import com.shuyu.core.widget.LoadMoreRecyclerView;
import com.shuyu.video.R;

import butterknife.Bind;

public class VideoDetailsActivity extends BaseActivity {

    @Bind(R.id.lrv_view)
    LoadMoreRecyclerView lrvView;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_video_details;
    }

    @Override
    protected void initData() {
//        if ()
    }





}
