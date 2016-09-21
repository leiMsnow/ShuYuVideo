package com.shuyu.core;

import android.app.Application;
import android.util.Base64;

import com.liulishuo.filedownloader.FileDownloader;
import com.shuyu.core.api.BasicParamsInterceptor;
import com.shuyu.core.api.CacheInterceptor;
import com.shuyu.core.model.CommParams;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by zhangleilei on 8/31/16.
 */
public class CoreApplication extends Application {

    private static CoreApplication mApplication;
    private static final int TIMEOUT_READ = 15;
    private static final int TIMEOUT_CONNECTION = 15;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        FileDownloader.init(getApplicationContext());
    }

    public static CoreApplication getApplication() {
        return mApplication;
    }

    public OkHttpClient genericClient() {

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Map<String, String> queryParams = new HashMap<>();
        CommParams commParams = new CommParams();
        queryParams.put("imsi", commParams.getImsi());
        queryParams.put("imei", commParams.getImei());
        queryParams.put("manufacturer", commParams.getManufacturer());
        queryParams.put("model", commParams.getModel());
        queryParams.put("versionCode", commParams.getVersionCode());
        queryParams.put("appId", String.valueOf(commParams.getAppId()));
        queryParams.put("dcVersion", commParams.getDcVersion());
        queryParams.put("ditchNo", commParams.getDitchNo());
        queryParams.put("uuid", commParams.getUuid());

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
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 10);

        return  new OkHttpClient.Builder()
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
        for (Map.Entry entry : headerParams.entrySet()) {
            encodeString.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        encodeString.append("payVersion=132");
        byte[] bytes = encodeString.toString().getBytes();
        return new String((Base64.encode(bytes, Base64.DEFAULT))).replace("\n", "");
    }
}
