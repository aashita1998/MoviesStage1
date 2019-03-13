package com.example.acer.moviesstage1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

class Adapterreview extends RecyclerView.Adapter<Adapterreview.ViewHolder> {

    ArrayList<ModelReview> pojoArrayList;
    Context ct;
//this is class method
    public Adapterreview(Main2Activity main2Activity, ArrayList<ModelReview> reviewsArrayList) {

        this.pojoArrayList=reviewsArrayList;
        this.ct=main2Activity;

       // Toast.makeText(main2Activity, ""+pojoArrayList, Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public Adapterreview.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(ct).inflate(R.layout.review,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapterreview.ViewHolder viewHolder, int i) {
        viewHolder.textView.setText(pojoArrayList.get(i).getA());
        viewHolder.textView1.setText(pojoArrayList.get(i).getC());

       //Toast.makeText(ct, ""+pojoArrayList.get(i).getA(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return pojoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView,textView1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.review);
            textView1=itemView.findViewById(R.id.author);
        }
    }
}
