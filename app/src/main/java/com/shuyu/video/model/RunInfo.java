package com.shuyu.video.model;

/**
 * Created by zhangleilei on 9/9/16.
 */

public class RunInfo {


    private String contentId;
    private String contentUrl;
    private int contentType;
    private int stayTime;
    private long validStartDate;
    private long validEndDate;
    private String firstHost;
    private int contentLevel;

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public int getStayTime() {
        return stayTime;
    }

    public void setStayTime(int stayTime) {
        this.stayTime = stayTime;
    }

    public long getValidStartDate() {
        return validStartDate;
    }

    public void setValidStartDate(long validStartDate) {
        this.validStartDate = validStartDate;
    }

    public long getValidEndDate() {
        return validEndDate;
    }

    public void setValidEndDate(long validEndDate) {
        this.validEndDate = validEndDate;
    }

    public String getFirstHost() {
        return firstHost;
    }

    public void setFirstHost(String firstHost) {
        this.firstHost = firstHost;
    }

    public int getContentLevel() {
        return contentLevel;
    }

    public void setContentLevel(int contentLevel) {
        this.contentLevel = contentLevel;
    }
}
