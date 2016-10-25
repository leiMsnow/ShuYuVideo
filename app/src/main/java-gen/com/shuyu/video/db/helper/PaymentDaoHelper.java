package com.shuyu.video.db.helper;

import android.text.TextUtils;

import com.shuyu.core.db.BaseDaoHelper;
import com.shuyu.video.db.dao.PaymentDao;
import com.shuyu.video.model.Payment;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Created by Azure on 2016/9/15.
 */
public class PaymentDaoHelper extends BaseDaoHelper<PaymentDao, Payment> {

    private static PaymentDaoHelper helper;
    private PaymentDaoHelper() {
        super(DBManager.getInstance().getDaoSession().getPaymentDao());
    }

    public static PaymentDaoHelper getHelper() {
        if (helper == null) {
            synchronized (PaymentDaoHelper.class) {
                if (helper == null) {
                    helper = new PaymentDaoHelper();
                }
            }
        }
        return helper;
    }

    @Override
    protected Payment getDataInfoById(String id) {
        if (checkDaoNotNull() && !TextUtils.isEmpty(id)) {
            return tableDao.load(Long.parseLong(id));
        }
        return null;
    }

    @Override
    protected boolean hasKeyById(String id) {
        if (checkDaoNotNull() && !TextUtils.isEmpty(id)) {
            QueryBuilder<Payment> qb = tableDao.queryBuilder();
            qb.where(PaymentDao.Properties.Id.eq(id));
            long count = qb.buildCount().count();
            return count > 0;
        }
        return false;
    }
}
