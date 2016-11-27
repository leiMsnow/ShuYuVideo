package com.shuyu.video;

import android.text.TextUtils;
import android.util.Base64;

import com.shuyu.core.CoreApplication;
import com.shuyu.core.api.BasicParamsInterceptor;
import com.shuyu.core.api.CacheInterceptor;
import com.shuyu.core.uils.LogUtils;
import com.shuyu.core.uils.SPUtils;
import com.shuyu.video.utils.CommonUtils;
import com.shuyu.video.utils.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by zhangleilei on 8/31/16.
 */
public class MyApplication extends CoreApplication {

    private static MyApplication mApplication;
    private static final int TIMEOUT_READ = 15;
    private static final int TIMEOUT_CONNECTION = 15;
    private static OkHttpClient mOkHttpClient;

    @Override
    public void onCreate() {
        if (!BuildConfig.IS_DEBUG)
            MCrashHandler.getInstance().init();
        super.onCreate();
        mApplication = this;
        LogUtils.isDebug = Constants.IS_DEBUG;
        SPUtils.put(Constants.STAY_TIME_ON, System.currentTimeMillis());

        String uuid = SPUtils.get(Constants.USER_UUID, "").toString();
        if (TextUtils.isEmpty(uuid)) {
            uuid = "client_" + java.util.UUID.randomUUID().toString();
            SPUtils.put(Constants.USER_UUID, uuid);
            LogUtils.d(MyApplication.class.getName(), "createUUID : " + uuid);
        }
    }

    public OkHttpClient genericClient() {

        if (mOkHttpClient != null)
            return mOkHttpClient;

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        HttpLoggingInterceptor.Level level = BuildConfig.IS_DEBUG ?
                HttpLoggingInterceptor.Level.HEADERS :
                HttpLoggingInterceptor.Level.NONE;
        logInterceptor.setLevel(level);

        Map<String, String> commonParams = CommonUtils.getCommonParams();

        List<String> headerParams = new ArrayList<>();
        headerParams.add("Connection:Keep-Alive");
        headerParams.add("accet:*/*");
        headerParams.add("Accept-Encoding:gzip");
        headerParams.add("User-Agent:okhttp/2.5.0");
        headerParams.add("H-Quality:L");
        headerParams.add("Pay-Key:" + getPayKey(commonParams));

        BasicParamsInterceptor basicParamsInterceptor = new BasicParamsInterceptor.Builder()
                .addHeaderLinesList(headerParams)
                .addQueryParamsMap(commonParams)
                .build();

        File cacheFile = new File(CoreApplication.getApplication().getCacheDir(), "retrofit_cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 10);

        return mOkHttpClient = new OkHttpClient.Builder()
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
        String encodeString = CommonUtils.parseMap(headerParams) + "&payVersion=132";
        byte[] bytes = encodeString.getBytes();
        return new String((Base64.encode(bytes, Base64.DEFAULT))).replace("\n", "");
    }

    public static MyApplication getApplication() {
        return mApplication;
    }
}
