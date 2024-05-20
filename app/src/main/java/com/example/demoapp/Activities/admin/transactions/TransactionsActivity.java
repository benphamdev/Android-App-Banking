package com.example.demoapp.Activities.admin.transactions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.demoapp.HttpRequest.ApiService;
import com.example.demoapp.Models.Dto.Response.BaseResponse;
import com.example.demoapp.Models.Dto.Response.PageResponse;
import com.example.demoapp.Models.Dto.Response.UserTransaction;
import com.example.demoapp.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionsActivity extends AppCompatActivity {
    private RecyclerView rcvTransaction;
    private TransactionAdapter transactionAdapter;
    private ArrayList<UserTransaction> transactionList = new ArrayList<>();
    private AppCompatButton appCompatButton;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        rcvTransaction = findViewById(R.id.rcv_admin_transaction);
        appCompatButton = findViewById(R.id.btn_filter_transaction);
        searchView = findViewById(R.id.search_bar_transaction);
        rcvTransaction.setLayoutManager(new LinearLayoutManager(this));
        transactionAdapter = new TransactionAdapter(transactionList);
        rcvTransaction.setAdapter(transactionAdapter);
        loadNotification();
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTransactionsWithSort(0, 10, "amount:asc");
            }
        });
//        searchView.clearFocus();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                search(newText);
//                return true;
//            }
//        });



    }

    public void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menu_item1) {
                    getTransactionsWithSort(0, 10, "amount:asc");
                } else if (id == R.id.menu_item2) {

                } else {

                }
                return true;
            }
        });
        popupMenu.inflate(R.menu.menu_transaction);
        popupMenu.show();
    }

    //    private void search(String string){
//        List<User> users = new ArrayList<>();
//        for(User user : ){
//            String lastName = user.getLastName();
//            String otherName = user.getOtherName();
//            if(lastName != null && otherName != null) {
//                if(lastName.toLowerCase().contains(string.toLowerCase()) || otherName.toLowerCase().contains(string.toLowerCase())) {
//                    users.add(user);
//                }
//            }
//        }
//        if(users.isEmpty()){
//            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
//        } else {
//            transactionAdapter.setUsers(users);
//        }
//    }
    private void getTransactionsWithSort(int pageNo, int pageSize, String sortBy) {
        ApiService.apiService.getTransactionsWithSort(pageNo, pageSize, sortBy)
                .enqueue(new Callback<BaseResponse<PageResponse<List<UserTransaction>>>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<PageResponse<List<UserTransaction>>>> call,
                                           Response<BaseResponse<PageResponse<List<UserTransaction>>>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            BaseResponse<PageResponse<List<UserTransaction>>> baseResponse = response.body();
                            PageResponse<List<UserTransaction>> pageResponse = baseResponse.getData();
                            if (pageResponse != null) {
                                List<UserTransaction> transactionList = pageResponse.getContent();
                                updateRecyclerView(transactionList);
                            }
                        } else {
                            try {
                                Log.e("TAG", "Error: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<PageResponse<List<UserTransaction>>>> call, Throwable t) {
                        Log.e("TAG", "onFailure: " + t.getMessage(), t);
                    }
                });
    }


    private void updateRecyclerView(List<UserTransaction> transactionList) {
        if (transactionAdapter == null) {
            transactionAdapter = new TransactionAdapter(transactionList);
            rcvTransaction.setAdapter(transactionAdapter);
        } else {
            transactionAdapter.setTransactions(transactionList);
            transactionAdapter.notifyDataSetChanged();
        }
    }


    private void loadNotification() {
        ApiService.apiService.notification().enqueue(new Callback<BaseResponse<List<UserTransaction>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<UserTransaction>>> call, Response<BaseResponse<List<UserTransaction>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("SUCCESS", response.message());
                    List<UserTransaction> userTransactions = response.body().getData();
                    for (UserTransaction user : userTransactions) {
                        transactionList.add(user);
                        transactionAdapter.notifyItemInserted(transactionList.size() - 1);
                    }
                } else {
                    try {
                        Log.e("ERROR", response.errorBody().string());
                    } catch (IOException e) {
                        Log.e("ERROR", "Error parsing error response", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<UserTransaction>>> call, Throwable t) {
                Log.e("FAILURE", "Request failed", t);
            }
        });
    }

    private String convertUserTransactionToString(UserTransaction userTransaction) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Transaction Type: ").append(userTransaction.getTransactionType()).append("\n");
        stringBuilder.append("From Account: ").append(userTransaction.getFromAccount()).append("\n");
        stringBuilder.append("To Account: ").append(userTransaction.getToAccount()).append("\n");
        stringBuilder.append("Amount: ").append(userTransaction.getAmount()).append("\n");
        stringBuilder.append("Status: ").append(userTransaction.getStatus()).append("\n");

        return stringBuilder.toString();
    }

}