package com.shuyu.video.pay;

import com.shuyu.core.uils.LogUtils;
import com.shuyu.video.api.BaseApi;
import com.shuyu.video.api.IPayServiceApi;
import com.shuyu.video.model.OrderInfo;
import com.shuyu.video.model.PayUrl;

/**
 * Created by zhangleilei on 19/11/2016.
 */

public class XunTuSPPay extends BasePay {

    public XunTuSPPay(OrderInfo orderInfo) {
        super(orderInfo);
    }

    @Override
    public void pay(final IPayCallback callback) {
        BaseApi.request(BaseApi.createApi(IPayServiceApi.class).getPayUrl(mOrderInfo.getOrderId())
                , new BaseApi.IResponseListener<PayUrl>() {
                    @Override
                    public void onSuccess(PayUrl data) {
                        LogUtils.d(XunTuSPPay.class.getName(), data.getPayUrl());
                    }

                    @Override
                    public void onFail() {

                    }
                });
    }
}
