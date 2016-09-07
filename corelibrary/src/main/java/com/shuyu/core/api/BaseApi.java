package com.shuyu.core.api;

import android.os.Build;

import com.ray.core.captain.utils.AppUtils;
import com.shuyu.core.CoreApplication;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Azure on 2016/9/5.
 */

public class BaseApi {

    private static final String SERVER_URL = "http://www.51shuyu.com:8008/";

    private static final int TIMEOUT_READ = 25;
    private static final int TIMEOUT_CONNECTION = 25;

    public static <T> T createApi(Class<T> service) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .client(genericClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(service);
    }


    public static <T> void request(Observable<T> observable, final IResponseListener<T> listener) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {
                        if (listener != null)
                            listener.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (listener != null)
                            listener.onFail();
                    }

                    @Override
                    public void onNext(T data) {
                        if (listener != null) {
                            listener.onSuccess(data);
                        }
                    }
                });
    }


    private static OkHttpClient genericClient() {

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Map<String, String> queryParams = new HashMap<>();

        queryParams.put("imsi", "");
        queryParams.put("imei", "");
        queryParams.put("manufacturer", Build.BRAND);
        queryParams.put("model", Build.MODEL);
        queryParams.put("versionCode", String.valueOf(Build.VERSION.SDK_INT));
        queryParams.put("dcVersion", "");
        queryParams.put("appId", AppUtils.getAppName(CoreApplication.getApplication()));
        queryParams.put("ditchNo", "0");
        queryParams.put("uuid", "");

        BasicParamsInterceptor basicParamsInterceptor = new BasicParamsInterceptor.Builder()
                .addQueryParamsMap(queryParams)
                .build();

        File cacheFile = new File(CoreApplication.getApplication().getCacheDir(), "retrofit_cache");
        //100Mb
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 10);
        return new OkHttpClient.Builder()
                .addInterceptor(logInterceptor)
                .addInterceptor(basicParamsInterceptor)
                .cache(cache)
                .addInterceptor(new CacheInterceptor())
                .retryOnConnectionFailure(true)
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .build();
    }


    public interface IResponseListener<T> {

        void onCompleted();

        void onSuccess(T data);

        void onFail();
    }

}
