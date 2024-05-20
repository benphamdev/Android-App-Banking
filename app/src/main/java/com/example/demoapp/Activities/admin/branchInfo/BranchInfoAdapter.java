package com.example.demoapp.Activities.admin.branchInfo;

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

public class BranchInfoAdapter extends RecyclerView.Adapter<BranchInfoAdapter.BranchInfoViewHolder> {
    private List<BranchInfo> branchInfoList;
    private Context context;

    public BranchInfoAdapter(List<BranchInfo> branchInfoList) {
        this.branchInfoList = branchInfoList;
    }

    @NonNull
    @Override
    public BranchInfoViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType
    ) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.admin_branch_info, parent, false);
        context = parent.getContext();
        return new BranchInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull BranchInfoViewHolder holder, int position
    ) {
        BranchInfo branchInfo = branchInfoList.get(position);
        if (branchInfo == null) {
            return;
        }
        holder.bind(branchInfo);
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return branchInfoList != null ? branchInfoList.size() : 0;
    }
    private void showDeleteConfirmationDialog(int position) {
        new AlertDialog.Builder(context)
                .setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> deleteBranch(position))
                .setNegativeButton(android.R.string.no, null)
                .show();
    }
    public void deleteBranch(int position) {
        int userId = Integer.parseInt(branchInfoList.get(position)
                                                    .getId());

        // Gọi API để xóa tài khoản
        ApiService.apiService.deleteBranch(userId)
                             .enqueue(new Callback<BaseResponse<Void>>() {
                                 @Override
                                 public void onResponse(
                                         Call<BaseResponse<Void>> call,
                                         Response<BaseResponse<Void>> response
                                 ) {
                                     if (response.isSuccessful()) {
                                         // Nếu xóa thành công từ hệ thống, thực hiện xóa tài khoản khỏi danh sách và cập nhật giao diện
                                         branchInfoList.remove(position);
                                         notifyItemRemoved(position);
                                         notifyItemRangeChanged(position, branchInfoList.size());

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


    public class BranchInfoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvId;
        private TextView tvBranchName;
        private TextView tvAddress;
        private TextView tvProvince;
        private TextView tvAccountName, tvAccountBalance, tvAccountNumber;
        private ImageView imgDelete;

        public BranchInfoViewHolder(
                @NonNull View itemView
        ) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvBranchName = itemView.findViewById(R.id.tv_branch_name);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvProvince = itemView.findViewById(R.id.tv_province_name);
            tvAccountName = itemView.findViewById(R.id.tv_account_name);
            tvAccountBalance = itemView.findViewById(R.id.tv_account_balance);
            tvAccountNumber = itemView.findViewById(R.id.tv_account_number);
            imgDelete = itemView.findViewById(R.id.deleteBranch);
        }

        public void bind(BranchInfo branchInfo) {

            tvId.setText(branchInfo.getId());
            tvBranchName.setText(branchInfo.getBranchName());
            tvAddress.setText(branchInfo.getAddress());
            if (tvProvince != null && branchInfo.getProvinceName() != null) {
                tvProvince.setText(branchInfo.getProvinceName()
                                             .getName());
            }
            List<AccountInfoResponse> accountInfoList = branchInfo.getAccountInfoResponse();
            if (accountInfoList != null && !accountInfoList.isEmpty()) {
                AccountInfoResponse accountInfo =
                        accountInfoList.get(0); // Assuming only one account is associated with the branch
                if (accountInfo != null) {
                    tvAccountName.setText(accountInfo.getAccountName());
                    tvAccountBalance.setText(String.valueOf(accountInfo.getAccountBalance()));
                    tvAccountNumber.setText(accountInfo.getAccountNumber());
                }
            }

        }
    }

}

