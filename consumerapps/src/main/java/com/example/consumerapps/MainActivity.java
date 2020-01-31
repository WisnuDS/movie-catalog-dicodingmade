package com.example.consumerapps;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consumerapps.adapter.Adapter;
import com.example.consumerapps.model.Model;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.rv_list_widget);
        Adapter adapter = new Adapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Cursor cursor = getContentResolver().query(Uri.parse("content://com.exemple.submition4/favorite_table"),null,null,null,null);
        ArrayList<Model> list;
        if (cursor != null) {
            list = new ArrayList<>(mapToArrayList(cursor));
            adapter.setList(list);
        }
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<Model> mapToArrayList(Cursor cursor) {
        ArrayList<Model> modelArrayList = new ArrayList<>();
        Model model;
        while (cursor.moveToNext()){
            model = new Model();
            model.setTitle(cursor.getString(2));
            model.setRelease(cursor.getString(3));
            model.setDescription(cursor.getString(4));
            model.setPhoto(cursor.getString(5));
            modelArrayList.add(model);
        }
        return modelArrayList;
    }
}
