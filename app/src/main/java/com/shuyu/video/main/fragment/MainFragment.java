package com.shuyu.video.main.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ExpandableListView;

import com.ray.core.captain.utils.LogUtils;
import com.shuyu.core.BaseFragment;
import com.shuyu.core.widget.CirclePageIndicator;
import com.shuyu.core.widget.TabsView;
import com.shuyu.video.R;
import com.shuyu.video.api.ApiUtils;
import com.shuyu.video.api.interfaces.IMainApi;
import com.shuyu.video.main.adapter.ChannelBannerAdapter;
import com.shuyu.video.main.adapter.ChannelGroupAdapter;
import com.shuyu.video.model.ChannelBanner;
import com.shuyu.video.model.ChannelContent;
import com.shuyu.video.model.ChannelTitle;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

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
    private ChannelGroupAdapter mGroupAdapter;

    private List<ChannelTitle> mChannelTitles;
    private List<ChannelBanner> mChannelBanners;
    private List<ChannelContent.VideoChannelListBean> mChannelContents;

    private int currIndex = 0;
    private Timer timer;
    private MyHandler mMyHandler;
    private TabsViewClick tabsViewClick;

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

        vChannelHeader = View.inflate(mContext, R.layout.header_channel, null);
        mVpContainer = (ViewPager) vChannelHeader.findViewById(R.id.vp_container);
        cpiIndicator = (CirclePageIndicator) vChannelHeader.findViewById(R.id.cpi_indicator);

        getChannelTitle();

        tabsViewClick = new TabsViewClick();
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

    private void getChannelTitle() {
        IMainApi mainApi = ApiUtils.createApi(IMainApi.class);
        Call<List<ChannelTitle>> call = mainApi.getChannelList();
        call.enqueue(new Callback<List<ChannelTitle>>() {
            @Override
            public void onResponse(Response<List<ChannelTitle>> response, Retrofit retrofit) {
                LogUtils.d(MainFragment.class.getSimpleName(), "response: "
                        + response.body().toString());
                mChannelTitles = response.body();
                List<String> title = new ArrayList<>();
                for (ChannelTitle title1 : response.body()) {
                    title.add(title1.getTitle());
                }
                tbIndicator.setChildView(title, tabsViewClick);
                getChannelData(mChannelTitles.get(0).getId());
            }

            @Override
            public void onFailure(Throwable t) {
                LogUtils.d(MainFragment.class.getSimpleName(), "onFailure");
            }
        });
    }


    private void getChannelBanner(int cid) {
        IMainApi mainApi = ApiUtils.createApi(IMainApi.class);
        Call<List<ChannelBanner>> call = mainApi.getChannelBanner(cid);
        call.enqueue(new Callback<List<ChannelBanner>>() {
            @Override
            public void onResponse(Response<List<ChannelBanner>> response, Retrofit retrofit) {
                LogUtils.d(MainFragment.class.getSimpleName(), "response: "
                        + response.body().toString());
                mChannelBanners = response.body();
                mBannerAdapter.setBanners(mChannelBanners);
                autoUpdateViewPager();
            }

            @Override
            public void onFailure(Throwable t) {
                LogUtils.d(MainFragment.class.getSimpleName(), "onFailure");
            }
        });

    }

    private void getChannelContent(int cid, int pageNo) {
        IMainApi mainApi = ApiUtils.createApi(IMainApi.class);
        Call<ChannelContent> call = mainApi.getChannelContent(cid, pageNo, 10);
        call.enqueue(new Callback<ChannelContent>() {
            @Override
            public void onResponse(Response<ChannelContent> response, Retrofit retrofit) {
                LogUtils.d(MainFragment.class.getSimpleName(), "response: "
                        + response.body().toString());
                mChannelContents = response.body().getVideoChannelList();
                mGroupAdapter.setChannelContents(mChannelContents);
                for (int i = 0; i < mChannelContents.size(); i++) {
                    rvContainer.expandGroup(i);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                LogUtils.d(MainFragment.class.getSimpleName(), "onFailure: " + t);
            }
        });
    }


    class TabsViewClick implements TabsView.TabsChildViewClickListener {
        @Override
        public void onTabsChildViewCLick(int position) {
            getChannelData(mChannelTitles.get(position).getId());
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
