package com.shuyu.video.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Azure on 2016/8/31.
 */

public class ChannelEntity {


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

    public static class VideoChannel implements Serializable {
        private int id;
        private String title;
        private Object imgUrl;

        private List<VideoDetails> channelContentList;

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

        public List<VideoDetails> getChannelContentList() {
            return channelContentList;
        }

        public void setChannelContentList(List<VideoDetails> channelContentList) {
            this.channelContentList = channelContentList;
        }

    }
}
