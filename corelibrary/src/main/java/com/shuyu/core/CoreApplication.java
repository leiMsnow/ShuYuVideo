package com.shuyu.core;

import android.app.Application;
import android.os.Build;
import android.util.Base64;

import com.liulishuo.filedownloader.FileDownloader;
import com.shuyu.core.api.BasicParamsInterceptor;
import com.shuyu.core.api.CacheInterceptor;
import com.shuyu.core.uils.AppUtils;

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
    private OkHttpClient mOkHttpClient;

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

        if (mOkHttpClient != null) return mOkHttpClient;

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

        // 设置具体的证书HttpsUtils.getSslSocketFactory(
        // 证书的inputstream,
        // 本地证书的inputstream,
        // 本地证书的密码)
//        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);

        File cacheFile = new File(CoreApplication.getApplication().getCacheDir(), "retrofit_cache");
        // 100Mb
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 10);

        return mOkHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .retryOnConnectionFailure(true)
                .addInterceptor(logInterceptor)
                .addNetworkInterceptor(new CacheInterceptor())
//                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
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
}
