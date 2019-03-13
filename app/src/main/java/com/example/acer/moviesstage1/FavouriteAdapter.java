package com.example.acer.moviesstage1;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public  class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {
    Context ct;
    List<FavMovieData>  favMovieData;

    public FavouriteAdapter(MainActivity mainActivity, List<FavMovieData> favMovieDataList) {
        this.ct=mainActivity;
        this.favMovieData=favMovieDataList;
        }

    @NonNull
    @Override
    public FavouriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(ct).inflate(R.layout.raw,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.ViewHolder viewHolder, int i) {
        Picasso.with(ct).load(favMovieData.get(i).getMoviePoster()).into(viewHolder.imageView);
        Log.d("favposter",favMovieData.get(i).getMoviePoster());
    }

    @Override
    public int getItemCount() {
        return favMovieData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.text1);
            imageView=itemView.findViewById(R.id.image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();

                    Intent i=new Intent(ct,Main2Activity.class);

                    i.putExtra("image",favMovieData.get(position).getMoviePoster());
                    i.putExtra("id",""+favMovieData.get(position).getMovieid());
                    i.putExtra("title",favMovieData.get(position).getMoviename());
                    i.putExtra("vote_avg",favMovieData.get(position).getMovieVoteAvg());
                    i.putExtra("overview",favMovieData.get(position).getMovieDesc());
                    i.putExtra("releasedate",favMovieData.get(position).getMovieRelease());
                    ct.startActivity(i);
                }
            });
        }
    }
}
