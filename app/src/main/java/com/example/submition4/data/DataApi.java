package com.example.submition4.data;


import com.example.submition4.model.ContentModel;
import java.util.ArrayList;

public class DataApi {
    public static ArrayList<ContentModel> movies = new ArrayList<>();
    public static ArrayList<ContentModel> tvShows = new ArrayList<>();

    public static ArrayList<ContentModel> getMovies() {
        return movies;
    }

    public static void setMovies(ArrayList<ContentModel> movies) {
        DataApi.movies = movies;
    }

    public static ArrayList<ContentModel> getTvShows() {
        return tvShows;
    }

    public static void setTvShows(ArrayList<ContentModel> tvShows) {
        DataApi.tvShows = tvShows;
    }

}
