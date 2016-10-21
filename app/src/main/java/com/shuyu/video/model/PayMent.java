package com.shuyu.video.model;

/**
 * Created by zhangleilei on 10/21/16.
 */

public class PayMent {

    private String title;
    private int payType;
    private String payCompanyCode;
    private String payCode;
    private String payBean;
    private String partnerId;
    private String md5Key;
    private String notifyUrl1;
    private String notifyUrl2;
    private String remark;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getPayCompanyCode() {
        return payCompanyCode;
    }

    public void setPayCompanyCode(String payCompanyCode) {
        this.payCompanyCode = payCompanyCode;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getPayBean() {
        return payBean;
    }

    public void setPayBean(String payBean) {
        this.payBean = payBean;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getMd5Key() {
        return md5Key;
    }

    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key;
    }

    public String getNotifyUrl1() {
        return notifyUrl1;
    }

    public void setNotifyUrl1(String notifyUrl1) {
        this.notifyUrl1 = notifyUrl1;
    }

    public String getNotifyUrl2() {
        return notifyUrl2;
    }

    public void setNotifyUrl2(String notifyUrl2) {
        this.notifyUrl2 = notifyUrl2;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
