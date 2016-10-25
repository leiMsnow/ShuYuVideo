package com.shuyu.video.db.helper;

import android.text.TextUtils;

import com.shuyu.core.db.BaseDaoHelper;
import com.shuyu.video.db.dao.AppPayInfoDao;
import com.shuyu.video.model.AppPayInfo;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Created by Azure on 2016/9/15.
 */
public class AppPayInfoDaoHelper extends BaseDaoHelper<AppPayInfoDao, AppPayInfo> {

    private static AppPayInfoDaoHelper helper;
    private AppPayInfoDaoHelper() {
        super(DBManager.getInstance().getDaoSession().getAppPayInfoDao());
    }

    public static AppPayInfoDaoHelper getHelper() {
        if (helper == null) {
            synchronized (AppPayInfoDaoHelper.class) {
                if (helper == null) {
                    helper = new AppPayInfoDaoHelper();
                }
            }
        }
        return helper;
    }

    @Override
    protected AppPayInfo getDataInfoById(String id) {
        if (checkDaoNotNull() && !TextUtils.isEmpty(id)) {
            return tableDao.load(id);
        }
        return null;
    }

    @Override
    protected boolean hasKeyById(String id) {
        if (checkDaoNotNull() && !TextUtils.isEmpty(id)) {
            QueryBuilder<AppPayInfo> qb = tableDao.queryBuilder();
            qb.where(AppPayInfoDao.Properties.AppId.eq(id));
            long count = qb.buildCount().count();
            return count > 0;
        }
        return false;
    }
}
