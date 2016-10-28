package com.shuyu.video.pay;

import com.shuyu.video.model.OrderInfo;

/**
 * Created by zhangleilei on 10/28/16.
 */

public abstract class BasePay implements IPay {


    protected OrderInfo mOrderInfo;

    public BasePay(OrderInfo orderInfo) {
        mOrderInfo = orderInfo;
    }

    @Override
    public void pay(IPayCallback callback) {
        pay();
        if (callback != null) {
            callback.callback();
        }
    }

    abstract void pay();
}
