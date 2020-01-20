package com.example.submition4.ui.favorite.favoriteTv;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.submition4.R;
import com.example.submition4.activity.DetailActivity;
import com.example.submition4.adapter.ContentAdapter;

import java.util.Objects;

public class FavoriteTvShowFragment extends Fragment {

    private FavoriteTvShowViewModel mViewModel;

    private ContentAdapter adapter = new ContentAdapter();

    public static FavoriteTvShowFragment newInstance() {
        return new FavoriteTvShowFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(FavoriteTvShowViewModel.class);
        View root = inflater.inflate(R.layout.favorite_tv_show_fragment, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.rv_tv_show_favorite);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mViewModel.getLists().observe(Objects.requireNonNull(getActivity()), adapter::setListContent);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickCallback(model -> {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("EXTRA_DATA",model);
            startActivityForResult(intent,1);
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FavoriteTvShowViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1){
            if (data != null) {
                mViewModel.refresh();
                mViewModel.getLists().observe(Objects.requireNonNull(getActivity()), adapter::setListContent);
            }
        }
    }
}
