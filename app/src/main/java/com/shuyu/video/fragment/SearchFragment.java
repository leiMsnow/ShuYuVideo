package com.shuyu.video.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shuyu.core.BaseFragment;
import com.shuyu.core.api.BaseApi;
import com.shuyu.video.R;
import com.shuyu.video.activity.SearchActivity;
import com.shuyu.video.adapter.SearchContentAdapter;
import com.shuyu.video.api.IMainApi;
import com.shuyu.video.model.SearchVideoData;

import butterknife.Bind;

public class SearchFragment extends BaseFragment {

    @Bind(R.id.rv_container)
    RecyclerView rvContainer;

    private SearchContentAdapter mSearchContentAdapter;


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

        mSearchContentAdapter = new SearchContentAdapter(mContext, null, R.layout.item_search_content);
        rvContainer.setLayoutManager(new GridLayoutManager(mContext, 2));
        rvContainer.setAdapter(mSearchContentAdapter);

    }

    private void searchVideo(String keyword) {
        BaseApi.request(BaseApi.createApi(IMainApi.class).searchVideo(keyword, 1, 6),
                new BaseApi.IResponseListener<SearchVideoData>() {
                    @Override
                    public void onSuccess(SearchVideoData data) {
                        mSearchContentAdapter.replaceAll(data.getChannelContentList());
                    }

                    @Override
                    public void onFail() {

                    }
                });
    }


}
