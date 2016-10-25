package com.shuyu.video.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.shuyu.video.model.AppPayInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "APP_PAY_INFO".
*/
public class AppPayInfoDao extends AbstractDao<AppPayInfo, String> {

    public static final String TABLENAME = "APP_PAY_INFO";

    /**
     * Properties of entity AppPayInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property AppId = new Property(0, String.class, "appId", true, "APP_ID");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property SpreePrice = new Property(2, float.class, "spreePrice", false, "SPREE_PRICE");
        public final static Property MemberPrice = new Property(3, float.class, "memberPrice", false, "MEMBER_PRICE");
        public final static Property VipPrice = new Property(4, float.class, "vipPrice", false, "VIP_PRICE");
        public final static Property SvipPrice = new Property(5, float.class, "svipPrice", false, "SVIP_PRICE");
        public final static Property Rebate = new Property(6, double.class, "rebate", false, "REBATE");
        public final static Property PackageName = new Property(7, String.class, "packageName", false, "PACKAGE_NAME");
        public final static Property VersionName = new Property(8, String.class, "versionName", false, "VERSION_NAME");
        public final static Property VersionNo = new Property(9, String.class, "versionNo", false, "VERSION_NO");
    };


    public AppPayInfoDao(DaoConfig config) {
        super(config);
    }
    
    public AppPayInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"APP_PAY_INFO\" (" + //
                "\"APP_ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: appId
                "\"TITLE\" TEXT," + // 1: title
                "\"SPREE_PRICE\" REAL NOT NULL ," + // 2: spreePrice
                "\"MEMBER_PRICE\" REAL NOT NULL ," + // 3: memberPrice
                "\"VIP_PRICE\" REAL NOT NULL ," + // 4: vipPrice
                "\"SVIP_PRICE\" REAL NOT NULL ," + // 5: svipPrice
                "\"REBATE\" REAL NOT NULL ," + // 6: rebate
                "\"PACKAGE_NAME\" TEXT," + // 7: packageName
                "\"VERSION_NAME\" TEXT," + // 8: versionName
                "\"VERSION_NO\" TEXT);"); // 9: versionNo
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"APP_PAY_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AppPayInfo entity) {
        stmt.clearBindings();
 
        String appId = entity.getAppId();
        if (appId != null) {
            stmt.bindString(1, appId);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }
        stmt.bindDouble(3, entity.getSpreePrice());
        stmt.bindDouble(4, entity.getMemberPrice());
        stmt.bindDouble(5, entity.getVipPrice());
        stmt.bindDouble(6, entity.getSvipPrice());
        stmt.bindDouble(7, entity.getRebate());
 
        String packageName = entity.getPackageName();
        if (packageName != null) {
            stmt.bindString(8, packageName);
        }
 
        String versionName = entity.getVersionName();
        if (versionName != null) {
            stmt.bindString(9, versionName);
        }
 
        String versionNo = entity.getVersionNo();
        if (versionNo != null) {
            stmt.bindString(10, versionNo);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AppPayInfo entity) {
        stmt.clearBindings();
 
        String appId = entity.getAppId();
        if (appId != null) {
            stmt.bindString(1, appId);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }
        stmt.bindDouble(3, entity.getSpreePrice());
        stmt.bindDouble(4, entity.getMemberPrice());
        stmt.bindDouble(5, entity.getVipPrice());
        stmt.bindDouble(6, entity.getSvipPrice());
        stmt.bindDouble(7, entity.getRebate());
 
        String packageName = entity.getPackageName();
        if (packageName != null) {
            stmt.bindString(8, packageName);
        }
 
        String versionName = entity.getVersionName();
        if (versionName != null) {
            stmt.bindString(9, versionName);
        }
 
        String versionNo = entity.getVersionNo();
        if (versionNo != null) {
            stmt.bindString(10, versionNo);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public AppPayInfo readEntity(Cursor cursor, int offset) {
        AppPayInfo entity = new AppPayInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // appId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // title
            cursor.getFloat(offset + 2), // spreePrice
            cursor.getFloat(offset + 3), // memberPrice
            cursor.getFloat(offset + 4), // vipPrice
            cursor.getFloat(offset + 5), // svipPrice
            cursor.getDouble(offset + 6), // rebate
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // packageName
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // versionName
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9) // versionNo
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AppPayInfo entity, int offset) {
        entity.setAppId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setTitle(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSpreePrice(cursor.getFloat(offset + 2));
        entity.setMemberPrice(cursor.getFloat(offset + 3));
        entity.setVipPrice(cursor.getFloat(offset + 4));
        entity.setSvipPrice(cursor.getFloat(offset + 5));
        entity.setRebate(cursor.getDouble(offset + 6));
        entity.setPackageName(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setVersionName(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setVersionNo(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
     }
    
    @Override
    protected final String updateKeyAfterInsert(AppPayInfo entity, long rowId) {
        return entity.getAppId();
    }
    
    @Override
    public String getKey(AppPayInfo entity) {
        if(entity != null) {
            return entity.getAppId();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
