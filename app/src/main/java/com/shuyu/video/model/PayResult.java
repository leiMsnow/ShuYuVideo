package com.shuyu.video.model;

/**
 * Created by zhangleilei on 11/2/16.
 */

public class PayResult {

    /**
     * 订单状态：订单状态 0-未支付 1-支付成功 2-支付失败 3-已取消 4-已退款 5-其他
     */
    public static final int PAY_STATE_NO_PAY = 0;
    public static final int PAY_STATE_SUCCESS = 1;
    public static final int PAY_STATE_FAIL = 2;
    public static final int PAY_STATE_CANCEL = 3;
    public static final int PAY_STATE_REFUND = 4;
    public static final int PAY_STATE_OTHER = 5;

    private String orderNo;
    private String payName;
    private String userName;
    private double payMoney;
    private String payPoint;
    private String appId;
    private String payCode;
    private int payState;
    private Object failReason;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
    }

    public String getPayPoint() {
        return payPoint;
    }

    public void setPayPoint(String payPoint) {
        this.payPoint = payPoint;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public int getPayState() {
        return payState;
    }

    public void setPayState(int payState) {
        this.payState = payState;
    }

    public Object getFailReason() {
        return failReason;
    }

    public void setFailReason(Object failReason) {
        this.failReason = failReason;
    }
}
