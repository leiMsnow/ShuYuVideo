package com.shuyu.video.api;

import com.shuyu.video.model.ChannelBanner;
import com.shuyu.video.model.ChannelPicture;
import com.shuyu.video.model.ChannelTitle;
import com.shuyu.video.model.ChannelVideo;
import com.shuyu.video.model.LiveVideo;
import com.shuyu.video.model.PictureDetails;
import com.shuyu.video.model.VideoComment;
import com.shuyu.video.model.VideoPicDetails;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 首页接口
 * Created by zhangleilei on 9/5/16.
 */

public interface IServiceApi {

    /**
     * 顶部（导航）频道列表
     */
    @GET("qryAllChnl.service")
    Observable<List<ChannelTitle>> getChannelList();

    /**
     * 轮播图Banner
     */
    @GET("bannerInfo.service")
    Observable<List<ChannelBanner>> getChannelBannerList(@Query("cid") int cid);

    /**
     * 视频频道内容列表
     */
    @GET("qryVideoChannelContentList.service?rv=2")
    Observable<ChannelVideo> getVideoListByChannelId(@Query("cid") int cid,
                                                     @Query("pageNo") int pageNo,
                                                     @Query("pageSize") int pageSize);

    /**
     * 视频详情
     */
    @GET("videoDtl.service")
    Observable<VideoPicDetails> getVideoDetails(@Query("id") int id);

    /**
     * 图库频道内容列表
     */
    @GET("qryPicChannelContentList.service?picRv=2")
    Observable<ChannelPicture> getPictureListByChannelId(@Query("cid") int cid,
                                                         @Query("pageNo") int pageNo,
                                                         @Query("pageSize") int pageSize);

    /**
     * 图片详情
     */
    @GET("pictureList.service")
    Observable<List<PictureDetails>> getPictureDetails(@Query("groupId") int groupId);

    /**
     * vip专区视频列表
     */
    @GET("liveVideoList.service?rv=2")
    Observable<LiveVideo> getLiveVideoList(@Query("pageNo") int pageNo);

    /**
     * 私密专区试播视频
     */
    // TODO: 9/21/16  私密专区试播视频
    /**
     * 私密专区视频列表
     */
    // TODO: 9/21/16  私密专区视频列表

    /**
     * 视频下方评论
     */
    @GET("videoComment.service")
    Observable<List<VideoComment>> getVideoCommentList();

}
