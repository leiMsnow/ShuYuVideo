package com.shuyu.video.main.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;

import com.shuyu.core.BaseFragment;
import com.shuyu.core.widget.CirclePageIndicator;
import com.shuyu.core.widget.TabsView;
import com.shuyu.video.R;
import com.shuyu.video.main.adapter.BannerAdapter;
import com.shuyu.video.model.Banner;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

public class MainFragment extends BaseFragment {

    public static final int UPDATE_VIEWPAGER = 200;
    @Bind(R.id.vp_container)
    ViewPager mVpContainer;
    @Bind(R.id.tb_indicator)
    TabsView tbIndicator;
    @Bind(R.id.rv_container)
    RecyclerView rvContainer;
    @Bind(R.id.cpi_indicator)
    CirclePageIndicator cpiIndicator;

    private BannerAdapter mBannerAdapter;
    private List<Banner> mBanners;
    private int currIndex = 0;
    private Timer timer ;
    private MyHandler mMyHandler;

    private String[] mTitles = new String[]{
            "精选", "推荐", "人气", "美图"
    };

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

        tbIndicator.setChildView(mTitles, new TabsView.TabsChildViewClickListener() {
            @Override
            public void onTabsChildViewCLick(int position) {

            }
        });

        mBanners = new ArrayList<>();
        mBanners.add(new Banner());
        mBanners.add(new Banner());
        mBanners.add(new Banner());
        mBanners.add(new Banner());

        mBannerAdapter = new BannerAdapter(mContext, mBanners);

        mVpContainer.setAdapter(mBannerAdapter);
        cpiIndicator.setViewPager(mVpContainer);

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
                if (currIndex == mBanners.size()) {
                    currIndex = -1;
                }
                message.arg1 = currIndex++;
                mMyHandler.sendMessage(message);
            }
        }, 5 * 1000, 5 * 1000);
    }

    private static class MyHandler extends Handler {

        private WeakReference<MainFragment> activityWeakReference;

        public MyHandler(MainFragment activity) {
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
