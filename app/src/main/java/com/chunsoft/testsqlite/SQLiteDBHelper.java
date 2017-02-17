package com.chunsoft.testsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Developer：chunsoft on 2017/2/17 14:09
 * Email：chun_soft@qq.com
 * Content：
 */

public class SQLiteDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "test.db";
    public static final int DB_VERSION = 1;


    //构造函数中指定数据库名与版本号
    public SQLiteDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //第一次创建数据库的时候，调用onCreate方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        //在这里通过db.execSQL函数执行SQL语句创建所需要的表
//        String sql = "create table if not exists students(id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "sname VARCHAR,sage INTEGER,ssex VARCHAR)";
        String sql = "create table if not exists students(id INTEGER ," +
                "sname VARCHAR,sage INTEGER,ssex VARCHAR)";
        db.execSQL(sql);
    }

    //数据库版本号升级变更会调用onUpgrade函数，根据版本号升级数据库
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                // do sth
                break;
            default:
                break;
        }
    }
}
