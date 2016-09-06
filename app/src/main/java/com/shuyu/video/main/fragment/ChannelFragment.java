package com.shuyu.video.main.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ExpandableListView;

import com.shuyu.core.BaseApi;
import com.shuyu.core.BaseFragment;
import com.shuyu.core.widget.CirclePageIndicator;
import com.shuyu.video.R;
import com.shuyu.video.api.interfaces.IMainApi;
import com.shuyu.video.main.adapter.ChannelBannerAdapter;
import com.shuyu.video.main.adapter.ChannelGroupAdapter;
import com.shuyu.video.main.adapter.ViewPagerAdapter;
import com.shuyu.video.model.ChannelBanner;
import com.shuyu.video.model.ChannelContent;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

public class ChannelFragment extends BaseFragment {

    public static final String CHANNEL_ID = "channel_id";

    public static final int UPDATE_VIEWPAGER = 200;

    @Bind(R.id.rv_container)
    ExpandableListView rvContainer;

    private View vChannelHeader;
    private ViewPager mVpContainer;
    private CirclePageIndicator cpiIndicator;

    private ChannelBannerAdapter mBannerAdapter;
    private ChannelGroupAdapter mGroupAdapter;

    private List<ChannelBanner> mChannelBanners;
    private List<ChannelContent.VideoChannelListBean> mChannelContents;

    private int currIndex = 0;
    private Timer timer;
    private MyHandler mMyHandler;

    public static ChannelFragment newInstance(int cid, String title) {
        ChannelFragment fragment = new ChannelFragment();
        Bundle args = new Bundle();
        args.putInt(CHANNEL_ID, cid);
        args.putString(ViewPagerAdapter.ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_channel_content;
    }

    @Override
    protected void initData() {

        vChannelHeader = View.inflate(mContext, R.layout.header_channel, null);
        mVpContainer = (ViewPager) vChannelHeader.findViewById(R.id.vp_container);
        cpiIndicator = (CirclePageIndicator) vChannelHeader.findViewById(R.id.cpi_indicator);

        mGroupAdapter = new ChannelGroupAdapter(mContext);
        mBannerAdapter = new ChannelBannerAdapter(mContext);
        mVpContainer.setAdapter(mBannerAdapter);
        cpiIndicator.setViewPager(mVpContainer);

        rvContainer.setAdapter(mGroupAdapter);
        rvContainer.setGroupIndicator(null);

        rvContainer.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
            }
        });
        rvContainer.addHeaderView(vChannelHeader);

        if (getArguments() != null) {
            getChannelData(getArguments().getInt(CHANNEL_ID, 0));
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMyHandler = new MyHandler(this);
    }

    private void getChannelData(int cid) {
        getChannelBanner(cid);
        getChannelContent(cid, 1);
    }


    private void getChannelBanner(int cid) {
        BaseApi.request(BaseApi.createApi(IMainApi.class).getChannelBanner(cid),
                new BaseApi.IResponseListener<List<ChannelBanner>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onSuccess(List<ChannelBanner> data) {
                        mChannelBanners = data;
                        mBannerAdapter.setBanners(mChannelBanners);
                        autoUpdateViewPager();
                    }

                    @Override
                    public void onFail() {

                    }
                });
    }

    private void getChannelContent(int cid, int pageNo) {
        BaseApi.request(BaseApi.createApi(IMainApi.class).getChannelContent(cid, pageNo, 4)
                , new BaseApi.IResponseListener<ChannelContent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onSuccess(ChannelContent data) {
                        mChannelContents = data.getVideoChannelList();
                        mGroupAdapter.setChannelContents(mChannelContents);
                        for (int i = 0; i < mChannelContents.size(); i++) {
                            rvContainer.expandGroup(i);
                        }
                    }

                    @Override
                    public void onFail() {

                    }
                });
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
                    currIndex = -1;
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
