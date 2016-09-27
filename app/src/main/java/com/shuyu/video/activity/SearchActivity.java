package com.shuyu.video.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuyu.core.api.BaseApi;
import com.shuyu.video.R;
import com.shuyu.video.adapter.ChannelContentAdapter;
import com.shuyu.video.adapter.HotWordAdapter;
import com.shuyu.video.api.ILocalServiceApi;
import com.shuyu.video.fragment.SearchFragment;
import com.shuyu.video.model.HotWord;
import com.shuyu.video.model.SearchVideoData;

import butterknife.Bind;

public class SearchActivity extends AppBaseActivity {

    @Bind(R.id.rv_hot_word)
    RecyclerView rvHotWord;
    @Bind(R.id.rv_hot_video)
    RecyclerView rvHotVideo;
    @Bind(R.id.in_hot_tags)
    View hotTags;
    @Bind(R.id.in_hot_video)
    View hotVideo;
    @Bind(R.id.fl_container)
    View mContainer;

    private HotWordAdapter mHotWordAdapter;
    private ChannelContentAdapter mSearchContentAdapter;
    private ISearchListener mSearchListener;

    public void setSearchListener(ISearchListener searchListener) {
        mSearchListener = searchListener;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_search;
    }

    @Override
    protected void initData() {

        ((TextView) hotTags.findViewById(R.id.tv_title)).setText("热搜标签");
        ImageView ivTags = (ImageView) hotVideo.findViewById(R.id.iv_channel_tags);
        ivTags.setImageResource(R.mipmap.ic_channel_tv_tags);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, SearchFragment.newInstance())
                .commit();

        mHotWordAdapter = new HotWordAdapter(mContext, null, R.layout.item_hot_word);
        rvHotWord.setLayoutManager(new GridLayoutManager(mContext, 4));
        rvHotWord.setAdapter(mHotWordAdapter);
        mHotWordAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchResult(v.getTag().toString());
            }
        });

        mSearchContentAdapter = new ChannelContentAdapter(mContext, null, R.layout.item_channel_content);
        rvHotVideo.setLayoutManager(new GridLayoutManager(mContext, 2));
        rvHotVideo.setAdapter(mSearchContentAdapter);
        getHotWord();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        initSearchView(searchView);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    private void openSearchResult(String query) {
        if (mSearchListener != null) {
            mContainer.setVisibility(View.VISIBLE);
            mSearchListener.onSearch(query);
        }
    }

    private void initSearchView(SearchView searchView) {
        searchView.setQueryHint(getResources().getString(R.string.search));
        View view = searchView.findViewById(R.id.search_src_text);
        view.setBackgroundResource(R.drawable.shape_search_bg);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)) {
                    openSearchResult(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void getHotWord() {
        BaseApi.request(BaseApi.createApi(ILocalServiceApi.class).getHotWordList(1, 16),
                new BaseApi.IResponseListener<HotWord>() {
                    @Override
                    public void onSuccess(HotWord data) {
                        hotTags.setVisibility(View.VISIBLE);
                        mHotWordAdapter.replaceAll(data.getWords());
                        searchVideo(data.getWords().get(0));
                    }

                    @Override
                    public void onFail() {

                    }
                });
    }

    private void searchVideo(String keyword) {
        BaseApi.request(BaseApi.createApi(ILocalServiceApi.class).searchVideo(keyword, 1, 4),
                new BaseApi.IResponseListener<SearchVideoData>() {
                    @Override
                    public void onSuccess(SearchVideoData data) {
                        hotVideo.setVisibility(View.VISIBLE);
                        mSearchContentAdapter.replaceAllData(data.getChannelContentList());
                    }

                    @Override
                    public void onFail() {

                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (mContainer.getVisibility() == View.VISIBLE) {
            mContainer.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }

    public interface ISearchListener {
        void onSearch(String keyword);
    }
}
