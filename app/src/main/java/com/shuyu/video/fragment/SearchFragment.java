package com.shuyu.video.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shuyu.core.BaseFragment;
import com.shuyu.core.api.BaseApi;
import com.shuyu.video.R;
import com.shuyu.video.activity.SearchActivity;
import com.shuyu.video.adapter.ChannelContentAdapter;
import com.shuyu.video.api.IServiceApi;
import com.shuyu.video.model.SearchVideoData;

import butterknife.Bind;

public class SearchFragment extends BaseFragment {

    @Bind(R.id.rv_container)
    RecyclerView rvContainer;

    private ChannelContentAdapter mSearchContentAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SearchActivity) {
            ((SearchActivity) context).setSearchListener(new SearchActivity.ISearchListener() {
                @Override
                public void onSearch(String keyword) {
                    searchVideo(keyword);
                }
            });
        }

    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initData() {

        mSearchContentAdapter = new ChannelContentAdapter(mContext, null, R.layout.item_channel_content);
        rvContainer.setLayoutManager(new GridLayoutManager(mContext, 2));
        rvContainer.setAdapter(mSearchContentAdapter);

    }

    private void searchVideo(String keyword) {
        BaseApi.request(BaseApi.createApi(IServiceApi.class).searchVideo(keyword, 1, 6),
                new BaseApi.IResponseListener<SearchVideoData>() {
                    @Override
                    public void onSuccess(SearchVideoData data) {
                        mSearchContentAdapter.replaceAllData(data.getChannelContentList());
                    }

                    @Override
                    public void onFail() {

                    }
                });
    }


}
