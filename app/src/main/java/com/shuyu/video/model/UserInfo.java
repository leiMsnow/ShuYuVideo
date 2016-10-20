package com.shuyu.video.model;

/**
 * Created by zhangleilei on 10/20/16.
 */

public class UserInfo {

    private String userName;
    private int userType;
    private int totalMoney;
    private String userTypeShow;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getUserTypeShow() {
        return userTypeShow;
    }

    public void setUserTypeShow(String userTypeShow) {
        this.userTypeShow = userTypeShow;
    }
}
