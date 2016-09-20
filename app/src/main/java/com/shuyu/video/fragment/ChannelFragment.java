package com.shuyu.video.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.shuyu.core.BaseFragment;
import com.shuyu.core.api.BaseApi;
import com.shuyu.core.uils.AppUtils;
import com.shuyu.core.widget.CirclePageIndicator;
import com.shuyu.video.R;
import com.shuyu.video.activity.PictureDetailsActivity;
import com.shuyu.video.activity.VideoDetailsActivity;
import com.shuyu.video.adapter.ChannelBannerAdapter;
import com.shuyu.video.adapter.ChannelGroupAdapter;
import com.shuyu.video.api.IServiceApi;
import com.shuyu.video.model.ChannelBanner;
import com.shuyu.video.model.ChannelPictureEntity;
import com.shuyu.video.model.ChannelTitle;
import com.shuyu.video.model.ChannelVideoEntity;
import com.shuyu.video.model.SubChannel;
import com.shuyu.video.utils.CommonUtils;
import com.shuyu.video.utils.Constants;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

import static com.shuyu.video.R.id.swipe_container;

public class ChannelFragment extends BaseFragment {

    public static final int UPDATE_VIEWPAGER = 200;

    @Bind(R.id.rv_container)
    ExpandableListView mExpandableListView;
    @Bind(swipe_container)
    SwipeRefreshLayout mSwipeContainer;

    private ViewPager mVpContainer;
    private CirclePageIndicator cpiIndicator;
    private TextView tvBannerTitle;

    private ChannelBannerAdapter mBannerAdapter;
    private ChannelGroupAdapter mGroupAdapter;

    private List<ChannelBanner> mChannelBanners;
    private List<SubChannel> mChannelContents;

    private Timer timer;
    private int currIndex = 0;
    private MyHandler mMyHandler;


    public static ChannelFragment newInstance(ChannelTitle channelTitle) {
        ChannelFragment fragment = new ChannelFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.CHANNEL_DETAILS, channelTitle);
        args.putString(Constants.BANNER_TITLE, channelTitle.getTitle());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_channel_content;
    }

    @Override
    protected void initData() {

        if (getArguments() == null) return;
        final ChannelTitle channelTitle = (ChannelTitle) getArguments().getSerializable(Constants.CHANNEL_DETAILS);
        if (channelTitle == null) return;
        getChannelData(channelTitle);

        mSwipeContainer.setColorSchemeResources(R.color.app_main_color);
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getChannelData(channelTitle);
            }
        });

        View vChannelHeader = View.inflate(mContext, R.layout.header_channel, null);
        mVpContainer = (ViewPager) vChannelHeader.findViewById(R.id.vp_container);
        cpiIndicator = (CirclePageIndicator) vChannelHeader.findViewById(R.id.cpi_indicator);
        tvBannerTitle = (TextView) vChannelHeader.findViewById(R.id.tv_banner_title);

        mGroupAdapter = new ChannelGroupAdapter(mContext);
        mBannerAdapter = new ChannelBannerAdapter(mContext);

        mExpandableListView.setAdapter(mGroupAdapter);
        mExpandableListView.setGroupIndicator(null);

        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
            }
        });
        mExpandableListView.addHeaderView(vChannelHeader);


        cpiIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvBannerTitle.setText(mChannelBanners.get(position).getTitle());
                currIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mSwipeContainer.setEnabled(state == ViewPager.SCROLL_STATE_IDLE);
            }

        });

        mBannerAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChannelBanner channelBanner = (ChannelBanner) v.getTag();
                if (channelBanner.getBannerType() == Constants.BANNER_VIDEO) {
                    Intent intent = new Intent(mContext, VideoDetailsActivity.class);
                    intent.putExtra(Constants.VIDEO_DETAIL_ID, channelBanner.getTargetId());
                    mContext.startActivity(intent);
                } else if (channelBanner.getBannerType() == Constants.BANNER_PICTURE) {
                    Intent intent = new Intent(mContext, PictureDetailsActivity.class);
                    intent.putExtra(Constants.PICTURE_DETAIL_ID, channelBanner.getTargetId());
                    mContext.startActivity(intent);
                } else if (channelBanner.getBannerType() == Constants.BANNER_APP) {
                    CommonUtils.createDownloadTask(channelBanner.getTargetUrl(), null, null).start();
                } else if (channelBanner.getBannerType() == Constants.BANNER_WAP) {
                    AppUtils.openBrowser(mContext, channelBanner.getTargetUrl());
                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMyHandler = new MyHandler(this);
    }

    private void getChannelData(ChannelTitle channelTitle) {
        getChannelBanner(channelTitle.getId());
        if (channelTitle.getChannelType() == Constants.BANNER_VIDEO) {
            getVideoList(channelTitle.getId(), 1);
        } else if (channelTitle.getChannelType() == Constants.BANNER_PICTURE) {
            getPictureList(channelTitle.getId(), 1);
        }
    }

    private void getChannelBanner(int cid) {
        BaseApi.request(BaseApi.createApi(IServiceApi.class).getChannelBannerList(cid),
                new BaseApi.IResponseListener<List<ChannelBanner>>() {

                    @Override
                    public void onSuccess(List<ChannelBanner> data) {
                        mSwipeContainer.setRefreshing(false);
                        cancelTimer();
                        mChannelBanners = data;
                        mBannerAdapter.setBanners(mChannelBanners);
                        mVpContainer.setAdapter(mBannerAdapter);
                        cpiIndicator.setViewPager(mVpContainer);
                        tvBannerTitle.setText(data.get(0).getTitle());
                        autoUpdateViewPager();
                    }

                    @Override
                    public void onFail() {
                        mSwipeContainer.setRefreshing(false);
                    }
                });
    }

    private void getVideoList(int cid, int pageNo) {
        BaseApi.request(BaseApi.createApi(IServiceApi.class).getVideoListByChannelId(cid, pageNo, 6)
                , new BaseApi.IResponseListener<ChannelVideoEntity>() {

                    @Override
                    public void onSuccess(ChannelVideoEntity data) {
                        mChannelContents = data.getVideoChannelList();
                        updateGroupData();
                    }

                    @Override
                    public void onFail() {

                    }
                });
    }

    private void getPictureList(int cid, int pageNo) {
        BaseApi.request(BaseApi.createApi(IServiceApi.class).getPictureListByChannelId(cid, pageNo, 6)
                , new BaseApi.IResponseListener<ChannelPictureEntity>() {

                    @Override
                    public void onSuccess(ChannelPictureEntity data) {
                        mChannelContents = data.getPicChannelList();
                        updateGroupData();
                    }

                    @Override
                    public void onFail() {

                    }
                });
    }

    private void updateGroupData() {
        mGroupAdapter.setChannelContents(mChannelContents);
        for (int i = 0; i < mChannelContents.size(); i++) {
            mExpandableListView.expandGroup(i);
        }
    }

    private void autoUpdateViewPager() {
        if (mChannelContents == null)
            return;
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Message message = new Message();
                    message.what = UPDATE_VIEWPAGER;
                    if (currIndex == mChannelBanners.size()) {
                        currIndex = 0;
                    }
                    message.arg1 = currIndex++;
                    mMyHandler.sendMessage(message);
                }
            }, 5 * 1000, 5 * 1000);
        }
    }

    private static class MyHandler extends Handler {

        private WeakReference<ChannelFragment> activityWeakReference;

        MyHandler(ChannelFragment fragment) {
            activityWeakReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            ChannelFragment fragment = activityWeakReference.get();
            if (fragment != null) {
                switch (msg.what) {
                    case ChannelFragment.UPDATE_VIEWPAGER:
                        fragment.tvBannerTitle.setText(fragment.mChannelBanners.get(msg.arg1).getTitle());
                        if (msg.arg1 != 0) {
                            fragment.mVpContainer.setCurrentItem(msg.arg1);
                        } else {
                            fragment.mVpContainer.setCurrentItem(msg.arg1, false);
                        }
                        break;
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cancelTimer();
    }

    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
