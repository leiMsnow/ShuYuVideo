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
import com.shuyu.video.activity.VideoActivity;
import com.shuyu.video.adapter.VipPageAdapter;
import com.shuyu.video.api.IMainApi;
import com.shuyu.video.model.LiveVideoEntitiy;
import com.shuyu.video.model.VideoDetails;
import com.shuyu.video.utils.Constants;

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
                VideoDetails vip = (VideoDetails) v.getTag();
                Intent intent = new Intent(mContext, VideoActivity.class);
                intent.putExtra(Constants.VIDEO_DETAILS, vip);
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
        BaseApi.request(BaseApi.createApi(IMainApi.class).getLiveVideoList(1),
                new BaseApi.IResponseListener<LiveVideoEntitiy>() {
                    @Override
                    public void onSuccess(LiveVideoEntitiy data) {
                        mPageAdapter.setLiveVideoDataList(data.getNightVideoDetailList());
                        vpContainer.setOffscreenPageLimit(mPageAdapter.getCount());
                    }

                    @Override
                    public void onFail() {

                    }
                });
    }


}
