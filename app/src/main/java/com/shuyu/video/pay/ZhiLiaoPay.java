package com.shuyu.video.pay;

import android.content.Intent;

import com.shuyu.video.activity.WebViewActivity;
import com.shuyu.video.api.BaseApi;
import com.shuyu.video.api.IPayServiceApi;
import com.shuyu.video.model.OrderInfo;
import com.shuyu.video.model.PayUrl;
import com.shuyu.video.utils.Constants;

/**
 * Created by zhangleilei on 10/28/16.
 */

public class ZhiLiaoPay extends BasePay {

    public ZhiLiaoPay(OrderInfo orderInfo) {
        super(orderInfo);
    }

    @Override
    public void pay(IPayCallback callback) {
        super.pay(callback);
        BaseApi.request(BaseApi.createApi(IPayServiceApi.class).getPayUrl(mOrderInfo.getOrderId()),
                new BaseApi.IResponseListener<PayUrl>() {

                    @Override
                    public void onSuccess(int code,PayUrl data) {
                        if (code == BaseApi.RESCODE_FAILURE) {
                            return;
                        }
                        Intent intent = new Intent(mOrderInfo.getContext(), WebViewActivity.class);
                        intent.putExtra(Constants.KEY_WEB_VIEW_TYPE, WebViewActivity.VIEW_VIEW_TYPE_PAY_URL);
                        intent.putExtra(Constants.KEY_PAY_URL, data.getPayUrl());
                        mOrderInfo.getContext().startActivity(intent);
                    }

                });
    }
}
