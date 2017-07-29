package com.example.newsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.newsapp.data.DBHelper;
import com.example.newsapp.data.DBUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Barry on 7/28/2017.
 */

public class RefreshTasks {
    public static final String ACTION_REFRESH = "refresh";


    public static void refreshArticles(Context context) {
        ArrayList<NewsItem> result = null;
        URL url = NetworkUtils.makeURL(context.getResources().getString(R.string.key));

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        //essentially what a refresh does-delete old data and insert new data
        try {
            DBUtils.deleteAll(db);
            String json = NetworkUtils.getResponseFromHttpUrl(url);
            result = NetworkUtils.parseJSON(json);
            DBUtils.bulkInsert(db, result);

        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        db.close();
    }
}
