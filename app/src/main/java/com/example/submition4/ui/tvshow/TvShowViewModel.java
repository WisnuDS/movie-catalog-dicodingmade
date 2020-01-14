package com.example.submition4.ui.tvshow;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.submition4.data.DataApi;
import com.example.submition4.model.ContentModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvShowViewModel extends ViewModel {

    private static final String TAG = "TvShowViewModel";
    private static final String API_KEY = "2269263b235103153a5408730cb932a9";
    private MutableLiveData<ArrayList<ContentModel>> listContent = new MutableLiveData<>();

    public TvShowViewModel() {
        if (DataApi.getTvShows().isEmpty()){
            setListContent();
        }else {
            listContent.postValue(DataApi.getTvShows());
        }
    }

    private void setListContent() {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.themoviedb.org/3/discover/tv?api_key="+API_KEY+"&language=en-US";
        ArrayList<ContentModel> contentModels = new ArrayList<>();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d(TAG, "onSuccess: success load data");
                String responses = new String(responseBody);
                ContentModel model;
                try {
                    JSONObject jsonResponses = new JSONObject(responses);
                    JSONArray contens = jsonResponses.getJSONArray("results");
                    for (int i = 0; i < contens.length(); i++) {
                        model = new ContentModel();
                        JSONObject object = contens.getJSONObject(i);
                        model.setId(2);
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d(TAG, "onFailure: Failed...");
            }
        });

    }

    public LiveData<ArrayList<ContentModel>> getData(){
        return listContent;
    }
}