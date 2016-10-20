package com.shuyu.video.utils;

import android.content.Context;

import com.shuyu.core.uils.SPUtils;
import com.shuyu.video.widget.PayDialogView;

/**
 *
 * Created by Azure on 2016/9/27.
 */
public class DialogUtils {

    /**
     * 是否拥有播放权限
     * @param context
     * @param rule
     * @return
     */
    public static boolean canPlayer(Context context, int rule) {
        if (rule <= (int) SPUtils.get(context, Constants.KEY_USER_RULE, 0))
            return true;

        PayDialogView.Builder payBuilder = new PayDialogView.Builder(context);
        payBuilder.show();
        return false;
    }
}
