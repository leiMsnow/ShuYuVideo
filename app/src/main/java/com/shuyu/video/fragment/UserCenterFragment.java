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
import com.shuyu.video.activity.ServiceActivity;
import com.shuyu.video.activity.WebViewActivity;
import com.shuyu.video.adapter.SettingAdapter;
import com.shuyu.video.model.SettingsInfo;
import com.shuyu.video.utils.Constants;

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
                Intent intent;
                switch (mSettingAdapter.getItem(position).getSetId()) {
                    case SettingsInfo.RECOMMEND:
                        if (mRecommendListener != null)
                            mRecommendListener.recommendTab();
                        break;
                    case SettingsInfo.CLEAR:
                        ToastUtils.getInstance().showToast("清除完成");
                        break;
                    case SettingsInfo.FEEDBACK:
                        intent = new Intent(mContext, FeedbackActivity.class);
                        startActivity(intent);
                        break;
                    case SettingsInfo.UPDATE:
                        ToastUtils.getInstance().showToast("已经是最新版本");
                        break;
                    case SettingsInfo.DISCLAIMER:
                        intent = new Intent(mContext, WebViewActivity.class);
                        intent.putExtra(Constants.DISCLAIMER, true);
                        startActivity(intent);
                        break;
                    case SettingsInfo.SERVICE:
                        intent = new Intent(mContext, ServiceActivity.class);
                        startActivity(intent);
                        break;
                    case SettingsInfo.ABOUT:
                        intent = new Intent(mContext, AboutActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    private List<SettingsInfo> initSettingData() {
        List<SettingsInfo> settings = new ArrayList<>();

        settings.add(new SettingsInfo(SettingsInfo.RECOMMEND, R.mipmap.ic_protocol, getString(R.string.recommend)));
        settings.add(new SettingsInfo(SettingsInfo.CLEAR, R.mipmap.ic_clean_cache, getString(R.string.clean_cache)));
        settings.add(new SettingsInfo(SettingsInfo.SERVICE, R.mipmap.ic_service, getString(R.string.service)));
        settings.add(new SettingsInfo(SettingsInfo.FEEDBACK, R.mipmap.ic_feedback, getString(R.string.feedback)));
        settings.add(new SettingsInfo(SettingsInfo.UPDATE, R.mipmap.ic_update, getString(R.string.update)));
        settings.add(new SettingsInfo(SettingsInfo.DISCLAIMER, R.mipmap.ic_disclaimer, getString(R.string.disclaimer)));
        settings.add(new SettingsInfo(SettingsInfo.ABOUT, R.mipmap.ic_about, getString(R.string.about)));

        return settings;
    }

    public interface IRecommendListener {
        void recommendTab();
    }
}
