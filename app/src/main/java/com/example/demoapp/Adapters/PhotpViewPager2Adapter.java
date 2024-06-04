package com.example.demoapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.demoapp.Activities.Photo;
import com.example.demoapp.R;

import java.util.List;

public class PhotpViewPager2Adapter extends RecyclerView.Adapter<PhotpViewPager2Adapter.PhotoViewHolder> {

    private static Context context;
    private List<Photo> photoList;

    public PhotpViewPager2Adapter(List<Photo> photoList) {
        this.photoList = photoList;
    }

    public void setItem(List<Photo> photos) {
        this.photoList = photos;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType
    ) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.layout_image_view_pager, parent, false);
        context = parent.getContext();
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull PhotoViewHolder holder, int position
    ) {
        holder.setImageView(photoList.get(position));

    }

    @Override
    public int getItemCount() {
        if (photoList != null) {
            return photoList.size();
        }
        return 0;
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgViewPager2;

        public PhotoViewHolder(
                @NonNull View itemView
        ) {
            super(itemView);
            imgViewPager2 = itemView.findViewById(R.id.img_slider);
        }

        void setImageView(Photo photo) {
            //custom settings for fast loading image
            RequestOptions options = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .centerCrop()
                    .priority(Priority.HIGH)
                    .format(DecodeFormat.PREFER_RGB_565);

            Glide.with(context)
                 .applyDefaultRequestOptions(options)
                 .load(photo.getUrl())
                 .thumbnail(0.4f)
                 .into(imgViewPager2);
        }
    }
}
