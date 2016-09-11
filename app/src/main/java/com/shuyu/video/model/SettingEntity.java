package com.shuyu.video.model;

/**
 * Created by Azure on 2016/9/11.
 */

public class SettingEntity {

    private String title;
    private int resId;

    public SettingEntity(int resId, String title) {
        this.resId = resId;
        this.title = title;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
