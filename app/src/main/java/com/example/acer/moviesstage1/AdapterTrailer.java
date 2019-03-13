package com.example.acer.moviesstage1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class AdapterTrailer extends RecyclerView.Adapter<AdapterTrailer.ViewHolder> {
    Context context;
    ArrayList<ModelTrailer> modelTrailerArrayList;

    public AdapterTrailer(Main2Activity main2Activity, ArrayList<ModelTrailer> trailerArrayList) {
        this.context=main2Activity;
        this.modelTrailerArrayList=trailerArrayList;
    }


    @Override
    public AdapterTrailer.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(context).inflate(R.layout.trailer,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTrailer.ViewHolder viewHolder, int i) {
       viewHolder.textView1.setText(modelTrailerArrayList.get(i).getMname());
       // Picasso.with(context).load(modelTrailerArrayList.get(i).getMt()).into(viewHolder.imageView1);


    }

    @Override
    public int getItemCount() {
        return modelTrailerArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        ImageView imageView1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1=itemView.findViewById(R.id.tratext);
          //  imageView1=itemView.findViewById(R.id.img2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position =getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION);

                    String keys =modelTrailerArrayList.get(position).getMkey();
                    Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+keys));
                    context.startActivity(intent);
                }
            });
        }
    }
}
