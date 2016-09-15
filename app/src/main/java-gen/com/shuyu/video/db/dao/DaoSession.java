package com.shuyu.video.db.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.shuyu.video.model.AppInfoListEntity;

import com.shuyu.video.db.dao.AppInfoListEntityDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig appInfoListEntityDaoConfig;

    private final AppInfoListEntityDao appInfoListEntityDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        appInfoListEntityDaoConfig = daoConfigMap.get(AppInfoListEntityDao.class).clone();
        appInfoListEntityDaoConfig.initIdentityScope(type);

        appInfoListEntityDao = new AppInfoListEntityDao(appInfoListEntityDaoConfig, this);

        registerDao(AppInfoListEntity.class, appInfoListEntityDao);
    }
    
    public void clear() {
        appInfoListEntityDaoConfig.getIdentityScope().clear();
    }

    public AppInfoListEntityDao getAppInfoListEntityDao() {
        return appInfoListEntityDao;
    }

}