package com.example.submition4.data;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.submition4.model.ContentModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

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
