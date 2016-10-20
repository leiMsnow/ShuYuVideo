package com.shuyu.video.db.helper;

import android.text.TextUtils;

import com.shuyu.core.db.BaseDaoHelper;
import com.shuyu.video.db.dao.AppStoreDao;
import com.shuyu.video.model.AppStore;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Created by Azure on 2016/9/15.
 */
public class AppStoreDaoHelper extends BaseDaoHelper<AppStoreDao, AppStore> {

    private static AppStoreDaoHelper helper;
    private AppStoreDaoHelper() {
        super(DBManager.getInstance().getDaoSession().getAppStoreDao());
    }

    public static AppStoreDaoHelper getHelper() {
        if (helper == null) {
            synchronized (AppStoreDaoHelper.class) {
                if (helper == null) {
                    helper = new AppStoreDaoHelper();
                }
            }
        }
        return helper;
    }

    @Override
    protected AppStore getDataInfoById(String id) {
        if (checkDaoNotNull() && !TextUtils.isEmpty(id)) {
            return tableDao.load(Long.parseLong(id));
        }
        return null;
    }

    @Override
    protected boolean hasKeyById(String id) {
        if (checkDaoNotNull() && !TextUtils.isEmpty(id)) {
            QueryBuilder<AppStore> qb = tableDao.queryBuilder();
            qb.where(AppStoreDao.Properties.Id.eq(id));
            long count = qb.buildCount().count();
            return count > 0;
        }
        return false;
    }
}
