package com.shuyu.video;

import com.shuyu.core.CoreApplication;
import com.shuyu.core.MCrashHandler;
import com.shuyu.core.uils.LogUtils;
import com.shuyu.core.uils.SPUtils;
import com.shuyu.video.utils.Constants;

/**
 * Created by zhangleilei on 8/31/16.
 */

public class MyApplication extends CoreApplication {

    @Override
    public void onCreate() {
        MCrashHandler.getInstance().init(this);
        super.onCreate();
        LogUtils.isDebug = Constants.IS_DEBUG;

        SPUtils.put(this,Constants.STAY_TIME_ON,System.currentTimeMillis());

        // 加密
//         AESUtils.getInstance().encryptToBase64Str(json,aesKey)

        // 解密
        // AESUtils aesUtils = AESUtils.getInstance(aesKey);
        // String decryptData = AESUtils.decryptFromBase64Str(decodeData);
//        AESkey=!#@ishuyu@2016omc
//        MD5Key=@2016yu_shu_ai@!#

        //验证签名
//        String signRaw = (uuid==null?"":uuid) + (imei==null?"":imei) + (imsi==null?"":imsi) + (ditchNo==null?"":ditchNo)
//                + (appId==null?"":appId) + (versionCode==null?"":versionCode) + MMYSEncryptUtil.MD5Key_NEW;
//        String calculateMd5Sign = MMYSEncryptUtil.md5(signRaw);
    }
}
