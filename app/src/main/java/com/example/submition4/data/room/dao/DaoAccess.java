package com.example.submition4.data.room.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.submition4.model.ContentModel;

import java.util.List;

@Dao
public interface DaoAccess {
    @Query("SELECT*FROM favorite_table WHERE type=1")
    LiveData<List<ContentModel>> getFavoriteMovie();

    @Query("SELECT photo FROM favorite_table WHERE type=1")
    List<String> getFavoriteMovieWidget();

    @Query("SELECT*FROM favorite_table WHERE type=2")
    LiveData<List<ContentModel>> getFavoriteTvShow();

    @Query("SELECT*FROM favorite_table WHERE title LIKE :title")
    List<ContentModel> getDataByName(String title);

    @Insert
    void insertTask(ContentModel contentModel);

    @Delete
    void delete(ContentModel contentModel);
}
