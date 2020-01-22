package com.example.submition4.ui.tvshow;

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

public class TvShowFragment extends Fragment {

    private ProgressBar progressBar;
    private TvShowViewModel tvShowViewModel;
    private ContentAdapter adapter = new ContentAdapter();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tv_show, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.rv_tv_show);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tvShowViewModel.getData().observe(Objects.requireNonNull(getActivity()), adapter::setListContent);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickCallback(model -> {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("EXTRA_DATA",model);
            startActivityForResult(intent,1);
        });
        progressBar = root.findViewById(R.id.progress_tv_show);
        IntentFilter intentFilter = new IntentFilter("FinishLoader");
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                progressBar.setVisibility(View.GONE);
            }
        };
        Objects.requireNonNull(getActivity()).registerReceiver(broadcastReceiver, intentFilter);
        Objects.requireNonNull(getActivity()).registerReceiver(broadcastReceiver, intentFilter);
        ((MainActivity)getActivity()).setOnSearchBundleChange(new MainActivity.SearchBundel() {
            @Override
            public void onSearchBundelChange(Bundle bundle) {
                Log.d("ASUSS", "onSearchBundelChange: "+bundle.getString("EXTRA"));
                tvShowViewModel.search(bundle.getString("EXTRA"));
                progressBar.setVisibility(View.VISIBLE);
                tvShowViewModel.getData().observe(Objects.requireNonNull(getActivity()), adapter::setListContent);
            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1){
            if (data != null) {
                tvShowViewModel.refresh();
                tvShowViewModel.getData().observe(Objects.requireNonNull(getActivity()), adapter::setListContent);
            }
        }
    }
}