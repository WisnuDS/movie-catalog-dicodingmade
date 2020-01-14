package com.example.submition4.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.submition4.R;
import com.example.submition4.model.ContentModel;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {
    ImageView imgContentDetail,imgBackdrop,imgBack;
    TextView titleDetail;
    TextView textViewReleaseDetail;
    TextView tvDescriptionDetail;
    RatingBar ratingBar;
    TextView tvRating;
    public static final String EXTRA_IMAGE = "EXTRA_IMAGE";
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        titleDetail = findViewById(R.id.title_detail);
        textViewReleaseDetail = findViewById(R.id.textView_release_detail);
        tvDescriptionDetail = findViewById(R.id.tv_description_detail);
        imgContentDetail = findViewById(R.id.img_content_detail);
        ratingBar = findViewById(R.id.ratingBar);
        imgBackdrop =  findViewById(R.id.backdop);
        tvRating = findViewById(R.id.tv_rating);
        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(v -> onBackPressed());
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
        actionBar.hide();
        ContentModel model = getIntent().getParcelableExtra("EXTRA_DATA");
        assert model != null;
        titleDetail.setText(model.getTitle());
        textViewReleaseDetail.setText(model.getRelease());
        tvDescriptionDetail.setText(model.getDescription());
        ratingBar.setRating((float) (Float.parseFloat(model.getRating())*0.5));
        tvRating.setText("("+model.getRating()+")");
        String photoPath = "https://image.tmdb.org/t/p/w342"+model.getPhoto();
        String backdropPath = "https://image.tmdb.org/t/p/w342"+model.getBackdropPhoto();
        Glide.with(this)
                .load(photoPath)
                .placeholder(getResources().getDrawable(R.drawable.placeholder_portrait))
                .into(imgContentDetail);
        Glide.with(this)
                .load(backdropPath)
                .placeholder(getResources().getDrawable(R.drawable.img_placeholder))
                .into(imgBackdrop);
        imgContentDetail.setOnClickListener(v -> {
            Intent intent = new Intent(this, ImageZoomActivity.class);
            intent.putExtra(EXTRA_IMAGE,photoPath);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(this,android.R.anim.fade_in,android.R.anim.fade_in);
            startActivity(intent,options.toBundle());
        });
    }
}
