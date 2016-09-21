package com.shuyu.core.model;

import android.os.Build;

import com.shuyu.core.uils.AppUtils;

/**
 * Created by Azure on 2016/9/21.
 */

public class CommParams {

    private String imsi = AppUtils.getIMSI();
    private String imei = AppUtils.getIMEI();
    private String manufacturer = Build.BRAND;
    private String model =  Build.MODEL;
    private String versionCode = "10101";
    private int appId = 1001;
    private String dcVersion = "000002";
    private String ditchNo = "0";
    private String uuid = AppUtils.getUUID();

    public String getImsi() {
        return imsi;
    }

    public String getImei() {
        return imei;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public int getAppId() {
        return appId;
    }

    public String getDcVersion() {
        return dcVersion;
    }

    public String getDitchNo() {
        return ditchNo;
    }

    public String getUuid() {
        return uuid;
    }
}
