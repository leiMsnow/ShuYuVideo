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
    }
}
