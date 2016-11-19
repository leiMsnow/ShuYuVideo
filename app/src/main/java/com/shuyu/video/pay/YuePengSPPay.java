package com.shuyu.video.pay;

import com.lp.sdk.yninterface.PayListener;
import com.lp.sdk.yninterface.YNInterface;
import com.shuyu.core.uils.LogUtils;
import com.shuyu.video.model.OrderInfo;

/**
 * Created by Azure on 2016/11/7.
 */

public class YuePengSPPay extends BasePay {

    public YuePengSPPay(OrderInfo orderInfo) {
        super(orderInfo);

    }

    @Override
    public void pay(IPayCallback callback) {
        payDialog(mOrderInfo, callback);
    }

    @Override
    void pay() {

    }

    private static void payDialog(final OrderInfo mOrderInfo, final IPayCallback callback) {
        YNInterface.getInstance(mOrderInfo.getContext()).initSdk(
                mOrderInfo.getPaymentParams().optString("appCode"),
                mOrderInfo.getPaymentParams().optString("channelCode"));
        YNInterface.getInstance(mOrderInfo.getContext()).pay(
                mOrderInfo.getOrderId(),
                mOrderInfo.getPaymentParams().optString("chargCode"),
                ((int) mOrderInfo.getPrice()), new PayListener() {
                    @Override
                    public void onPaySuccess(String extData, String orderCode) {
                        LogUtils.d(YuePengSPPay.class.getName(), "支付成功" + extData + "," + orderCode);
                        if (callback != null) {
                            callback.paySuccess();
                        }
                    }

                    public void onPayError(String errMsg, String extData, String orderCode) {
                        LogUtils.d(YuePengSPPay.class.getName(), "支付失败:" + errMsg + "," + extData + "," + orderCode);
                        if (callback != null) {
                            callback.payFail();
                        }
                    }

                    @Override
                    public void onPayCancel(String extData, String orderCode) {
                        LogUtils.e(YuePengSPPay.class.getName(), "支付失败");
                        if (callback != null) {
                            callback.payFail();
                        }
                    }
                });
    }
}
