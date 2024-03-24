package com.example.semar5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ortiz.touchview.TouchImageView;

import java.util.List;

public class ViewSliderPicDeluxe1 extends AppCompatActivity {

    private TouchImageView imageView;
    private List<String> imageUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_slider_pic_deluxe1);

        imageView = findViewById(R.id.image_view_popup_deluxe1);

        Intent intent = getIntent();
        if (intent != null) {
            int position = intent.getIntExtra("imagePosition", 0);
            imageUrls = intent.getStringArrayListExtra("imageUrls");
            Glide.with(this)
                    .load(imageUrls.get(position))
                    .into(imageView);
        }

    }
}