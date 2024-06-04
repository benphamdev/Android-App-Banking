package com.example.demoapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.Adapters.BalanceAlertAdapter;
import com.example.demoapp.HttpRequest.ApiService;
import com.example.demoapp.Models.dto.response.BaseResponse;
import com.example.demoapp.Models.dto.response.UserTransaction;
import com.example.demoapp.R;
import com.example.demoapp.Utils.sharePreferences.SharePreferencesManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalAlertFragment extends Fragment {

    private final ArrayList<String> notificationList = new ArrayList<>();
    private RecyclerView rcvPersonalAlert;
    private BalanceAlertAdapter adapter;
    private int userId;
    private int accountId;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_personal_alert, container, false);

        rcvPersonalAlert = view.findViewById(R.id.rcv_notification2);
        rcvPersonalAlert.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize the adapter with the notificationList
        adapter = new BalanceAlertAdapter(notificationList);
        rcvPersonalAlert.setAdapter(adapter);

        Context context = getActivity();
        SharePreferencesManager manager1 = new SharePreferencesManager(context);
        userId = manager1.getUserId();

        ApiFullLSGD();

        return view;
    }

    private void ApiFullLSGD() {
        SharePreferencesManager manager2 = new SharePreferencesManager(getContext());
        accountId = manager2.getAccountId();
        ApiService.apiService.getTransactionWithAccountID(accountId)
                             .enqueue(new Callback<BaseResponse<List<UserTransaction>>>() {
                                 @Override
                                 public void onResponse(
                                         Call<BaseResponse<List<UserTransaction>>> call,
                                         Response<BaseResponse<List<UserTransaction>>> response
                                 ) {
                                     if (response.isSuccessful() && response.body() != null) {
                                         Log.d("SUCCESS", response.message());
                                         List<UserTransaction> userTransactions = response.body()
                                                                                          .getData();
                                         for (UserTransaction user : userTransactions) {
                                             notificationList.add(convertUserTransactionToString(
                                                     user));
                                             Log.d("Transaction", user.getToAccount());
                                         }
                                         // Notify the adapter once the data is updated
                                         adapter.notifyDataSetChanged();
                                     } else {
                                         try {
                                             Log.e(
                                                     "Error",
                                                     response.errorBody()
                                                             .string()
                                             );
                                         } catch (Exception e) {
                                             Log.e("Exception", e.getMessage());
                                         }
                                     }
                                 }

                                 @Override
                                 public void onFailure(
                                         Call<BaseResponse<List<UserTransaction>>> call,
                                         Throwable throwable
                                 ) {
                                     Log.e("Failure", throwable.getMessage());
                                 }
                             });
    }

    private String convertUserTransactionToString(UserTransaction userTransaction) {

        String stringBuilder = "Transaction Type: " +
                userTransaction.getTransactionType() +
                "\n" +
                "From Account: " +
                userTransaction.getFromAccount() +
                "\n" +
                "To Account: " +
                userTransaction.getToAccount() +
                "\n" +
                "Amount: " +
                userTransaction.getAmount() +
                "\n" +
                "Status: " +
                userTransaction.getStatus() +
                "\n";

        return stringBuilder;
    }
}
