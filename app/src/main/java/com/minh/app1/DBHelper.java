package com.minh.app1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "BookDatabase.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String author = "create table Authors(id_author integer primary key, name text, address text, email text)";
        db.execSQL(author);


        String book = "create table Books(id_book integer primary key, title text, " +
                "id_author integer constraint id_author references Authors(id_author) on delete cascade on update cascade)";
        db.execSQL(book);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String book = "drop table if exists Books";
        String author = "drop table if exists Authors";
        db.execSQL(book);
        db.execSQL(author);
        onCreate(db);
    }


}
