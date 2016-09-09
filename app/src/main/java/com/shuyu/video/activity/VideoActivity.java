package com.shuyu.video.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.shuyu.video.R;
import com.shuyu.video.fragment.VideoFragment;


public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        initData();
    }


    protected void initData() {

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, VideoFragment.newInstance(getIntent().getExtras()))
                .commit();

    }

}
