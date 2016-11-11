package com.shuyu.video.pay;

/**
 * Created by zhangleilei on 10/28/16.
 */
public interface IPay {

    void pay(IPayCallback callback);

    interface IPayCallback {
        void paySuccess();
        void payFail();
    }
}
