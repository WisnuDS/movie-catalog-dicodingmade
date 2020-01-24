package com.example.submition4.data.room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.submition4.BuildConfig;
import com.example.submition4.data.DataApi;
import com.example.submition4.data.room.database.Database;
import com.example.submition4.model.ContentModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class ContentRepository {
    private static final String TAG = "MovieViewModel";
    private static final String API_KEY = new String(Base64.decode(BuildConfig.API_KEY,1));

    private Database database;

    public ContentRepository(Context context){
        String DB_NAME = "db_favorite";
        database = Room.databaseBuilder(context,Database.class, DB_NAME).build();
    }

    public ArrayList<String> getFavoriteWidget(){
        return new ArrayList<>(database.daoAccess().getFavoriteMovieWidget());
    }

    public LiveData<List<ContentModel>> getFavoriteMovies(){
        return database.daoAccess().getFavoriteMovie();
    }

    public LiveData <List<ContentModel>> getFavoriteTvShows(){
        return database.daoAccess().getFavoriteTvShow();
    }

    @SuppressLint("StaticFieldLeak")
    public void insertFavorite(final ContentModel contentModel){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                database.daoAccess().insertTask(contentModel);
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteFavorite(final ContentModel contentModel){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                database.daoAccess().delete(contentModel);
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private List<ContentModel> getDataByName(String title){
        return database.daoAccess().getDataByName(title);
    }

    @SuppressLint("StaticFieldLeak")
    private void setFavoriteMovie(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                List<ContentModel> list = new ArrayList<>();
                for (ContentModel model :
                        DataApi.getMovies()) {
                    list.clear();
                    list.addAll(getDataByName(model.getTitle()));
                    model.setFavorite(!list.isEmpty());
                    if(model.isFavorite()){
                        model.setId(list.get(0).getId());
                    }
                }
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void setFavoriteTvShow(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                List<ContentModel> list = new ArrayList<>();
                for (ContentModel model :
                        DataApi.getTvShows()) {
                    list.clear();
                    list.addAll(getDataByName(model.getTitle()));
                    model.setFavorite(!list.isEmpty());
                    if(model.isFavorite()){
                        model.setId(list.get(0).getId());
                    }
                    Log.d(TAG, "onSuccess: cek Favorite : "+model.isFavorite());
                }
                return null;
            }
        }.execute();
    }

    public MutableLiveData<ArrayList<ContentModel>> setListMovie() {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.themoviedb.org/3/discover/movie?api_key="+API_KEY+"&language=en-US";
        MutableLiveData<ArrayList<ContentModel>> listContent = new MutableLiveData<>();
        ArrayList<ContentModel> contentModels = new ArrayList<>();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d(TAG, "onSuccess: success load data");
                String responses = new String(responseBody);
                ContentModel model;
                try {
                    JSONObject jsonResponses = new JSONObject(responses);
                    JSONArray contents = jsonResponses.getJSONArray("results");
                    for (int i = 0; i < contents.length(); i++) {
                        model = new ContentModel();
                        JSONObject object = contents.getJSONObject(i);
                        model.setType(1);
                        model.setTitle(object.getString("title"));
                        model.setRating(object.getString("vote_average"));
                        model.setDescription(object.getString("overview"));
                        model.setRelease(object.getString("release_date"));
                        model.setPhoto(object.getString("poster_path"));
                        model.setBackdropPhoto(object.getString("backdrop_path"));
                        contentModels.add(model);
                    }
                    listContent.postValue(contentModels);
                    DataApi.setMovies(contentModels);
                    setFavoriteMovie();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d(TAG, "onFailure: Failed...");
            }
        });
        return listContent;
    }

    public MutableLiveData<ArrayList<ContentModel>> setListMovie(String search) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.themoviedb.org/3/search/movie?api_key="+API_KEY+"&language=en-US&query="+search;
        MutableLiveData<ArrayList<ContentModel>> listContent = new MutableLiveData<>();
        ArrayList<ContentModel> contentModels = new ArrayList<>();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d(TAG, "onSuccess: success load data");
                String responses = new String(responseBody);
                ContentModel model;
                try {
                    JSONObject jsonResponses = new JSONObject(responses);
                    JSONArray contents = jsonResponses.getJSONArray("results");
                    for (int i = 0; i < contents.length(); i++) {
                        model = new ContentModel();
                        JSONObject object = contents.getJSONObject(i);
                        model.setType(1);
                        model.setTitle(object.getString("title"));
                        model.setRating(object.getString("vote_average"));
                        model.setDescription(object.getString("overview"));
                        model.setRelease(object.getString("release_date"));
                        model.setPhoto(object.getString("poster_path"));
                        model.setBackdropPhoto(object.getString("backdrop_path"));
                        contentModels.add(model);
                    }
                    listContent.postValue(contentModels);
                    DataApi.setMovies(contentModels);
                    setFavoriteMovie();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d(TAG, "onFailure: Failed..."+statusCode);
            }
        });
        return listContent;
    }

    public MutableLiveData<ArrayList<ContentModel>> setListTvShow() {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.themoviedb.org/3/discover/tv?api_key="+API_KEY+"&language=en-US";
        ArrayList<ContentModel> contentModels = new ArrayList<>();
        MutableLiveData<ArrayList<ContentModel>> listContent = new MutableLiveData<>();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d(TAG, "onSuccess: success load data");
                String responses = new String(responseBody);
                ContentModel model;
                try {
                    JSONObject jsonResponses = new JSONObject(responses);
                    JSONArray contents = jsonResponses.getJSONArray("results");
                    for (int i = 0; i < contents.length(); i++) {
                        model = new ContentModel();
                        JSONObject object = contents.getJSONObject(i);
                        model.setType(2);
                        model.setTitle(object.getString("name"));
                        model.setRating(object.getString("vote_average"));
                        model.setDescription(object.getString("overview"));
                        model.setRelease(object.getString("first_air_date"));
                        model.setPhoto(object.getString("poster_path"));
                        model.setBackdropPhoto(object.getString("backdrop_path"));
                        contentModels.add(model);
                    }
                    listContent.postValue(contentModels);
                    DataApi.setTvShows(contentModels);
                    setFavoriteTvShow();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d(TAG, "onFailure: Failed...");
            }
        });
        return listContent;
    }

    public MutableLiveData<ArrayList<ContentModel>> setListTvShow(String search) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.themoviedb.org/3/search/tv?api_key="+API_KEY+"&language=en-US&query="+search;
        ArrayList<ContentModel> contentModels = new ArrayList<>();
        MutableLiveData<ArrayList<ContentModel>> listContent = new MutableLiveData<>();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d(TAG, "onSuccess: success load data");
                String responses = new String(responseBody);
                Log.d(TAG, "onSuccess: "+responses);
                ContentModel model;
                try {
                    JSONObject jsonResponses = new JSONObject(responses);
                    JSONArray contents = jsonResponses.getJSONArray("results");
                    for (int i = 0; i < contents.length(); i++) {
                        model = new ContentModel();
                        JSONObject object = contents.getJSONObject(i);
                        model.setType(2);
                        model.setTitle(object.getString("name"));
                        model.setRating(object.getString("vote_average"));
                        model.setDescription(object.getString("overview"));
                        model.setRelease(object.getString("first_air_date"));
                        model.setPhoto(object.getString("poster_path"));
                        model.setBackdropPhoto(object.getString("backdrop_path"));
                        contentModels.add(model);
                    }
                    listContent.postValue(contentModels);
                    DataApi.setTvShows(contentModels);
                    setFavoriteTvShow();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d(TAG, "onFailure: Failed...");
            }
        });
        return listContent;
    }

    public ArrayList<String>getMovieReleaseToday() {
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        SyncHttpClient client = new SyncHttpClient();
        String url = "https://api.themoviedb.org/3/discover/movie?api_key="+API_KEY+"&primary_release_date.gte="+date+"&primary_release_date.lte="+date;
        ArrayList<String> contentModels = new ArrayList<>();
        client.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(TAG, "onFailure: Failed... hoho");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d(TAG, "onSuccess: success load data");
                Log.d(TAG, "onSuccess: "+ responseString);
                try {
                    JSONObject jsonResponses = new JSONObject(responseString);
                    JSONArray contents = jsonResponses.getJSONArray("results");
                    for (int i = 0; i < contents.length(); i++) {
                        JSONObject object = contents.getJSONObject(i);
                        contentModels.add(object.getString("title"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return contentModels;
    }
}
