package com.shuyu.video.model;

import java.util.List;

/**
 * Created by Azure on 2016/8/31.
 */

public class ChannelContent {


    /**
     * totalPageCount : 1
     * totalItemCount : 1
     * pageNo : 1
     * videoChannelList : [{"id":2091,"title":"综艺节目","imgUrl":null,"channelContentList":[{"title":"相亲才会赢_20160525_穿尖头皮鞋的电台主播","contentType":1,"subTitle":"尖头皮鞋大头皮鞋","id":500072,"description":"","imgUrl":"http://s.wasu.tv/mams/pic/201605/25/22/20160525225438506063b35b7.jpg","videoPageUrl":"http://www.wasu.cn/wap/Play/show/id/7526130?refer=souying","videoUrl":"http://183.131.82.139/play/E55B869DEA59AF3002CEDA99B5652DC4F842628A.mp4","isPage":"1","videoLength":2097,"fee":400,"feeTime":"00:00:00","feeRule":1,"isClip":1,"clipUrlList":[],"tags":"相亲,综艺","isTryplay":1,"tryPlayLength":0,"videoType":1}]}]
     */

    private int totalPageCount;
    private int totalItemCount;
    private int pageNo;
    /**
     * id : 2091
     * title : 综艺节目
     * imgUrl : null
     * channelContentList : [{"title":"相亲才会赢_20160525_穿尖头皮鞋的电台主播","contentType":1,"subTitle":"尖头皮鞋大头皮鞋","id":500072,"description":"","imgUrl":"http://s.wasu.tv/mams/pic/201605/25/22/20160525225438506063b35b7.jpg","videoPageUrl":"http://www.wasu.cn/wap/Play/show/id/7526130?refer=souying","videoUrl":"http://183.131.82.139/play/E55B869DEA59AF3002CEDA99B5652DC4F842628A.mp4","isPage":"1","videoLength":2097,"fee":400,"feeTime":"00:00:00","feeRule":1,"isClip":1,"clipUrlList":[],"tags":"相亲,综艺","isTryplay":1,"tryPlayLength":0,"videoType":1}]
     */

    private List<VideoChannelListBean> videoChannelList;

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

    public List<VideoChannelListBean> getVideoChannelList() {
        return videoChannelList;
    }

    public void setVideoChannelList(List<VideoChannelListBean> videoChannelList) {
        this.videoChannelList = videoChannelList;
    }

    public static class VideoChannelListBean {
        private int id;
        private String title;
        private Object imgUrl;
        /**
         * title : 相亲才会赢_20160525_穿尖头皮鞋的电台主播
         * contentType : 1
         * subTitle : 尖头皮鞋大头皮鞋
         * id : 500072
         * description :
         * imgUrl : http://s.wasu.tv/mams/pic/201605/25/22/20160525225438506063b35b7.jpg
         * videoPageUrl : http://www.wasu.cn/wap/Play/show/id/7526130?refer=souying
         * videoUrl : http://183.131.82.139/play/E55B869DEA59AF3002CEDA99B5652DC4F842628A.mp4
         * isPage : 1
         * videoLength : 2097
         * fee : 400
         * feeTime : 00:00:00
         * feeRule : 1
         * isClip : 1
         * clipUrlList : []
         * tags : 相亲,综艺
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
