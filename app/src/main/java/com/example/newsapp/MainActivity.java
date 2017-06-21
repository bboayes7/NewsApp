package com.example.newsapp;

import android.net.Network;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.Menu;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivity";
    private TextView textView;
    private ProgressBar progress;
    //private EditText search;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.news);
      //  search = (EditText) findViewById(R.id.search);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        NetworkTask task = new NetworkTask();
        task.execute();






    }

    class NetworkTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(URL... params) {
            String result = null;
            URL url = NetworkUtils.makeURL(getResources().getString(R.string.key));
            Log.d(TAG, "url: " + url.toString());
            try{
                result = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e){
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progress.setVisibility(View.GONE);
            if(s == null){
                textView.setText("No News");
            } else{
                textView.setText(s);
            }
        }
    }
}
