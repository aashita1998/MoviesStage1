package com.example.acer.moviesstage1;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.acer.moviesstage1.Model_Movies.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
            StatefulRecyclerView recyclerView;
            RequestQueue requestQueue;
            ArrayList<Movie>list;
            String image,title;
            String link="https://image.tmdb.org/t/p/w500/";
            SharedPreferences sharedPreferences;
            SharedPreferences.Editor editor;
            String getSavedMovie;

    FavModel favModel;
    List<FavMovieData> favMovieDataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recmain);
        requestQueue=Volley.newRequestQueue(this);
        list = new ArrayList<>();
       // toprated();

        sharedPreferences=getSharedPreferences("mypref",MODE_PRIVATE);
        favModel=ViewModelProviders.of(this).get(FavModel.class);
        editor=sharedPreferences.edit();
        favMovieDataList=new ArrayList<>();
        favModel= ViewModelProviders.of(this).get(FavModel.class);

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            getSavedMovie=sharedPreferences.getString("key",null);
            if (getSavedMovie==null)
            {
                Popular();
            }
            else {
                if (getSavedMovie.equalsIgnoreCase("popular"))
                {
                    Popular();
                }
                else if (getSavedMovie.equalsIgnoreCase("toprated"))
                {

                    toprated();
                }
                else if(getSavedMovie.equalsIgnoreCase( "favourite")) {

                    displayFavourites();
                }
            }
        }
        else {
            noInternetMessage();
        }
        if (getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        }
        else {
            recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflaterer=getMenuInflater();
        menuInflaterer.inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.popular:
                ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (null != activeNetwork) {

                    Popular();
                    editor.putString("key", "popular");
                    editor.commit();

                }
                else {
                    noInternetMessage();
                }

                break;
            case R.id.Toprated:

                ConnectivityManager cm1 = (ConnectivityManager) getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork1 = cm1.getActiveNetworkInfo();
                if (null != activeNetwork1) {

                    toprated();
                    editor.putString("key","toprated");
                    editor.commit();

                }
                else {
                    noInternetMessage();
                }
                break;
            case R.id.Favorites:
                ConnectivityManager cm2 = (ConnectivityManager) getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork2 = cm2.getActiveNetworkInfo();
                if (null != activeNetwork2)
                {

                    displayFavourites();
                    editor.putString("key","favourite");
                    editor.commit();

                }
                else {
                    noInternetMessage();
                }
                break;
        }

            return super.onOptionsItemSelected(item);
        }

    private void noInternetMessage() {
        AlertDialog.Builder alertBuild=new AlertDialog.Builder(this);
        alertBuild.setTitle("Alert!");
        alertBuild.setMessage("Check Your Internet ");
        alertBuild.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

alertBuild.show();

    }


    private void displayFavourites()
    {

        favModel.getLiveData.observe(this, new Observer<List<FavMovieData>>() {
            @Override
            public void onChanged(@Nullable List<FavMovieData> favMovieData) {
                favMovieDataList=favMovieData;

                if(favMovieDataList.size()==0){
                    Toast.makeText(MainActivity.this, "No Favorites Added", Toast.LENGTH_SHORT).show();
                }
                else{

                    FavouriteAdapter adapter=new FavouriteAdapter(MainActivity.this,favMovieDataList);
                    recyclerView.setAdapter(adapter);
                }

            }
        });
    }

        public void toprated(){

        list.clear();

         String url1="https://api.themoviedb.org/3/movie/top_rated?api_key=473ca20c632d37f8e9e644fe04097d89";
            final StringRequest stringRequest=new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        String poster_path = null;
                        String title = null;
                        String id = null;
                        String vote = null;
                        String overview = null;
                        String releasedate = null;

                        JSONObject root = new JSONObject(response);
                        JSONArray results = root.getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject obj = results.getJSONObject(i);
                            image = link + obj.optString("poster_path");
                            title = obj.optString("title");
                            id = obj.getString("id");
                            vote = obj.getString("vote_average");
                            overview = obj.getString("overview");
                            releasedate = obj.getString("release_date");

                            Movie movie = new Movie(image, title, id, vote, overview, releasedate);
                            list.add(movie);

                        }
                        MyAdapter adapter = new MyAdapter(MainActivity.this, list);
                        recyclerView.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(stringRequest);
        }
        public void Popular(){

            editor.putString("key","popular");
            editor.commit();


            final String url2 = "https://api.themoviedb.org/3/movie/popular?api_key=473ca20c632d37f8e9e644fe04097d89";
            StringRequest request=new StringRequest(Request.Method.GET, url2,new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    try {
                        String poster_path = null;
                        String title = null;
                        String id = null;
                        String vote = null;
                        String overview = null;
                        String releasedate = null;
                        list = new ArrayList<>();

                        JSONObject root = null;
                        root = new JSONObject(response);
                        JSONArray results = root.getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject obj = results.getJSONObject(i);
                            image = link + obj.optString("poster_path");
                            title = obj.optString("title");
                            id = obj.getString("id");
                            vote = obj.getString("vote_average");
                            overview = obj.getString("overview");
                            releasedate = obj.getString("release_date");
                            Movie movie = new Movie(image, title, id, vote, overview, releasedate);
                            list.add(movie);

                        }
                        MyAdapter adapter = new MyAdapter(MainActivity.this, list);
                       // recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                        recyclerView.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(request);


        }

}
