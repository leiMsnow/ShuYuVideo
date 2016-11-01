package com.shuyu.video.api;

import com.shuyu.video.model.AppStoreList;
import com.shuyu.video.model.ChannelBanner;
import com.shuyu.video.model.ChannelPicture;
import com.shuyu.video.model.ChannelTitle;
import com.shuyu.video.model.ChannelVideo;
import com.shuyu.video.model.HotWord;
import com.shuyu.video.model.LiveVideo;
import com.shuyu.video.model.PictureDetails;
import com.shuyu.video.model.ResultEntity;
import com.shuyu.video.model.RunInfo;
import com.shuyu.video.model.SearchVideoList;
import com.shuyu.video.model.VideoComment;
import com.shuyu.video.model.VideoPicDetails;

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
    @GET(BaseApi.BASE_URL + "start.service")
    Observable<List<RunInfo>> getRunInfo();

    /**
     * 应用商店（应用推广）
     */
    @GET(BaseApi.BASE_URL + "appshop.service")
    Observable<AppStoreList> getAppStoreList(@Query("pageNo") int pageNo);

    /**
     * 热搜词（搜索标签）
     */
    @GET(BaseApi.BASE_URL + "hotsword.service")
    Observable<HotWord> getHotWordList(@Query("pageNo") int pageNo,
                                       @Query("pageSize") int pageSize);

    /**
     * 搜索
     */
    @GET(BaseApi.BASE_URL + "search.service?rv=2")
    Observable<SearchVideoList> searchVideo(@Query("word") String word,
                                            @Query("pageNo") int pageNo,
                                            @Query("pageSize") int pageSize);

    /**
     * 用户反馈
     */
    @POST(BaseApi.BASE_URL + "feedback.service")
    Observable<ResultEntity> feedback(@Query("qType") int qType,
                                      @Query("content") String content,
                                      @Query("contactWay") String contact);

    /**
     * 在线时长统计
     */
    @POST(BaseApi.BASE_URL + "onlineTime.service")
    Observable<ResultEntity> stayTime(@Query("onTime") long onTime,
                                      @Query("offTime") long offTime,
                                      @Query("thirdChannelId") String thirdChannelId);

    /**
     * 视频观看统计
     */
    @POST(BaseApi.BASE_URL + "lookVideoCount.service")
    Observable<ResultEntity> lookVideoState(@Query("videoId") int videoId,
                                            @Query("isRmd") int isRmd);

    /**
     * 用户激活应用
     */
    @POST(BaseApi.BASE_URL + "appActivation.service")
    Observable<ResultEntity> userActivation(@Query("data") String data,
                                            @Query("dcVersion") String dcVersion);

    /**
     * 用户访问接口
     */
    @POST(BaseApi.BASE_URL + "appVisit.service")
    Observable<ResultEntity> userVisit(@Query("data") String data,
                                       @Query("dcVersion") String dcVersion);





}

