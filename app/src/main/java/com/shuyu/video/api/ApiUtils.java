package com.shuyu.video.api;

import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Azure on 2016/9/5.
 */

public class ApiUtils {

    public static final String SERVER_URL = "http://www.51shuyu.com:8008/";

    public static <T> T createApi(Class<T> service){
         Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        return retrofit.create(service);
    }

}
