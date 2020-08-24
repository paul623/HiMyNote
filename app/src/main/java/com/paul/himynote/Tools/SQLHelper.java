package com.paul.himynote.Tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.paul.himynote.Model.BeanSqlStr;

/**
 * SQLite的辅助工具
 * */
public class SQLHelper extends SQLiteOpenHelper {

    public SQLHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version)
    {
        super(context, name, cursorFactory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO 创建数据库后，对数据库的操作
        //Note表
        db.execSQL(BeanSqlStr.NoteBeanSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO 更改数据库版本的操作
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        // TODO 每次成功打开数据库后首先被执行
    }

}
