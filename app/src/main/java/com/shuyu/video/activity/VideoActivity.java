package com.shuyu.video.activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.ray.core.captain.utils.ToastUtils;
import com.shuyu.core.BaseActivity;
import com.shuyu.video.R;
import com.shuyu.video.model.ChannelContent;

import java.io.IOException;

import butterknife.Bind;
import butterknife.OnClick;


public class VideoActivity extends BaseActivity {

    public static final String VIDEO_CONTENT = "video_content";

    @Bind(R.id.sv_video)
    SurfaceView svVideo;
    @Bind(R.id.iv_control)
    ImageView ivControl;
    @Bind(R.id.seek_bar)
    SeekBar seekBar;
    @Bind(R.id.iv_full)
    ImageView ivFull;

    private ChannelContent.VideoChannelListBean.ChannelContentListBean mContentListBean;
    private String mVideoUrl;
    private MediaPlayer mMediaPlayer;
    private int mCurrentPosition;
    private boolean mIsPlaying;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_video;
    }

    @Override
    protected boolean setOrientationPortrait() {
        return false;
    }

    @Override
    protected void initData() {
        mContentListBean = (ChannelContent.VideoChannelListBean.ChannelContentListBean)
                getIntent().getSerializableExtra(VIDEO_CONTENT);

        if (mContentListBean == null)
            return;

        setTitle(mContentListBean.getTitle());
        mVideoUrl = mContentListBean.getVideoUrl();
        if (!TextUtils.isEmpty(mVideoUrl)) {
            svVideo.getHolder().addCallback(callback);
            seekBar.setOnSeekBarChangeListener(change);
        }
    }

    @OnClick(R.id.iv_control)
    public void onControlVideo(View view) {
        if (mIsPlaying) {
            stopPlay();
        } else {
            onPlay(0);
        }
    }

    private void stopPlay() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            resetPlay();
        }
    }

    private void resetPlay() {
        if (mMediaPlayer != null) {
            mIsPlaying = false;
            mMediaPlayer = null;
            ivControl.setImageResource(R.mipmap.ic_play);
        }
    }

    private void onPlay(final int currentPosition) {
        ivControl.setImageResource(R.mipmap.ic_pause);
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMediaPlayer.start();
                mMediaPlayer.seekTo(currentPosition);
                seekBar.setMax(mMediaPlayer.getDuration());

                new Thread() {

                    @Override
                    public void run() {
                        mIsPlaying = true;
                        while (mIsPlaying) {
                            int current = mMediaPlayer.getCurrentPosition();
                            seekBar.setProgress(current);
                            try {
                                sleep(300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();

            }
        });

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                ivControl.setImageResource(R.mipmap.ic_play);
            }
        });

        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                ToastUtils.getInstance(mContext).showToast("播放发生错误");
                resetPlay();
                return false;
            }
        });
    }

    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDisplay(svVideo.getHolder());
            try {
                mMediaPlayer.setDataSource(mVideoUrl);
                mMediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                mCurrentPosition = mMediaPlayer.getCurrentPosition();
                mMediaPlayer.stop();
            }
        }
    };


    private SeekBar.OnSeekBarChangeListener change = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int progress = seekBar.getProgress();
            if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                mMediaPlayer.seekTo(progress);
            }
        }
    };


}
