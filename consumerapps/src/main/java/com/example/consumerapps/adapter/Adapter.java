package com.example.consumerapps.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.consumerapps.R;
import com.example.consumerapps.model.Model;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private ArrayList<Model> list = new ArrayList<>();

    public void setList(ArrayList<Model> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_widget_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.release.setText(list.get(position).getRelease());
        holder.description.setText(list.get(position).getDescription());
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w185"+list.get(position).getPhoto())
                .into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, release,description;
        ImageView imgPoster;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            release = itemView.findViewById(R.id.tv_release);
            description = itemView.findViewById(R.id.tv_description);
            imgPoster = itemView.findViewById(R.id.img_content);
        }
    }
}
