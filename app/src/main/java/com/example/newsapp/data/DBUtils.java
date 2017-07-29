package com.example.newsapp.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.newsapp.NewsItem;

import java.util.ArrayList;

import static com.example.newsapp.data.Contract.TABLE_ARTICLES.COLUMN_NAME_DESCRIPTION;
import static com.example.newsapp.data.Contract.TABLE_ARTICLES.COLUMN_NAME_PUBLISHED_DATE;
import static com.example.newsapp.data.Contract.TABLE_ARTICLES.COLUMN_NAME_THUMBURL;
import static com.example.newsapp.data.Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE;
import static com.example.newsapp.data.Contract.TABLE_ARTICLES.COLUMN_NAME_URL;
import static com.example.newsapp.data.Contract.TABLE_ARTICLES.TABLE_NAME;

/**
 * Created by Barry on 7/28/2017.
 */

public class DBUtils {

    //third part of setting up the database for the app

    //this method provides the cursor with all the data and sorts it by date
    public static Cursor getAll(SQLiteDatabase db) {
        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                COLUMN_NAME_PUBLISHED_DATE + " DESC"
        );
        return cursor;
    }

    //inserts data into the database
    public static void bulkInsert(SQLiteDatabase db, ArrayList<NewsItem> articles) {

        db.beginTransaction();
        try {
            for (NewsItem a : articles) {
                ContentValues cv = new ContentValues();
                cv.put(COLUMN_NAME_TITLE, a.getTitle());
                cv.put(COLUMN_NAME_DESCRIPTION, a.getDescription());
                cv.put(COLUMN_NAME_PUBLISHED_DATE, a.getPublishedAt());
                cv.put(COLUMN_NAME_THUMBURL, a.getUrlToImage());
                cv.put(COLUMN_NAME_URL, a.getUrl());
                db.insert(TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    //deletes data from the database
    public static void deleteAll(SQLiteDatabase db) {
        db.delete(TABLE_NAME, null, null);
    }
}
