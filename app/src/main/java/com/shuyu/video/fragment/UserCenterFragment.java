package com.shuyu.video.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.shuyu.core.BaseFragment;
import com.shuyu.core.uils.ToastUtils;
import com.shuyu.video.R;
import com.shuyu.video.activity.AboutActivity;
import com.shuyu.video.activity.FeedbackActivity;
import com.shuyu.video.adapter.SettingAdapter;
import com.shuyu.video.model.SettingEntity;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class UserCenterFragment extends BaseFragment {

    @Bind(R.id.rv_container)
    RecyclerView mRvContainer;
    private SettingAdapter mSettingAdapter;
    private IRecommendListener mRecommendListener;

    public void setRecommendListener(IRecommendListener recommendListener) {
        mRecommendListener = recommendListener;
    }

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
        mRvContainer.setLayoutManager(new LinearLayoutManager(mContext));
        mRvContainer.setAdapter(mSettingAdapter);

        mSettingAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                switch (mSettingAdapter.getItem(position).getSetId()) {
                    case SettingEntity.RECOMMEND:
                        if (mRecommendListener!=null){
                            mRecommendListener.recommendTab();
                        }
                        break;
                    case SettingEntity.CLEAR:
                        ToastUtils.getInstance().showToast("清除完成");
                        break;
                    case SettingEntity.FEEDBACK:
                        startActivity(new Intent(mContext, FeedbackActivity.class));
                        break;
                    case SettingEntity.UPDATE:
                        ToastUtils.getInstance().showToast("已经是最新版本");
                        break;
                    case SettingEntity.ABOUT:
                        startActivity(new Intent(mContext, AboutActivity.class));
                        break;
                }
            }
        });
    }

    private List<SettingEntity> initSettingData() {
        List<SettingEntity> settingEntityList = new ArrayList<>();

        settingEntityList.add(new SettingEntity(SettingEntity.RECOMMEND,R.mipmap.ic_protocol, "精品推荐"));
        settingEntityList.add(new SettingEntity(SettingEntity.CLEAR,R.mipmap.ic_clean_cache, "清除缓存"));
        settingEntityList.add(new SettingEntity(SettingEntity.FEEDBACK,R.mipmap.ic_feedback, getString(R.string.feedback)));
        settingEntityList.add(new SettingEntity(SettingEntity.UPDATE,R.mipmap.ic_update, "版本更新"));
        settingEntityList.add(new SettingEntity(SettingEntity.ABOUT,R.mipmap.ic_about, getString(R.string.about)));

        return settingEntityList;
    }

    public interface IRecommendListener{
        void recommendTab();
    }
}
