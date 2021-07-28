package com.example.tryjson;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

class RecyclerAdapter extends
        RecyclerView.Adapter<RecyclerAdapter.NewsViewHolder> {

    Context context;
    ArrayList<NewsModel> newsModelArrayList;

    public RecyclerAdapter(Context context, ArrayList<NewsModel> newsModelArrayList) {
        this.context = context;
        this.newsModelArrayList = newsModelArrayList;

    }


    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());

        View itemView = layoutInflater.inflate(R.layout.news_item_layout, viewGroup, false);
        //itemView.setOnClickListener((View.OnClickListener) this);
        NewsViewHolder newsViewHolder = new NewsViewHolder(itemView);
       // itemView.setOnClickListener((View.OnClickListener) this);

        return newsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int i){

        NewsModel newsModel = newsModelArrayList.get(i);
        newsViewHolder.ReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = newsModel.getUrl();
                Log.d("jatin", "Button Clicked " + url);

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(browserIntent);

            }});
        newsViewHolder.newsTitle.setText(String.valueOf(newsModel.getNewsTitle()));
        newsViewHolder.newsDescription.setText(String.valueOf(newsModel.getNewsDescription()));
        newsViewHolder.newsDate.setText(String.valueOf(newsModel.getNewsDate()));
        Glide.with(context).load(newsModel.getNewsImageUrl()).into(newsViewHolder.newsImageViews);
    }

    @Override
    public int getItemCount() {
        return newsModelArrayList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        public ImageView newsImageViews;
        public TextView newsTitle, newsDescription, newsDate;
        public Button ReadMore;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newsTitle = itemView.findViewById(R.id.news_title);
            newsDescription = itemView.findViewById(R.id.news_description);
            newsDate = itemView.findViewById(R.id.news_date);
            newsImageViews = itemView.findViewById(R.id.news_image_view);
            ReadMore=itemView.findViewById(R.id.read_more);
        }

    }
}