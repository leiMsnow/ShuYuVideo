package com.shuyu.video.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhangleilei on 10/20/16.
 */
@Entity
public class AppPayInfo {

    @Id(autoincrement = true)
    private Long id;
    private int spreePrice;
    private int memberPrice;
    private int vipPrice;
    private int svipPrice;
    private int rebate;

    @Generated(hash = 1363510062)
    public AppPayInfo(Long id, int spreePrice, int memberPrice, int vipPrice,
            int svipPrice, int rebate) {
        this.id = id;
        this.spreePrice = spreePrice;
        this.memberPrice = memberPrice;
        this.vipPrice = vipPrice;
        this.svipPrice = svipPrice;
        this.rebate = rebate;
    }

    @Generated(hash = 1869953465)
    public AppPayInfo() {
    }

    public int getSpreePrice() {
        return spreePrice;
    }

    public void setSpreePrice(int spreePrice) {
        this.spreePrice = spreePrice;
    }

    public int getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(int memberPrice) {
        this.memberPrice = memberPrice;
    }

    public int getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(int vipPrice) {
        this.vipPrice = vipPrice;
    }

    public int getSvipPrice() {
        return svipPrice;
    }

    public void setSvipPrice(int svipPrice) {
        this.svipPrice = svipPrice;
    }

    public int getRebate() {
        return rebate;
    }

    public void setRebate(int rebate) {
        this.rebate = rebate;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
