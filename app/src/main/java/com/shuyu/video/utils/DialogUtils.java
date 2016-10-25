package com.shuyu.video.utils;

import android.content.Context;

import com.shuyu.core.uils.SPUtils;
import com.shuyu.video.db.helper.AppPayInfoDaoHelper;
import com.shuyu.video.model.AppPayInfo;
import com.shuyu.video.widget.PayDialogView;

import java.util.List;

/**
 * Created by Azure on 2016/9/27.
 */
public class DialogUtils {

    /**
     * 是否拥有播放权限
     *
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


    public static String createOrderNo() {
        return String.valueOf(CommonUtils.getAppId()) +
                System.currentTimeMillis() +
                (int) ((Math.random() * 9 + 1) * 1000000);
    }

    public static int getPayMoney(Context context) {
        int payMoney = 30;
        List<AppPayInfo> appPayInfoList = AppPayInfoDaoHelper.getHelper().getDataAll();
        if (appPayInfoList != null && appPayInfoList.size() > 0) {
            AppPayInfo appPayInfo = appPayInfoList.get(0);
            int userRule = (int) SPUtils.get(context, Constants.KEY_USER_RULE, 0);
            int[] moneys = new int[]{appPayInfo.getMemberPrice(),
                    appPayInfo.getVipPrice(),
                    appPayInfo.getSvipPrice()};
            return moneys[userRule];
        }
        return payMoney;
    }

    public static String getPayMoneyTips(Context context) {
        String[] tips = new String[]{"注册会员", "升级vip", "升级超级vip"};
        int userRule = (int) SPUtils.get(context, Constants.KEY_USER_RULE, 0);
        return tips[userRule];
    }

    public static String getPayPoint(Context context) {
        int userRule = (int) SPUtils.get(context, Constants.KEY_USER_RULE, 0);
        String[] payPoints = new String[]{"member", "vip", "svip"};
        return payPoints[userRule];
    }
}
