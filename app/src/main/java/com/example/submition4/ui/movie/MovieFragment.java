package com.example.submition4.ui.movie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.submition4.R;
import com.example.submition4.activity.DetailActivity;
import com.example.submition4.adapter.ContentAdapter;
import com.example.submition4.model.ContentModel;

import java.util.ArrayList;
import java.util.Objects;

public class MovieFragment extends Fragment {

    private MovieViewModel movieViewModel;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private IntentFilter intentFilter;
    private BroadcastReceiver broadcastReceiver;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        movieViewModel =
                ViewModelProviders.of(this).get(MovieViewModel.class);
        View root = inflater.inflate(R.layout.fragment_movie, container, false);
        recyclerView = root.findViewById(R.id.rv_movie);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ContentAdapter adapter = new ContentAdapter();
        movieViewModel.getData().observe(Objects.requireNonNull(getActivity()), adapter::setListContent);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickCallback(model -> {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("EXTRA_DATA",model);
            startActivity(intent);
        });
        progressBar = root.findViewById(R.id.progress_movie);
        intentFilter = new IntentFilter("FinishLoader");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("Asu", "onReceive: Dapet uy");
                progressBar.setVisibility(View.GONE);
            }
        };
        Objects.requireNonNull(getActivity()).registerReceiver(broadcastReceiver,intentFilter);
        return root;
    }
}