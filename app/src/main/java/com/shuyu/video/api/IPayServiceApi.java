package com.shuyu.video.api;

import com.shuyu.core.api.BaseApi;
import com.shuyu.video.model.AppPayInfo;
import com.shuyu.video.model.CreateOrderResult;
import com.shuyu.video.model.Payment;
import com.shuyu.video.model.UserInfo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhangleilei on 10/21/16.
 */

public interface IPayServiceApi {

    /**
     * 用户信息接口
     */
    @GET(BaseApi.PAY_URL + "getUserInfo.service")
    Observable<UserInfo> getUserInfo(@Query("sign") String sign);

    /**
     * 获取支付金额
     */
    @GET(BaseApi.PAY_URL + "getAppInfo.service")
    Observable<AppPayInfo> getAppPayInfo();

    /**
     * 获取下发支付方式
     */
    @GET(BaseApi.PAY_URL + "selectPayment.service")
    Observable<List<Payment>> selectPayment();

    /**
     * 根据payCode获取签名数据
     */
    @GET(BaseApi.PAY_URL + "getSignData.service")
    Observable<String> getSignData(@Query("payCode") String payCode);

    /**
     * 根据payCode获取支付url
     */
    @GET(BaseApi.PAY_URL + "getPayUrl.service")
    Observable<String> getPayUrl(@Query("payCode") String payCode);

    /**
     * 创建订单
     */
    @POST(BaseApi.ORDER_URL + "createOrder.service")
    Observable<CreateOrderResult> createOrder(@Query("orderNo") String orderNo,
                                              @Query("payCode") String payCode,
                                              @Query("sign") String sign);

    /**
     * app标准通知
     */
    @GET(BaseApi.NOTICE_URL + "normalNotice.service")
    Observable<String> normalNotice(@Query("orderNo") String orderNo,
                                     @Query("payState") String payState,
                                     @Query("sign") String sign);

}
