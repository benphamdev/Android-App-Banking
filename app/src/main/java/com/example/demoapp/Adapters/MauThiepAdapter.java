package com.example.demoapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.Activities.ChuyenTien.MauThiep;
import com.example.demoapp.R;

import java.util.List;

public class MauThiepAdapter extends RecyclerView.Adapter<MauThiepAdapter.MauThiepViewHolder> {

    private List<MauThiep> mauThiepList;

    public void setData(List<MauThiep> mauThieps) {
        this.mauThiepList = mauThieps;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MauThiepViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType
    ) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.layout_qua_tang, parent, false);
        return new MauThiepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull MauThiepViewHolder holder, int position
    ) {
        MauThiep mauThiep = mauThiepList.get(position);
        if (mauThiep == null) {
            return;
        }
        holder.imgMauThiep.setImageResource(mauThiep.getImage());

    }

    @Override
    public int getItemCount() {
        if (mauThiepList != null) {
            return mauThiepList.size();
        }
        return 0;
    }

    public class MauThiepViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgMauThiep;

        public MauThiepViewHolder(
                @NonNull View itemView
        ) {
            super(itemView);
            imgMauThiep = itemView.findViewById(R.id.img_mauthiep);
        }
    }
}
