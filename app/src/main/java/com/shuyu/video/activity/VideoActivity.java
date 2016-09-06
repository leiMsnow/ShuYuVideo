package com.shuyu.video.activity;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.text.TextUtils;

import com.ray.core.captain.utils.ToastUtils;
import com.shuyu.core.BaseActivity;
import com.shuyu.video.R;
import com.shuyu.video.model.ChannelContent;
import com.shuyu.video.widget.CustomVideoView;

import butterknife.Bind;

public class VideoActivity extends BaseActivity {

    public static final String VIDEO_URL = "video_url";

    @Bind(R.id.cvv_video)
    CustomVideoView mCvvVideo;

    private ChannelContent.VideoChannelListBean.ChannelContentListBean url;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_video;
    }

    @Override
    protected void initData() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        url = (ChannelContent.VideoChannelListBean.ChannelContentListBean)
                getIntent().getSerializableExtra(VIDEO_URL);

        if (url == null)
            return;

        setTitle(url.getTitle());
        if (url.getIsPage().equals("1")) {

        } else {
            if (!TextUtils.isEmpty(url.getVideoUrl())) {
                mCvvVideo.setVideoPath(url.getVideoUrl());
                mCvvVideo.start();
                mCvvVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        ToastUtils.getInstance(mContext).showToast("播放完成");
                    }
                });
            }
        }
    }
}
