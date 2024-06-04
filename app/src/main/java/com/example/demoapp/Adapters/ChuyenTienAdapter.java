package com.example.demoapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.Models.entity.Application;
import com.example.demoapp.R;

import java.util.List;

public class ChuyenTienAdapter extends RecyclerView.Adapter<ChuyenTienAdapter.ChuyenTienViewHolder> {

    private List<Application> chuyenTienList;

    public void setData(List<Application> list) {
        this.chuyenTienList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChuyenTienViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType
    ) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.layout_chuyen_tien, parent, false);

        return new ChuyenTienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ChuyenTienViewHolder holder, int position
    ) {
        Application application = chuyenTienList.get(position);
        if (application == null) {
            return;
        }
        holder.tvChuyenTien.setText(application.getName());
        holder.imgChuyenTien.setImageResource(application.getResourceID());

    }

    @Override
    public int getItemCount() {
        if (chuyenTienList != null) {
            return chuyenTienList.size();
        }
        return 0;
    }

    public class ChuyenTienViewHolder extends RecyclerView.ViewHolder {

        private ImageButton imgChuyenTien;
        private TextView tvChuyenTien;

        public ChuyenTienViewHolder(
                @NonNull View itemView
        ) {
            super(itemView);
//            imgChuyenTien = itemView.findViewById(R.id.img_chuyen_tien);
//            tvChuyenTien = itemView.findViewById(R.id.tv_chuyentien);
        }
    }
}
