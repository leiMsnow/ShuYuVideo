package com.shuyu.video.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shuyu.core.BaseFragment;
import com.shuyu.core.api.BaseApi;
import com.shuyu.video.R;
import com.shuyu.video.activity.SearchActivity;
import com.shuyu.video.adapter.ChannelContentAdapter;
import com.shuyu.video.api.ILocalServiceApi;
import com.shuyu.video.model.SearchVideoList;

import butterknife.Bind;

public class SearchFragment extends BaseFragment {

    @Bind(R.id.rv_container)
    RecyclerView rvContainer;
    @Bind(R.id.tv_total)
    TextView tvTotal;
    @Bind(R.id.tv_empty)
    TextView tvEmpty;

    private ChannelContentAdapter mSearchContentAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SearchActivity) {
            ((SearchActivity) context).setSearchListener(new SearchActivity.ISearchListener() {
                @Override
                public void onSearch(String keyword) {
                    mSearchContentAdapter.clear();
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

    private void searchVideo(final String keyword) {
        tvTotal.setVisibility(View.GONE);
        tvEmpty.setVisibility(View.GONE);
        BaseApi.request(BaseApi.createApi(ILocalServiceApi.class).searchVideo(keyword, 1, 20),
                new BaseApi.IResponseListener<SearchVideoList>() {
                    @Override
                    public void onSuccess(SearchVideoList data) {
                        if (data.getTotalItemCount() == 0) {
                            setTvEmpty(keyword);
                            return;
                        }
                        tvTotal.setVisibility(View.VISIBLE);
                        tvTotal.setText(String.format("共为您搜索出%d部视频", data.getTotalItemCount()));
                        mSearchContentAdapter.replaceAllData(data.getChannelContentList());
                    }

                    @Override
                    public void onFail() {
                        setTvEmpty(keyword);
                    }
                });
    }

    private void setTvEmpty(String keyword) {
        tvEmpty.setText("没有找到和" + keyword + "相关的视频,请及时关注后续更新");
        tvEmpty.setVisibility(View.VISIBLE);
    }
}
