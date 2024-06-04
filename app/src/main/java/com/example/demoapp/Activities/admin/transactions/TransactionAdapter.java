package com.example.demoapp.Activities.admin.transactions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.Models.Dto.Response.UserTransaction;
import com.example.demoapp.R;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.UserTransactionViewHolder> {

    private List<UserTransaction> transactionList;
    private Context context;

    public TransactionAdapter(List<UserTransaction> transactionList) {
        this.transactionList = transactionList;
    }

    public void setTransactions(List<UserTransaction> userTransactions){
        this.transactionList = userTransactions;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public UserTransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_transaction, parent, false);
        return new UserTransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserTransactionViewHolder holder, int position) {
        UserTransaction transaction = transactionList.get(position);
        holder.bind(transaction);
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public class UserTransactionViewHolder extends RecyclerView.ViewHolder {
        private TextView transactionTypeTextView;
        private TextView fromAccountTextView;
        private TextView toAccountTextView;
        private TextView amountTextView;
        private TextView descriptionTextView;
        private TextView statusTextView;

        public UserTransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            transactionTypeTextView = itemView.findViewById(R.id.tv_transaction_type);
            fromAccountTextView = itemView.findViewById(R.id.tv_transaction_fromaccount);
            toAccountTextView = itemView.findViewById(R.id.tv_transaction_toaccount);
            amountTextView = itemView.findViewById(R.id.tv_transaction_amount);
            descriptionTextView = itemView.findViewById(R.id.tv_transaction_description);
            statusTextView = itemView.findViewById(R.id.tv_transaction_status);
        }

        public void bind(UserTransaction transaction) {
            transactionTypeTextView.setText("Type: "+transaction.getTransactionType());
            fromAccountTextView.setText("From account: "+transaction.getFromAccount());
            toAccountTextView.setText("To account: "+transaction.getToAccount());
            amountTextView.setText("Amount: "+transaction.getAmount().toString());
            descriptionTextView.setText("Description: "+transaction.getDescription());
            statusTextView.setText("Status: "+transaction.getStatus());
        }
    }
}
