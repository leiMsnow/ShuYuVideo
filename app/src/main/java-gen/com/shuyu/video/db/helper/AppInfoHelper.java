package com.shuyu.video.db.helper;

import android.text.TextUtils;

import com.shuyu.core.db.BaseDaoHelper;
import com.shuyu.video.db.dao.AppInfoListEntityDao;
import com.shuyu.video.model.AppInfoListEntity;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Created by Azure on 2016/9/15.
 */
public class AppInfoHelper extends BaseDaoHelper<AppInfoListEntityDao, AppInfoListEntity> {

    private static AppInfoHelper helper;
    private AppInfoHelper() {
        super(DBManager.getInstance().getDaoSession().getAppInfoListEntityDao());
    }

    public static AppInfoHelper getHelper() {
        if (helper == null) {
            synchronized (AppInfoHelper.class) {
                if (helper == null) {
                    helper = new AppInfoHelper();
                }
            }
        }
        return helper;
    }

    @Override
    protected AppInfoListEntity getDataInfoById(String id) {
        if (checkDaoNotNull() && !TextUtils.isEmpty(id)) {
            return tableDao.load(Long.parseLong(id));
        }
        return null;
    }

    @Override
    protected boolean hasKeyById(String id) {
        if (checkDaoNotNull() && !TextUtils.isEmpty(id)) {
            QueryBuilder<AppInfoListEntity> qb = tableDao.queryBuilder();
            qb.where(AppInfoListEntityDao.Properties.Id.eq(id));
            long count = qb.buildCount().count();
            return count > 0;
        }
        return false;
    }
}
