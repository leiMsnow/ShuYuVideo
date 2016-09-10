package com.shuyu.video.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Azure on 2016/8/31.
 */

public class ChannelData implements Serializable{


    private int totalPageCount;
    private int totalItemCount;
    private int pageNo;

    private List<VideoChannel> videoChannelList;

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public int getTotalItemCount() {
        return totalItemCount;
    }

    public void setTotalItemCount(int totalItemCount) {
        this.totalItemCount = totalItemCount;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public List<VideoChannel> getVideoChannelList() {
        return videoChannelList;
    }

    public void setVideoChannelList(List<VideoChannel> videoChannelList) {
        this.videoChannelList = videoChannelList;
    }

    public static class VideoChannel implements Serializable{
        private int id;
        private String title;
        private Object imgUrl;

        private List<ChannelContent> channelContentList;

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

        public List<ChannelContent> getChannelContentList() {
            return channelContentList;
        }

        public void setChannelContentList(List<ChannelContent> channelContentList) {
            this.channelContentList = channelContentList;
        }

        public static class ChannelContent implements Serializable{
            private String title;
            private int contentType;
            private String subTitle;
            private int id;
            private String description;
            private String imgUrl;
            private String videoPageUrl;
            private String videoUrl;
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
}
