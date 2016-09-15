package com.shuyu.video.fragment;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.shuyu.core.BaseFragment;
import com.shuyu.video.R;
import com.shuyu.video.model.VideoPicDetails;
import com.shuyu.video.utils.Constants;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import butterknife.Bind;

public class VideoPlayerFragment extends BaseFragment {

    @Bind(R.id.video_view)
    UniversalVideoView mVideoView;
    @Bind(R.id.media_controller)
    UniversalMediaController mMediaController;
    @Bind(R.id.video_layout)
    FrameLayout mVideoLayout;

    private VideoPicDetails mPlayDetails;
    private int mSeekPosition;
    private int mCachedHeight;

    public static VideoPlayerFragment newInstance(Bundle bundle) {
        VideoPlayerFragment fragment = new VideoPlayerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_video_player;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mVideoView != null && mVideoView.isPlaying()) {
            mSeekPosition = mVideoView.getCurrentPosition();
            mVideoView.pause();
        }
    }

    /**
     * 置视频区域大小
     */
    private void setVideoAreaSize() {
        mVideoView.setFullscreen(true);
        mVideoLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = mVideoLayout.getWidth();
                mCachedHeight = (int) (width * 405f / 720f);
                ViewGroup.LayoutParams videoLayoutParams = mVideoLayout.getLayoutParams();
                videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                videoLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                mVideoLayout.setLayoutParams(videoLayoutParams);
                mVideoView.setVideoPath(mPlayDetails.getVideoUrl());
                mVideoView.requestFocus();
                mVideoView.start();
                mMediaController.setTitle(mPlayDetails.getTitle());
            }
        });
    }

    @Override
    protected void initData() {
        mPlayDetails = (VideoPicDetails) getArguments().getSerializable(Constants.VIDEO_DETAILS);
        if (mPlayDetails == null) return;

        mVideoView.setMediaController(mMediaController);
        setVideoAreaSize();
        mVideoView.setVideoViewCallback(new UniversalVideoView.VideoViewCallback() {
            @Override
            public void onScaleChange(boolean isFullscreen) {
                if (isFullscreen) {
                    ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    mVideoLayout.setLayoutParams(layoutParams);
                } else {
                    ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    layoutParams.height = mCachedHeight;
                    mVideoLayout.setLayoutParams(layoutParams);
                }
            }

            // 视频暂停
            @Override
            public void onPause(MediaPlayer mediaPlayer) {
            }

            // 视频开始播放或恢复播放
            @Override
            public void onStart(MediaPlayer mediaPlayer) {
            }

            // 视频开始缓冲
            @Override
            public void onBufferingStart(MediaPlayer mediaPlayer) {
            }

            // 视频结束缓冲
            @Override
            public void onBufferingEnd(MediaPlayer mediaPlayer) {
            }
        });
    }
}
