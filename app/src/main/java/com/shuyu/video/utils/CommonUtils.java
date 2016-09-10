package com.shuyu.video.utils;

import android.content.Context;
import android.content.Intent;

import com.shuyu.core.uils.AppUtils;
import com.shuyu.video.activity.VideoActivity;
import com.shuyu.video.model.VideoDetails;

/**
 * Created by zhangleilei on 9/10/16.
 */

public class CommonUtils {

    public static void goToVideoPage(Context mContext,
                                     VideoDetails mVideoDetails) {
        if (mVideoDetails.getIsPage().equals("1")) {
            AppUtils.openBrowser(mContext, mVideoDetails.getVideoPageUrl());
        } else {
            Intent intent = new Intent(mContext, VideoActivity.class);
            intent.putExtra(Constants.VIDEO_DETAILS, mVideoDetails);
            mContext.startActivity(intent);
        }
    }
}
