package com.shuyu.video.api;

import com.shuyu.core.api.BaseApi;
import com.shuyu.video.model.AppStoreList;
import com.shuyu.video.model.HotWord;
import com.shuyu.video.model.AppPayInfo;
import com.shuyu.video.model.ResultEntity;
import com.shuyu.video.model.RunInfo;
import com.shuyu.video.model.SearchVideoList;
import com.shuyu.video.model.UserInfo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhangleilei on 9/21/16.
 */
public interface ILocalServiceApi {

    /**
     * 应用启动画面接口
     */
    @GET(BaseApi.BASE_URL + "runInfo.service")
    Observable<List<RunInfo>> getRunInfo();

    /**
     * 应用商店（应用推广）
     */
    @GET(BaseApi.BASE_URL + "appstore.service")
    Observable<AppStoreList> getAppStoreList(@Query("pageNo") int pageNo);

    /**
     * 热搜词（搜索标签）
     */
    @GET(BaseApi.BASE_URL + "hotword.service")
    Observable<HotWord> getHotWordList(@Query("pageNo") int pageNo,
                                       @Query("pageSize") int pageSize);

    /**
     * 搜索
     */
    @GET(BaseApi.BASE_URL + "searchVideo.service?rv=2")
    Observable<SearchVideoList> searchVideo(@Query("word") String word,
                                            @Query("pageNo") int pageNo,
                                            @Query("pageSize") int pageSize);

    /**
     * 用户反馈
     */
    @POST(BaseApi.BASE_URL + "userFeedback.service")
    Observable<ResultEntity> feedback(@Query("qType") int qType,
                                      @Query("content") String content,
                                      @Query("contactWay") String contact);

    /**
     * 在线时长统计
     */
    @POST(BaseApi.BASE_URL + "suos.service")
    Observable<ResultEntity> stayTime(@Query("onTime") long onTime,
                                      @Query("offTime") long offTime,
                                      @Query("thirdChannelId") String thirdChannelId);

    /**
     * 视频观看统计
     */
    @POST(BaseApi.BASE_URL + "lookVideoStat.service")
    Observable<ResultEntity> lookVideoState(@Query("videoId") int videoId,
                                            @Query("isRmd") int isRmd);

    /**
     * 用户激活应用
     */
    @POST(BaseApi.BASE_URL + "userActivation.service")
    Observable<ResultEntity> userActivation(@Query("data") String data,
                                            @Query("dcVersion") String dcVersion);

    /**
     * 用户访问接口
     */
    @POST(BaseApi.BASE_URL + "userVisit.service")
    Observable<ResultEntity> userVisit(@Query("data") String data,
                                       @Query("dcVersion") String dcVersion);

    /**
     * 用户信息接口
     */
    @GET(BaseApi.PAY_URL + "getUserInfo.service")
    Observable<UserInfo> getUserInfo(@Query("sign") String sign);

    /**
     * 获取支付金额
     */
    @GET(BaseApi.PAY_URL+ "getAppInfo.service")
    Observable<AppPayInfo> getAppPayInfo();

    /**
     * 获取支付插件接口
     */
    @GET(BaseApi.PAY_URL+ "selectPayment.service")
    Observable<ResultEntity> selectPayment();
}

