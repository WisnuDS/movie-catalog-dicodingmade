package com.example.submition4.ui.tvshow;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.submition4.data.room.ContentRepository;
import com.example.submition4.model.ContentModel;

import java.util.ArrayList;


public class TvShowViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<ContentModel>> listContent;

    public TvShowViewModel(Application application) {
        super(application);
        refresh();
    }

    void search(String search){
        if (search.isEmpty()){
            refresh();
        }else {
            ContentRepository repository = new ContentRepository(getApplication());
            listContent = repository.setListTvShow(search);
        }
    }

    void refresh(){
        ContentRepository repository = new ContentRepository(getApplication());
        listContent = new MutableLiveData<>();
        listContent = repository.setListTvShow();
    }

    public LiveData<ArrayList<ContentModel>> getData(){
        return listContent;
    }
}