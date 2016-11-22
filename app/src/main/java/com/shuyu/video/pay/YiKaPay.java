package com.shuyu.video.pay;

import com.example.jokers.payplatform.MyTask;
import com.shuyu.video.model.OrderInfo;

/**
 * Created by zhangleilei on 10/28/16.
 */

public class YiKaPay {

    private static String payUrl = "http://app.6lyy.com/appCharge.aspx";

    public static class AliPay extends BasePay {

        public AliPay(OrderInfo orderInfo) {
            super(orderInfo);
        }

        @Override
        public void pay(IPayCallback callback) {
            MyTask task = new MyTask(mOrderInfo.getContext(),
                    mOrderInfo.getPartnerId(),
                    mOrderInfo.getCallBackUrl(),
                    mOrderInfo.getKey(),
                    mOrderInfo.getOrderId(),
                    String.valueOf(mOrderInfo.getPrice()),
                    payUrl,
                    mOrderInfo.getOrderName());
            try {
                task.execute(payUrl);
                callback.paySuccess();
            } catch (Exception e) {
                if (callback != null) {
                    callback.payFail();
                }
            }
        }
    }

    public static class WeChatPay extends BasePay {

        public WeChatPay(OrderInfo orderInfo) {
            super(orderInfo);
        }

    }
}
