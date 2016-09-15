package com.shuyu.video.fragment;


import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shuyu.core.BaseFragment;
import com.shuyu.core.uils.LogUtils;
import com.shuyu.core.uils.ToastUtils;
import com.shuyu.video.R;
import com.shuyu.video.model.VideoPicDetails;
import com.shuyu.video.utils.Constants;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

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
    @Bind(R.id.iv_video_url)
    ImageView ivVideoUrl;
    @Bind(R.id.tv_video_time)
    TextView tvVideoTime;

    private VideoPicDetails mPlayDetails;
    private MediaPlayer mMediaPlayer;
    private int mCurrentPosition;
    private Timer mTimer = new Timer();
    private int mPlayProgress;

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

        mPlayDetails = (VideoPicDetails) getArguments().getSerializable(Constants.VIDEO_DETAILS);

        if (mPlayDetails == null) return;

        Glide.with(this).load(mPlayDetails.getImgUrl()).into(ivVideoUrl);

        svVideo.getHolder().addCallback(callback);
        svVideo.getHolder().setKeepScreenOn(true);
        seekBar.setOnSeekBarChangeListener(change);
        mTimer.schedule(mTimerTask, 0, 1000);
    }

    private void initMediaPlayer() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setDisplay(svVideo.getHolder());
        try {
            mMediaPlayer.setDataSource(mPlayDetails.getVideoUrl());
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                startPlay();
            }
        });
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                ivControl.setImageResource(R.drawable.selector_video_play);
            }
        });
        mMediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                seekBar.setSecondaryProgress(percent);
                int currentProgress = seekBar.getMax() * mp.getCurrentPosition() / mp.getDuration();
                LogUtils.i("videoPlayer", currentProgress + "% play" + percent + "% buffer");
            }
        });
        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                ToastUtils.getInstance().showToast("播放发生错误");
                return false;
            }
        });
    }

    @OnClick(R.id.iv_control)
    public void onControlVideo(View view) {
        if (view.getId() == R.id.iv_control) {
            if (mMediaPlayer == null)
                return;

            if (mMediaPlayer.isPlaying()) {
                pausePlay();
            } else {
                startPlay();
            }
        }
    }

    private void stopPlay() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    private void pausePlay() {
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
            mCurrentPosition = mMediaPlayer.getCurrentPosition();
            ivControl.setImageResource(R.drawable.selector_video_play);
        }
    }

    public void startPlay() {
        if (mMediaPlayer != null) {
            ivControl.setImageResource(R.drawable.selector_video_pause);
            ivVideoUrl.setVisibility(View.GONE);
            if (mCurrentPosition > 0)
                mMediaPlayer.seekTo(mCurrentPosition);
            mMediaPlayer.start();
        }
    }

    private void setVideoTime(int time) {
        int musicTime = time / 1000;
        String videoTime = musicTime / 60 + ":" + musicTime % 60;
        tvVideoTime.setText(videoTime);
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
            if (!TextUtils.isEmpty(mPlayDetails.getVideoUrl())) {
//                initMediaPlayer();
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
        public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
            if (fromUser) {
                mPlayProgress = i * mMediaPlayer.getDuration() / seekBar.getMax();
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            mMediaPlayer.seekTo(mPlayProgress);
        }
    };

    TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            if (mMediaPlayer == null)
                return;
            if (mMediaPlayer.isPlaying() && !seekBar.isPressed()) {
                handleProgress.sendEmptyMessage(0);
            }
        }
    };

    Handler handleProgress = new Handler() {
        public void handleMessage(Message msg) {

            int position = mMediaPlayer.getCurrentPosition();
            int duration = mMediaPlayer.getDuration();

            if (duration > 0) {
                long pos = seekBar.getMax() * position / duration;
                seekBar.setProgress((int) pos);
                setVideoTime(duration);
            }
        }
    };

}
