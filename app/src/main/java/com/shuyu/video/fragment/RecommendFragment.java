package com.shuyu.video.fragment;


import android.os.Bundle;

import com.shuyu.core.BaseFragment;
import com.shuyu.core.api.BaseApi;
import com.shuyu.video.R;
import com.shuyu.video.api.IMainApi;
import com.shuyu.video.model.AppStoreEntity;

public class RecommendFragment extends BaseFragment {

    public RecommendFragment() {

    }


    public static RecommendFragment newInstance() {
        RecommendFragment fragment = new RecommendFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void initData() {

    }


    private void getAppstoreInfo() {
        BaseApi.request(BaseApi.createApi(IMainApi.class).getAppstoreList(1),
                new BaseApi.IResponseListener<AppStoreEntity>() {
                    @Override
                    public void onSuccess(AppStoreEntity data) {

                    }

                    @Override
                    public void onFail() {

                    }
                });
    }

}
