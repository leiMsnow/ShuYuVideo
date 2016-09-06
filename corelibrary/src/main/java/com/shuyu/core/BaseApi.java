package com.shuyu.core;

import java.io.File;
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
    //设缓存有效期为两天
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0时则不会使用缓存而请求服务器
    private static final String CACHE_CONTROL_NETWORK = "max-age=0";


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

        File cacheFile = new File(CoreApplication.getApplication().getCacheDir(), "retrofit_cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 10); //100Mb

        return new OkHttpClient.Builder()
                .addInterceptor(logInterceptor)
                //stetho,可以在chrome中查看请求
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        Request request = chain.request()
//                                .newBuilder()
//                                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
//                                .addHeader("Accept-Encoding", "gzip, deflate")
//                                .addHeader("Connection", "keep-alive")
//                                .addHeader("Accept", "*/*")
//                                .addHeader("Cookie", "add cookies here")
//                                .build();
//                        return chain.proceed(request);
//                    }
//                })
                .cache(cache)
                //失败重连
                .retryOnConnectionFailure(true)
                //time out
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
