package com.shuyu.video.model;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

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
    private int onlineInterval;
    private int notifyCount;
    private int notifyInterval;
    private String wapUrl;
    private String blackVersion;
    private String params;
    private JSONObject mJsonParams;

    public JSONObject getParams() {
        if (mJsonParams != null)
            return mJsonParams;
        if (TextUtils.isEmpty(params)) {
            return null;
        }

        try {
            mJsonParams = new JSONObject(params);
        } catch (JSONException e) {
            e.printStackTrace();
            mJsonParams = new JSONObject();
        }
        return mJsonParams;
    }

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

    public int getOnlineInterval() {
        return onlineInterval;
    }

    public void setOnlineInterval(int onlineInterval) {
        this.onlineInterval = onlineInterval;
    }

    public int getNotifyCount() {
        return notifyCount;
    }

    public void setNotifyCount(int notifyCount) {
        this.notifyCount = notifyCount;
    }

    public int getNotifyInterval() {
        return notifyInterval;
    }

    public void setNotifyInterval(int notifyInterval) {
        this.notifyInterval = notifyInterval;
    }

    public String getWapUrl() {
        return wapUrl;
    }

    public void setWapUrl(String wapUrl) {
        this.wapUrl = wapUrl;
    }

    public String getBlackVersion() {
        return blackVersion;
    }

    public void setBlackVersion(String blackVersion) {
        this.blackVersion = blackVersion;
    }
}
