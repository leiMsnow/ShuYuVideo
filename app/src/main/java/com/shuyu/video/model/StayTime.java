package com.shuyu.video.model;

/**
 * Created by zhangleilei on 9/21/16.
 */

public class StayTime {

    long onTime;
    long offTime;
    String thirdChannelId;

    public StayTime(long onTime, long offTime, String thirdChannelId) {
        this.onTime = onTime;
        this.offTime = offTime;
        this.thirdChannelId = thirdChannelId;
    }
}
