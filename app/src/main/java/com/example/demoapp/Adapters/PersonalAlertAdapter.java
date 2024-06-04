package com.example.demoapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demoapp.R;

import java.util.ArrayList;
import java.util.List;

public class PersonalAlertAdapter extends RecyclerView.Adapter<PersonalAlertAdapter.ExampleViewHolder> {
    private List<NotificationPersonalPromotion> mData;
    private Context context;

    public PersonalAlertAdapter(List<NotificationPersonalPromotion> data) {
        mData = data;
    }

    public void setFilter(List<NotificationPersonalPromotion> filteredList) {
        this.mData = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType
    ) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.layout_notification23, parent, false);
        context = parent.getContext();
        return new ExampleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ExampleViewHolder holder, int position
    ) {
        NotificationPersonalPromotion currentNotification = mData.get(position);
        holder.tvDescription.setText(currentNotification.getDescription());
        holder.tvTime.setText(currentNotification.getName());
        Glide.with(context)
             .load(currentNotification.getResourceID())
             .into(holder.imgNotification);
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    static class ExampleViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTime, tvDescription;
        private ImageView imgNotification, imgFavorite, imgComment;

        ExampleViewHolder(View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvDescription = itemView.findViewById(R.id.tv_description);
            imgNotification = itemView.findViewById(R.id.img_notification);
            imgFavorite = itemView.findViewById(R.id.img_favorite);
        }
    }
}
