package com.shuyu.video.model;

public class AppInfoListEntity extends DownloadEntity {
    private int id;
    private String title;
    private String brief;
    private String summary;
    private String downloadUrl;
    private String iconUrl;
    private String versionCode;
    private String versionName;
    private String packageName;
    private int softwareSize;
    private int appType;
    private String md5;
    private Object downloadCount;
    private String imgUrl;
    private int downloadType;
    private int onlineInterval;
    private int notifyCount;
    private int notifyInterval;
    private Object adImgUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getSoftwareSize() {
        return softwareSize;
    }

    public void setSoftwareSize(int softwareSize) {
        this.softwareSize = softwareSize;
    }

    public int getAppType() {
        return appType;
    }

    public void setAppType(int appType) {
        this.appType = appType;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Object getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Object downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getDownloadType() {
        return downloadType;
    }

    public void setDownloadType(int downloadType) {
        this.downloadType = downloadType;
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

    public Object getAdImgUrl() {
        return adImgUrl;
    }

    public void setAdImgUrl(Object adImgUrl) {
        this.adImgUrl = adImgUrl;
    }
}