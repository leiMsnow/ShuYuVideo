package com.shuyu.video.api.interfaces;

import com.shuyu.video.model.ChannelBanner;
import com.shuyu.video.model.ChannelContent;
import com.shuyu.video.model.ChannelTitle;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by zhangleilei on 9/5/16.
 */

public interface IMainApi {

    @GET("/qryAllChnl.service/")
    Call<List<ChannelTitle>> getChannelList();

    @GET("/bannerInfo.service/")
    Call<List<ChannelBanner>> getChannelBanner(@Query("cid") int cid);

    @GET("/qryVideoChannelContentList.service/")
    Call<ChannelContent> getChannelContent(@Query("cid") int cid,
                                                 @Query("pageNo") int pageNo,
                                                 @Query("pageSize") int pageSize);

}
