package com.example.demoapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.demoapp.Activities.Photo;
import com.example.demoapp.R;

import java.util.List;

public class PhotoAdapter extends PagerAdapter {
    private Context context;
    private List<Photo> photos;

    public PhotoAdapter(Context context, List<Photo> photos) {
        this.context = context;
        this.photos = photos;
    }

    @NonNull
    @Override
    public Object instantiateItem(
            @NonNull ViewGroup container, int position
    ) {
        View view = LayoutInflater.from(container.getContext())
                                  .inflate(R.layout.layout_image_view_pager, container, false);
        ImageView imgPhoto = view.findViewById(R.id.img_slider);
        Photo photo = photos.get(position);

        if (photo != null) {
            Glide.with(context)
                 .load(photo.getUrl())
                 .into(imgPhoto);

        }
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if (photos != null) {
            return photos.size();
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
