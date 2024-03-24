package com.example.semar5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

public class ZoomGallery extends AppCompatActivity {

    private PhotoView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_gallery);

        imageView = findViewById(R.id.zoom_img);
        Intent intent = getIntent();

        String imageUrl = intent.getStringExtra("zoom_img");
        Glide.with(ZoomGallery.this)
                .load(imageUrl)
                .error(R.drawable.loading6)
                .into(imageView);
        Log.d("ZoomGallery", "Image URL: " + imageUrl);

    }
}