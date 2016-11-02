package com.shuyu.video.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuyu.core.BaseFragment;
import com.shuyu.core.uils.AppUtils;
import com.shuyu.core.uils.ToastUtils;
import com.shuyu.video.R;
import com.shuyu.video.activity.AboutActivity;
import com.shuyu.video.activity.FeedbackActivity;
import com.shuyu.video.activity.ServiceActivity;
import com.shuyu.video.activity.WebViewActivity;
import com.shuyu.video.adapter.SettingAdapter;
import com.shuyu.video.api.BaseApi;
import com.shuyu.video.model.SettingsInfo;
import com.shuyu.video.model.UserInfo;
import com.shuyu.video.utils.Constants;
import com.shuyu.video.utils.PayUtils;

import org.byteam.superadapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class UserCenterFragment extends BaseFragment {

    @Bind(R.id.rv_container)
    RecyclerView mRvContainer;
    @Bind(R.id.tv_member_tips)
    TextView mTvMemberTips;
    @Bind(R.id.btn_member)
    Button mBtnMember;
    @Bind(R.id.iv_user)
    ImageView mIvUser;
    @Bind(R.id.tv_account)
    TextView mTvAccount;
    @Bind(R.id.tv_password)
    TextView mTvPassword;
    @Bind(R.id.btn_show_password)
    Button mBtnShowPassword;
    @Bind(R.id.rl_user)
    RelativeLayout mRlUser;
    private SettingAdapter mSettingAdapter;
    private IRecommendListener mRecommendListener;
    private UserInfo mData;

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
                        intent.putExtra(Constants.KEY_WEB_VIEW_TYPE,
                                WebViewActivity.VIEW_VIEW_TYPE_DISCLAIMER);
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

        mBtnMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayUtils.showPayDialog(getActivity());
            }
        });

        String userName = AppUtils.getIMEI().substring(0, 8);
        final String password = AppUtils.getIMEI().substring(7);
        mTvAccount.setText(getString(R.string.user_account, userName));
        mTvPassword.setText(getString(R.string.user_password, "******"));
        mBtnShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnShowPassword.setVisibility(View.GONE);
                mTvPassword.setText(getString(R.string.user_password, password));
            }
        });
    }

    private void getUserInfo() {
        PayUtils.getUserInfo(new BaseApi.IResponseListener<UserInfo>() {
            @Override
            public void onSuccess(UserInfo data) {
                mData = data;
                setUserInfo();
            }

            @Override
            public void onFail() {

            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            getUserInfo();
        }
    }

    private void setUserInfo() {
        mTvMemberTips.setText(getString(R.string.user_rule_tips, mData.getUserTypeShow()));
        mBtnMember.setText(PayUtils.getPayMoneyTips(mData.getUserType(),false));
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
