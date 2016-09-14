package com.shuyu.core.api.download;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by zhangleilei on 9/14/16.
 */

public interface IDownloadApi {

//    @Streaming
    @GET()
    Call<ResponseBody> downloadFile(@Url String   fileUrl);

}
