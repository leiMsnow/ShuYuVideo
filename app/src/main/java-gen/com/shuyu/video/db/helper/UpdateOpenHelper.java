package com.shuyu.video.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.shuyu.core.uils.LogUtils;
import com.shuyu.video.db.dao.DaoMaster;

/**
 * 数据库更新操作类
 * // 加入新字段
 * db.execSQL("ALTER TABLE 'table' ADD 'columns' TEXT;");
 */
public class UpdateOpenHelper extends DaoMaster.OpenHelper {

    public UpdateOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtils.d("UpdateOpenHelper", "oldVersion-" + oldVersion + "\t newVersion-" + newVersion);
        switch (oldVersion) {
            case 1:
                //创建新表
                break;
        }
    }
}