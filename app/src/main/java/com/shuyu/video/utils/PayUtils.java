package com.shuyu.video.utils;

import android.app.Activity;
import android.content.Context;

import com.shuyu.core.uils.SPUtils;
import com.shuyu.video.db.helper.AppPayInfoDaoHelper;
import com.shuyu.video.fragment.PayDialogFragment;
import com.shuyu.video.model.AppPayInfo;

import java.util.List;

/**
 * Created by Azure on 2016/9/27.
 */
public class PayUtils {

    public static final int WE_CHAT_PAY = 1;
    public static final int ALI_PAY = 2;

    /**
     * 是否拥有播放权限
     *
     * @param context
     * @param rule
     * @return
     */
    public static boolean canPlayer(Activity context, int rule) {
        if (rule <= (int) SPUtils.get(context, Constants.KEY_USER_RULE, 0))
            return true;

//        PayDialogView.Builder payBuilder = new PayDialogView.Builder(context);
//        payBuilder.show();
        PayDialogFragment payDialogFragment = new PayDialogFragment();
        payDialogFragment.showDialog();
        return false;
    }


    public static String createOrderNo() {
        return String.valueOf(CommonUtils.getAppId()) +
                System.currentTimeMillis() +
                (int) ((Math.random() * 9 + 1) * 1000000);
    }

    public static AppPayInfo getPayInfo() {
        List<AppPayInfo> appPayInfoList = AppPayInfoDaoHelper.getHelper().getDataAll();
        if (appPayInfoList != null && appPayInfoList.size() > 0) {
            return appPayInfoList.get(0);
        }
        return null;
    }

    public static double[] getPayMoney(Context context) {
        AppPayInfo appPayInfo = getPayInfo();
        if (appPayInfo == null) {
            return new double[]{30, 25};
        }
        int userRule = (int) SPUtils.get(context, Constants.KEY_USER_RULE, 0);
        double[][] moneys = new double[][]{

                new double[]{appPayInfo.getMemberPrice(),
                        appPayInfo.getMemberPrice() * appPayInfo.getRebate()},

                new double[]{appPayInfo.getVipPrice(),
                        appPayInfo.getVipPrice() * appPayInfo.getRebate()},

                new double[]{appPayInfo.getSvipPrice(),
                        appPayInfo.getSvipPrice() * appPayInfo.getRebate()}
        };
        return moneys[userRule];
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
