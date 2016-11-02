package com.shuyu.video.api;

import com.shuyu.video.model.AppPayInfo;
import com.shuyu.video.model.CreateOrderResult;
import com.shuyu.video.model.PayResult;
import com.shuyu.video.model.PayUrl;
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
     * 根据orderNo获取支付url
     */
    @GET(BaseApi.PAY_URL + "getPayUrl.service")
    Observable<PayUrl> getPayUrl(@Query("orderNo") String orderNo);

    /**
     * 创建订单
     */
    @POST(BaseApi.ORDER_URL + "createOrder.service")
    Observable<CreateOrderResult> createOrder(
            @Query("payName") String payName,
            @Query("number") int number,
            @Query("userName") String userName,
            @Query("orderNo") String orderNo,
            @Query("payMoney") double payMoney,
            @Query("userPayMoney") double userPayMoney,
            @Query("payPoint") String payPoint,
            @Query("payType") int payType,
            @Query("payCompanyCode") String payCompanyCode,
            @Query("payCode") String payCode,
            //订单状态 0-未支付 1-支付成功 2-支付失败 3-已取消 4-已退款 5-其他
            @Query("payState") int payState,
            @Query("userOs") String userOs,
            @Query("userIp") String userIp,
            @Query("channelNo") String channelNo,
            @Query("sign") String sign);

    /**
     * app标准通知
     */
    @GET(BaseApi.NOTICE_URL + "normalNotice.service")
    Observable<String> normalNotice(@Query("orderNo") String orderNo,
                                    @Query("payState") String payState,
                                    @Query("sign") String sign);

    /**
     * 查询订单
     */
    @GET(BaseApi.PAY_URL + "getOrder.service")
    Observable<PayResult> getOrder(@Query("orderNo") String orderNo);

}
