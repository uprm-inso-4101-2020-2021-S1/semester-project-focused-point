package com.focusedpoint.weighttracker.SQLiteDatabase;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLite extends SQLiteOpenHelper {
    public static final String USER_TABLE = "USER_TABLE";
    public static final String USER_USERNAME = "USER_USERNAME";
    public static final String USER_PASSWORD = "USER_PASSWORD";
    SQLiteDatabase weight;

    public SQLite(@Nullable Context context) {
        super(context, "user.db", null, 1);
    }

    //called first time a database is accessed.
    @Override
    public void onCreate(SQLiteDatabase db) {
    String createTableStatement = "CREATE TABLE " + USER_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_USERNAME + " TEXT, "
            + USER_PASSWORD + " TEXT)";
    db.execSQL(createTableStatement);
    }
    //upgrades the users database on the apps when the database is changed
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
