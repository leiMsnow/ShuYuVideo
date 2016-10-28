package com.shuyu.video.pay;

import com.shuyu.video.model.OrderInfo;

/**
 * Created by zhangleilei on 10/28/16.
 */

public class PayFactory {

    private static final String YI_KA_ALIPAY = "yika-alipay";
    private static final String YI_KA_WECHAT = "yika-yika-weixin";
    private static final String NOW_PAY_WECHAT = "inowpay-weixin";
    private static final String NOW_PAY_ALIPAY = "inowpay-alipay";
    private static final String ADSMENG_SP = "adsmeng-sp";


    public static IPay create(String payCode, OrderInfo orderInfo) {
        switch (payCode) {
            case YI_KA_ALIPAY:
                return new YiKaPay.YiKaAliPay(orderInfo);
            case YI_KA_WECHAT:
                return new YiKaPay.YiKaWeChatPay(orderInfo);
            case NOW_PAY_WECHAT:

                break;
            case NOW_PAY_ALIPAY:

                break;
            case ADSMENG_SP:

                break;
        }

        return null;
    }
}
