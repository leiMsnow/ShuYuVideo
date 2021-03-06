package com.shuyu.video.activity;

import android.app.ActivityManager;
import android.content.Context;

import com.shuyu.core.BaseActivity;
import com.shuyu.core.uils.LogUtils;
import com.shuyu.core.uils.SPUtils;
import com.shuyu.video.api.BaseApi;
import com.shuyu.video.api.ILocalServiceApi;
import com.shuyu.video.model.ResultEntity;
import com.shuyu.video.utils.Constants;

import java.util.List;

/**
 * Created by zhangleilei on 9/10/16.
 */

public abstract class AppBaseActivity extends BaseActivity {

    @Override
    protected boolean hasToolbar() {
        return true;
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (isBackground(this)) {
            stayTime();
        }
    }

    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                return appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
            }
        }
        return false;
    }


    private void stayTime() {
        long onTime = (long) SPUtils.get(mContext, Constants.STAY_TIME_ON, System.currentTimeMillis());
        long offTime = System.currentTimeMillis();

        BaseApi.request(BaseApi.createApi(ILocalServiceApi.class)
                .stayTime(onTime, offTime, "tg1001"),
                new BaseApi.IResponseListener<ResultEntity>() {
                    @Override
                    public void onSuccess(ResultEntity data) {
                        LogUtils.d(AppBaseActivity.class.getName(), "resultCode: " + data.getResultMessage());
                    }

                    @Override
                    public void onFail() {

                    }
                });
    }
}
