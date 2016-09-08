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

import com.ray.core.captain.utils.ToastUtils;
import com.shuyu.core.BaseFragment;
import com.shuyu.core.api.BaseApi;
import com.shuyu.video.R;
import com.shuyu.video.api.IMainApi;
import com.shuyu.video.model.ChannelBanner;
import com.shuyu.video.model.ChannelContent;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class VideoFragment extends BaseFragment {

    public static final String VIDEO_DETAIL_ID = "VIDEO_DETAIL_ID";

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

        int detailId = getArguments().getInt(VIDEO_DETAIL_ID, 0);

        if (detailId == 0)
            return;

        getVideoDetails(detailId);

    }

    private void initMediaPlayer() {
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
            ivControl.setImageResource(R.mipmap.ic_play);
        }
    }

    private void pausePlay() {
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
            mCurrentPosition = mMediaPlayer.getCurrentPosition();
            ivControl.setImageResource(R.mipmap.ic_play);
        }
    }

    public void startPlay() {
        if (mMediaPlayer != null) {

            if (mCurrentPosition > 0) {
                mMediaPlayer.seekTo(mCurrentPosition);
            }
            mMediaPlayer.start();
            ivControl.setImageResource(R.mipmap.ic_pause);
        }
    }

    private void getVideoDetails(int id) {
        BaseApi.createApi(IMainApi.class);
        BaseApi.request(BaseApi.createApi(IMainApi.class).getVideoDetails(id),
                new BaseApi.IResponseListener<List<ChannelBanner>>() {
                    @Override
                    public void onSuccess(List<ChannelBanner> data) {
                        mVideoUrl = mContentListBean.getVideoUrl();
                        if (!TextUtils.isEmpty(mVideoUrl)) {
                            svVideo.getHolder().addCallback(callback);
                            svVideo.getHolder().setKeepScreenOn(true);
                            seekBar.setOnSeekBarChangeListener(change);
                            mMediaPlayer = new MediaPlayer();
                            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            initMediaPlayer();
                        }
                    }

                    @Override
                    public void onFail() {

                    }
                });
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
        mMediaPlayer.release();
    }

    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
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
