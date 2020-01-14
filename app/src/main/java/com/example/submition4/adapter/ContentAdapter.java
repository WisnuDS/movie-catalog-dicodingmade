package com.example.submition4.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.submition4.R;
import com.example.submition4.model.ContentModel;

import java.util.ArrayList;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ListViewHolder> {
    private ArrayList<ContentModel> listContent = new ArrayList<>();

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setListContent(ArrayList<ContentModel> listContent) {
        this.listContent.clear();
        this.listContent.addAll(listContent);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        ContentModel contentModel = listContent.get(position);
        holder.tvTitle.setText(contentModel.getTitle());
        holder.tvDescription.setText(contentModel.getDescription());
        holder.tvRelease.setText(contentModel.getRelease());
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w185"+contentModel.getPhoto())
                .placeholder(holder.itemView.getResources().getDrawable(R.drawable.placeholder_portrait))
                .into(holder.imgContent);
        holder.itemView.setOnClickListener(v -> onItemClickCallback.onItemCallback(listContent.get(holder.getAdapterPosition())));
        Intent intent = new Intent("FinishLoader");
        holder.itemView.getContext().sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return listContent.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription, tvRelease;
        ImageView imgContent;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvRelease = itemView.findViewById(R.id.tv_release);
            imgContent = itemView.findViewById(R.id.img_content);

        }
    }

    public interface OnItemClickCallback {
        void onItemCallback(ContentModel model);
    }
}
