package com.shuyu.video.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shuyu.core.BaseFragment;
import com.shuyu.video.R;
import com.shuyu.video.adapter.SettingAdapter;
import com.shuyu.video.model.SettingEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class UserCenterFragment extends BaseFragment {

    @Bind(R.id.rv_container)
    RecyclerView mRvContainer;
    private SettingAdapter mSettingAdapter;

    public static UserCenterFragment newInstance() {
        UserCenterFragment fragment = new UserCenterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_user_center;
    }

    @Override
    protected void initData() {
        mSettingAdapter = new SettingAdapter(mContext, initSettingData(), R.layout.item_settings);
        mRvContainer.setAdapter(mSettingAdapter);
        mRvContainer.setLayoutManager(new LinearLayoutManager(mContext));
    }

    private List<SettingEntity> initSettingData() {

        List<SettingEntity> settingEntityList = new ArrayList<>();
        settingEntityList.add(new SettingEntity(R.mipmap.ic_protocol, "精品推荐"));
        settingEntityList.add(new SettingEntity(R.mipmap.ic_clean_cache, "清除缓存"));
        settingEntityList.add(new SettingEntity(R.mipmap.ic_feedback, "反馈意见"));
        settingEntityList.add(new SettingEntity(R.mipmap.ic_protocol, "版本更新"));
        settingEntityList.add(new SettingEntity(R.mipmap.ic_copyright, "关于我们"));

        return settingEntityList;
    }

}
