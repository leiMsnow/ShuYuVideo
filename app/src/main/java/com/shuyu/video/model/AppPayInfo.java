package com.shuyu.video.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by zhangleilei on 10/20/16.
 */
@Entity
public class AppPayInfo {

    @Id
    private String appId;
    private String title;
    private float spreePrice;
    private float memberPrice;
    private float vipPrice;
    private float svipPrice;
    private double rebate;
    private String packageName;
    private String versionName;
    private String versionNo;

    @Generated(hash = 1545061232)
    public AppPayInfo(String appId, String title, float spreePrice,
                      float memberPrice, float vipPrice, float svipPrice, double rebate,
                      String packageName, String versionName, String versionNo) {
        this.appId = appId;
        this.title = title;
        this.spreePrice = spreePrice;
        this.memberPrice = memberPrice;
        this.vipPrice = vipPrice;
        this.svipPrice = svipPrice;
        this.rebate = rebate;
        this.packageName = packageName;
        this.versionName = versionName;
        this.versionNo = versionNo;
    }

    @Generated(hash = 1869953465)
    public AppPayInfo() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public float getSpreePrice() {
        return spreePrice;
    }

    public void setSpreePrice(float spreePrice) {
        this.spreePrice = spreePrice;
    }

    public float getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(float memberPrice) {
        this.memberPrice = memberPrice;
    }

    public float getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(float vipPrice) {
        this.vipPrice = vipPrice;
    }

    public float getSvipPrice() {
        return svipPrice;
    }

    public void setSvipPrice(float svipPrice) {
        this.svipPrice = svipPrice;
    }

    public double getRebate() {
        if (rebate <= 0 || rebate >= 1)
            rebate = 1;
        return rebate;
    }

    public void setRebate(double rebate) {
        this.rebate = rebate;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }
}
