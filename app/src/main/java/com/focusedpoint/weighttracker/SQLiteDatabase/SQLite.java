package com.focusedpoint.weighttracker.SQLiteDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLite extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "User.db";
    public static final String USER_TABLE = "USER_TABLE";
    public static final String USER_USERNAME = "USER_USERNAME";
    public static final String USER_PASSWORD = "USER_PASSWORD";
    public static final String USER_DATA = "USER_DATA";
    SQLiteDatabase weight;

    public SQLite(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //called first time a database is accessed.
    @Override
    public void onCreate(SQLiteDatabase db) {
    String createTableStatement = "CREATE TABLE " + USER_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_USERNAME + " TEXT, "
            + USER_PASSWORD + " TEXT,"+ USER_DATA +"TEXT)";

    db.execSQL(createTableStatement);
    }


    //upgrades the users database on the apps when the database is changed
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table If Exists "+DATABASE_NAME);
        onCreate(db);
    }

    public boolean insertData(String userName, String passWord,DatabaseEntry Entry){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_USERNAME,userName);
        contentValues.put(USER_PASSWORD,passWord);
        contentValues.put(USER_DATA,Entry.toString());
        long result = myDB.insert(USER_TABLE,null, contentValues);

        if(result == -1){
            return false;
        }
        else{
            return true;
        }

    }
    //Checks whether the username exists or not
    public boolean checkUser(String username){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from users where username =?", new String[] {username});
        if(cursor.getCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }
    //Checks whether the username password exists or not
    public boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from users where username = ? and password = ?",new String[]{username,password});
        if(cursor.getCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }
}
