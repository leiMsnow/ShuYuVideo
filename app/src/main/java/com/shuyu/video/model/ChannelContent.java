package com.shuyu.video.model;

import java.util.List;

/**
 * Created by Azure on 2016/8/31.
 */

public class ChannelContent {

    /**
     * id : 2080
     * title : 试看
     * imgUrl : null
     * channelContentList : [{"title":"吟笑派","contentType":1,"subTitle":"","id":3000069,"description":"文豪病中追忆似水流年","imgUrl":"http://appcdn.syingkj.com/video/video_img/xqtp024.jpg","videoPageUrl":"http://7xs6kc.media1.z0.glb.clouddn.com/506b43e8ba8c4ffe90c86682f6df3317.mp4","videoUrl":"http://183.131.82.139/play/E55B869DEA59AF3002CEDA99B5652DC4F842628A.mp4","isPage":"0","videoLength":325000,"fee":400,"feeTime":"00:00:00","feeRule":0,"isClip":1,"clipUrlList":[],"tags":"","isTryplay":1,"tryPlayLength":0,"videoType":1}]
     */
    private int id = 2080;
    private String title = "试看";
    private Object imgUrl;
    /**
     * title : 吟笑派
     * contentType : 1
     * subTitle :
     * id : 3000069
     * description : 文豪病中追忆似水流年
     * imgUrl : http://appcdn.syingkj.com/video/video_img/xqtp024.jpg
     * videoPageUrl : http://7xs6kc.media1.z0.glb.clouddn.com/506b43e8ba8c4ffe90c86682f6df3317.mp4
     * videoUrl : http://183.131.82.139/play/E55B869DEA59AF3002CEDA99B5652DC4F842628A.mp4
     * isPage : 0
     * videoLength : 325000
     * fee : 400
     * feeTime : 00:00:00
     * feeRule : 0
     * isClip : 1
     * clipUrlList : []
     * tags :
     * isTryplay : 1
     * tryPlayLength : 0
     * videoType : 1
     */

    private List<ChannelContentListBean> channelContentList;

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

    public Object getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(Object imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<ChannelContentListBean> getChannelContentList() {
        return channelContentList;
    }

    public void setChannelContentList(List<ChannelContentListBean> channelContentList) {
        this.channelContentList = channelContentList;
    }

    public static class ChannelContentListBean {
        private String title = "吟笑派";
        private int contentType;
        private String subTitle;
        private int id;
        private String description = "文豪病中追忆似水流年";
        private String imgUrl = "http://appcdn.syingkj.com/video/video_img/xqtp024.jpg";
        private String videoPageUrl;
        private String videoUrl = "http://183.131.82.139/play/E55B869DEA59AF3002CEDA99B5652DC4F842628A.mp4";
        private String isPage;
        private int videoLength;
        private int fee;
        private String feeTime;
        private int feeRule;
        private int isClip;
        private String tags;
        private int isTryplay;
        private int tryPlayLength;
        private int videoType;
        private List<?> clipUrlList;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getContentType() {
            return contentType;
        }

        public void setContentType(int contentType) {
            this.contentType = contentType;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
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

        public String getIsPage() {
            return isPage;
        }

        public void setIsPage(String isPage) {
            this.isPage = isPage;
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

        public int getIsClip() {
            return isClip;
        }

        public void setIsClip(int isClip) {
            this.isClip = isClip;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
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

        public int getVideoType() {
            return videoType;
        }

        public void setVideoType(int videoType) {
            this.videoType = videoType;
        }

        public List<?> getClipUrlList() {
            return clipUrlList;
        }

        public void setClipUrlList(List<?> clipUrlList) {
            this.clipUrlList = clipUrlList;
        }
    }
}
