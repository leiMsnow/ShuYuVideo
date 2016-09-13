package com.shuyu.video.api;

import com.shuyu.core.api.BaseApi;
import com.shuyu.video.model.AppStoreEntity;
import com.shuyu.video.model.ChannelBanner;
import com.shuyu.video.model.ChannelPictureEntity;
import com.shuyu.video.model.ChannelVideoEntity;
import com.shuyu.video.model.ChannelTitle;
import com.shuyu.video.model.HotWord;
import com.shuyu.video.model.LiveVideoEntity;
import com.shuyu.video.model.PictureDetails;
import com.shuyu.video.model.RunInfo;
import com.shuyu.video.model.SearchVideoData;
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

public interface IMainApi {

    /**
     * 应用启动画面接口
     *
     * @return
     */
    @GET(BaseApi.LOCAL_SERVER_URL + "runInfo.service")
    Observable<List<RunInfo>> getRunInfo();

    /**
     * 顶部（导航）频道列表
     *
     * @return
     */
    @GET("qryAllChnl.service")
    Observable<List<ChannelTitle>> getChannelList();

    /**
     * 轮播图Banner
     *
     * @param cid
     * @return
     */
    @GET("bannerInfo.service")
    Observable<List<ChannelBanner>> getChannelBannerList(@Query("cid") int cid);

    /**
     * 视频频道内容列表
     *
     * @param cid
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GET("qryVideoChannelContentList.service?rv=2")
    Observable<ChannelVideoEntity> getVideoListByChannelId(@Query("cid") int cid,
                                                           @Query("pageNo") int pageNo,
                                                           @Query("pageSize") int pageSize);


    /**
     * 视频详情
     *
     * @param id
     * @return
     */
    @GET("videoDtl.service")
    Observable<VideoPicDetails> getVideoDetails(@Query("id") int id);


    /**
     * 图库频道内容列表
     *
     * @param cid
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GET("qryPicChannelContentList.service?picRv=2")
    Observable<ChannelPictureEntity> getPictureListByChannelId(@Query("cid") int cid,
                                                               @Query("pageNo") int pageNo,
                                                               @Query("pageSize") int pageSize);

    /**
     * 图片详情
     *
     * @param groupId
     * @return
     */
    @GET("pictureList.service")
    Observable<List<PictureDetails>> getPictureDetails(@Query("groupId") int groupId);


    /**
     * vip专区视频列表
     *
     * @param pageNo
     * @return
     */
    @GET("liveVideoList.service?rv=2")
    Observable<LiveVideoEntity> getLiveVideoList(@Query("pageNo") int pageNo);


    /**
     * 应用商店（应用推广）
     * @param pageNo
     * @return
     */
    @GET("appstore.service")
    Observable<AppStoreEntity> getAppStoreList(@Query("pageNo") int pageNo);

    /**
     * 视频下方评论
     *
     * @return
     */
    @GET("videoComment.service")
    Observable<List<VideoComment>> getVideoCommentList();

    /**
     * 热搜词（搜索标签）
     *
     * @return
     */
    @GET("hotword.service")
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
    @GET("searchVideo.service?rv=2")
    Observable<SearchVideoData> searchVideo(@Query("word") String word,
                                            @Query("pageNo") int pageNo,
                                            @Query("pageSize") int pageSize);


}
