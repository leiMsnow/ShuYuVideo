package com.shuyu.video.pay;

import com.shuyu.video.model.OrderInfo;

/**
 * Created by zhangleilei on 10/28/16.
 */

public class PayFactory {

    public static final String YI_KA_ALIPAY = "yika-alipay";
    public static final String YI_KA_WECHAT = "yika-yika-weixin";

    public static final String NOW_PAY_ALIPAY = "inowpay-alipay";
    public static final String NOW_PAY_WECHAT = "inowpay-weixin";

    public static final String ZHILIAOFU_PAY_ALIPAY = "zhiliaofu-alipay";
    public static final String ZHILIAOFU_PAY_WECHAT = "zhiliaofu-weixin";

    public static final String ADSMENG_SP = "adsmeng-sp";
    //苏州乐鹏10元
    public static final String PAY_CODE_YUEPENG_SP_10 = "yuepengsp-10";
    //苏州乐鹏20元
    public static final String PAY_CODE_YUEPENG_SP_20 = "yuepengsp-20";


    public static IPay create(String payCode, OrderInfo orderInfo) {
        switch (payCode) {
            case YI_KA_ALIPAY:
                return new YiKaPay.AliPay(orderInfo);
            case YI_KA_WECHAT:
                return new YiKaPay.WeChatPay(orderInfo);

            case ZHILIAOFU_PAY_ALIPAY:
            case ZHILIAOFU_PAY_WECHAT:
                return new ZhiLiaoPay(orderInfo);
            case PAY_CODE_YUEPENG_SP_10:
            case PAY_CODE_YUEPENG_SP_20:
                return new YuePengSPPay(orderInfo);
        }

        return null;
    }
}
