package com.shuyu.video.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.shuyu.core.uils.ToastUtils;
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
    @Bind(R.id.pb_progress)
    ProgressBar mPbProgress;
    @Bind(R.id.btn_open_wechat)
    Button mBtnOpenWechat;
    @Bind(R.id.ll_wechat_pay)
    LinearLayout mLlWechatPay;

    private boolean isWeChatPay = false;

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
                break;
            case VIEW_VIEW_TYPE_PLAY_VIDEO:
                VideoPicDetails contentListBean = (VideoPicDetails)
                        getIntent().getSerializableExtra(VIDEO_DETAIL_ID);
                if (contentListBean == null)
                    return;
                toolbarHide();
                webView.loadUrl(contentListBean.getVideoPageUrl());
                break;
            case VIEW_VIEW_TYPE_PAY_URL:
                toolbarHide();
                String payUrl = getIntent().getStringExtra(Constants.KEY_PAY_URL);
                webView.loadUrl(payUrl);
                break;
        }
        setView();
    }

    private void setView() {

        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(false);
        webView.getSettings().setSupportZoom(false);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("banktype=WEIXIN")) {
                    isWeChatPay = true;
                } else if (url.equals("http://b.zhiliaofu.com/")) {
                    finish();
                    return true;
                }
                view.loadUrl(url);
                return true;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mPbProgress.setVisibility(View.GONE);
                    if (isWeChatPay)
                        mLlWechatPay.setVisibility(View.VISIBLE);
                } else {
                    mPbProgress.setVisibility(View.VISIBLE);
                    mPbProgress.setProgress(newProgress);
                }
            }
        });

        mBtnOpenWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    ComponentName cmp = new ComponentName(" com.tencent.mm ", "com.tencent.mm.ui.LauncherUI");
                    intent.setAction(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setComponent(cmp);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast("打开微信失败，请手动尝试");
                }
            }
        });
    }
}
