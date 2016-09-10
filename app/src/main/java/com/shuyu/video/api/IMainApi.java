package com.shuyu.video.api;

import com.shuyu.core.api.BaseApi;
import com.shuyu.video.model.ChannelBanner;
import com.shuyu.video.model.ChannelData;
import com.shuyu.video.model.ChannelTitle;
import com.shuyu.video.model.LiveVideoData;
import com.shuyu.video.model.RunInfo;
import com.shuyu.video.model.VideoComment;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 首页接口
 * Created by zhangleilei on 9/5/16.
 */

public interface IMainApi {

    @GET(BaseApi.LOCAL_SERVER_URL + "runInfo.service")
    Observable<List<RunInfo>> getRunInfo();

    @GET("qryAllChnl.service")
    Observable<List<ChannelTitle>> getChannelList();

    @GET("bannerInfo.service")
    Observable<List<ChannelBanner>> getChannelBannerList(@Query("cid") int cid);

    @GET("qryVideoChannelContentList.service")
    Observable<ChannelData> getVideoListByChannelId(@Query("cid") int cid,
                                                    @Query("pageNo") int pageNo,
                                                    @Query("pageSize") int pageSize);

    @GET("videoDtl.service")
    Observable<ChannelData.VideoChannel.ChannelContent> getVideoDetails(@Query("id") int id);

    @GET("videoComment.service")
    Observable<List<VideoComment>> getVideoCommentList();

    @GET("liveVideoList.service")
    Observable<LiveVideoData> getLiveVideoList(@Query("pageNo") int pageNo);

}
