package com.example.demoapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.demoapp.Models.entity.Thumbnail;
import com.example.demoapp.R;

import java.util.List;

public class PhotoAdapter extends PagerAdapter {
    private final Context context;
    private final List<Thumbnail> thumbnails;

    public PhotoAdapter(Context context, List<Thumbnail> thumbnails) {
        this.context = context;
        this.thumbnails = thumbnails;
    }

    @NonNull
    @Override
    public Object instantiateItem(
            @NonNull ViewGroup container, int position
    ) {
        View view = LayoutInflater.from(container.getContext())
                                  .inflate(R.layout.layout_image_view_pager, container, false);
        ImageView imgPhoto = view.findViewById(R.id.img_slider);
        Thumbnail thumbnail = thumbnails.get(position);

        if (thumbnail != null) {
            Glide.with(context)
                 .load(thumbnail.getUrl())
                 .into(imgPhoto);

        }
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if (thumbnails != null) {
            return thumbnails.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(
            @NonNull View view,
            @NonNull Object object
    ) {
        return view == object;
    }

    @Override
    public void destroyItem(
            @NonNull ViewGroup container, int position,
            @NonNull Object object
    ) {
        container.removeView((View) object);
    }
}
