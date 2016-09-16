package com.shuyu.video.utils;

import com.shuyu.video.BuildConfig;

/**
 * Created by Azure on 2016/9/8.
 */

public interface Constants {

     String ENVIRONMENT = BuildConfig.ENVIRONMENT;
     boolean IS_DEBUG = BuildConfig.IS_DEBUG;

    String CHANNEL_DETAILS = "CHANNEL_DETAILS";

    String VIDEO_DETAIL_ID = "VIDEO_DETAIL_ID";
    String IS_VIP_VIDEO = "IS_VIP_VIDEO";
    String VIDEO_DETAILS = "VIDEO_DETAILS";

    String PICTURE_DETAIL_ID = "PICTURE_DETAIL_ID";
    String PICTURE_URL = "PICTURE_URL";

    String LAUNCHER_IMG = "LAUNCHER_IMG";

    String BANNEL_TITLE = "BANNEL_TITLE";

    int BANNEL_VIDEO = 1;
    int BANNEL_PICTURE = 2;
    int BANNEL_APP = 3;
    int BANNEL_WAP = 4;

}
