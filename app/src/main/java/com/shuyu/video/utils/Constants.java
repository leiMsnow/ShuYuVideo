package com.shuyu.video.utils;

import com.shuyu.video.BuildConfig;

/**
 * Created by Azure on 2016/9/8.
 */

public interface Constants {

    String IS_ACTIVATION = "IS_ACTIVATION";

    boolean IS_DEBUG = BuildConfig.IS_DEBUG;

    String CHANNEL_DETAILS = "CHANNEL_DETAILS";

    String VIDEO_DETAIL_ID = "VIDEO_DETAIL_ID";
    String IS_VIP_VIDEO = "IS_VIP_VIDEO";
    String VIDEO_DETAILS = "VIDEO_DETAILS";

    String PICTURE_DETAIL_ID = "PICTURE_DETAIL_ID";
    String PICTURE_URL = "PICTURE_URL";

    String LAUNCHER_IMG = "LAUNCHER_IMG";

    String KEY_WEB_VIEW_TYPE = "KEY_WEB_VIEW_TYPE";

    String BANNER_TITLE = "BANNER_TITLE";
    int BANNER_VIDEO = 1;
    int BANNER_PICTURE = 2;
    int BANNER_APP = 3;
    int BANNER_WAP = 4;

    String STAY_TIME_ON = "STAY_TIME_ON";
    String KEY_ORDER_INFO = "KEY_ORDER_INFO";
    String KEY_PAY_DIALOG = "KEY_PAY_DIALOG";

    String KEY_PAY_URL = "KEY_PAY_URL";
    String USER_UUID = "USER_UUID";
}
