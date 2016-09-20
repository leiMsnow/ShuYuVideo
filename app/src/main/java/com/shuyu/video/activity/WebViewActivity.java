package com.shuyu.video.activity;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.shuyu.video.R;
import com.shuyu.video.model.VideoPicDetails;
import com.shuyu.video.utils.Constants;

import butterknife.Bind;

import static com.shuyu.video.utils.Constants.VIDEO_DETAIL_ID;


public class WebViewActivity extends AppBaseActivity {

    @Bind(R.id.web_view)
    WebView webView;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initData() {

        if (getIntent().getBooleanExtra(Constants.DISCLAIMER, false)) {
            setTitle("免责声明");
            webView.loadUrl("file:///android_asset/disclaimer.html");
            webView.getSettings().setSupportZoom(false);
        } else {
            VideoPicDetails contentListBean = (VideoPicDetails)
                    getIntent().getSerializableExtra(VIDEO_DETAIL_ID);

            if (contentListBean == null)
                return;
            mToolbar.setVisibility(View.GONE);
            webView.loadUrl(contentListBean.getVideoPageUrl());
        }
        setView();
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void setView() {

        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(false);
    }
}
