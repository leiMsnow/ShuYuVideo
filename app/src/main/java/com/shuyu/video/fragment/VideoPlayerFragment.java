package com.shuyu.video.fragment;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.shuyu.core.BaseFragment;
import com.shuyu.video.R;
import com.shuyu.video.model.VideoPicDetails;
import com.shuyu.video.utils.Constants;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import butterknife.Bind;

import static android.content.ContentValues.TAG;

public class VideoPlayerFragment extends BaseFragment {

    private static final String SEEK_POSITION_KEY = "SEEK_POSITION_KEY";

    @Bind(R.id.video_view)
    UniversalVideoView mVideoView;
    @Bind(R.id.media_controller)
    UniversalMediaController mMediaController;
    @Bind(R.id.video_layout)
    FrameLayout mVideoLayout;

    private VideoPicDetails mPlayDetails;
    private int mSeekPosition;
    private int mCachedHeight;
    private boolean mIsFullscreen;

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
        mVideoLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = mVideoLayout.getWidth();
                mCachedHeight = mVideoLayout.getHeight();//int) (width * 405f / 720f);
                ViewGroup.LayoutParams videoLayoutParams = mVideoLayout.getLayoutParams();
                videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                videoLayoutParams.height = mCachedHeight;
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
                mIsFullscreen = isFullscreen;
                if (isFullscreen) {
                    ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    mVideoLayout.setLayoutParams(layoutParams);
                    //设置全屏时,无关的View消失,以便为视频控件和控制器控件留出最大化的位置
//                    mBottomLayout.setVisibility(View.GONE);
                } else {
                    ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    layoutParams.height = mCachedHeight;
                    mVideoLayout.setLayoutParams(layoutParams);
//                    mBottomLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPause(MediaPlayer mediaPlayer) { // 视频暂停
                Log.d(TAG, "onPause UniversalVideoView callback");
            }

            @Override
            public void onStart(MediaPlayer mediaPlayer) { // 视频开始播放或恢复播放
                Log.d(TAG, "onStart UniversalVideoView callback");
            }

            @Override
            public void onBufferingStart(MediaPlayer mediaPlayer) {// 视频开始缓冲
                Log.d(TAG, "onBufferingStart UniversalVideoView callback");
            }

            @Override
            public void onBufferingEnd(MediaPlayer mediaPlayer) {// 视频结束缓冲
                Log.d(TAG, "onBufferingEnd UniversalVideoView callback");
            }
        });
    }
}
