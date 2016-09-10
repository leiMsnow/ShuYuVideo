package com.shuyu.video.model;

import java.util.List;

/**
 * Created by zhangleilei on 9/10/16.
 */

public class LiveVideoEntitiy {


    private Object totalPageCount;
    private Object pageNo;
    private int totalItemCount;

    private List<VideoDetails> nightVideoDetailList;

    public Object getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(Object totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public Object getPageNo() {
        return pageNo;
    }

    public void setPageNo(Object pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotalItemCount() {
        return totalItemCount;
    }

    public void setTotalItemCount(int totalItemCount) {
        this.totalItemCount = totalItemCount;
    }

    public List<VideoDetails> getNightVideoDetailList() {
        return nightVideoDetailList;
    }

    public void setNightVideoDetailList(List<VideoDetails> nightVideoDetailList) {
        this.nightVideoDetailList = nightVideoDetailList;
    }

}
