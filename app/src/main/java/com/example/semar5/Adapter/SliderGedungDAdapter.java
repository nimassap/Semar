package com.example.semar5.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.semar5.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class SliderGedungDAdapter extends SliderViewAdapter<SliderGedungDAdapter.Holder> {

    private List<String> imageUrls;
    private Context context;
    private OnSliderItemClickListener listener;

    public SliderGedungDAdapter(List<String> imageUrls, Context context) {
        this.imageUrls = imageUrls;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slider_viewimage, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, final int position) {
        Glide.with(context)
                .load(imageUrls.get(position))
                .into(viewHolder.imageView);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onSliderItemClick(position);
                }
            }
        });
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    public class Holder extends ViewHolder {
        ImageView imageView;

        public Holder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }

    public interface OnSliderItemClickListener {
        void onSliderItemClick(int position);
    }

    public void setOnSliderItemClickListener(OnSliderItemClickListener listener) {
        this.listener = listener;
    }
}