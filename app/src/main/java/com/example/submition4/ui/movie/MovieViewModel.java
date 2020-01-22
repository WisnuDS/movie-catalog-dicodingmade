package com.example.submition4.ui.movie;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.submition4.data.room.ContentRepository;
import com.example.submition4.model.ContentModel;

import java.util.ArrayList;

public class MovieViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<ContentModel>> listContent;

    public MovieViewModel(Application application) {
        super(application);
        refresh();
    }

    void search(String search){
        if (search.isEmpty()){
            refresh();
        }else {
            ContentRepository repository = new ContentRepository(getApplication());
            listContent = repository.setListMovie(search);
        }
    }

    void refresh(){
        ContentRepository repository = new ContentRepository(getApplication());
        listContent = new MutableLiveData<>();
        listContent = repository.setListMovie();
    }

    public LiveData<ArrayList<ContentModel>> getData(){
        return listContent;
    }

}