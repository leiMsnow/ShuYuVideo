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

    public static final int VIEW_VIEW_TYPE_DISCLAIMER = 1;
    public static final int VIEW_VIEW_TYPE_PLAY_VIDEO = 2;
    public static final int VIEW_VIEW_TYPE_PAY_URL = 3;

    @Bind(R.id.web_view)
    WebView webView;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initData() {

        switch (getIntent().getIntExtra(Constants.KEY_WEB_VIEW_TYPE, -1)) {
            case VIEW_VIEW_TYPE_DISCLAIMER:
                setTitle("免责声明");
                webView.loadUrl("file:///android_asset/disclaimer.html");
                webView.getSettings().setSupportZoom(false);
                break;
            case VIEW_VIEW_TYPE_PLAY_VIDEO:
                VideoPicDetails contentListBean = (VideoPicDetails)
                        getIntent().getSerializableExtra(VIDEO_DETAIL_ID);

                if (contentListBean == null)
                    return;
                mToolbar.setVisibility(View.GONE);
                webView.loadUrl(contentListBean.getVideoPageUrl());
                break;

            case VIEW_VIEW_TYPE_PAY_URL:
                String payUrl = getIntent().getStringExtra(Constants.KEY_PAY_URL);
                webView.loadUrl(payUrl);
                break;
        }
        setView();
    }

    private void setView() {

        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(false);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
