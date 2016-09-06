package com.shuyu.video.api.interfaces;

import com.shuyu.video.model.ChannelBanner;
import com.shuyu.video.model.ChannelContent;
import com.shuyu.video.model.ChannelTitle;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhangleilei on 9/5/16.
 */

public interface IMainApi {

    @GET("/qryAllChnl.service/")
    Observable<List<ChannelTitle>> getChannelList();

    @GET("/bannerInfo.service/")
    Observable<List<ChannelBanner>> getChannelBanner(@Query("cid") int cid);

    @GET("/qryVideoChannelContentList.service/")
    Observable<ChannelContent> getChannelContent(@Query("cid") int cid,
                                                 @Query("pageNo") int pageNo,
                                                 @Query("pageSize") int pageSize);

}
