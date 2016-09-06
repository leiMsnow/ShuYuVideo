package com.shuyu.core;

import android.app.Application;

/**
 * Created by zhangleilei on 8/31/16.
 */

public class CoreApplication extends Application {

    private static CoreApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }


    public static CoreApplication getApplication() {
        return mApplication;
    }

}
