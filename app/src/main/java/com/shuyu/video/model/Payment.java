package com.shuyu.video.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhangleilei on 10/21/16.
 */
public class Payment implements Parcelable {

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

    protected Payment(Parcel in) {
        id = in.readInt();
        title = in.readString();
        payType = in.readInt();
        payCompanyCode = in.readString();
        payCode = in.readString();
        payBean = in.readString();
        partnerId = in.readString();
        md5Key = in.readString();
        notifyUrl1 = in.readString();
        notifyUrl2 = in.readString();
        remark = in.readString();
        payUrl = in.readString();
        params = in.readString();
    }

    public static final Creator<Payment> CREATOR = new Creator<Payment>() {
        @Override
        public Payment createFromParcel(Parcel in) {
            return new Payment(in);
        }

        @Override
        public Payment[] newArray(int size) {
            return new Payment[size];
        }
    };

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

        try {
            mJsonParams = new JSONObject(params);
        } catch (JSONException e) {
            e.printStackTrace();
            mJsonParams = new JSONObject();
        }
        return mJsonParams;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeInt(payType);
        dest.writeString(payCompanyCode);
        dest.writeString(payCode);
        dest.writeString(payBean);
        dest.writeString(partnerId);
        dest.writeString(md5Key);
        dest.writeString(notifyUrl1);
        dest.writeString(notifyUrl2);
        dest.writeString(remark);
        dest.writeString(payUrl);
        dest.writeString(params);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", payType=" + payType +
                ", payCompanyCode='" + payCompanyCode + '\'' +
                ", payCode='" + payCode + '\'' +
                ", payBean='" + payBean + '\'' +
                ", partnerId='" + partnerId + '\'' +
                ", md5Key='" + md5Key + '\'' +
                ", notifyUrl1='" + notifyUrl1 + '\'' +
                ", notifyUrl2='" + notifyUrl2 + '\'' +
                ", remark='" + remark + '\'' +
                ", payUrl='" + payUrl + '\'' +
                ", params='" + params + '\'' +
                ", mJsonParams=" + mJsonParams +
                '}';
    }
}
