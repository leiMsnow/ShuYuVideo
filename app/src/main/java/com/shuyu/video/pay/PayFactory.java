package com.shuyu.video.pay;

import com.shuyu.video.model.OrderInfo;

/**
 * Created by zhangleilei on 10/28/16.
 */

public class PayFactory {

    private static final String YI_KA_ALIPAY = "yika-alipay";
    private static final String YI_KA_WECHAT = "yika-yika-weixin";

    private static final String NOW_PAY_ALIPAY = "inowpay-alipay";
    private static final String NOW_PAY_WECHAT = "inowpay-weixin";

    private static final String ZHILIAOFU_PAY_ALIPAY = "zhiliaofu-alipay";
    private static final String ZHILIAOFU_PAY_WECHAT = "zhiliaofu-weixin";

    private static final String ADSMENG_SP = "adsmeng-sp";


    public static IPay create(String payCode, OrderInfo orderInfo) {
        switch (payCode) {
            case YI_KA_ALIPAY:
                return new YiKaPay.AliPay(orderInfo);
            case YI_KA_WECHAT:
                return new YiKaPay.WeChatPay(orderInfo);

            case ZHILIAOFU_PAY_ALIPAY:
            case ZHILIAOFU_PAY_WECHAT:
                return new ZhiLiaoPay(orderInfo);
            case ADSMENG_SP:

                break;
        }

        return null;
    }
}
