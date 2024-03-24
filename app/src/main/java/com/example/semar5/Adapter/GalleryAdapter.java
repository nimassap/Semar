package com.example.semar5.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.semar5.R;
import com.example.semar5.ZoomGallery;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    String[] imageUrls;
    Context context;

    public GalleryAdapter(String[] imageUrls, Context context) {
        this.imageUrls = imageUrls;
        this.context = context;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_gallery,parent,false);
        GalleryViewHolder galleryViewHolder = new GalleryViewHolder(view);

        return galleryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        Glide.with(context)
                .load(imageUrls[position])
                .apply(RequestOptions.centerCropTransform())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageUrls.length;
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;

        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.view_gallery);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                String imageUrl = imageUrls[position];

                Intent intent = new Intent(context, ZoomGallery.class);
                intent.putExtra("zoom_img", imageUrl);
                context.startActivity(intent);
                Log.d("ZoomGalleryAdapter", "Image URL: " + imageUrl);
            }
        }
    }
}
