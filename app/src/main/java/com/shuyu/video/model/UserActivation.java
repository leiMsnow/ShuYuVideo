package com.shuyu.video.model;

import android.os.Build;

import com.shuyu.core.model.CommParams;
import com.shuyu.core.uils.AppUtils;
import com.shuyu.core.uils.ScreenUtils;

/**
 * Created by zhangleilei on 9/21/16.
 */

public class UserActivation extends CommParams {

    private String sign;
    private int platformType = 1;
    private String smsNum;
    private int screenWidth = ScreenUtils.getScreenWidth();
    private int screenHeight = ScreenUtils.getScreenHeight();
    private String sdkVersion = String.valueOf(Build.VERSION.SDK_INT);
    private String cpuVersion = AppUtils.getCPUInfo();
    private String appVersion = String.valueOf(AppUtils.getAppVersion());
    private String thirdChannelId = "0";
    private int activateFlag = 0;

    public void setSign(String sign) {
        this.sign = sign;
    }
}
