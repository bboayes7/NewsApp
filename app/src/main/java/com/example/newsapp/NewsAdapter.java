package com.example.newsapp;


import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import com.example.newsapp.data.Contract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;



public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ItemHolder>{
    //Since we're taking data from a database we need a cursor and a context variable
    public static final String TAG = "newsAdapter";
    private Cursor cursor;
    private Context context;
    private ItemClickListener listener;

    //change the constructor's parameters
    public NewsAdapter(Cursor cursor, ItemClickListener listener){
        this.cursor = cursor;
        this.listener = listener;
    }

    //needed to click on each article
    public interface ItemClickListener{
        void onItemClick(Cursor cursor, int clickedItemIndex);
    }

    //creates viewholders for all news items
    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttackToParentImmediately = false;

        View view = inflater.inflate(R.layout.item, parent, shouldAttackToParentImmediately);
        ItemHolder holder = new ItemHolder(view);

        return holder;
    }

    //helps the bind method know what position to put the item in
    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(position);
    }

    //recycler view needs to know how many items it's holding
    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    //important class to have in making an adapter is the item holder
    //this holds all the data you need in each item in your recycler view
    //in this case we're getting data from the database
    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        TextView description;
        TextView time;
        //we're including a thumbnail now so we're adding an imageview
        ImageView img;

        //adding img to the constructor
        ItemHolder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            time = (TextView) view.findViewById(R.id.time);
            img = (ImageView) view.findViewById(R.id.img);

            view.setOnClickListener(this);
        }

        //the bind needs to include database info now
        public void bind(int pos){
            cursor.moveToPosition(pos);

            title.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE)));
            description.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_DESCRIPTION)));
            time.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_PUBLISHED_DATE)));

            //using picasso to load the image
            String imgUrl = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_THUMBURL));

            Log.d(TAG, imgUrl);
            if(imgUrl != null){
                Picasso.with(context).load(imgUrl).into(img);
            }

        }


        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(cursor, pos);
        }
    }
}
