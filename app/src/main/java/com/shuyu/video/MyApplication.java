package com.shuyu.video;

import com.shuyu.core.CoreApplication;
import com.shuyu.core.api.BaseApi;
import com.shuyu.core.uils.LogUtils;
import com.shuyu.video.api.IMainApi;
import com.shuyu.video.db.helper.AppInfoHelper;
import com.shuyu.video.model.AppInfoListEntity;
import com.shuyu.video.model.AppStoreEntity;
import com.shuyu.video.utils.Constants;

import static com.shuyu.core.api.BaseApi.createApi;

/**
 * Created by zhangleilei on 8/31/16.
 */

public class MyApplication extends CoreApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        LogUtils.isDebug = Constants.IS_DEBUG;

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
        getAppStoreInfo();
    }

    private void getAppStoreInfo() {
        BaseApi.request(createApi(IMainApi.class).getAppStoreList(1),
                new BaseApi.IResponseListener<AppStoreEntity>() {
                    @Override
                    public void onSuccess(final AppStoreEntity data) {
                        AppInfoHelper.getHelper().deleteAll();
                        for (AppInfoListEntity entity : data.getAppInfoList()) {
                            AppInfoHelper.getHelper().addData(entity);
                        }
                    }

                    @Override
                    public void onFail() {

                    }
                });
    }
}
