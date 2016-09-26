package com.shuyu.video.model;

import java.io.Serializable;
import java.util.List;

public class SubChannel implements Serializable {
    private int id;
    private String title;
    private Object imgUrl;

    private List<VideoPicDetails> channelContentList;

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

    public List<VideoPicDetails> getChannelContentList() {
        return channelContentList;
    }

    public void setChannelContentList(List<VideoPicDetails> channelContentList) {
        this.channelContentList = channelContentList;
    }

    public void updateContentList() {
        if (channelContentList == null) return;
        for (VideoPicDetails videoPicDetails : channelContentList) {
            videoPicDetails.setGroupIdd(id);
            videoPicDetails.setGroupTitle(title);
        }
    }

}