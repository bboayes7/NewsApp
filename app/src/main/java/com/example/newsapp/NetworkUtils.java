package com.example.newsapp;


import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class NetworkUtils {
    public static final String TAG = "NetworkUtils";
    public static final String NEWS_BASE_URL = "https://newsapi.org/v1/articles";
    public static final String PARAM_SOURCE = "source";
    public static final String PARAM_SORT_BY = "sortBy";
    public static final String PARAM_API_KEY = "apiKey";

    public static URL makeURL(String key){

        Uri uri = Uri.parse(NEWS_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_SOURCE, "the-next-web")
                .appendQueryParameter(PARAM_SORT_BY, "latest")
                .appendQueryParameter(PARAM_API_KEY, key).build();
        URL url = null;

        try{
            String urlString = uri.toString();
            Log.d(TAG, "Url: " + urlString);
            url = new URL(uri.toString());
        } catch(MalformedURLException e){
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput){
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static ArrayList<NewsItem> parseJSON(String json) throws JSONException {
        ArrayList<NewsItem> result = new ArrayList<>();
        JSONObject main = new JSONObject(json);
        JSONArray articles = main.getJSONArray("articles");

        for(int i = 0; i < articles.length(); i++){
            JSONObject article = articles.getJSONObject(i);
            String title = article.getString("title");
            String author = article.getString("author");
            String description = article.getString("description");
            String url = article.getString("url");
            String urlToImage = article.getString("urlToImage");
            String publishedAt = article.getString("publishedAt");
            NewsItem item = new NewsItem(title, author, description, url, urlToImage, publishedAt);
            result.add(item);
        }
        return result;
    }
}
