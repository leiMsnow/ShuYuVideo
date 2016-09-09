package com.shuyu.core.api;

import android.os.Build;

import com.shuyu.core.CoreApplication;
import com.shuyu.core.uils.AppUtils;
import com.shuyu.core.uils.SPUtils;

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

    public static final String BASE_URL = "BASE_URL";

    private static final String SERVER_URL_TEST = "http://www.51shuyu.com:8008/";
//    private static final String SERVER_URL_NEW = "http://mmys-cps.ywpod.com/mmys-cps/ ";

    private static final int TIMEOUT_READ = 25;
    private static final int TIMEOUT_CONNECTION = 25;

    public static <T> T createApi(Class<T> service) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SPUtils.get(CoreApplication.getApplication(),BASE_URL,SERVER_URL_TEST).toString())
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

        queryParams.put("imsi", AppUtils.getIMEI(CoreApplication.getApplication()));
        queryParams.put("imei", AppUtils.getIMSI(CoreApplication.getApplication()));
        queryParams.put("manufacturer", Build.BRAND);
        queryParams.put("model", Build.MODEL);
//        queryParams.put("versionCode", String.valueOf(AppUtils.getAppVersion(CoreApplication.getApplication())));
        queryParams.put("versionCode", "10101");
        queryParams.put("dcVersion", "000002");
//        queryParams.put("appId", AppUtils.getPackageName());
        queryParams.put("appId", "1021");
        queryParams.put("ditchNo", "0");
        queryParams.put("uuid", AppUtils.getUUID(CoreApplication.getApplication()));

        Map<String, String> headerParams = new HashMap<>();
        headerParams.put("Host","mmys-cps.ywpod.com");
        headerParams.put("Connection","Keep-Alive");
        headerParams.put("Accept-Encoding","gzip");
        headerParams.put("User-Agent","okhttp/2.5.0");
        headerParams.put("H-Quality","L");
        headerParams.put("Pay-Key","YXBwSWQ9MTAwMSZpbWVpPTg2Mjc1NjAzMTA3MTM4OCZpbXNpPW51bGwmY2hh" +
                "bm5lbE5vPXNwd3lz%0AYl8xMTExMTEwMzFfMDAwMjM4MSZwYXlWZXJzaW9uPTEzMg%3D%3D%0A");

        BasicParamsInterceptor basicParamsInterceptor = new BasicParamsInterceptor.Builder()
                .addQueryParamsMap(queryParams)
                .addHeaderParamsMap(headerParams)
                .build();

        File cacheFile = new File(CoreApplication.getApplication().getCacheDir(), "retrofit_cache");
        //100Mb
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 10);
        return new OkHttpClient.Builder()
                .cache(cache)
                .retryOnConnectionFailure(true)
                .addInterceptor(logInterceptor)
                .addInterceptor(new CacheInterceptor())
                .addInterceptor(basicParamsInterceptor)
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .build();
    }


    public interface IResponseListener<T> {

        void onSuccess(T data);

        void onFail();
    }

}
