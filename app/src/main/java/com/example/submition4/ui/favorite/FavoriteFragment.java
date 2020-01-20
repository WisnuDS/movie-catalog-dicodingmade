package com.example.submition4.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.submition4.R;
import com.example.submition4.adapter.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class FavoriteFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FragmentAdapter fragmentAdapter = new FragmentAdapter(Objects.requireNonNull(getActivity()).getSupportFragmentManager(),getActivity());
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);
        TabLayout tabLayout = root.findViewById(R.id.tab_favorite);
        ViewPager viewPager = root.findViewById(R.id.view_pager);
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return root;
    }
}