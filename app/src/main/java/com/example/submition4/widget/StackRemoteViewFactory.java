package com.example.submition4.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.submition4.R;
import com.example.submition4.data.room.ContentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StackRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private final ArrayList<String> mWidgetItems = new ArrayList<>();
    private final List<Bitmap> mWidgetItemsBitmap = new ArrayList<>();
    private final Context mContext;

    StackRemoteViewFactory(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {

    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onDataSetChanged() {
        mWidgetItems.clear();
        mWidgetItemsBitmap.clear();
        ContentRepository repository = new ContentRepository(mContext);
        mWidgetItems.addAll(repository.getFavoriteWidget());
        for (String item :
                mWidgetItems) {
            String path = "https://image.tmdb.org/t/p/w342"+item;
            Log.d("HAHAHA", "onDataSetChanged: "+path);
            Bitmap bitmap = null;
            try {
                bitmap=Glide.with(mContext).asBitmap().load(path).into(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL).get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            mWidgetItemsBitmap.add(bitmap);
        }
        Log.d("ASS", "onDataSetChanged() called");
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        rv.setImageViewBitmap(R.id.imageView,mWidgetItemsBitmap.get(position));
        Bundle extras = new Bundle();
        extras.putInt(FavoriteMovieWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
