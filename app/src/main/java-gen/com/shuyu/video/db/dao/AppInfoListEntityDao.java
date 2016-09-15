package com.shuyu.video.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.shuyu.video.model.AppInfoListEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "AppInfo".
*/
public class AppInfoListEntityDao extends AbstractDao<AppInfoListEntity, Long> {

    public static final String TABLENAME = "AppInfo";

    /**
     * Properties of entity AppInfoListEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property AppId = new Property(0, Long.class, "appId", true, "_id");
        public final static Property Id = new Property(1, int.class, "id", false, "ID");
        public final static Property Title = new Property(2, String.class, "title", false, "TITLE");
        public final static Property Brief = new Property(3, String.class, "brief", false, "BRIEF");
        public final static Property Summary = new Property(4, String.class, "summary", false, "SUMMARY");
        public final static Property DownloadUrl = new Property(5, String.class, "downloadUrl", false, "DOWNLOAD_URL");
        public final static Property IconUrl = new Property(6, String.class, "iconUrl", false, "ICON_URL");
        public final static Property VersionCode = new Property(7, String.class, "versionCode", false, "VERSION_CODE");
        public final static Property VersionName = new Property(8, String.class, "versionName", false, "VERSION_NAME");
        public final static Property PackageName = new Property(9, String.class, "packageName", false, "PACKAGE_NAME");
        public final static Property SoftwareSize = new Property(10, int.class, "softwareSize", false, "SOFTWARE_SIZE");
        public final static Property AppType = new Property(11, int.class, "appType", false, "APP_TYPE");
        public final static Property Md5 = new Property(12, String.class, "md5", false, "MD5");
        public final static Property DownloadCount = new Property(13, int.class, "downloadCount", false, "DOWNLOAD_COUNT");
        public final static Property ImgUrl = new Property(14, String.class, "imgUrl", false, "IMG_URL");
        public final static Property DownloadType = new Property(15, int.class, "downloadType", false, "DOWNLOAD_TYPE");
        public final static Property OnlineInterval = new Property(16, int.class, "onlineInterval", false, "ONLINE_INTERVAL");
        public final static Property NotifyCount = new Property(17, int.class, "notifyCount", false, "NOTIFY_COUNT");
        public final static Property NotifyInterval = new Property(18, int.class, "notifyInterval", false, "NOTIFY_INTERVAL");
        public final static Property AdImgUrl = new Property(19, String.class, "adImgUrl", false, "AD_IMG_URL");
    };


    public AppInfoListEntityDao(DaoConfig config) {
        super(config);
    }
    
    public AppInfoListEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"AppInfo\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: appId
                "\"ID\" INTEGER NOT NULL ," + // 1: id
                "\"TITLE\" TEXT," + // 2: title
                "\"BRIEF\" TEXT," + // 3: brief
                "\"SUMMARY\" TEXT," + // 4: summary
                "\"DOWNLOAD_URL\" TEXT," + // 5: downloadUrl
                "\"ICON_URL\" TEXT," + // 6: iconUrl
                "\"VERSION_CODE\" TEXT," + // 7: versionCode
                "\"VERSION_NAME\" TEXT," + // 8: versionName
                "\"PACKAGE_NAME\" TEXT," + // 9: packageName
                "\"SOFTWARE_SIZE\" INTEGER NOT NULL ," + // 10: softwareSize
                "\"APP_TYPE\" INTEGER NOT NULL ," + // 11: appType
                "\"MD5\" TEXT," + // 12: md5
                "\"DOWNLOAD_COUNT\" INTEGER NOT NULL ," + // 13: downloadCount
                "\"IMG_URL\" TEXT," + // 14: imgUrl
                "\"DOWNLOAD_TYPE\" INTEGER NOT NULL ," + // 15: downloadType
                "\"ONLINE_INTERVAL\" INTEGER NOT NULL ," + // 16: onlineInterval
                "\"NOTIFY_COUNT\" INTEGER NOT NULL ," + // 17: notifyCount
                "\"NOTIFY_INTERVAL\" INTEGER NOT NULL ," + // 18: notifyInterval
                "\"AD_IMG_URL\" TEXT);"); // 19: adImgUrl
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"AppInfo\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AppInfoListEntity entity) {
        stmt.clearBindings();
 
        Long appId = entity.getAppId();
        if (appId != null) {
            stmt.bindLong(1, appId);
        }
        stmt.bindLong(2, entity.getId());
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }
 
        String brief = entity.getBrief();
        if (brief != null) {
            stmt.bindString(4, brief);
        }
 
        String summary = entity.getSummary();
        if (summary != null) {
            stmt.bindString(5, summary);
        }
 
        String downloadUrl = entity.getDownloadUrl();
        if (downloadUrl != null) {
            stmt.bindString(6, downloadUrl);
        }
 
        String iconUrl = entity.getIconUrl();
        if (iconUrl != null) {
            stmt.bindString(7, iconUrl);
        }
 
        String versionCode = entity.getVersionCode();
        if (versionCode != null) {
            stmt.bindString(8, versionCode);
        }
 
        String versionName = entity.getVersionName();
        if (versionName != null) {
            stmt.bindString(9, versionName);
        }
 
        String packageName = entity.getPackageName();
        if (packageName != null) {
            stmt.bindString(10, packageName);
        }
        stmt.bindLong(11, entity.getSoftwareSize());
        stmt.bindLong(12, entity.getAppType());
 
        String md5 = entity.getMd5();
        if (md5 != null) {
            stmt.bindString(13, md5);
        }
        stmt.bindLong(14, entity.getDownloadCount());
 
        String imgUrl = entity.getImgUrl();
        if (imgUrl != null) {
            stmt.bindString(15, imgUrl);
        }
        stmt.bindLong(16, entity.getDownloadType());
        stmt.bindLong(17, entity.getOnlineInterval());
        stmt.bindLong(18, entity.getNotifyCount());
        stmt.bindLong(19, entity.getNotifyInterval());
 
        String adImgUrl = entity.getAdImgUrl();
        if (adImgUrl != null) {
            stmt.bindString(20, adImgUrl);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AppInfoListEntity entity) {
        stmt.clearBindings();
 
        Long appId = entity.getAppId();
        if (appId != null) {
            stmt.bindLong(1, appId);
        }
        stmt.bindLong(2, entity.getId());
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(3, title);
        }
 
        String brief = entity.getBrief();
        if (brief != null) {
            stmt.bindString(4, brief);
        }
 
        String summary = entity.getSummary();
        if (summary != null) {
            stmt.bindString(5, summary);
        }
 
        String downloadUrl = entity.getDownloadUrl();
        if (downloadUrl != null) {
            stmt.bindString(6, downloadUrl);
        }
 
        String iconUrl = entity.getIconUrl();
        if (iconUrl != null) {
            stmt.bindString(7, iconUrl);
        }
 
        String versionCode = entity.getVersionCode();
        if (versionCode != null) {
            stmt.bindString(8, versionCode);
        }
 
        String versionName = entity.getVersionName();
        if (versionName != null) {
            stmt.bindString(9, versionName);
        }
 
        String packageName = entity.getPackageName();
        if (packageName != null) {
            stmt.bindString(10, packageName);
        }
        stmt.bindLong(11, entity.getSoftwareSize());
        stmt.bindLong(12, entity.getAppType());
 
        String md5 = entity.getMd5();
        if (md5 != null) {
            stmt.bindString(13, md5);
        }
        stmt.bindLong(14, entity.getDownloadCount());
 
        String imgUrl = entity.getImgUrl();
        if (imgUrl != null) {
            stmt.bindString(15, imgUrl);
        }
        stmt.bindLong(16, entity.getDownloadType());
        stmt.bindLong(17, entity.getOnlineInterval());
        stmt.bindLong(18, entity.getNotifyCount());
        stmt.bindLong(19, entity.getNotifyInterval());
 
        String adImgUrl = entity.getAdImgUrl();
        if (adImgUrl != null) {
            stmt.bindString(20, adImgUrl);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public AppInfoListEntity readEntity(Cursor cursor, int offset) {
        AppInfoListEntity entity = new AppInfoListEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // appId
            cursor.getInt(offset + 1), // id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // title
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // brief
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // summary
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // downloadUrl
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // iconUrl
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // versionCode
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // versionName
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // packageName
            cursor.getInt(offset + 10), // softwareSize
            cursor.getInt(offset + 11), // appType
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // md5
            cursor.getInt(offset + 13), // downloadCount
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // imgUrl
            cursor.getInt(offset + 15), // downloadType
            cursor.getInt(offset + 16), // onlineInterval
            cursor.getInt(offset + 17), // notifyCount
            cursor.getInt(offset + 18), // notifyInterval
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19) // adImgUrl
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AppInfoListEntity entity, int offset) {
        entity.setAppId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setId(cursor.getInt(offset + 1));
        entity.setTitle(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setBrief(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSummary(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setDownloadUrl(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setIconUrl(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setVersionCode(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setVersionName(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setPackageName(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setSoftwareSize(cursor.getInt(offset + 10));
        entity.setAppType(cursor.getInt(offset + 11));
        entity.setMd5(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setDownloadCount(cursor.getInt(offset + 13));
        entity.setImgUrl(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setDownloadType(cursor.getInt(offset + 15));
        entity.setOnlineInterval(cursor.getInt(offset + 16));
        entity.setNotifyCount(cursor.getInt(offset + 17));
        entity.setNotifyInterval(cursor.getInt(offset + 18));
        entity.setAdImgUrl(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(AppInfoListEntity entity, long rowId) {
        entity.setAppId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(AppInfoListEntity entity) {
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