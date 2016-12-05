package com.shuyu.video.model;

import android.support.v7.app.AppCompatActivity;

import org.json.JSONObject;

/**
 * Created by zhangleilei on 10/26/16.
 */

public class OrderInfo {

    private AppCompatActivity context;
    private String orderId;
    private String orderName;
    private double price;
    private String partnerId;
    private String key;
    private JSONObject mPaymentParams;
    private String callBackUrl;
    private String payUrl;

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    public String getCallBackUrl() {
        return callBackUrl;
    }

    public void setCallBackUrl(String callBackUrl) {
        this.callBackUrl = callBackUrl;
    }

    public OrderInfo(AppCompatActivity context) {
        this.context = context;
    }

    public AppCompatActivity getContext() {
        return context;
    }

    public void setContext(AppCompatActivity context) {
        this.context = context;
    }

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

    public JSONObject getPaymentParams() {
        return mPaymentParams;
    }

    public void setPaymentParams(JSONObject paymentParams) {
        mPaymentParams = paymentParams;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "context=" + context +
                ", orderId='" + orderId + '\'' +
                ", orderName='" + orderName + '\'' +
                ", price=" + price +
                ", partnerId='" + partnerId + '\'' +
                ", key='" + key + '\'' +
                ", mPaymentParams=" + mPaymentParams +
                ", callBackUrl='" + callBackUrl + '\'' +
                ", payUrl='" + payUrl + '\'' +
                '}';
    }
}
