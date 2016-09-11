package com.shuyu.video.activity;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.shuyu.video.R;
import com.shuyu.video.model.VideoPicDetails;

import butterknife.Bind;

import static com.shuyu.video.utils.Constants.VIDEO_DETAIL_ID;


public class WebViewActivity extends AppBaseActivity {

    @Bind(R.id.web_view)
    WebView webView;

    private VideoPicDetails mContentListBean;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initData() {
        mContentListBean = (VideoPicDetails)
                getIntent().getSerializableExtra(VIDEO_DETAIL_ID);

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
