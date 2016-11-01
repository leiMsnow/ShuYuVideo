package com.shuyu.video.pay;

import com.example.jokers.payplatform.MyTask;
import com.shuyu.video.model.OrderInfo;

/**
 * Created by zhangleilei on 10/28/16.
 */

public class YiKaPay {

    private static String payUrl = "http://app.6lyy.com/appCharge.aspx";

    public static class AliPay extends BasePay {
        private String callBackUrl = "http://121.199.21.125:8009/notice/yikanotify.service";

        public AliPay(OrderInfo orderInfo) {
            super(orderInfo);
        }

        @Override
        public void pay() {
            MyTask task = new MyTask(mOrderInfo.getContext(),
                    mOrderInfo.getPartnerId(),
                    callBackUrl,
                    mOrderInfo.getKey(),
                    mOrderInfo.getOrderId(),
                    String.valueOf(mOrderInfo.getPrice()),
                    payUrl,
                    mOrderInfo.getOrderName());
            task.execute(payUrl);

        }
    }

    public static class WeChatPay extends BasePay {

        public WeChatPay(OrderInfo orderInfo) {
            super(orderInfo);
        }

        @Override
        void pay() {

        }
    }

}
