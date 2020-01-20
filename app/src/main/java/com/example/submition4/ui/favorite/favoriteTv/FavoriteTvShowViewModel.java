package com.example.submition4.ui.favorite.favoriteTv;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.submition4.data.room.ContentRepository;
import com.example.submition4.model.ContentModel;

import java.util.ArrayList;

public class FavoriteTvShowViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<ContentModel>> lists = new MutableLiveData<>();

    public FavoriteTvShowViewModel(Application application) {
        super(application);
        refresh();
    }

    LiveData<ArrayList<ContentModel>> getLists(){
        return lists;
    }

    void refresh() {
        ContentRepository repository = new ContentRepository(getApplication());
        repository.getFavoriteTvShows().observeForever(contentModels -> {
            ArrayList<ContentModel> models = new ArrayList<>(contentModels);
            lists.postValue(models);
        });
    }
}
