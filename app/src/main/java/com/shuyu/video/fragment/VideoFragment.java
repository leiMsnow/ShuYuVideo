package com.shuyu.video.fragment;


import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.shuyu.core.BaseFragment;
import com.shuyu.core.uils.ToastUtils;
import com.shuyu.video.R;
import com.shuyu.video.model.ChannelContent;
import com.shuyu.video.utils.Constants;

import java.io.IOException;

import butterknife.Bind;
import butterknife.OnClick;

public class VideoFragment extends BaseFragment {


    @Bind(R.id.sv_video)
    SurfaceView svVideo;
    @Bind(R.id.iv_control)
    ImageView ivControl;
    @Bind(R.id.seek_bar)
    SeekBar seekBar;
    @Bind(R.id.iv_full)
    ImageView ivFull;

    private  ChannelContent.VideoChannelListBean.ChannelContentListBean data;
    private MediaPlayer mMediaPlayer;
    private int mCurrentPosition;


    public static VideoFragment newInstance(Bundle bundle) {
        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initData() {

        data = (ChannelContent.VideoChannelListBean.ChannelContentListBean)
                getArguments().getSerializable(Constants.VIDEO_DETAILS);

        if (data == null)
            return;
        svVideo.getHolder().addCallback(callback);
        svVideo.getHolder().setKeepScreenOn(true);
        seekBar.setOnSeekBarChangeListener(change);
    }

    private void initMediaPlayer() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setDisplay(svVideo.getHolder());

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                ivControl.setImageResource(R.mipmap.ic_vide_play);
            }
        });

        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                ToastUtils.getInstance(mContext).showToast("播放发生错误");
                return false;
            }
        });
    }

    @OnClick(R.id.iv_control)
    public void onControlVideo(View view) {
        if (mMediaPlayer == null)
            return;

        if (mMediaPlayer.isPlaying()) {
            pausePlay();
        } else {
            startPlay();
        }
    }

    private void stopPlay() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();
        }
    }

    private void pausePlay() {
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
            mCurrentPosition = mMediaPlayer.getCurrentPosition();
            ivControl.setImageResource(R.mipmap.ic_vide_play);
        }
    }

    public void startPlay() {
        if (mMediaPlayer != null) {
            if (mCurrentPosition > 0)
                mMediaPlayer.seekTo(mCurrentPosition);
            mMediaPlayer.start();
            ivControl.setImageResource(R.mipmap.ic_video_pause);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mCurrentPosition = mMediaPlayer.getCurrentPosition();
            mMediaPlayer.stop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopPlay();
    }

    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            if (!TextUtils.isEmpty(data.getVideoUrl())) {
                initMediaPlayer();
                try {
                    mMediaPlayer.setDataSource(data.getVideoUrl());
                    mMediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        }
    };

    private SeekBar.OnSeekBarChangeListener change = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            if (b) {
                int playtime = i * mMediaPlayer.getDuration() / 100;
                mMediaPlayer.seekTo(playtime);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };


}
