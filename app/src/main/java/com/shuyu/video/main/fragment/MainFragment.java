package com.shuyu.video.main.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ExpandableListView;

import com.shuyu.core.BaseFragment;
import com.shuyu.core.widget.CirclePageIndicator;
import com.shuyu.core.widget.TabsView;
import com.shuyu.video.R;
import com.shuyu.video.main.adapter.ChannelBannerAdapter;
import com.shuyu.video.main.adapter.ChannelGroupAdapter;
import com.shuyu.video.model.ChannelBanner;
import com.shuyu.video.model.ChannelContent;
import com.shuyu.video.model.ChannelType;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

public class MainFragment extends BaseFragment {

    public static final int UPDATE_VIEWPAGER = 200;

    @Bind(R.id.tb_indicator)
    TabsView tbIndicator;
    @Bind(R.id.rv_container)
    ExpandableListView rvContainer;

    private View vChannelHeader;
    private ViewPager mVpContainer;
    private CirclePageIndicator cpiIndicator;

    private ChannelBannerAdapter mBannerAdapter;
    private ChannelGroupAdapter mContentAdapter;

    private List<ChannelType> mChannelTypes;
    private List<ChannelBanner> mChannelBanners;
    private List<ChannelContent> mChannelContents;

    private int currIndex = 0;
    private Timer timer;
    private MyHandler mMyHandler;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initData() {

        vChannelHeader = View.inflate(mContext,R.layout.header_channel,null);
        mVpContainer = (ViewPager) vChannelHeader.findViewById(R.id.vp_container);
        cpiIndicator = (CirclePageIndicator) vChannelHeader.findViewById(R.id.cpi_indicator);

        String[] title = new String[]{"推荐", "人气", "青春", "动画"};
        mChannelTypes = new ArrayList<>();
        List<String> mTitles = new ArrayList<>();
        for (String aTitle : title) {
            mTitles.add(aTitle);
            ChannelType channelType = new ChannelType();
            channelType.setTitle(aTitle);
            mChannelTypes.add(channelType);
        }

        tbIndicator.setChildView(mTitles, new TabsView.TabsChildViewClickListener() {
            @Override
            public void onTabsChildViewCLick(int position) {

            }
        });

        mChannelBanners = new ArrayList<>();
        mChannelBanners.add(new ChannelBanner());
        mChannelBanners.add(new ChannelBanner());
        mChannelBanners.add(new ChannelBanner());
        mBannerAdapter = new ChannelBannerAdapter(mContext, mChannelBanners);
        mVpContainer.setAdapter(mBannerAdapter);
        cpiIndicator.setViewPager(mVpContainer);

        mChannelContents = new ArrayList<>();
        ChannelContent channelContent = new ChannelContent();
        mChannelContents.add(channelContent);
        List<ChannelContent.ChannelContentListBean> contentListBeans = new ArrayList<>();
        channelContent.setChannelContentList(contentListBeans);
        for (int i = 0; i < 10; i++) {
            ChannelContent.ChannelContentListBean channelContentListBean = new ChannelContent.ChannelContentListBean();
            contentListBeans.add(channelContentListBean);
        }
        mContentAdapter = new ChannelGroupAdapter(mContext, mChannelContents);
        rvContainer.setAdapter(mContentAdapter);
        rvContainer.setGroupIndicator(null);
        for (int i = 0; i < mChannelContents.size(); i++) {
            rvContainer.expandGroup(i);
        }
        rvContainer.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
            }
        });

        rvContainer.addHeaderView(vChannelHeader);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMyHandler = new MyHandler(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (timer == null) {
            timer = new Timer();
            autoUpdateViewPager();
        }
    }

    private void autoUpdateViewPager() {
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

        private WeakReference<MainFragment> activityWeakReference;

        MyHandler(MainFragment activity) {
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {

            MainFragment activity = activityWeakReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case MainFragment.UPDATE_VIEWPAGER:
                        if (msg.arg1 != 0) {
                            activity.mVpContainer.setCurrentItem(msg.arg1);
                        } else {
                            activity.mVpContainer.setCurrentItem(msg.arg1, false);
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
