package com.shuyu.video.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.shuyu.core.uils.LogUtils;
import com.shuyu.video.BuildConfig;
import com.shuyu.video.R;
import com.shuyu.video.activity.PictureDetailsActivity;
import com.shuyu.video.api.BaseApi;
import com.shuyu.video.api.IPayServiceApi;
import com.shuyu.video.db.helper.AppPayInfoDaoHelper;
import com.shuyu.video.fragment.ADSDialogFragment;
import com.shuyu.video.fragment.PayDialogFragment;
import com.shuyu.video.model.AppPayInfo;
import com.shuyu.video.model.Payment;
import com.shuyu.video.model.UserInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    public static final int ADS_PAY = 3;

    /**
     * 是否拥有播放权限
     *
     * @param context
     * @return
     */
    public static void isShowPayDialog(final Context context, final int needRule,
                                       final IPlayerListener playerListener) {
        getUserInfo(new BaseApi.IResponseListener<UserInfo>() {
            @Override
            public void onSuccess(UserInfo data) {
                LogUtils.d("getUserInfo", data.toString());
                boolean canPlay = !showPayDialog(context, data.getUserType(), needRule);
                if (playerListener != null) {
                    playerListener.canPlayer(canPlay);
                }
            }

            @Override
            public void onFail() {
                if (playerListener != null)
                    playerListener.canPlayer(false);
            }
        });

    }

    public static void canPlay(final int needRule, final IPlayerListener playerListener) {
        getUserInfo(new BaseApi.IResponseListener<UserInfo>() {
            @Override
            public void onSuccess(UserInfo data) {
                boolean canPlay = ((data.getUserType() >= needRule) || (needRule < 2));
                if (playerListener != null) {
                    playerListener.canPlayer(canPlay);
                }
            }

            @Override
            public void onFail() {
                if (playerListener != null)
                    playerListener.canPlayer(false);
            }
        });
    }

    private static boolean showPayDialog(Context context, int userRule, int needRule) {
        if ((userRule < needRule) || (userRule == 0 && needRule == 0)) {
            showPayDialog(context);
            return true;
        }

        return false;
    }

    public static void showGiftPayDialog(final Context context) {
        if (!TextUtils.isEmpty(CommonUtils.getIMSI())) {
            BaseApi.request(createApi(IPayServiceApi.class).selectPayment(),
                    new BaseApi.IResponseListener<List<Payment>>() {
                        @Override
                        public void onSuccess(List<Payment> mPayments) {
                            if (mPayments != null) {
                                ArrayList<Payment> payments = new ArrayList<>();
                                for (Payment payment : mPayments) {
                                    if (payment.getPayType() == PayUtils.ADS_PAY) {
                                        payments.add(payment);
                                    }
                                }
                                showADSPayDialog(context, payments);
                            }
                        }

                        @Override
                        public void onFail() {

                        }
                    });
        }
    }

    private static void showADSPayDialog(Context context, ArrayList<Payment> payments) {
        ADSDialogFragment adsDialogFragment = new ADSDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("PaymentList", payments);
        adsDialogFragment.setArguments(bundle);
        adsDialogFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "ADSDialog");
    }

    public static void showPayDialog(Context context) {
        showPayDialog(context, null);
    }

    private static long currentTime = 0;

    public static void showPayDialog(Context context, Bundle bundle) {
        PayDialogFragment dialogFragment = new PayDialogFragment();
        if (bundle != null)
            dialogFragment.setArguments(bundle);
        if (System.currentTimeMillis() - currentTime > 1000) {
            dialogFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "PayDialog");
            currentTime = System.currentTimeMillis();
        }
    }

    public static void canShowPic(final Context mContext, final int needRule, final int id) {
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

    public static double getPayRebateMoney(int userRule, boolean isRebate, boolean isSpree) {
        AppPayInfo appPayInfo = getPayInfo();
        if (appPayInfo == null) {
            return 30.00f;
        }
        double[] moneys;
        if (isSpree) {
            moneys = new double[]{appPayInfo.getSpreePrice(),
                    appPayInfo.getSpreePrice()};
        } else {
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
        }
        BigDecimal b = new BigDecimal(moneys[userRule]);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static String getPayMoneyTips(int userRule, boolean isSpree) {
        if (isSpree) return "会员大礼包";
        String[] tips = new String[]{"注册会员", "升级vip", "升级超级vip", "升级超级vip"};
        return tips[userRule];
    }

    public static String getPayPoint(int userRule, boolean isSpree) {
        if (isSpree) return "spree";
        String[] payPoints = new String[]{"member", "vip", "svip", "sivp"};
        return payPoints[userRule];
    }

    public static int getPayDialogBG(int userRule) {

        if (BuildConfig.ditchNo.equals("normal")) {
            return 0;
        }

        int[] vipIcon = new int[]{R.mipmap.bg_pay_dialog_member,
                R.mipmap.bg_pay_dialog_vip,
                R.mipmap.bg_pay_dialog_vip,
                R.mipmap.bg_pay_dialog_vip};
        return vipIcon[userRule];
    }

    public static int getVipIcon(int userRule) {
        int[] vipIcon = new int[]{0, 0, R.mipmap.ic_vip_flag, R.mipmap.ic_svip_flag};
        return vipIcon[userRule];
    }
}
