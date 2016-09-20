package com.shuyu.video.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Azure on 2016/9/11.
 */

public class SettingEntity {

    public static final int RECOMMEND = 0;
    public static final int CLEAR = 1;
    public static final int FEEDBACK = 2;
    public static final int UPDATE = 3;
    public static final int ABOUT = 4;
    public static final int DISCLAIMER = 5;
    public static final int SERVICE = 6;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({RECOMMEND,CLEAR,FEEDBACK,UPDATE,ABOUT,DISCLAIMER,SERVICE})
    public @interface SettingType{}

    private  int setId;
    private int resId;
    private String title;

    public SettingEntity(@SettingType int setId, int resId, String title) {
        this.setId = setId;
        this.resId = resId;
        this.title = title;
    }

    public int getSetId() {
        return setId;
    }

    public void setSetId(int setId) {
        this.setId = setId;
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
