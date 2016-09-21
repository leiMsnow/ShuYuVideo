package com.shuyu.video.model;

/**
 * Created by Azure on 2016/9/17.
 */

public class ResultEntity {

    public static final String BUSI_SUCCESS = "0000";// 操作成功
    public static final String BUSI_ERROR = "0001";// 业务错误
    public static final String PARAM_ERROR = "0002";// 参数错误
    public static final String DB_ERROR = "0003";// 数据库错误
    public static final String KEY_ERROR = "0004";// 秘钥错误
    public static final String UNKNOW_ERROR = "0008";// 未知错误
    public static final String SYS_ERROR = "0009";//系统错误
    public static final String DATA_EXIST_ERROR = "0005";//数据已经存在

    private String resultCode;

    public String getResultCode() {
        return getResultMessage(resultCode);
    }


    private String getResultMessage(String resultCode) {
        return resultCode;
    }
}
