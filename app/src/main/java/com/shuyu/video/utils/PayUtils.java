package com.shuyu.video.utils;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.shuyu.core.uils.LogUtils;
import com.shuyu.video.activity.PictureDetailsActivity;
import com.shuyu.video.api.BaseApi;
import com.shuyu.video.api.IPayServiceApi;
import com.shuyu.video.db.helper.AppPayInfoDaoHelper;
import com.shuyu.video.fragment.PayDialogFragment;
import com.shuyu.video.model.AppPayInfo;
import com.shuyu.video.model.UserInfo;

import java.math.BigDecimal;
import java.util.List;

import static com.shuyu.video.api.BaseApi.createApi;

/**
 * Created by Azure on 2016/9/27.
 */
public class PayUtils {


    public interface IPlayerListener {
        void canPlayer(boolean canPlayer);
    }

    public static final int WE_CHAT_PAY = 1;
    public static final int ALI_PAY = 2;

    /**
     * 是否拥有播放权限
     *
     * @param context
     * @return
     */
    public static void canPlayer(final AppCompatActivity context, final int needRule,
                                 final IPlayerListener playerListener) {
        getUserInfo(new BaseApi.IResponseListener<UserInfo>() {
            @Override
            public void onSuccess(UserInfo data) {
                LogUtils.d("getUserInfo", data.toString());
                if (playerListener != null) {
                    playerListener.canPlayer(!showPayDialog(context, data.getUserType(), needRule));
                }
            }

            @Override
            public void onFail() {
                if (playerListener != null)
                    playerListener.canPlayer(false);
            }
        });

    }

    private static boolean showPayDialog(AppCompatActivity context, int userRule, int needRule) {
        if (userRule <= needRule) {
            PayDialogFragment dialogFragment = new PayDialogFragment();
            dialogFragment.show(context.getSupportFragmentManager(), "payDialog");
            return true;
        }
        return false;
    }

    public static void canShowPic(final AppCompatActivity mContext, final int needRule, final int id) {
        getUserInfo(new BaseApi.IResponseListener<UserInfo>() {
            @Override
            public void onSuccess(UserInfo data) {
                if (showPayDialog(mContext, data.getUserType(), needRule)) {
                    return;
                }
                Intent intent = new Intent(mContext, PictureDetailsActivity.class);
                intent.putExtra(Constants.PICTURE_DETAIL_ID, id);
                mContext.startActivity(intent);
            }

            @Override
            public void onFail() {

            }
        });
    }

    public static void getUserInfo(BaseApi.IResponseListener<UserInfo> responseListener) {
        String sign = DataSignUtils.getSign();
        BaseApi.request(createApi(IPayServiceApi.class).getUserInfo(sign), responseListener);
    }


    public static String createOrderNo() {
        return String.valueOf(CommonUtils.getAppId()) +
                System.currentTimeMillis() +
                (int) ((Math.random() * 9 + 1) * 1000000);
    }

    private static AppPayInfo getPayInfo() {
        List<AppPayInfo> appPayInfoList = AppPayInfoDaoHelper.getHelper().getDataAll();
        if (appPayInfoList != null && appPayInfoList.size() > 0) {
            return appPayInfoList.get(0);
        }
        return null;
    }

    public static double getPayRebateMoney(int userRule, boolean isRebate) {
        AppPayInfo appPayInfo = getPayInfo();
        if (appPayInfo == null) {
            return 30.00f;
        }
        double[] moneys;
        if (isRebate && appPayInfo.getRebate() > 0 && appPayInfo.getRebate() < 1) {
            moneys = new double[]{
                    appPayInfo.getMemberPrice() * appPayInfo.getRebate(),
                    appPayInfo.getVipPrice() * appPayInfo.getRebate(),
                    appPayInfo.getSvipPrice() * appPayInfo.getRebate(),
                    appPayInfo.getSvipPrice() * appPayInfo.getRebate()
            };
        } else {
            moneys = new double[]{
                    appPayInfo.getMemberPrice(),
                    appPayInfo.getVipPrice(),
                    appPayInfo.getSvipPrice(),
                    appPayInfo.getSvipPrice()
            };
        }
        BigDecimal b = new BigDecimal(moneys[userRule]);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    public static String getPayMoneyTips(int userRule) {
        String[] tips = new String[]{"注册会员", "升级vip", "升级超级vip", "已经满级"};
        return tips[userRule];
    }

    public static String getPayPoint(int userRule) {
        String[] payPoints = new String[]{"member", "vip", "svip","sipv+"};
        return payPoints[userRule];
    }
}
