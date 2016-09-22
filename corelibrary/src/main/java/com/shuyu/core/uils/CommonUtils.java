package com.shuyu.core.uils;

import com.shuyu.core.model.CommParams;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Azure on 2016/9/22.
 */

public class CommonUtils {

    public static Map<String,String> getCommonParams(){
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
        return queryParams;
    }

    public static String parseMap(Map<String, String> headerParams) {
        StringBuilder encodeString = new StringBuilder();
        for (Map.Entry entry : headerParams.entrySet()) {
            encodeString.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return encodeString.toString().substring(0,encodeString.toString().lastIndexOf("&"));
    }
}
