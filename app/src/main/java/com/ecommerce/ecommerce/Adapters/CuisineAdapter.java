package com.ecommerce.ecommerce.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ecommerce.ecommerce.DataStructure.Cuisine;
import com.ecommerce.ecommerce.R;

import java.util.ArrayList;

public class CuisineAdapter extends RecyclerView.Adapter<CuisineAdapter.ViewHolder> {
    Activity context;
    ArrayList<Cuisine> cuisines;

    public CuisineAdapter(Activity context, ArrayList<Cuisine> cuisines){
        this.context = context;
        this.cuisines = cuisines;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cuisine_thumbnail,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Service will be available soon.", Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cuisine cuisine = cuisines.get(position);
        //Glide.with(context).load(url).into(holder.bannerImage);
        Glide.with(context).load(cuisine.getIcon()).into(holder.cuisineImage);
        holder.cuisineName.setText(cuisine.getName());
    }

    @Override
    public int getItemCount() {
        if (this.cuisines != null){
            return this.cuisines.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cuisineImage;
        TextView cuisineName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cuisineImage = itemView.findViewById(R.id.cuisine_icon);
            cuisineName = itemView.findViewById(R.id.cuisine_name);
        }
    }
}
