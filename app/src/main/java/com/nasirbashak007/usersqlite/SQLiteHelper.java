package com.nasirbashak007.usersqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by Nasir Basha K on 17-01-2018.
 */

public class SQLiteHelper extends SQLiteOpenHelper {


    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql) {

        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertData(String name, String email, byte[] image) {

        SQLiteDatabase database = getWritableDatabase();

        String sqlCommand = "INSERT INTO USER VALUES (NULL,?,?,?)";

        SQLiteStatement sqLiteStatement = database.compileStatement(sqlCommand);

        sqLiteStatement.clearBindings();

        sqLiteStatement.bindString(1, name);
        sqLiteStatement.bindString(2, email);
        sqLiteStatement.bindBlob(3, image);
        sqLiteStatement.executeInsert();

    }

    public Cursor getData(String sql) {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        return sqLiteDatabase.rawQuery(sql, null);


    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
