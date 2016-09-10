package com.shuyu.video.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shuyu.core.BaseActivity;
import com.shuyu.core.api.BaseApi;
import com.shuyu.video.R;
import com.shuyu.video.adapter.VideoCommentAdapter;
import com.shuyu.video.api.IMainApi;
import com.shuyu.video.model.VideoComment;
import com.shuyu.video.model.VideoDetails;
import com.shuyu.video.utils.CommonUtils;
import com.shuyu.video.utils.Constants;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class VideoDetailsActivity extends BaseActivity {

    @Bind(R.id.lrv_view)
    RecyclerView lrvView;
    @Bind(R.id.iv_url)
    ImageView mIvUrl;

    private int mVideoId;
    private VideoDetails mVideoDetails;
    private VideoCommentAdapter mCommentAdapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_video_details;
    }

    @Override
    protected void initData() {

        if (getIntent() == null) return;
        mVideoId = getIntent().getIntExtra(Constants.VIDEO_DETAIL_ID, 0);
        if (mVideoId == 0) return;

        mCommentAdapter = new VideoCommentAdapter(mContext, null, R.layout.item_video_comment);
        lrvView.setLayoutManager(new LinearLayoutManager(mContext));
        lrvView.setAdapter(mCommentAdapter);
        getVideoDetails(mVideoId);
        getVideoComment();
    }

    private void getVideoDetails(int id) {
        BaseApi.request(BaseApi.createApi(IMainApi.class).getVideoDetails(id),
                new BaseApi.IResponseListener<VideoDetails>() {
                    @Override
                    public void onSuccess(VideoDetails data) {
                        Glide.with(mContext).load(data.getImgUrl()).into(mIvUrl);
                        mVideoDetails = data;
                        setTitle(data.getTitle());
                    }

                    @Override
                    public void onFail() {
                        mIvUrl.setImageResource(R.mipmap.ic_default_image);
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

    @OnClick(R.id.iv_url)
    public void onClick(View view) {
        CommonUtils.goToVideoPage(mContext,mVideoDetails);
    }

}
