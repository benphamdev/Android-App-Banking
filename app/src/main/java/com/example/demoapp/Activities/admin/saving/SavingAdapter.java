package com.example.demoapp.Activities.admin.saving;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.demoapp.Models.Dto.entity.Saving;
import com.example.demoapp.Models.Dto.sharePreferences.SharePreferencesManager;
import com.example.demoapp.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SavingAdapter extends RecyclerView.Adapter<SavingAdapter.SavingViewHolder> {

    private Saving saving;
    private Context context;

    public SavingAdapter(Saving saving) {
        this.saving = saving;
    }

    @NonNull
    @Override
    public SavingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.saving, parent, false);
        context = parent.getContext();
        return new SavingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SavingViewHolder holder, int position) {
        holder.bind(saving);
    }

    @Override
    public int getItemCount() {
        return 1; // Chỉ có một mục trong danh sách
    }

    public class SavingViewHolder extends RecyclerView.ViewHolder {
        private TextView tvID;
        private TextView maturationDateTextView;
        private TextView baseAmountTextView;
        private TextView refundAmountTextView;
        private TextView durationTextView;
        private TextView statusRefundTextView;

        public SavingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.tv_saving_id);
            maturationDateTextView = itemView.findViewById(R.id.tv_saving_maturationDate);
            baseAmountTextView = itemView.findViewById(R.id.tv_saving_baseAmount);
            refundAmountTextView = itemView.findViewById(R.id.tv_saving_refundAmount);
            durationTextView = itemView.findViewById(R.id.tv_saving_duration);
            statusRefundTextView = itemView.findViewById(R.id.tv_saving_statusRefund);
        }

        public void bind(Saving saving) {
            tvID.setText("Id: "+ saving.getId());
            maturationDateTextView.setText("MaturationDate:" +saving.getMaturationDate());
            baseAmountTextView.setText("Base Amount: " +saving.getBaseAmount().toString());
            refundAmountTextView.setText("Refund Amount: " +saving.getRefundAmount().toString());
            durationTextView.setText("Duration: "+String.valueOf(saving.getDuration()));
            statusRefundTextView.setText(saving.isStatusRefund() ? "Refunded" : "Not refunded");
            SharePreferencesManager manager = new SharePreferencesManager(context);
            manager.saveSavingId(saving.getId());

        }
    }
}
