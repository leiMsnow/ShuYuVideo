package com.shuyu.video.model;

/**
 * Created by Azure on 2016/8/31.
 */

public class ChannelTitle {
    /**
     * id : 1011
     * title : 华北
     * channelType : 2
     * imgUrl : null
     * isRmd : 1
     */
    private int id;
    private String title;
    private int channelType;
    private Object imgUrl;
    private int isRmd;

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

    public int getChannelType() {
        return channelType;
    }

    public void setChannelType(int channelType) {
        this.channelType = channelType;
    }

    public Object getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(Object imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getIsRmd() {
        return isRmd;
    }

    public void setIsRmd(int isRmd) {
        this.isRmd = isRmd;
    }

    @Override
    public String toString() {
        return "ChannelTitle{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", channelType=" + channelType +
                ", imgUrl=" + imgUrl +
                ", isRmd=" + isRmd +
                '}';
    }
}
