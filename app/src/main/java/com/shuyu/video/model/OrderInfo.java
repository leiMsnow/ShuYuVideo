package com.shuyu.video.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangleilei on 10/26/16.
 */

public class OrderInfo implements Parcelable{

    private String orderId;
    private String orderName;
    private double price;
    private String partnerId;
    private String key;

    public OrderInfo() {
    }

    protected OrderInfo(Parcel in) {
        orderId = in.readString();
        orderName = in.readString();
        price = in.readDouble();
        partnerId = in.readString();
        key = in.readString();
    }

    public static final Creator<OrderInfo> CREATOR = new Creator<OrderInfo>() {
        @Override
        public OrderInfo createFromParcel(Parcel in) {
            return new OrderInfo(in);
        }

        @Override
        public OrderInfo[] newArray(int size) {
            return new OrderInfo[size];
        }
    };

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderId);
        dest.writeString(orderName);
        dest.writeDouble(price);
        dest.writeString(partnerId);
        dest.writeString(key);
    }
}
