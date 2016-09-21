package com.shuyu.video.model;

/**
 * Created by zhangleilei on 9/21/16.
 */

public class Feedback {

    int qType;
    String content;
    String contactWay;

    public Feedback(int qType, String content, String contactWay) {
        this.qType = qType;
        this.content = content;
        this.contactWay = contactWay;
    }
}
