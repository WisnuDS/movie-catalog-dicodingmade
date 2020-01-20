package com.example.submition4.data.room.database;

import androidx.room.RoomDatabase;

import com.example.submition4.data.room.dao.DaoAccess;
import com.example.submition4.model.ContentModel;

@androidx.room.Database(entities = {ContentModel.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {
    public abstract DaoAccess daoAccess();
}
