package com.shuyu.video.activity;

import android.media.MediaPlayer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shuyu.core.api.BaseApi;
import com.shuyu.core.uils.AppUtils;
import com.shuyu.video.R;
import com.shuyu.video.adapter.VideoCommentAdapter;
import com.shuyu.video.api.IMainApi;
import com.shuyu.video.model.VideoComment;
import com.shuyu.video.model.VideoPicDetails;
import com.shuyu.video.utils.Constants;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class VideoDetailsActivity extends AppBaseActivity {

    @Bind(R.id.lrv_view)
    RecyclerView lrvView;
    @Bind(R.id.iv_url)
    ImageView mIvUrl;
    @Bind(R.id.iv_video_player)
    ImageView ivVideoPlayer;
    @Bind(R.id.video_view)
    UniversalVideoView mVideoView;
    @Bind(R.id.media_controller)
    UniversalMediaController mMediaController;
    @Bind(R.id.video_layout)
    FrameLayout mVideoLayout;

    private VideoPicDetails mVideoDetails;
    private VideoCommentAdapter mCommentAdapter;
    private boolean mIsVIP;
    private int mCachedHeight;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_video_details;
    }

    @Override
    protected void initData() {
        if (getIntent() == null) return;
        int videoId = getIntent().getIntExtra(Constants.VIDEO_DETAIL_ID, 0);
        if (videoId == 0) return;
        mIsVIP = getIntent().getBooleanExtra(Constants.IS_VIP_VIDEO, false);
        getVideoDetails(videoId);
        if (!mIsVIP) {
            mCommentAdapter = new VideoCommentAdapter(mContext, null, R.layout.item_video_comment);
            lrvView.setLayoutManager(new LinearLayoutManager(mContext));
            lrvView.setAdapter(mCommentAdapter);
            getVideoComment();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mVideoView != null && mVideoView.isPlaying()) {
            mVideoView.pause();
        }
    }

    private void getVideoDetails(int id) {
        BaseApi.request(BaseApi.createApi(IMainApi.class).getVideoDetails(id),
                new BaseApi.IResponseListener<VideoPicDetails>() {
                    @Override
                    public void onSuccess(VideoPicDetails data) {
                        Glide.with(mContext).load(data.getImgUrl()).into(mIvUrl);
                        setTitle(data.getTitle());
                        mVideoDetails = data;
                        initVideoView();
                    }

                    @Override
                    public void onFail() {
                        mIvUrl.setImageResource(R.mipmap.ic_default_image);
                    }
                });
    }

    private void setVideoAreaSize() {
        mVideoLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = mVideoLayout.getWidth();
                mCachedHeight = mVideoLayout.getHeight();
                ViewGroup.LayoutParams videoLayoutParams = mVideoLayout.getLayoutParams();
                videoLayoutParams.width = width;
                videoLayoutParams.height = mCachedHeight;
                mVideoLayout.setLayoutParams(videoLayoutParams);
                mVideoView.setMediaController(mMediaController);
                mVideoView.setVideoPath(mVideoDetails.getVideoUrl());
                mVideoView.requestFocus();
                if (mIsVIP) {
                    mVideoView.start();
                    mVideoView.setFullscreen(true);
                    mIvUrl.setVisibility(View.GONE);
                    ivVideoPlayer.setVisibility(View.GONE);
                    mMediaController.setTitle(mVideoDetails.getTitle());
                }
            }
        });
    }

    private void initVideoView() {
        setVideoAreaSize();
        mVideoView.setVideoViewCallback(new UniversalVideoView.VideoViewCallback() {
            @Override
            public void onScaleChange(boolean isFullscreen) {
                if (isFullscreen) {
                    ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    mVideoLayout.setLayoutParams(layoutParams);
                    lrvView.setVisibility(View.GONE);
                    mToolbar.setVisibility(View.GONE);
                } else {
                    if (mIsVIP) return;
                    ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    layoutParams.height = mCachedHeight;
                    mVideoLayout.setLayoutParams(layoutParams);
                    lrvView.setVisibility(View.VISIBLE);
                    mToolbar.setVisibility(View.VISIBLE);
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

    private void getVideoComment() {
        BaseApi.request(BaseApi.createApi(IMainApi.class).getVideoCommentList(),
                new BaseApi.IResponseListener<List<VideoComment>>() {
                    @Override
                    public void onSuccess(List<VideoComment> data) {
                        mCommentAdapter.replaceAll(data);
                    }

                    @Override
                    public void onFail() {

                    }
                });
    }

    @OnClick(R.id.iv_video_player)
    public void onClick(View view) {
        if (mVideoDetails.getIsPage().equals("1")) {
            AppUtils.openBrowser(mContext, mVideoDetails.getVideoPageUrl());
        } else {
            mIvUrl.setVisibility(View.GONE);
            ivVideoPlayer.setVisibility(View.GONE);
            mVideoView.start();
        }
    }
}
