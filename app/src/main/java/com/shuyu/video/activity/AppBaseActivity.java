package com.shuyu.video.activity;

import android.os.Bundle;

import com.shuyu.core.BaseActivity;
import com.shuyu.core.uils.AppUtils;
import com.shuyu.core.uils.LogUtils;
import com.shuyu.core.uils.SPUtils;
import com.shuyu.video.R;
import com.shuyu.video.api.BaseApi;
import com.shuyu.video.api.ILocalServiceApi;
import com.shuyu.video.model.ResultEntity;
import com.shuyu.video.model.UserInfo;
import com.shuyu.video.utils.Constants;
import com.shuyu.video.utils.PayUtils;

/**
 * Created by zhangleilei on 9/10/16.
 */

public abstract class AppBaseActivity extends BaseActivity {
    private long currentTime = 0;
    @Override
    protected boolean hasToolbar() {
        return true;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        if (mToolbar != null) {
            mToolbar.setBackgroundResource(R.color.toolbar_main_color);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (AppUtils.isBackground(this)) {
            stayTime();
        }
    }

    @Override
    public void onBackPressed() {
        if (mContext instanceof MainActivity  && System.currentTimeMillis() - currentTime >= 10000) {
            PayUtils.getUserInfo(new BaseApi.IResponseListener<UserInfo>() {
                @Override
                public void onSuccess(UserInfo data) {
                    if (data.getUserType() == 0) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constants.KEY_PAY_DIALOG,R.mipmap.bg_pay_dialog_spree);
                        PayUtils.showPayDialog(mContext,bundle);
                    }else{
                        AppBaseActivity.super.onBackPressed();
                    }
                }

                @Override
                public void onFail() {
                    AppBaseActivity.super.onBackPressed();
                }
            });
            currentTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
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
