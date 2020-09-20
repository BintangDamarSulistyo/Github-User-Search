package com.githubusersearch.submission3.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.githubusersearch.submission3.Database.UserDatabase.UserColumns.TABLE_USER_NAME;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String USER_DB_NAME = "userdbasename";
    private static final int USER_DB_VERSION = 2;

    private static final String SQL_CREATE_TABLE_USER = String.format(
            "CREATE TABLE %s" +
                    "(%s INTEGER PRIMARY KEY," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL)",
            TABLE_USER_NAME,
            UserDatabase.UserColumns.ID,
            UserDatabase.UserColumns.USERNAME,
            UserDatabase.UserColumns.URL,
            UserDatabase.UserColumns.AVATAR
    );

    public DatabaseHelper(Context c){
        super(c,USER_DB_NAME,null,USER_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_NAME);
        onCreate(db);
    }
}