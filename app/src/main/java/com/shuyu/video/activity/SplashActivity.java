package com.shuyu.video.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shuyu.core.uils.LogUtils;
import com.shuyu.core.uils.SPUtils;
import com.shuyu.video.R;
import com.shuyu.video.api.BaseApi;
import com.shuyu.video.api.ILocalServiceApi;
import com.shuyu.video.api.IPayServiceApi;
import com.shuyu.video.db.helper.AppPayInfoDaoHelper;
import com.shuyu.video.db.helper.AppStoreDaoHelper;
import com.shuyu.video.db.helper.PaymentDaoHelper;
import com.shuyu.video.model.AppPayInfo;
import com.shuyu.video.model.AppStore;
import com.shuyu.video.model.AppStoreList;
import com.shuyu.video.model.Payment;
import com.shuyu.video.model.ResultEntity;
import com.shuyu.video.model.RunInfo;
import com.shuyu.video.model.UserInfo;
import com.shuyu.video.utils.CommonUtils;
import com.shuyu.video.utils.Constants;
import com.shuyu.video.utils.DataSignUtils;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

import static com.shuyu.video.api.BaseApi.createApi;

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
        Glide.with(mContext).load(SPUtils.get(mContext, Constants.LAUNCHER_IMG, ""))
                .placeholder(R.mipmap.bg_splash).into(ivLauncherUrl);
        getAppStoreInfo();
        userVisitOrActivation();
        getUserInfo();
        getAppInfo();
        selectPayment();
        getRunInfo();
    }

    private void getRunInfo() {
        BaseApi.request(createApi(ILocalServiceApi.class).getRunInfo(),
                new BaseApi.IResponseListener<List<RunInfo>>() {
                    @Override
                    public void onSuccess(List<RunInfo> data) {
                        if (TextUtils.isEmpty(data.get(0).getFirstHost())) {
                            SPUtils.put(mContext, BaseApi.KEY_BASE_URL, BaseApi.BASE_URL);
                        } else {
                            SPUtils.put(mContext, BaseApi.KEY_BASE_URL, data.get(0).getFirstHost());
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
                if (mSecond <= 0) {
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
        BaseApi.request(BaseApi.createApi(ILocalServiceApi.class).getAppStoreList(1),
                new BaseApi.IResponseListener<AppStoreList>() {
                    @Override
                    public void onSuccess(final AppStoreList data) {
                        AppStoreDaoHelper.getHelper().deleteAll();
                        for (AppStore entity : data.getAppInfoList()) {
                            AppStoreDaoHelper.getHelper().addData(entity);
                        }
                    }

                    @Override
                    public void onFail() {

                    }
                });
    }

    private void userVisitOrActivation() {
        String encrypt = CommonUtils.parseMap(DataSignUtils.getEncryptParams());
        String data = DataSignUtils.encryptData(encrypt);
        if (!TextUtils.isEmpty(data)) {
            if (!(Boolean) SPUtils.get(mContext, Constants.IS_ACTIVATION, false)) {
                userActivation(data);
            }
            userVisit(data);
        }
    }

    private void userActivation(String data) {
        if ((Boolean) SPUtils.get(mContext, Constants.IS_ACTIVATION, false)) return;

        BaseApi.request(createApi(ILocalServiceApi.class)
                .userActivation(data, "0"), new BaseApi.IResponseListener<ResultEntity>() {
            @Override
            public void onSuccess(ResultEntity data) {
                LogUtils.d(SplashActivity.class.getName(), data.getResultMessage());
                if (data.getResultCode().equals("0000") ||
                        data.getResultCode().equals("0005")) {
                    SPUtils.put(mContext, Constants.IS_ACTIVATION, true);
                }
            }

            @Override
            public void onFail() {

            }
        });
    }

    private void getUserInfo() {
        String sign = DataSignUtils.getSign();
        BaseApi.request(createApi(IPayServiceApi.class).getUserInfo(sign),
                new BaseApi.IResponseListener<UserInfo>() {
                    @Override
                    public void onSuccess(UserInfo data) {
                        SPUtils.put(mContext, Constants.KEY_USER_RULE, data.getUserType());
                    }

                    @Override
                    public void onFail() {

                    }
                });
    }

    private void userVisit(String data) {
        BaseApi.request(createApi(ILocalServiceApi.class)
                .userVisit(data, "0"), null);
    }

    private void getAppInfo() {
        BaseApi.request(createApi(IPayServiceApi.class).getAppPayInfo(),
                new BaseApi.IResponseListener<AppPayInfo>() {
                    @Override
                    public void onSuccess(AppPayInfo data) {
                        AppPayInfoDaoHelper.getHelper().addData(data);
                    }

                    @Override
                    public void onFail() {
                        LogUtils.e("SplashActivity","getAppInfo-Error");
                    }
                });
    }

    private void selectPayment() {
        BaseApi.request(createApi(IPayServiceApi.class).selectPayment(),
                new BaseApi.IResponseListener<List<Payment>>() {
                    @Override
                    public void onSuccess(List<Payment> data) {
                        PaymentDaoHelper.getHelper().addDataAll(data);
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
