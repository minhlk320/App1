package com.minh.app1;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

public class MyContentProvider extends ContentProvider {

    static final String AUTHORITY = "book";
    static final String CONTENT_PATH = "bookdata";
    static final String URL = "content://" + AUTHORITY + "/" + CONTENT_PATH;
    static final Uri CONTENT_URI = Uri.parse(URL);
    static final String TABLE_NAME = "Books";
    private SQLiteDatabase db;

    private static HashMap<String, String> BOOKS_PROJECTION_MAP;

    static final int ALLITEMS = 1;
    static final int ONEITEM = 2;

    static final UriMatcher uriMathcher;

    static {
        uriMathcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMathcher.addURI(AUTHORITY, CONTENT_PATH, ALLITEMS);
        uriMathcher.addURI(AUTHORITY, CONTENT_PATH + "/#", ONEITEM);
    }

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMathcher.match(uri)) {
            case ALLITEMS:
                count = db.delete(TABLE_NAME, selection, selectionArgs);
                break;
            case ONEITEM:
                String id = uri.getPathSegments().get(1);
                count = db.delete(TABLE_NAME, "id_book" + " = " + id + (!TextUtils.isEmpty(selection)
                        ? "AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long number_row = db.insert(TABLE_NAME, "", values);
        if (number_row > 0) {
            Uri uri1 = ContentUris.withAppendedId(CONTENT_URI, number_row);
            getContext().getContentResolver().notifyChange(uri1, null);
            return uri1;
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        if (db == null)
            return false;
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqlite_builder = new SQLiteQueryBuilder();
        sqlite_builder.setTables(TABLE_NAME);
        switch (uriMathcher.match(uri)) {
            case ALLITEMS:
                sqlite_builder.setProjectionMap(BOOKS_PROJECTION_MAP);
                break;
            case ONEITEM:
                sqlite_builder.appendWhere("id_book" + "=" + uri.getPathSegments().get(1));
                break;
        }
        if (sortOrder == null || sortOrder == "") {
            sortOrder = "title";
        }
        Cursor cursor = sqlite_builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count = 0;
        switch (uriMathcher.match(uri)) {
            case ALLITEMS:
                count = db.update(TABLE_NAME, values, selection, selectionArgs);
                break;
            case ONEITEM:
                count = db.update(TABLE_NAME, values, "id_book" + " = " + uri.getPathSegments().get(1)
                        + (!TextUtils.isEmpty(selection) ? "AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
