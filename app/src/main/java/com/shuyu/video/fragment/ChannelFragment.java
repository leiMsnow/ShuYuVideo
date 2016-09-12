package com.shuyu.video.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.shuyu.core.BaseFragment;
import com.shuyu.core.api.BaseApi;
import com.shuyu.core.widget.CirclePageIndicator;
import com.shuyu.video.R;
import com.shuyu.video.adapter.ChannelBannerAdapter;
import com.shuyu.video.adapter.ChannelGroupAdapter;
import com.shuyu.video.api.IMainApi;
import com.shuyu.video.model.ChannelBanner;
import com.shuyu.video.model.ChannelPictureEntity;
import com.shuyu.video.model.ChannelVideoEntity;
import com.shuyu.video.model.ChannelTitle;
import com.shuyu.video.model.SubChannel;
import com.shuyu.video.utils.Constants;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

public class ChannelFragment extends BaseFragment {

    public static final int UPDATE_VIEWPAGER = 200;

    @Bind(R.id.rv_container)
    ExpandableListView mExpandableListView;

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

    private int[] tagColorResId = new int[]{
            R.drawable.shape_round_red,
            R.drawable.shape_round_orange,
            R.drawable.shape_round_yellow,
            R.drawable.shape_round_light_green,
            R.drawable.shape_round_light_blue,
            R.drawable.shape_round_pink,
            R.drawable.shape_round_deep_purple

    };

    public static ChannelFragment newInstance(ChannelTitle channelTitle) {
        ChannelFragment fragment = new ChannelFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.CHANNEL_DETAILS, channelTitle);
        args.putString(Constants.BANNEL_TITLE, channelTitle.getTitle());
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
        ChannelTitle channelTitle = (ChannelTitle) getArguments().getSerializable(Constants.CHANNEL_DETAILS);
        if (channelTitle == null) return;
        getChannelData(channelTitle);

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
        if (channelTitle.getChannelType() == Constants.BANNEL_VIDEO) {
            getVideoList(channelTitle.getId(), 1);
        } else if (channelTitle.getChannelType() == Constants.BANNEL_PICTURE) {
            getPictureList(channelTitle.getId(), 1);
        }
    }

    private void getChannelBanner(int cid) {
        BaseApi.request(BaseApi.createApi(IMainApi.class).getChannelBannerList(cid),
                new BaseApi.IResponseListener<List<ChannelBanner>>() {

                    @Override
                    public void onSuccess(List<ChannelBanner> data) {
                        mChannelBanners = data;
                        mBannerAdapter.setBanners(mChannelBanners);
                        mVpContainer.setAdapter(mBannerAdapter);
                        cpiIndicator.setViewPager(mVpContainer);
                        tvBannerTitle.setText(data.get(0).getTitle());
                        autoUpdateViewPager();
                    }

                    @Override
                    public void onFail() {

                    }
                });
    }

    private void getVideoList(int cid, int pageNo) {
        BaseApi.request(BaseApi.createApi(IMainApi.class).getVideoListByChannelId(cid, pageNo, 6)
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
        BaseApi.request(BaseApi.createApi(IMainApi.class).getPictureListByChannelId(cid, pageNo, 6)
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
        Random random = new Random();
        mGroupAdapter.setChannelContents(mChannelContents);
        for (int i = 0; i < mChannelContents.size(); i++) {
            mExpandableListView.expandGroup(i);
            for (int j = 0; j < mChannelContents.get(i).getChannelContentList().size(); j++) {
                mChannelContents.get(i).getChannelContentList().get(j).setTagColor(
                        new int[]{
                                tagColorResId[random.nextInt(tagColorResId.length)],
                                tagColorResId[random.nextInt(tagColorResId.length)]
                        }
                );
            }
        }
    }

    private void autoUpdateViewPager() {
        if (mChannelContents == null)
            return;
        if (timer == null) {
            timer = new Timer();
        }
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
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
