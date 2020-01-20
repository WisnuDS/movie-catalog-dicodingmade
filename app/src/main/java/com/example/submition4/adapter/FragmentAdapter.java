package com.example.submition4.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.submition4.R;
import com.example.submition4.ui.favorite.favoriteMovie.FavoriteMovieFragment;
import com.example.submition4.ui.favorite.favoriteTv.FavoriteTvShowFragment;

public class FragmentAdapter extends FragmentStatePagerAdapter {
    private static final int[] TAB_TITLES = new int[]{R.string.title_movie, R.string.title_tv_show};
    private final Context mContext;
    public FragmentAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return FavoriteMovieFragment.newInstance();
        }else{
            return FavoriteTvShowFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }
}
