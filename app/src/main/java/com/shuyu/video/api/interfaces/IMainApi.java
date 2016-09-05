package com.shuyu.video.api.interfaces;

import com.shuyu.video.model.ChannelTitle;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by zhangleilei on 9/5/16.
 */

public interface IMainApi {

    @GET("/qryAllChnl.service/")
    Call<List<ChannelTitle>> getChannelList();

}
