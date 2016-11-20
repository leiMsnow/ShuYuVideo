package com.shuyu.video.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shuyu.core.BaseFragment;
import com.shuyu.core.widget.transformer.ZoomOutPageTransformer;
import com.shuyu.video.R;
import com.shuyu.video.activity.VideoDetailsActivity;
import com.shuyu.video.adapter.VipPageAdapter;
import com.shuyu.video.api.BaseApi;
import com.shuyu.video.api.IServiceApi;
import com.shuyu.video.model.LiveVideo;
import com.shuyu.video.model.UserInfo;
import com.shuyu.video.model.VideoPicDetails;
import com.shuyu.video.utils.Constants;
import com.shuyu.video.utils.PayUtils;
import com.shuyu.video.widget.MyViewPager;

import butterknife.Bind;
import butterknife.OnPageChange;

public class VipFragment extends BaseFragment {

    @Bind(R.id.vp_container)
    MyViewPager vpContainer;
    @Bind(R.id.tv_desc)
    TextView tvDesc;
    private boolean mCanScroll = false;

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

        getLiveVideoList();

        mPageAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final VideoPicDetails vip = (VideoPicDetails) v.getTag();
                PayUtils.isShowPayDialog(getActivity(), vip.getFeeRule(),
                        new PayUtils.IPlayerListener() {
                            @Override
                            public void canPlayer(boolean canPlayer) {
                                if (canPlayer) {
                                    Intent intent = new Intent(mContext, VideoDetailsActivity.class);
                                    intent.putExtra(Constants.VIDEO_DETAIL_ID, vip.getId());
                                    intent.putExtra(Constants.IS_VIP_VIDEO, true);
                                    mContext.startActivity(intent);
                                }
                            }
                        });

            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            canScroll();
        }
    }

    private void canScroll() {
        PayUtils.getUserInfo(new BaseApi.IResponseListener<UserInfo>() {
            @Override
            public void onSuccess(UserInfo data) {
                vpContainer.setCanScroll(mCanScroll = data.getUserType() > 2);
            }

            @Override
            public void onFail() {

            }
        });
    }

    @OnPageChange(value = R.id.vp_container, callback = OnPageChange.Callback.PAGE_SELECTED)
    public void onPageSelected(int position) {
        if (mCanScroll) {
            tvDesc.setText(mPageAdapter.getPageTitle(position));
        } else {
            PayUtils.showPayDialog(mContext);
        }
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
