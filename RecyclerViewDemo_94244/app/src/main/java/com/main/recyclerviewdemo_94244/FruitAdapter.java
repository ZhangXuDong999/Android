package com.main.recyclerviewdemo_94244;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2019/5/15.
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.MyViewHolder> {


    List<Fruit> fruits = null;


    static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView fruit_image;
        TextView fruit_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fruit_image = (ImageView)itemView.findViewById(R.id.fruit_image);
            fruit_name = (TextView)itemView.findViewById(R.id.fruit_name);
        }
    }

    public FruitAdapter(List<Fruit> fruits) {
        this.fruits = fruits;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fruit_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Fruit fruit = fruits.get(position);
        holder.fruit_image.setImageResource(fruit.getImageId());
        holder.fruit_name.setText(fruit.getName());
    }

    @Override
    public int getItemCount() {
        return fruits.size();
    }
}
