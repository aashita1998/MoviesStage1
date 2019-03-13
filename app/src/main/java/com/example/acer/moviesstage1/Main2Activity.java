package com.example.acer.moviesstage1;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    RequestQueue requestQueue;
    TextView title, vote, overview, releasedate, description, reviews,trailers;
    ImageView imageView2;
    String poster, t, i, rid, v, ov, rd;
    StatefulRecyclerView reviewrec, recyclerView1;
    ArrayList<ModelReview> reviewsArrayList;
    ArrayList<ModelTrailer> trailerArrayList;
    Adapterreview adapterreview;
    LikeButton imageView;
    FavModel favModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        requestQueue = Volley.newRequestQueue(this);
        title = findViewById(R.id.tv2);
        vote = findViewById(R.id.tv3);
        overview = findViewById(R.id.tv4);
        releasedate = findViewById(R.id.tv5);
        imageView2 = findViewById(R.id.img2);

        reviewrec = findViewById(R.id.recrev);

        recyclerView1 = findViewById(R.id.trailer);

        reviews = findViewById(R.id.tv6);
        trailers=findViewById(R.id.tv7);


        poster = getIntent().getStringExtra("image");
        t = getIntent().getStringExtra("title");
        i = getIntent().getStringExtra("id");
        rid = getIntent().getStringExtra("id");
        v = getIntent().getStringExtra("vote_avg");
        ov = getIntent().getStringExtra("overview");
        rd = getIntent().getStringExtra("releasedate");

        Picasso.with(this).load(poster).into(imageView2);

        title.setText(" " + t);
        vote.setText(" " + i);
        overview.setText(" " + ov);
        releasedate.setText(" " + rd);
        vote.setText("" + v);
        showReviews();
        showTrailers();

        favModel = ViewModelProviders.of(this).get(FavModel.class);
        imageView=findViewById(R.id.favImg);
        imageView.setOnLikeListener(new OnLikeListener()
        {
            @Override
            public void liked(LikeButton likeButton)
            {
                saveToRoom();
            }
            @Override
            public void unLiked(LikeButton likeButton)
            {
                deleteFromRoom();
            }
        });

        MovieCheck();
    }



    public void showReviews() {
        String link1 = "https://api.themoviedb.org/3/movie/" + i + "/reviews?api_key=473ca20c632d37f8e9e644fe04097d89 ";

        StringRequest request1 = new StringRequest(Request.Method.GET, link1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {


                    String author = null;
                    String content = null;
                    reviewsArrayList = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray results = jsonObject.getJSONArray("results");
                    if (results.length() == 0) {

                        reviews.setVisibility(View.GONE);
                        reviewrec.setVisibility(View.GONE);


                    } else {

                        for (int i = 0; i < results.length(); i++) {
                            JSONObject jsonObject1 = results.getJSONObject(i);
                            author = jsonObject1.getString("author");
                            content = jsonObject1.getString("content");

                            //Toast.makeText(Main2Activity.this, author+"\n"+content, Toast.LENGTH_SHORT).show();
                            ModelReview modelReview = new ModelReview(author, content);
                            reviewsArrayList.add(modelReview);
                        }

                    }


                    adapterreview = new Adapterreview(Main2Activity.this, reviewsArrayList);
                    reviewrec.setLayoutManager(new LinearLayoutManager(Main2Activity.this));
                    reviewrec.setAdapter(adapterreview);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(request1);


    }

    public void showTrailers() {
        String link = "https://api.themoviedb.org/3/movie/" + i + "/videos?api_key=473ca20c632d37f8e9e644fe04097d89";

        StringRequest request = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    trailerArrayList = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    if (jsonArray.length()==0){
                        trailers.setVisibility(View.GONE);
                        recyclerView1.setVisibility(View.GONE);
                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object1 = jsonArray.getJSONObject(i);
                        String n = object1.getString("name");
                        String k = object1.getString("key");
                        String t = object1.getString("type");
                        ModelTrailer modelTrailer = new ModelTrailer(n, k, t);
                        trailerArrayList.add(modelTrailer);

                    }

                    AdapterTrailer adapterTrailer = new AdapterTrailer(Main2Activity.this, trailerArrayList);
                    recyclerView1.setLayoutManager(new LinearLayoutManager(Main2Activity.this));
                    recyclerView1.setAdapter(adapterTrailer);

                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);

    }

    public void saveToRoom() {
        FavMovieData favMovieData = new FavMovieData();
        favMovieData.setMovieDesc(ov);
        favMovieData.setMovieid(Integer.parseInt(i));
        favMovieData.setMoviename(t);
        favMovieData.setMoviePoster(poster);
        favMovieData.setMovieRelease(rd);
        favMovieData.setMovieVoteAvg(v);
        favModel.insertData(favMovieData);
        Toast.makeText(this, "Data saved Successfully", Toast.LENGTH_SHORT).show();

    }

    public void deleteFromRoom() {
        FavMovieData favMovieData = new FavMovieData();
        favMovieData.setMovieDesc(ov);
        favMovieData.setMovieid(Integer.parseInt(i));
        favMovieData.setMoviename(t);
        favMovieData.setMoviePoster(poster);
        favMovieData.setMovieRelease(rd);
        favMovieData.setMovieVoteAvg(v);
        favModel.deleteData(favMovieData);
        Toast.makeText(this, "Data deleted Successfully", Toast.LENGTH_SHORT).show();
    }


    public void MovieCheck()
    {
        FavMovieData favMovieData=favModel.checkFavMovie(i);

        if (favMovieData!=null)
        {
            imageView.setLiked(true);
        }
        else {
            imageView.setLiked(false);
        }
    }

    }

