package com.shuyu.video.db.helper;

import com.shuyu.core.CoreApplication;
import com.shuyu.video.db.dao.DaoMaster;
import com.shuyu.video.db.dao.DaoSession;

/**
 * 数据库管理类
 */
public class DBManager {

    private static DBManager instance;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private static final String DATABASE_NAME = "DATABASE_NAME-DB";

    public static DBManager getInstance() {
        if (instance == null) {
            synchronized (DBManager.class) {
                if (instance == null) {
                    instance = new DBManager();
                }
            }
        }
        return instance;
    }

    private DBManager() {
        if (daoSession == null) {
            if (daoMaster == null) {
                UpdateOpenHelper helper = new UpdateOpenHelper(
                        CoreApplication.getApplication().getApplicationContext(), DATABASE_NAME, null);
                daoMaster = new DaoMaster(helper.getWritableDatabase());
            }
            daoSession = daoMaster.newSession();
        }
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

}
