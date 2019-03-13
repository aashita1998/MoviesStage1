package com.example.acer.moviesstage1;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acer.moviesstage1.Model_Movies.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    ArrayList<Movie> list;
    Context ct;
    public MyAdapter(MainActivity mainActivity, ArrayList<Movie> list) {
        this.ct = mainActivity;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(ct).inflate(R.layout.raw,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder viewHolder, int i) {
       viewHolder.textView.setText(list.get(i).getTitle());
        Picasso.with(ct).load(list.get(i).getImage()).into(viewHolder.imageView);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            textView=itemView.findViewById(R.id.text1);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int pos=getAdapterPosition();
            Intent i=new Intent(ct,Main2Activity.class);
            i.putExtra("image",list.get(pos).getImage());
            i.putExtra("id",list.get(pos).getId());
            i.putExtra("title",list.get(pos).getTitle());
            i.putExtra("vote_avg",list.get(pos).getVote());
            i.putExtra("overview",list.get(pos).getOverview());
            i.putExtra("releasedate",list.get(pos).getReleasedate());
            ct.startActivity(i);


        }
    }
}
