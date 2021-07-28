package com.example.tryjson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.Cache;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    ProgressBar progressBar;
    RecyclerView recyclerView;
    TextView textView;
    ArrayList<NewsModel> newsModelArrayList = new ArrayList<>();
    RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.recycler_view);
        textView = findViewById(R.id.error_tv);

        recyclerAdapter = new RecyclerAdapter(getApplicationContext(), newsModelArrayList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(recyclerAdapter);
        DiskBasedCache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        RequestQueue requestQueue = new RequestQueue(cache, network);

        requestQueue.start();
//Log.d("jatin","Working Properly");
        String newsUrl = "https://gnews.io/api/v4/top-headlines?q=hi&lang=en&country=in&token=2e291ebee74580f96195fac0a56f6e60";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, newsUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
              //  Log.d("jatin", response.toString());
                        try {
                            int results = response.getInt("totalArticles");
                            Log.d("jatin", "Results found: " + results);

                            JSONArray newsArticles = response.getJSONArray("articles");


                            for (int i = 0; i < newsArticles.length(); i++) {
                                JSONObject article = newsArticles.getJSONObject(i);
                                Log.d("jatin", String.valueOf(newsArticles));
                                String title = article.getString("title");
//                                Log.d("jatin",title);
                                String description = article.getString("description");
                                String date = article.getString("publishedAt");
                                String urlToImage = article.getString("image");
                                String url=article.getString("url");

                                newsModelArrayList.add(new NewsModel(title,description,date,urlToImage,url));

                            }

                            recyclerAdapter.notifyDataSetChanged();

                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);



                        } catch (JSONException e) {

                            progressBar.setVisibility(View.GONE);
                            textView.setText("Error in parsing content. Please check the code.");
                            textView.setVisibility(View.VISIBLE);

                            Toast.makeText(getApplicationContext(), "Error in parsing results", Toast.LENGTH_LONG).show();
                        }
//                progressBar.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);
//recyclerView.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//
//        Toast.makeText(MainActivity.this,"Position",Toast.LENGTH_SHORT).show();
//    }
//});
    }
}