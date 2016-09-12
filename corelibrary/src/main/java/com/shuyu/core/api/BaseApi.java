package com.shuyu.core.api;

import android.os.Build;
import android.util.Base64;

import com.shuyu.core.CoreApplication;
import com.shuyu.core.uils.AppUtils;
import com.shuyu.core.uils.SPUtils;
import com.shuyu.core.uils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    //    public static final String LOCAL_SERVER_URL = "http://www.51shuyu.com:8008/";
    public static final String LOCAL_SERVER_URL = "http://101.201.233.134:8008/";

    private static final int TIMEOUT_READ = 15;
    private static final int TIMEOUT_CONNECTION = 15;

    public static <T> T createApi(Class<T> service) {
        final String url = SPUtils.get(CoreApplication.getApplication(), BASE_URL, LOCAL_SERVER_URL).toString() + "%20/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
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
                                   ToastUtils.getInstance().showToast("网络异常,请稍后重试");
                                   if (listener != null) {
                                       listener.onFail();
                                   }
                               }

                               @Override
                               public void onNext(T data) {
                                   if (listener != null) {
                                       listener.onSuccess(data);
                                   }
                               }
                           }

                );
    }


    private static OkHttpClient genericClient() {

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        logInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        Map<String, String> queryParams = new HashMap<>();

        queryParams.put("imsi", AppUtils.getIMEI(CoreApplication.getApplication()));
        queryParams.put("imei", AppUtils.getIMSI(CoreApplication.getApplication()));
        queryParams.put("manufacturer", Build.BRAND);
        queryParams.put("model", Build.MODEL);
//        queryParams.put("versionCode", String.valueOf(AppUtils.getAppVersion(CoreApplication.getApplication())));
        queryParams.put("versionCode", "10101");
        queryParams.put("dcVersion", "000002");
//        queryParams.put("appId", AppUtils.getPackageName());
        queryParams.put("appId", "1001");
        queryParams.put("ditchNo", "0");
        queryParams.put("uuid", AppUtils.getUUID(CoreApplication.getApplication()));

        List<String> headerParams = new ArrayList<>();
        headerParams.add("Host:mmys-cps.ywpod.com");
        headerParams.add("Connection:Keep-Alive");
        headerParams.add("Accept-Encoding:gzip");
        headerParams.add("User-Agent:okhttp/2.5.0");
        headerParams.add("H-Quality:L");
        headerParams.add("Pay-Key:" + getPayKey(queryParams));

        BasicParamsInterceptor basicParamsInterceptor = new BasicParamsInterceptor.Builder()
                .addQueryParamsMap(queryParams)
                .addHeaderLinesList(headerParams)
                .build();

        File cacheFile = new File(CoreApplication.getApplication().getCacheDir(), "retrofit_cache");
        //100Mb
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 10);
        return new OkHttpClient.Builder()
                .cache(cache)
                .retryOnConnectionFailure(true)
                .addInterceptor(logInterceptor)
                .addNetworkInterceptor(new CacheInterceptor())
                .addInterceptor(basicParamsInterceptor)
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .build();
    }

    private static String getPayKey(Map<String, String> headerParams) {

        StringBuilder encodeString = new StringBuilder();

        encodeString
                .append("appId=")
                .append(headerParams.get("appId"))
                .append("&")
                .append("imei=")
                .append(headerParams.get("imei"))
                .append("&")
                .append("imsi=")
                .append(headerParams.get("imsi"))
                .append("&")
                .append("channelNo=")
                .append(headerParams.get("channelNo"))
                .append("&")
                .append("payVersion=132");
        byte[] bytes = encodeString.toString().getBytes();
        return new String((Base64.encode(bytes, Base64.DEFAULT))).replace("\n", "");

    }


    public interface IResponseListener<T> {

        void onSuccess(T data);

        void onFail();
    }

}
