package com.shuyu.video.activity;

import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shuyu.core.api.BaseApi;
import com.shuyu.core.uils.AppUtils;
import com.shuyu.core.uils.LogUtils;
import com.shuyu.core.uils.ToastUtils;
import com.shuyu.video.R;
import com.shuyu.video.adapter.VideoCommentAdapter;
import com.shuyu.video.api.ILocalServiceApi;
import com.shuyu.video.api.IServiceApi;
import com.shuyu.video.model.ResultEntity;
import com.shuyu.video.model.VideoComment;
import com.shuyu.video.model.VideoPicDetails;
import com.shuyu.video.utils.DialogUtils;
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
    @Bind(R.id.tv_comment)
    TextView tvComment;
    @Bind(R.id.video_bg)
    View videoBG;

    private VideoPicDetails mVideoDetails;
    private VideoCommentAdapter mCommentAdapter;
    private boolean mIsVIP;
    private int mCachedHeight;
    private int mCurrentPosition;
    private boolean mIsPlaying;
    private boolean mIsFullscreen;
    private boolean mIsLook;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_video_details;
    }

    @Override
    protected boolean hasToolbar() {
        return false;
    }

    @Override
    protected void initData() {
        if (getIntent() == null) return;
        int videoId = getIntent().getIntExtra(Constants.VIDEO_DETAIL_ID, 0);
        if (videoId == 0) return;
        mIsVIP = getIntent().getBooleanExtra(Constants.IS_VIP_VIDEO, false);
        getVideoDetails(videoId);
        if (!mIsVIP) {
            videoBG.setVisibility(View.GONE);
            mCommentAdapter = new VideoCommentAdapter(mContext, null, R.layout.item_video_comment);
            lrvView.setLayoutManager(new LinearLayoutManager(mContext));
            lrvView.setAdapter(mCommentAdapter);
            tvComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCommentDialog();
                }
            });
            getVideoComment();
        }
    }

    private void showCommentDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        View view = View.inflate(mContext, R.layout.alert_comment, null);
        alertDialog.setView(view);
        final AlertDialog dialog = alertDialog.create();
        final EditText editText = (EditText) view.findViewById(R.id.et_comment);
        final Button btnSubmit = (Button) view.findViewById(R.id.btn_submit);
        final Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    ToastUtils.getInstance().showToast("请输入评论内容");
                    return;
                }
                ToastUtils.getInstance().showToast("评论成功");
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mVideoView != null) {
            mCurrentPosition = mVideoView.getCurrentPosition();
            mIsPlaying = mVideoView.isPlaying();
            mVideoView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoView != null) {
            if (mCurrentPosition != 0) {
                mVideoView.seekTo(mCurrentPosition);
                if (mIsPlaying)
                    startPlay();
            }
        }
    }

    private void getVideoDetails(int id) {
        BaseApi.request(BaseApi.createApi(IServiceApi.class).getVideoDetails(id),
                new BaseApi.IResponseListener<VideoPicDetails>() {
                    @Override
                    public void onSuccess(VideoPicDetails data) {
                        videoBG.setVisibility(View.GONE);
                        Glide.with(mContext).load(data.getImgUrl()).into(mIvUrl);
                        setTitle(data.getTitle());
                        mVideoDetails = data;
                        initVideoView();
                    }

                    @Override
                    public void onFail() {
                        videoBG.setVisibility(View.GONE);
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
                mMediaController.setTitle(mVideoDetails.getTitle());
                mVideoView.requestFocus();
                if (mIsVIP) {
                    mVideoView.setFullscreen(true);
                }
                startPlay();
                mMediaController.setBackListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mIsFullscreen) {
            mVideoView.setFullscreen(false);
        } else {
            super.onBackPressed();
        }
    }

    private void initVideoView() {
        setVideoAreaSize();
        mVideoView.setVideoViewCallback(new UniversalVideoView.VideoViewCallback() {
            @Override
            public void onScaleChange(boolean isFullscreen) {
                mIsFullscreen = isFullscreen;
                if (mIsFullscreen) {
                    ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    mVideoLayout.setLayoutParams(layoutParams);
                    lrvView.setVisibility(View.GONE);
                } else {
                    if (mIsVIP) {
                        finish();
                        return;
                    }
                    ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    layoutParams.height = mCachedHeight;
                    mVideoLayout.setLayoutParams(layoutParams);
                    lrvView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPause(MediaPlayer mediaPlayer) {
            }

            @Override
            public void onStart(MediaPlayer mediaPlayer) {
                if (mIsLook) return;
                mIsLook = true;
                lookVideoState();
            }

            @Override
            public void onBufferingStart(MediaPlayer mediaPlayer) {
            }

            @Override
            public void onBufferingEnd(MediaPlayer mediaPlayer) {
            }
        });
    }

    private void getVideoComment() {
        BaseApi.request(BaseApi.createApi(IServiceApi.class).getVideoCommentList(),
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
            startPlay();
        }
    }

    private void startPlay() {
        if (DialogUtils.canPlayer(mContext, mVideoDetails.getFeeRule())) {
            mIvUrl.setVisibility(View.GONE);
            ivVideoPlayer.setVisibility(View.GONE);
            mVideoView.start();
        }
    }

    private void lookVideoState() {
        BaseApi.request(BaseApi.createApi(ILocalServiceApi.class)
                        .lookVideoState(mVideoDetails.getId(), 0)
                , new BaseApi.IResponseListener<ResultEntity>() {
                    @Override
                    public void onSuccess(ResultEntity data) {
                        LogUtils.d(VideoDetailsActivity.class.getName(), data.getResultMessage());
                    }

                    @Override
                    public void onFail() {

                    }
                });
    }

}
