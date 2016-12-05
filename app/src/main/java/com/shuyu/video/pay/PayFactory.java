package com.shuyu.video.pay;

import com.shuyu.video.model.OrderInfo;

/**
 * Created by zhangleilei on 10/28/16.
 */

public class PayFactory {

    public static final String PAY_YIKA_ALIPAY = "yika-alipay";
    public static final String PAY_YIKA_WECHAT = "yika-weixin";

    public static final String PAY_ZHILIAOFU_ALIPAY = "zhiliaofu-alipay";
    public static final String PAY_ZHILIAOFU_WECHAT = "zhiliaofu-weixin";

    // 苏州乐鹏10元
    public static final String PAY_SP_YUEPENG_10 = "yuepengsp-10";
    // 苏州乐鹏20元
    public static final String PAY_SP_YUEPENG_20 = "yuepengsp-20";
    // 讯通10元
    public static final String PAY_SP_XUN_TONG_10 = "xuntusp-telecom-10";

    public static IPay create(String payCode, OrderInfo orderInfo) {
        switch (payCode) {
            case PAY_YIKA_ALIPAY:
                return new YiKaPay.AliPay(orderInfo);
            case PAY_YIKA_WECHAT:
                return new YiKaPay.WeChatPay(orderInfo);

            case PAY_ZHILIAOFU_ALIPAY:
            case PAY_ZHILIAOFU_WECHAT:
                return new ZhiLiaoPay(orderInfo);
            case PAY_SP_YUEPENG_10:
            case PAY_SP_YUEPENG_20:
                return new LePengSPPay(orderInfo);
            case PAY_SP_XUN_TONG_10:
                return new XunTuSPPay(orderInfo);
        }

        return null;
    }
}
