package com.example.demoapp.Activities.admin.accounts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.HttpRequest.ApiService;
import com.example.demoapp.Models.Dto.Response.AccountInfoResponse;
import com.example.demoapp.Models.Dto.Response.BaseResponse;
import com.example.demoapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountInfoAdapter extends RecyclerView.Adapter<AccountInfoAdapter.AccountInfoViewHolder> {
    private List<AccountInfoResponse> accountInfoList;
    private Context context;

    public AccountInfoAdapter(List<AccountInfoResponse> accountInfoList) {
        this.accountInfoList = accountInfoList;
    }

    @NonNull
    @Override
    public AccountInfoViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType
    ) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.admin_account, parent, false);
        context = parent.getContext();
        return new AccountInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull AccountInfoViewHolder holder, int position
    ) {
        AccountInfoResponse accountInfo = accountInfoList.get(position);
        holder.bind(accountInfo);
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return accountInfoList != null ? accountInfoList.size() : 0;
    }

    private void showDeleteConfirmationDialog(int position) {
        new AlertDialog.Builder(context)
                .setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> deleteAccount(position))
                .setNegativeButton(android.R.string.no, null)
                .show();
    }
    public void deleteAccount(int position) {
        // Lấy ID của tài khoản cần xóa
        int accountId = Integer.parseInt(accountInfoList.get(position).getId());

        // Gọi API để xóa tài khoản
        ApiService.apiService.deleteAccount(accountId)
                             .enqueue(new Callback<BaseResponse<Void>>() {
                                 @Override
                                 public void onResponse(
                                         Call<BaseResponse<Void>> call,
                                          Response<BaseResponse<Void>> response
                                 ) {
                                     if (response.isSuccessful()) {
                                         // Nếu xóa thành công từ hệ thống, thực hiện xóa tài khoản khỏi danh sách và cập nhật giao diện
                                         accountInfoList.remove(position);
                                         notifyItemRemoved(position);
                                         notifyItemRangeChanged(position, accountInfoList.size());
                                     } else {

                                     }
                                 }

                                 @Override
                                 public void onFailure(Call<BaseResponse<Void>> call, Throwable t) {
                                     // Xử lý khi gặp lỗi kết nối
                                     // Ví dụ: Hiển thị thông báo lỗi cho người dùng
                                 }
                             });
    }

    public class AccountInfoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvId;
        private TextView tvAccountName;
        private TextView tvAccountBalance;
        private TextView tvAccountNumber;
        private TextView tvBranchName;
        private ImageView imgDelete;

        public AccountInfoViewHolder(
                @NonNull View itemView
        ) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_account_id);
            tvAccountName = itemView.findViewById(R.id.tv_admin_account_name);
            tvAccountBalance = itemView.findViewById(R.id.tv_admin_account_balance);
            tvAccountNumber = itemView.findViewById(R.id.tv_admin_account_number);
            tvBranchName = itemView.findViewById(R.id.tv_admin_branch_name);
            imgDelete = itemView.findViewById(R.id.deleteaccount);
        }

        public void bind(AccountInfoResponse accountInfo) {
            tvId.setText(accountInfo.getId());
            tvAccountName.setText(accountInfo.getAccountName());
            tvAccountBalance.setText(accountInfo.getAccountBalance()
                                                .toString());
            tvAccountNumber.setText(accountInfo.getAccountNumber());
            tvBranchName.setText(accountInfo.getBranchName());
        }
    }

}

