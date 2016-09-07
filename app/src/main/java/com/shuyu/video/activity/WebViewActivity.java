package com.shuyu.video.activity;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.shuyu.core.BaseActivity;
import com.shuyu.video.R;
import com.shuyu.video.model.ChannelContent;

import butterknife.Bind;

import static com.shuyu.video.activity.VideoActivity.VIDEO_CONTENT;

public class WebViewActivity extends BaseActivity {

    @Bind(R.id.web_view)
    WebView webView;

    private ChannelContent.VideoChannelListBean.ChannelContentListBean mContentListBean;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initData() {
        mContentListBean = (ChannelContent.VideoChannelListBean.ChannelContentListBean)
                getIntent().getSerializableExtra(VIDEO_CONTENT);

        if (mContentListBean == null)
            return;

        setTitle(mContentListBean.getTitle());
        webView.loadUrl(mContentListBean.getVideoPageUrl());

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
