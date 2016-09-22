package com.shuyu.video.utils;

import com.shuyu.core.model.CommParams;
import com.sooying.utils.AESUtils;

/**
 * Created by zhangleilei on 9/22/16.
 */

public class DataSignUtils {

    private static final String AES_KEY = "sooyingsys2016@2";

    // 加密
    public static String encryptData(String dataJson) {
        try {
            return AESUtils.getInstance().encryptToBase64Str(dataJson, AES_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    // 解密
    public static String decryptData(String decodeData) {
        try {
            return AESUtils.getInstance(AES_KEY).decryptFromBase64Str(decodeData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    //验证签名
    public static String getSign() {
        CommParams commParams = new CommParams();
        StringBuilder sign = new StringBuilder()
                .append(commParams.getUuid())
                .append(commParams.getImei())
                .append(commParams.getImsi())
                .append(commParams.getDitchNo())
                .append(commParams.getAppId())
                .append(commParams.getVersionCode())
                .append(MD5Utils.MD5KEY);

        return MD5Utils.md5(sign.toString());
    }
}
