package com.example.submition4.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.submition4.R;

public class ImageZoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_image_zoom);
        String imagePath = getIntent().getStringExtra(DetailActivity.EXTRA_IMAGE);
        ImageView imageView = findViewById(R.id.img_zoom);
        Glide.with(this)
                .load(imagePath)
                .placeholder(getDrawable(R.drawable.placeholder_portrait))
                .into(imageView);
    }
}
