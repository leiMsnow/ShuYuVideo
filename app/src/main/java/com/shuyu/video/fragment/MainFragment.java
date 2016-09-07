package com.shuyu.video.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.shuyu.core.api.BaseApi;
import com.shuyu.core.BaseFragment;
import com.shuyu.core.widget.HorizontalIndicatorView;
import com.shuyu.video.R;
import com.shuyu.video.api.IMainApi;
import com.shuyu.video.adapter.ViewPagerAdapter;
import com.shuyu.video.model.ChannelTitle;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MainFragment extends BaseFragment {


    @Bind(R.id.tb_indicator)
    HorizontalIndicatorView tbIndicator;
    @Bind(R.id.rv_container)
    ViewPager rvContainer;

    private ViewPagerAdapter mPagerAdapter;

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
        mPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        getChannelTitle();
    }

    private void getChannelTitle() {
        BaseApi.request(BaseApi.createApi(IMainApi.class).getChannelList(),
                new BaseApi.IResponseListener<List<ChannelTitle>>() {

                    @Override
                    public void onSuccess(List<ChannelTitle> data) {
                        addFragments(data);
                    }

                    @Override
                    public void onFail() {

                    }
                });

    }

    private void addFragments(List<ChannelTitle> data) {
        List<Fragment> mFragments = new ArrayList<>();
        for (ChannelTitle channelTitle : data) {
            mFragments.add(ChannelFragment.newInstance(channelTitle.getId(),
                    channelTitle.getTitle()));
        }
        mPagerAdapter.setFragments(mFragments);
        rvContainer.setAdapter(mPagerAdapter);
        tbIndicator.setViewPager(rvContainer);
    }

}
