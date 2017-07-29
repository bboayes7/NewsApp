package com.example.newsapp.data;

import android.provider.BaseColumns;

/**
 * Created by Barry on 7/28/2017.
 */

public class Contract {

    //first part for setting up the database
    //makes it easier to make calls using these strings
    public static class TABLE_ARTICLES implements BaseColumns {
        public static final String TABLE_NAME = "articles";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_PUBLISHED_DATE = "published_date";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_THUMBURL = "thumb_url";
        public static final String COLUMN_NAME_URL = "url";
    }

}
