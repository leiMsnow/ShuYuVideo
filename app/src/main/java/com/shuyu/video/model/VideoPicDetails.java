package com.shuyu.video.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangleilei on 9/10/16.
 */

public class VideoPicDetails implements Serializable {

    private int id;
    private String title;
    private int contentType;
    private String videoPageUrl;
    private String videoUrl;
    private int videoLength;
    private String imgUrl;
    private String tags;
    private String subTitle;
    private String description;
    private int isTryplay;
    private int tryPlayLength;
    private String isPage;
    private int fee;
    private String feeTime;
    private int feeRule;
    private int isClip;
    private List<?> clipUrlList;

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

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

    public String getVideoPageUrl() {
        return videoPageUrl;
    }

    public void setVideoPageUrl(String videoPageUrl) {
        this.videoPageUrl = videoPageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getVideoLength() {
        return videoLength;
    }

    public void setVideoLength(int videoLength) {
        this.videoLength = videoLength;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsTryplay() {
        return isTryplay;
    }

    public void setIsTryplay(int isTryplay) {
        this.isTryplay = isTryplay;
    }

    public int getTryPlayLength() {
        return tryPlayLength;
    }

    public void setTryPlayLength(int tryPlayLength) {
        this.tryPlayLength = tryPlayLength;
    }

    public String getIsPage() {
        return isPage;
    }

    public void setIsPage(String isPage) {
        this.isPage = isPage;
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

    public int getIsClip() {
        return isClip;
    }

    public void setIsClip(int isClip) {
        this.isClip = isClip;
    }

    public List<?> getClipUrlList() {
        return clipUrlList;
    }

    public void setClipUrlList(List<?> clipUrlList) {
        this.clipUrlList = clipUrlList;
    }
}