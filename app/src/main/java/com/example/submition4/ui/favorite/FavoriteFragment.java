package com.example.submition4.ui.favorite;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.submition4.R;
import com.example.submition4.adapter.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import static com.example.submition4.R.menu.menu_item;

public class FavoriteFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FragmentAdapter fragmentAdapter = new FragmentAdapter(Objects.requireNonNull(getActivity()).getSupportFragmentManager(),getActivity());
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);
        TabLayout tabLayout = root.findViewById(R.id.tab_favorite);
        ViewPager viewPager = root.findViewById(R.id.view_pager);
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.findItem(R.id.search_view);
        item.setVisible(false);
    }

}


