package com.shuyu.video.api;

import com.shuyu.core.api.BaseApi;
import com.shuyu.video.model.AppStoreEntity;
import com.shuyu.video.model.HotWord;
import com.shuyu.video.model.ResultEntity;
import com.shuyu.video.model.RunInfo;
import com.shuyu.video.model.SearchVideoData;

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
     *
     * @return
     */
    @GET(BaseApi.LOCAL_SERVER_URL + "runInfo.service")
    Observable<List<RunInfo>> getRunInfo();

    /**
     * 应用商店（应用推广）
     *
     * @param pageNo
     * @return
     */
    @GET(BaseApi.LOCAL_SERVER_URL + "appstore.service")
    Observable<AppStoreEntity> getAppStoreList(@Query("pageNo") int pageNo);

    /**
     * 热搜词（搜索标签）
     *
     * @return
     */
    @GET(BaseApi.LOCAL_SERVER_URL + "hotword.service")
    Observable<HotWord> getHotWordList(@Query("pageNo") int pageNo,
                                       @Query("pageSize") int pageSize);

    /**
     * 搜索
     *
     * @param word
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GET(BaseApi.LOCAL_SERVER_URL + "searchVideo.service?rv=2")
    Observable<SearchVideoData> searchVideo(@Query("word") String word,
                                            @Query("pageNo") int pageNo,
                                            @Query("pageSize") int pageSize);

    /**
     * 用户反馈
     *
     * @return
     */
    @POST(BaseApi.LOCAL_SERVER_URL + "userFeedback.service")
    Observable<ResultEntity> feedback(@Query("qType") int qType,
                                      @Query("content") String content,
                                      @Query("contactWay") String contact);

    /**
     * 在线时长统计
     *
     * @return
     */
    @POST(BaseApi.LOCAL_SERVER_URL + "suos.service")
    Observable<ResultEntity> stayTime(@Query("onTime") long onTime,
                                      @Query("offTime") long offTime,
                                      @Query("thirdChannelId") String thirdChannelId);


    /**
     * 视频观看统计
     *
     * @return
     */
    @POST(BaseApi.LOCAL_SERVER_URL + "lookVideoStat.service")
    Observable<ResultEntity> lookVideoState(@Query("videoId") int videoId,
                                            @Query("isRmd") int isRmd);

    /**
     * 用户激活应用
     */
    @POST(BaseApi.LOCAL_SERVER_URL + "userActivation.service")
    Observable<ResultEntity> userActivation(@Query("data") String data,
                                            @Query("dcVersion") String dcVersion);

    @POST(BaseApi.LOCAL_SERVER_URL + "userVisit.service")
    Observable<ResultEntity> userVisit(@Query("data") String data,
                                            @Query("dcVersion") String dcVersion);


}

