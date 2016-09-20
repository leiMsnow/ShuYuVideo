package com.shuyu.video.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shuyu.core.api.BaseApi;
import com.shuyu.core.uils.SPUtils;
import com.shuyu.video.R;
import com.shuyu.video.api.IServiceApi;
import com.shuyu.video.db.helper.AppInfoHelper;
import com.shuyu.video.model.AppInfoListEntity;
import com.shuyu.video.model.AppStoreEntity;
import com.shuyu.video.model.RunInfo;
import com.shuyu.video.utils.Constants;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

import static com.shuyu.core.api.BaseApi.createApi;

public class SplashActivity extends AppBaseActivity {

    private static final int MESSAGE_GOTO_MAIN = 1;
    private static final int MESSAGE_COUNTDOWN = 0;

    @Bind(R.id.iv_launcher_url)
    ImageView ivLauncherUrl;
    @Bind(R.id.tv_countdown)
    TextView tvCountdown;

    private MyHandler mMyHandler;
    private Timer mTimer;
    private int mSecond = 3;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected boolean hasToolbar() {
        return false;
    }

    @Override
    protected void initData() {
        mMyHandler = new MyHandler(this);
        Glide.with(mContext).load(SPUtils.get(mContext, Constants.LAUNCHER_IMG, "")).into(ivLauncherUrl);
        getRunInfo();
        getAppStoreInfo();
    }

    private void getRunInfo() {
        BaseApi.request(BaseApi.createApi(IServiceApi.class).getRunInfo(),
                new BaseApi.IResponseListener<List<RunInfo>>() {
                    @Override
                    public void onSuccess(List<RunInfo> data) {
                        if (TextUtils.isEmpty(data.get(0).getFirstHost())) {
                            SPUtils.put(mContext, BaseApi.BASE_URL, BaseApi.LOCAL_SERVER_URL);
                        } else {
                            SPUtils.put(mContext, BaseApi.BASE_URL, data.get(0).getFirstHost());
                        }
                        SPUtils.put(mContext, Constants.LAUNCHER_IMG, data.get(0).getContentUrl());
                        Glide.with(mContext).load(data.get(0).getContentUrl()).into(ivLauncherUrl);
                        mSecond = data.get(0).getStayTime();
                        startCountdown();
                    }

                    @Override
                    public void onFail() {
                        startCountdown();
                    }
                });
    }


    private void startCountdown() {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mSecond < 0) {
                    Message message = new Message();
                    message.what = MESSAGE_GOTO_MAIN;
                    mMyHandler.sendMessage(message);
                    return;
                }
                Message message = new Message();
                message.what = MESSAGE_COUNTDOWN;
                message.arg1 = mSecond;
                mMyHandler.sendMessage(message);
                mSecond--;
            }
        }, 1000, 1000);
    }


    private static class MyHandler extends Handler {

        private WeakReference<SplashActivity> activityWeakReference;

        MyHandler(SplashActivity activity) {
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {

            SplashActivity activity = activityWeakReference.get();
            if (activity != null) {
                if (msg.what == MESSAGE_GOTO_MAIN) {
                    activity.startActivity(new Intent(activity.mContext, MainActivity.class));
                    activity.finish();
                } else if (msg.what == MESSAGE_COUNTDOWN) {
                    activity.tvCountdown.setVisibility(View.VISIBLE);
                    activity.tvCountdown.setText(msg.arg1 + "s");
                }
            }

        }
    }

    private void getAppStoreInfo() {
        BaseApi.request(createApi(IServiceApi.class).getAppStoreList(1),
                new BaseApi.IResponseListener<AppStoreEntity>() {
                    @Override
                    public void onSuccess(final AppStoreEntity data) {
                        AppInfoHelper.getHelper().deleteAll();
                        for (AppInfoListEntity entity : data.getAppInfoList()) {
                            AppInfoHelper.getHelper().addData(entity);
                        }
                    }

                    @Override
                    public void onFail() {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }
}
