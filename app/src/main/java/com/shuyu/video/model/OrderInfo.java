package com.shuyu.video.model;

import android.support.v7.app.AppCompatActivity;

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

}
