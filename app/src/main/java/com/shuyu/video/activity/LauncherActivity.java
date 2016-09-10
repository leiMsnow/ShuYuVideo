package com.shuyu.video.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shuyu.core.BaseActivity;
import com.shuyu.core.api.BaseApi;
import com.shuyu.core.uils.SPUtils;
import com.shuyu.video.R;
import com.shuyu.video.api.IMainApi;
import com.shuyu.video.model.RunInfo;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.Bind;

public class LauncherActivity extends BaseActivity {

    private static final int STAY_TIME_FLAG = 0;

    @Bind(R.id.iv_launcher_url)
    ImageView ivLauncherUrl;

    private MyHandler mMyHandler;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_launcher;
    }

    @Override
    protected void initData() {
        mMyHandler = new MyHandler(this);
        getRunInfo();

    }


    private void getRunInfo() {
        BaseApi.request(BaseApi.createApi(IMainApi.class).getRunInfo(),
                new BaseApi.IResponseListener<List<RunInfo>>() {
            @Override
            public void onSuccess(List<RunInfo> data) {
                if (TextUtils.isEmpty(data.get(0).getFirstHost())) {
                    SPUtils.put(mContext, BaseApi.BASE_URL, BaseApi.LOCAL_SERVER_URL);
                }else{
                    SPUtils.put(mContext, BaseApi.BASE_URL, data.get(0).getFirstHost());
                }
                Glide.with(mContext).load(data.get(0).getContentUrl()).into(ivLauncherUrl);
                sendLauncher(data.get(0).getStayTime());
            }

            @Override
            public void onFail() {
                sendLauncher(0);
            }
        });
    }

    private void sendLauncher(int sec) {
        mMyHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = STAY_TIME_FLAG;
                mMyHandler.sendMessage(message);
            }
        }, sec * 1000);
    }


    private static class MyHandler extends Handler {

        private WeakReference<LauncherActivity> activityWeakReference;

        MyHandler(LauncherActivity activity) {
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {

            LauncherActivity activity = activityWeakReference.get();
            if (activity != null) {
                if (msg.what == STAY_TIME_FLAG) {
                    activity.startActivity(new Intent(activity.mContext, MainActivity.class));
                    activity.finish();
                }
            }

        }
    }
}
