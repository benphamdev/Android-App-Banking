package com.example.demoapp.Activities.admin.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.HttpRequest.ApiService;
import com.example.demoapp.Models.Dto.Requests.UserCreationRequest;
import com.example.demoapp.Models.Dto.Response.BaseResponse;
import com.example.demoapp.Models.Dto.Response.UserResponse;
import com.example.demoapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> userList;
    private Context context;

    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }
    public void setUsers(List<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType
    ) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_admin, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder, int position
    ) {
        User userResponse = userList.get(position);
        holder.bind(userResponse);
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    private void showDeleteConfirmationDialog(int position) {
        new AlertDialog.Builder(context)
                .setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> deleteTextViews(position))
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    public void deleteTextViews(int position) {
        int userId = userList.get(position).getId();
        ApiService.apiService.deleteUser(userId)
                .enqueue(new Callback<BaseResponse<Void>>() {
                    @Override
                    public void onResponse(
                            Call<BaseResponse<Void>> call,
                            Response<BaseResponse<Void>> response
                    ) {
                        if (response.isSuccessful()) {

                            userList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, userList.size());
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Void>> call, Throwable t) {

                    }
                });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFirst, tvLast, tvOther, tvEmail, tvId, tvDob, tvGender, tvAddress, tvPhoneNumber;
        ImageView imgDelete;

        public ViewHolder(
                @NonNull View itemView
        ) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_user_id);
            tvFirst = itemView.findViewById(R.id.tv_user_first_name);
            tvLast = itemView.findViewById(R.id.tv_user_last_name);
            tvOther = itemView.findViewById(R.id.tv_user_other_name);
            tvEmail = itemView.findViewById(R.id.tv_user_email);
            imgDelete = itemView.findViewById(R.id.deleteImageView);
            tvAddress = itemView.findViewById(R.id.tv_user_address);
            tvDob = itemView.findViewById(R.id.tv_user_dob);
            tvGender = itemView.findViewById(R.id.tv_user_gender);
            tvPhoneNumber = itemView.findViewById(R.id.tv_user_phonenumber);
        }

        public void bind(User userResponse) {
            tvId.setText("Id: " + userResponse.getId());
            tvFirst.setText("First Name: " + userResponse.getFirstname());
            tvLast.setText("Last Name: " + userResponse.getLastName());
            tvOther.setText("Other Name: " + userResponse.getOtherName());
            tvDob.setText("Dob: " + userResponse.getDob());
            tvGender.setText("Gender: " + userResponse.getGender());
            tvAddress.setText("Address: " + userResponse.getAddress());
            tvEmail.setText("Email: " + userResponse.getEmail());
            tvPhoneNumber.setText("Phone number: " + userResponse.getPhoneNumber());
        }
    }
}

