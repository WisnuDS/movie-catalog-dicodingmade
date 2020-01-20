package com.example.submition4.ui.favorite.favoriteMovie;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.submition4.data.room.ContentRepository;
import com.example.submition4.model.ContentModel;

import java.util.ArrayList;

public class FavoriteMovieViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<ContentModel>> lists = new MutableLiveData<>();

    public FavoriteMovieViewModel(Application application) {
        super(application);
        refresh();
    }

    void refresh(){
        ContentRepository repository = new ContentRepository(getApplication());
        repository.getFavoriteMovies().observeForever(contentModels -> {
            ArrayList<ContentModel> models = new ArrayList<>(contentModels);
            lists.postValue(models);
        });
    }

    LiveData<ArrayList<ContentModel>> getLists(){
        return lists;
    }
}
