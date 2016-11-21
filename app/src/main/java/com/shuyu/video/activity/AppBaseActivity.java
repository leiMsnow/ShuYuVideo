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
import com.shuyu.video.utils.CommonUtils;
import com.shuyu.video.utils.Constants;
import com.shuyu.video.utils.PayUtils;

/**
 * Created by zhangleilei on 9/10/16.
 */
public abstract class AppBaseActivity extends BaseActivity {
    private long currentTime = 0;
//    private ImageView imageView;

    private boolean needShowRed = false;

    public void setNeedShowRed(boolean needShowRed) {
        this.needShowRed = needShowRed;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!needShowRed)
            return;

//        PayUtils.getUserInfo(new BaseApi.IResponseListener<UserInfo>() {
//            @Override
//            public void onSuccess(UserInfo data) {
//                if (data.getUserType() == 0 && !TextUtils.isEmpty(CommonUtils.getIMSI())) {
//                    if (imageView == null) {
//                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
//                                DensityUtils.dp2px(mContext, 56),
//                                DensityUtils.dp2px(mContext, 56));
//                        int marginBottom = mContext.getResources().getDisplayMetrics().heightPixels;
//                        layoutParams.gravity = Gravity.RIGHT | Gravity.END | Gravity.BOTTOM;
//                        layoutParams.setMargins(0, 0, 0, marginBottom / 6);
//                        imageView = new ImageView(mContext);
//                        imageView.setImageResource(R.mipmap.bg_luck_money);
//                        addContentView(imageView, layoutParams);
//                        imageView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                PayUtils.showGiftPayDialog(mContext);
//                                imageView.setVisibility(View.GONE);
//                            }
//                        });
//                    } else {
//                        imageView.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    if (imageView != null) {
//                        imageView.setVisibility(View.GONE);
//                    }
//                }
//            }
//
//            @Override
//            public void onFail() {
//                if (imageView != null) {
//                    imageView.setVisibility(View.GONE);
//                }
//            }
//        });
    }

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
        if (mContext instanceof MainActivity && System.currentTimeMillis() - currentTime >= 10000) {
            PayUtils.getUserInfo(new BaseApi.IResponseListener<UserInfo>() {
                @Override
                public void onSuccess(UserInfo data) {
                    if (data.getUserType() == 0) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constants.KEY_PAY_DIALOG, R.mipmap.bg_pay_dialog_spree);
                        PayUtils.showPayDialog(mContext, bundle);
                    } else {
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
                        .stayTime(onTime, offTime, CommonUtils.getDitchNo()),
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
