package com.example.demoapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.R;

import java.util.ArrayList;

public class BalanceAlertAdapter extends RecyclerView.Adapter<BalanceAlertAdapter.ExampleViewHolder> {
    private ArrayList<String> mData;
    private ArrayList<String> filteredList;

    public BalanceAlertAdapter(ArrayList<String> data) {
        mData = data;
    }

    public void setFilter(ArrayList<String> filteredList) {
        this.filteredList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType
    ) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.layout_notification_balance, parent, false);
        return new ExampleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ExampleViewHolder holder, int position
    ) {
        String data = mData.get(position);
        if (data == null) {
            return;
        }
        holder.tvNotification.setText(data);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class ExampleViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNotification;

        ExampleViewHolder(View itemView) {
            super(itemView);
            tvNotification = itemView.findViewById(R.id.tv_notification_balance);

        }
    }
}
