package com.shuyu.video.utils;

import android.text.TextUtils;

/**
 * @author zhangbo
 * @Description: 获取运营商
 */
public enum NetTypeEnumUtils {
    //460代表中国
    //中国移动系统使用00、02、07，中国联通GSM系统使用01、06，中国电信CDMA系统使用03、05、电信4G使用11，中国铁通系统使用20。
    MOBILE("移动", "1", new String[]{"46000", "46002", "46007", "46020"}),

    UNICOM("联通", "2", new String[]{"46001", "46006", "46010"}),

    TELECOM("电信", "3", new String[]{"46003", "46005", "46011", "20404"});//45404

    private String message;

    private String value;

    private String[] netCodes;

    private NetTypeEnumUtils(String message, String value, String[] netCodes) {
        this.message = message;
        this.value = value;
        this.netCodes = netCodes;
    }


    public String message() {
        return message;
    }


    public String[] netCodes() {
        return netCodes;
    }


    public String value() {
        return value;
    }

    /**
     * 根据imsi获取指定运营商类型
     *
     * @param imsi
     * @return
     */
    public static NetTypeEnumUtils getNettypeByImsi(String imsi) {
        if (!TextUtils.isEmpty(imsi)) {
            for (NetTypeEnumUtils nettypeEnum : NetTypeEnumUtils.values()) {
                for (String code : nettypeEnum.netCodes()) {
                    if (imsi.startsWith(code)) {
                        return nettypeEnum;
                    }
                }
            }
        }

        return null;
    }

    public static String getNettypeValueByImsi(String imsi) {
        NetTypeEnumUtils nettype = getNettypeByImsi(imsi);
        if (nettype != null) {
            return nettype.value();
        }
        return null;
    }

}