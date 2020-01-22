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
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.submition4.MainActivity;
import com.example.submition4.R;
import com.example.submition4.activity.DetailActivity;
import com.example.submition4.adapter.ContentAdapter;

import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MovieFragment extends Fragment {

    private ProgressBar progressBar;
    private MovieViewModel movieViewModel;
    private ContentAdapter adapter = new ContentAdapter();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        View root = inflater.inflate(R.layout.fragment_movie, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.rv_movie);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        movieViewModel.getData().observe(Objects.requireNonNull(getActivity()), adapter::setListContent);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickCallback(model -> {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("EXTRA_DATA",model);
            startActivityForResult(intent,1);
        });
        progressBar = root.findViewById(R.id.progress_movie);
        IntentFilter intentFilter = new IntentFilter("FinishLoader");
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("Asu", "onReceive: Dapet uy");
                progressBar.setVisibility(View.GONE);
            }
        };
        Objects.requireNonNull(getActivity()).registerReceiver(broadcastReceiver, intentFilter);
        ((MainActivity)getActivity()).setOnSearchBundleChange(new MainActivity.SearchBundel() {
            @Override
            public void onSearchBundelChange(Bundle bundle) {
                Log.d("ASUS", "onSearchBundelChange: "+bundle.getString("EXTRA"));
                movieViewModel.search(bundle.getString("EXTRA"));
                progressBar.setVisibility(View.VISIBLE);
                movieViewModel.getData().observe(Objects.requireNonNull(getActivity()), adapter::setListContent);
            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1){
            if (data != null) {
                movieViewModel.refresh();
                movieViewModel.getData().observe(Objects.requireNonNull(getActivity()), adapter::setListContent);
            }
        }
    }
}