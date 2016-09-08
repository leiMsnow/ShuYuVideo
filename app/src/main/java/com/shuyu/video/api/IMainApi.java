package com.shuyu.video.api;

import com.shuyu.video.model.ChannelBanner;
import com.shuyu.video.model.ChannelContent;
import com.shuyu.video.model.ChannelTitle;
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

    @GET("/qryAllChnl.service/")
    Observable<List<ChannelTitle>> getChannelList();

    @GET("/bannerInfo.service/")
    Observable<List<ChannelBanner>> getChannelBanner(@Query("cid") int cid);

    @GET("/qryVideoChannelContentList.service/")
    Observable<ChannelContent> getVideoListByChannelId(@Query("cid") int cid,
                                                       @Query("pageNo") int pageNo,
                                                       @Query("pageSize") int pageSize);

    @GET("/videoDtl.service/")
    Observable<ChannelContent.VideoChannelListBean.ChannelContentListBean> getVideoDetails(@Query("id") int id);

    @GET("/videoComment.service/")
    Observable<List<VideoComment>> getVideoComments();

}
