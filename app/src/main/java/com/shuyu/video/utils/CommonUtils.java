package com.shuyu.video.utils;

import android.os.Build;

import com.shuyu.core.uils.AppUtils;
import com.shuyu.video.BuildConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Azure on 2016/9/22.
 */

public class CommonUtils {


    public static String getImsi() {
        return AppUtils.getIMSI();
    }

    public static String getImei() {
        return AppUtils.getIMEI();
    }

    public static String getManufacturer() {
        return Build.BRAND;
    }

    public static String getModel() {
        return Build.MODEL;
    }

    public static String getVersionCode() {
        return "10101";
    }

    public static int getAppId() {
        return 1001;
    }

    public static String getDcVersion() {
        return "000002";
    }

    public static String getDitchNo() {
        return BuildConfig.ditchNo;
    }

    public static String getChannelNo() {
        return BuildConfig.ChannelNo;
    }

    public static String getUuid() {
        return AppUtils.getUUID();
    }

    public static Map<String, String> getCommonParams() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("imsi", getImsi());
        queryParams.put("imei", getImei());
        queryParams.put("manufacturer", getManufacturer());
        queryParams.put("model", getModel());
        queryParams.put("versionCode", getVersionCode());
        queryParams.put("appId", String.valueOf(getAppId()));
        queryParams.put("dcVersion", getDcVersion());
        queryParams.put("ditchNo", getDitchNo());
        queryParams.put("uuid", getUuid());
        return queryParams;
    }

    public static String parseMap(Map<String, String> headerParams) {
        StringBuilder encodeString = new StringBuilder();
        for (Map.Entry entry : headerParams.entrySet()) {
            encodeString.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return encodeString.toString().substring(0, encodeString.toString().lastIndexOf("&"));
    }


}
