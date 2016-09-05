package com.shuyu.video.model;

/**
 * Created by zhangleilei on 8/31/16.
 */

public class ChannelBanner {


    /**
     * title :
     * bannerType : 3
     * targetId : 46
     * targetUrl :
     * imgUrl : http://appstoreimg.weichuanji.com/20160729/429b9dc7-c46b-42c1-bcb7-ddb13ab848db-7.png
     * videoLength : 0
     * fee : 0
     * feeTime : 00:00:00
     * feeRule : 0
     * isPage : 0
     * videoType : 0
     * isTryPlay : 0
     * tryPlayLength : 0
     * softwareSize : 0
     * packageName :
     * md5 :
     * appName : null
     * versionCode : null
     */

    private String title = "title";
    private int bannerType;
    private int targetId;
    private String targetUrl;
    private String imgUrl = "http://s.wasu.tv/mams/pic/201605/19/23/2016051923041274354e52867.jpg";
    private int videoLength;
    private int fee;
    private String feeTime;
    private int feeRule;
    private String isPage;
    private int videoType;
    private int isTryPlay;
    private int tryPlayLength;
    private int softwareSize;
    private String packageName;
    private String md5;
    private Object appName;
    private Object versionCode;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBannerType() {
        return bannerType;
    }

    public void setBannerType(int bannerType) {
        this.bannerType = bannerType;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getVideoLength() {
        return videoLength;
    }

    public void setVideoLength(int videoLength) {
        this.videoLength = videoLength;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public String getFeeTime() {
        return feeTime;
    }

    public void setFeeTime(String feeTime) {
        this.feeTime = feeTime;
    }

    public int getFeeRule() {
        return feeRule;
    }

    public void setFeeRule(int feeRule) {
        this.feeRule = feeRule;
    }

    public String getIsPage() {
        return isPage;
    }

    public void setIsPage(String isPage) {
        this.isPage = isPage;
    }

    public int getVideoType() {
        return videoType;
    }

    public void setVideoType(int videoType) {
        this.videoType = videoType;
    }

    public int getIsTryPlay() {
        return isTryPlay;
    }

    public void setIsTryPlay(int isTryPlay) {
        this.isTryPlay = isTryPlay;
    }

    public int getTryPlayLength() {
        return tryPlayLength;
    }

    public void setTryPlayLength(int tryPlayLength) {
        this.tryPlayLength = tryPlayLength;
    }

    public int getSoftwareSize() {
        return softwareSize;
    }

    public void setSoftwareSize(int softwareSize) {
        this.softwareSize = softwareSize;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Object getAppName() {
        return appName;
    }

    public void setAppName(Object appName) {
        this.appName = appName;
    }

    public Object getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Object versionCode) {
        this.versionCode = versionCode;
    }


    @Override
    public String toString() {
        return "ChannelBanner{" +
                "title='" + title + '\'' +
                ", bannerType=" + bannerType +
                ", targetId=" + targetId +
                ", targetUrl='" + targetUrl + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", videoLength=" + videoLength +
                ", fee=" + fee +
                ", feeTime='" + feeTime + '\'' +
                ", feeRule=" + feeRule +
                ", isPage='" + isPage + '\'' +
                ", videoType=" + videoType +
                ", isTryPlay=" + isTryPlay +
                ", tryPlayLength=" + tryPlayLength +
                ", softwareSize=" + softwareSize +
                ", packageName='" + packageName + '\'' +
                ", md5='" + md5 + '\'' +
                ", appName=" + appName +
                ", versionCode=" + versionCode +
                '}';
    }
}
