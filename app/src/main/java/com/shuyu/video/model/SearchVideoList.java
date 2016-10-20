package com.shuyu.video.model;

import java.util.List;

/**
 * Created by zhangleilei on 9/10/16.
 */

public class SearchVideoList {


    private int totalPageCount;
    private int pageNo;
    private int totalItemCount;

    private List<VideoPicDetails> channelContentList;

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotalItemCount() {
        return totalItemCount;
    }

    public void setTotalItemCount(int totalItemCount) {
        this.totalItemCount = totalItemCount;
    }

    public List<VideoPicDetails> getChannelContentList() {
        return channelContentList;
    }

    public void setChannelContentList(List<VideoPicDetails> channelContentList) {
        this.channelContentList = channelContentList;
    }

}
