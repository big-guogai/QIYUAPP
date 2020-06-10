package com.xuhao.myapp.utills;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String CREATE_USER = "create table historyBrowseId(" + "courseId integer primary key)";

    public DataBaseHelper(Context context) {
        super(context, "historyBrowse_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
