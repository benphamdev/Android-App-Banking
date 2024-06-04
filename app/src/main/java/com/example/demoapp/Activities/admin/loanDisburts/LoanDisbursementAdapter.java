package com.example.demoapp.Activities.admin.loanDisburts;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.Activities.admin.user.User;
import com.example.demoapp.HttpRequest.ApiService;
import com.example.demoapp.Models.Dto.Response.BaseResponse;
import com.example.demoapp.Models.Dto.entity.LoanDetail;
import com.example.demoapp.Models.Dto.entity.LoanDisbursement;
import com.example.demoapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoanDisbursementAdapter extends RecyclerView.Adapter<LoanDisbursementAdapter.LoanDisbursementViewHolder> {

    private List<LoanDisbursement> loanDisbursementList;
    private Context context;

    public LoanDisbursementAdapter(List<LoanDisbursement> loanDisbursementList) {
        this.loanDisbursementList = loanDisbursementList;
    }

    @NonNull
    @Override
    public LoanDisbursementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loan_disbursement, parent, false);
        context = parent.getContext();
        return new LoanDisbursementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoanDisbursementViewHolder holder, int position) {
        LoanDisbursement loanDisbursement = loanDisbursementList.get(position);
        holder.bindData(loanDisbursement);
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return loanDisbursementList.size();
    }
    private void showDeleteConfirmationDialog(int position) {
        new AlertDialog.Builder(context)
                .setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete this account?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> deleteLoanBurse(position))
                .setNegativeButton(android.R.string.no, null)
                .show();
    }
    public void deleteLoanBurse(int position) {
        int loanId = loanDisbursementList.get(position).getLoanDetail().getId();
        Log.d("id", String.valueOf(loanId));
        ApiService.apiService.deleterLoanDisbursements(loanId)
                .enqueue(new Callback<BaseResponse<Void>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Void>> call, Response<BaseResponse<Void>> response) {
                        if (response.isSuccessful()) {
                            loanDisbursementList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, loanDisbursementList.size());
                        } else {
                            try {
                                Log.e("TAG", response.errorBody().toString());
                            }catch (Exception e){
                                e.getMessage();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Void>> call, Throwable t) {
                        Log.e("E", t.getMessage());
                    }
                });
    }

    public class LoanDisbursementViewHolder extends RecyclerView.ViewHolder {
        TextView txtMonthlyPaymentDay, txtLoanDetail;
        ImageView imgDelete;

        public LoanDisbursementViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMonthlyPaymentDay = itemView.findViewById(R.id.tv_loan_monthlyPayDay);
            txtLoanDetail = itemView.findViewById(R.id.tv_loan_detail_burs);
            imgDelete = itemView.findViewById(R.id.deleteLoanBurse);
        }

        public void bindData(LoanDisbursement loanDisbursement) {
            txtMonthlyPaymentDay.setText("Monthly Payment Day: " + loanDisbursement.getMonthlyPaymentDay());
            LoanDetail loanDetail = loanDisbursement.getLoanDetail();
            if (loanDetail != null) {
                txtLoanDetail.setText("LoanDetail: " +"\nId: " + loanDetail.getId()+
                        "\nLoan status: " + loanDetail.getLoanStatus() + "\nLoan Payment Status: " + loanDetail.getLoanPaymentStatus()+
                        "\nReference number: " + loanDetail.getReferenceNumber() + "\nUser: " + "\n" +
                        loanDetail.getUser().getFirstname() + " " +
                        loanDetail.getUser().getLastName() + " " + loanDetail.getUser().getOtherName() + "\nDob: " +
                        loanDetail.getUser().getDob() + "\nGender: " + loanDetail.getUser().getGender() + "\nAddress: " +
                        loanDetail.getUser().getAddress()+ "\nEmail: " + loanDetail.getUser().getEmail()+ "\nPhone number: " +
                        loanDetail.getUser().getPhoneNumber()+
                        "\nLoan Info: " + "\n"+ "\nLoan Amount: " +
                        loanDetail.getLoanInfo().getLoanAmount() + "\nLoan Term: " +
                        loanDetail.getLoanInfo().getLoanTerm() + "\nInterestRate: " +
                        loanDetail.getLoanInfo().getInterestRate()
                );
            } else {
                txtLoanDetail.setText("LoanDetail is null");
            }
        }
    }

    public void setLoanDisbursementList(List<LoanDisbursement> loanDisbursementList) {
        this.loanDisbursementList = loanDisbursementList;
        notifyDataSetChanged();
    }
}
