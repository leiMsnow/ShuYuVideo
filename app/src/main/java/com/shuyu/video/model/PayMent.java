package com.shuyu.video.model;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhangleilei on 10/21/16.
 */
public class Payment {

    private int id;
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
    private String payUrl;
    private String params;
    private JSONObject mJsonParams;

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

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    public JSONObject getPaymentParams() {
        if (mJsonParams != null)
            return mJsonParams;
        if (TextUtils.isEmpty(params)) {
            return null;
        }

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonArray != null) {
            mJsonParams = jsonArray.optJSONObject(0);
        }
        return mJsonParams;
    }

    public void setParams(String params) {
        this.params = params;
    }

//    public static class PaymentParams {
//
//        private String appCode;
//        private String channelCode;
//        private String chargCode;
//        private String payNum;
//
//        public String getAppCode() {
//            return appCode;
//        }
//
//        public void setAppCode(String appCode) {
//            this.appCode = appCode;
//        }
//
//        public String getChannelCode() {
//            return channelCode;
//        }
//
//        public void setChannelCode(String channelCode) {
//            this.channelCode = channelCode;
//        }
//
//        public String getChargCode() {
//            return chargCode;
//        }
//
//        public void setChargCode(String chargCode) {
//            this.chargCode = chargCode;
//        }
//
//        public String getPayNum() {
//            return payNum;
//        }
//
//        public void setPayNum(String payNum) {
//            this.payNum = payNum;
//        }
//    }

}
