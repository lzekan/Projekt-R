package com.example.skladiteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelperSQLite extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "userdb";
    private static final String COL1 = "idUser";
    private static final String COL2 = "username";
    private static final String COL3 = "password";
    private static final String COL4 = "email";

    public DatabaseHelperSQLite(@Nullable Context context) {
        super(context, TABLE_NAME,null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(idUser INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT, " +
                COL3 + " TEXT, " +
                COL4 + " TEXT)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean addData(String username, String password, String email) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL2, username);
        contentValues.put(COL3, password);
        contentValues.put(COL4, email);

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData () {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = sqLiteDatabase.rawQuery(query, null);
        return data;
    }


}
