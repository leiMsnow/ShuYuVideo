package com.shuyu.video.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.shuyu.core.BaseFragment;
import com.shuyu.core.api.BaseApi;
import com.shuyu.core.widget.transformer.ZoomOutPageTransformer;
import com.shuyu.video.R;
import com.shuyu.video.activity.VideoDetailsActivity;
import com.shuyu.video.adapter.VipPageAdapter;
import com.shuyu.video.api.IServiceApi;
import com.shuyu.video.model.LiveVideo;
import com.shuyu.video.model.VideoPicDetails;
import com.shuyu.video.utils.Constants;
import com.shuyu.video.utils.DialogUtils;

import butterknife.Bind;
import butterknife.OnPageChange;

public class VipFragment extends BaseFragment {

    @Bind(R.id.vp_container)
    ViewPager vpContainer;
    @Bind(R.id.tv_desc)
    TextView tvDesc;

    private VipPageAdapter mPageAdapter;

    public static VipFragment newInstance() {
        VipFragment fragment = new VipFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_vip;
    }

    @Override
    protected void initData() {
        mPageAdapter = new VipPageAdapter(mContext);
        vpContainer.setAdapter(mPageAdapter);
        vpContainer.setPageTransformer(true, new ZoomOutPageTransformer());
        mPageAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoPicDetails vip = (VideoPicDetails) v.getTag();
                if (!DialogUtils.canPlayer(mContext, vip.getFeeRule())) return;

                Intent intent = new Intent(mContext, VideoDetailsActivity.class);
                intent.putExtra(Constants.VIDEO_DETAIL_ID, vip.getId());
                intent.putExtra(Constants.IS_VIP_VIDEO, true);
                mContext.startActivity(intent);
            }
        });
        getLiveVideoList();
    }

    @OnPageChange(value = R.id.vp_container, callback = OnPageChange.Callback.PAGE_SELECTED)
    public void onPageSelected(int position) {
        tvDesc.setText(mPageAdapter.getPageTitle(position));
    }

    private void getLiveVideoList() {
        BaseApi.request(BaseApi.createApi(IServiceApi.class).getLiveVideoList(1),
                new BaseApi.IResponseListener<LiveVideo>() {
                    @Override
                    public void onSuccess(LiveVideo data) {
                        mPageAdapter.setLiveVideoDataList(data.getNightVideoDetailList());
                        vpContainer.setOffscreenPageLimit(mPageAdapter.getCount());
                        tvDesc.setText(data.getNightVideoDetailList().get(0).getDescription());
                    }

                    @Override
                    public void onFail() {

                    }
                });
    }
}
