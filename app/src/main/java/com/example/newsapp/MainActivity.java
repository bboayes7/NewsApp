package com.example.newsapp;

import android.content.Intent;
import android.net.Network;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.Menu;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivity";
    private ProgressBar progress;
    private RecyclerView rv;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress = (ProgressBar) findViewById(R.id.progressBar);
        rv = (RecyclerView) findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));

        NetworkTask task = new NetworkTask();
        task.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemThatWasSelected = item.getItemId();
        if(menuItemThatWasSelected == R.id.search){
            Toast.makeText(MainActivity.this, "The Search Feature Doesn't Exist Anymore :(", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    class NetworkTask extends AsyncTask<URL, Void, ArrayList<NewsItem>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);

        }

        @Override
        protected ArrayList<NewsItem> doInBackground(URL... params) {
            ArrayList<NewsItem> result = null;
            URL url = NetworkUtils.makeURL(getResources().getString(R.string.key));
            Log.d(TAG, "url: " + url.toString());
            try{
                String json = NetworkUtils.getResponseFromHttpUrl(url);
                result = NetworkUtils.parseJSON(json);
            } catch (IOException e){
                e.printStackTrace();
            } catch (JSONException e){
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(final ArrayList<NewsItem> data) {
            super.onPostExecute(data);
            progress.setVisibility(View.GONE);
            if(data != null){
                //this is where url will be called and intent whateverwhatecer
                NewsAdapter adapter = new NewsAdapter(data, new NewsAdapter.ItemClickListener() {

                    @Override
                    public void onItemClick(int clickedItemIndex) {
                        String url = data.get(clickedItemIndex).getUrl();
                        Log.d(TAG, String.format("Url %s", url));
                        Uri uri = Uri.parse(url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
                rv.setAdapter(adapter);
            }
        }
    }
}
