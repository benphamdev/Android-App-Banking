package com.example.demoapp.Activities.admin.loan;

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

import com.example.demoapp.HttpRequest.ApiService;
import com.example.demoapp.Models.Dto.Response.BaseResponse;
import com.example.demoapp.Models.Dto.entity.Enums;
import com.example.demoapp.Models.Dto.entity.LoanDetail;
import com.example.demoapp.Models.Dto.entity.LoanInfo;
import com.example.demoapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoanDetailAdapter extends RecyclerView.Adapter<LoanDetailAdapter.LoanDetailViewHolder> {

    private List<LoanDetail> loanDetailList;
    private Context context;

    public LoanDetailAdapter(List<LoanDetail> loanDetailList) {
        this.loanDetailList = loanDetailList;
    }

    @NonNull
    @Override
    public LoanDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loan_detail, parent, false);
        context = parent.getContext();
        return new LoanDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoanDetailViewHolder holder, int position) {
        LoanDetail loanDetail = loanDetailList.get(position);
        holder.bindData(loanDetail);
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog(position);
            }
        });
        holder.imgApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog1(position);
            }
        });
        holder.imgDeny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog2(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return loanDetailList.size();
    }

    private void showDeleteConfirmationDialog2(int position) {
        new AlertDialog.Builder(context)
                .setTitle("Confirm Reject")
                .setMessage("Are you sure you want to reject this user?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> denyLoan(position))
                .setNegativeButton(android.R.string.no, null)
                .show();
    }
    private void showDeleteConfirmationDialog1(int position) {
        new AlertDialog.Builder(context)
                .setTitle("Confirm Approve")
                .setMessage("Are you sure you want to approve this user?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> approveLoan(position))
                .setNegativeButton(android.R.string.no, null)
                .show();
    }
    private void showDeleteConfirmationDialog(int position) {
        new AlertDialog.Builder(context)
                .setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete this user?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> deleteLoan(position))
                .setNegativeButton(android.R.string.no, null)
                .show();
    }
    public void approveLoan(int position) {
        int loanId = loanDetailList.get(position).getId();
        ApiService.apiService.approveLoanDetail(loanId)
                .enqueue(new Callback<BaseResponse<Void>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Void>> call, Response<BaseResponse<Void>> response) {
                        if (response.isSuccessful()) {
                            loanDetailList.get(position).setLoanStatus(Enums.LoanStatus.APPROVED);
                            notifyItemChanged(position);
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Void>> call, Throwable t) {

                    }
                });
    }

    public void denyLoan(int position) {
        int loanId = loanDetailList.get(position).getId();
        ApiService.apiService.denyLoanDetail(loanId)
                .enqueue(new Callback<BaseResponse<Void>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Void>> call, Response<BaseResponse<Void>> response) {
                        if (response.isSuccessful()) {
                            loanDetailList.get(position).setLoanStatus(Enums.LoanStatus.REJECTED);
                            notifyItemChanged(position);
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Void>> call, Throwable t) {

                    }
                });
    }


    public void deleteLoan(int position) {
        int loanId = loanDetailList.get(position).getId();
        Log.d("id", String.valueOf(loanId));
        ApiService.apiService.deleteLoanDetailById(loanId)
                .enqueue(new Callback<BaseResponse<Void>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Void>> call, Response<BaseResponse<Void>> response) {
                        if (response.isSuccessful()) {
                            loanDetailList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, loanDetailList.size());
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

    public class LoanDetailViewHolder extends RecyclerView.ViewHolder {
        TextView txtLoanStatus, txtLoanPaymentStatus, txtReferenceNumber, txtUserName, txtLoanInfo;
        ImageView imgDelete, imgApprove, imgDeny;

        public LoanDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLoanStatus = itemView.findViewById(R.id.tv_loan_status);
            txtLoanPaymentStatus = itemView.findViewById(R.id.tv_loan_paymentstastus);
            txtReferenceNumber = itemView.findViewById(R.id.tv_loan_reference_number);
            txtUserName = itemView.findViewById(R.id.tv_loan_user);
            txtLoanInfo = itemView.findViewById(R.id.tv_loan_info);
            imgDelete = itemView.findViewById(R.id.deleteLoan);
            imgApprove = itemView.findViewById(R.id.approve);
            imgDeny = itemView.findViewById(R.id.deny);
        }

        public void bindData(LoanDetail loanDetail) {
            txtLoanStatus.setText("Loan Status: " + loanDetail.getLoanStatus().toString());
            txtLoanPaymentStatus.setText("Loan Payment Status: " + loanDetail.getLoanPaymentStatus().toString());
            txtReferenceNumber.setText("Reference Number: " + loanDetail.getReferenceNumber());

            txtUserName.setText("User: " + loanDetail.getUser().getFirstname() + " " + loanDetail.getUser().getLastName() + " " + loanDetail.getUser().getOtherName() + "\nDob: " + loanDetail.getUser().getDob() + "\nGender: " + loanDetail.getUser().getGender() + "\nAddress: " + loanDetail.getUser().getAddress()+ "\nEmail: " + loanDetail.getUser().getEmail()+ "\nPhone number: " + loanDetail.getUser().getPhoneNumber());
            LoanInfo loanInfo = loanDetail.getLoanInfo();
            if (loanInfo != null) {
                txtLoanInfo.setText("Loan Info:"+"\nLoan Amount: " + loanInfo.getLoanAmount() + "\nLoan Term: " + loanInfo.getLoanTerm() + "\nInterestRate: " + loanInfo.getInterestRate());

            }
        }
    }

    public void setLoanDetailList(List<LoanDetail> loanDetailList) {
        this.loanDetailList = loanDetailList;
        notifyDataSetChanged();
    }
}
