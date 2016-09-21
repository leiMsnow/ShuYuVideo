package com.shuyu.video.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Azure on 2016/9/17.
 */

public class ResultEntity {


    private static final Map<String, String> resultMessage = new HashMap<>();

    {
        resultMessage.put("0000", "操作成功");
        resultMessage.put("0001", "业务错误");
        resultMessage.put("0002", "参数错误");
        resultMessage.put("0003", "数据库错误");
        resultMessage.put("0004", "密钥错误");
        resultMessage.put("0005", "数据已存在");
        resultMessage.put("0008", "未知错误");
        resultMessage.put("0009", "系统错误");

    }

    private String resultCode;
    public String getResultCode() {
        return getResultMessage(resultCode);
    }

    private static String getResultMessage(String resultCode) {
        return resultMessage.get(resultCode);
    }
}
