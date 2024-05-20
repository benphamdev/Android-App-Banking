package com.example.demoapp.Activities.admin.user;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.demoapp.Activities.admin.AdminsActivity;
import com.example.demoapp.Activities.admin.OnDeleteClickListener;
import com.example.demoapp.HttpRequest.ApiService;
import com.example.demoapp.Models.Dto.Response.BaseResponse;
import com.example.demoapp.Models.Dto.Response.PageResponse;
import com.example.demoapp.Models.Dto.Response.UserResponse;
import com.example.demoapp.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity implements OnDeleteClickListener {
    private RecyclerView rcvUser;
    private UserAdapter userAdapter;
    private List<User> userList = new ArrayList<>();
    private ImageView btnFilter;
    private ImageView searchIcon, micIcon;
    private SearchView searchView;
    private ActivityResultLauncher<Intent> speechResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        rcvUser = findViewById(R.id.rcv_admin_user);
        btnFilter = findViewById(R.id.img_btn_filter);
        searchIcon = findViewById(R.id.search_icon);
        searchView = findViewById(R.id.search_view);
        final Animation slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in);
        micIcon = findViewById(R.id.mic_icon);
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.startAnimation(slideIn);
                searchView.setVisibility(View.VISIBLE);
                micIcon.startAnimation(slideIn);
                micIcon.setVisibility(View.VISIBLE);
                searchView.setIconified(false);
                searchView.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                searchIcon.setVisibility(View.GONE);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchView.setVisibility(View.GONE);
                micIcon.setVisibility(View.GONE);
                searchIcon.setVisibility(View.VISIBLE);
                return false;
            }
        });
        rcvUser.setLayoutManager(new LinearLayoutManager(this));
        toolbars();
        userList();
        searchView.clearFocus();
       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               search(newText);
               return true;
           }
       });
       btnFilter.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               showPopupMenu(v);
           }
       });
        speechResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Handle the returned data here
                        String speechText = result.getData().getStringArrayListExtra(
                                RecognizerIntent.EXTRA_RESULTS
                        ).get(0);
                        searchView.setQuery(speechText, true);
                    }
                }
        );

        micIcon.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{android.Manifest.permission.RECORD_AUDIO},
                        1
                );
            } else {
                speakNow();
            }
        });
    }
    private void speakNow() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        );
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now");
        speechResultLauncher.launch(intent);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                speakNow();
            } else {
                // Permission denied, handle appropriately
            }
        }
    }
    public void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.menu_item1){
                    getUserWithSort("lastName:desc");
                } else if (id == R.id.menu_item2) {
                    getAllUsersWithSortByMultiColumn(new String[]{"lastName:desc","otherName:desc"});
                }else {
                    getAllUsersWithPagingAndSorting(0, 10, "f", "id:desc");
                }
                return true;
            }
        });
        popupMenu.inflate(R.menu.menu_filter);
        popupMenu.show();
    }
    private void search(String string){
        List<User> users = new ArrayList<>();
        for(User user : userList){
            String lastName = user.getLastName();
            String otherName = user.getOtherName();
            if(lastName != null && otherName != null) {
                if(lastName.toLowerCase().contains(string.toLowerCase()) || otherName.toLowerCase().contains(string.toLowerCase())) {
                    users.add(user);
                }
            }
        }
        if(users.isEmpty()){
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else {
            userAdapter.setUsers(users);
        }
    }

    private void getUserWithSort(String sortBy){
        ApiService.apiService.getAllUsersWithSortBy1(0, 10, sortBy).enqueue(new Callback<BaseResponse<PageResponse<List<User>>>>() {
            @Override
            public void onResponse(Call<BaseResponse<PageResponse<List<User>>>> call, Response<BaseResponse<PageResponse<List<User>>>> response) {
                if(response.isSuccessful() && response.body() != null){
                    userList = response.body().getData().getContent();
                    updateRecyclerView(userList);
                }else {
                    try {
                        Log.e("TAG", response.errorBody().toString());
                    }catch (Exception e){
                        e.getMessage();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<PageResponse<List<User>>>> call, Throwable t) {
                    Log.e("EEE", t.getMessage());
            }
        });
    }
    private void getAllUsersWithSortByMultiColumn(String[] sorts) {
        ApiService.apiService.getAllUsersWithSortByMultiColumns(0, 10, sorts).enqueue(new Callback<BaseResponse<PageResponse<List<User>>>>() {
            @Override
            public void onResponse(Call<BaseResponse<PageResponse<List<User>>>> call, Response<BaseResponse<PageResponse<List<User>>>> response) {
                if (response.isSuccessful()) {
                    BaseResponse<PageResponse<List<User>>> baseResponse = response.body();
                    if (baseResponse != null && baseResponse.getData() != null) {
                        PageResponse<List<User>> pageResponse = baseResponse.getData();
                        List<User> users = pageResponse.getContent();
                        updateRecyclerView(users);
                    } else {
                        Log.e("TAG1", "Response body or data is null");
                    }
                } else {
                    try {
                        Log.e("TAG1", "Error: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<PageResponse<List<User>>>> call, Throwable t) {
                Log.e("E", t.getMessage());
            }
        });
    }

    private void getAllUsersWithPagingAndSorting(int pageNo, int pageSize, String search, String sortBy) {
        ApiService.apiService.getAllUsersWithPagingAndSorting(pageNo, pageSize, search, sortBy)
                .enqueue(new Callback<BaseResponse<PageResponse<List<User>>>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<PageResponse<List<User>>>> call, Response<BaseResponse<PageResponse<List<User>>>> response) {
                        if (response.isSuccessful()) {
                            BaseResponse<PageResponse<List<User>>> baseResponse = response.body();
                            if (baseResponse != null && baseResponse.getData() != null) {
                                PageResponse<List<User>> pageResponse = baseResponse.getData();
                                List<User> users = pageResponse.getContent();
                                updateRecyclerView(users);
                            } else {
                                Log.e("TAG", "Response body or data is null");
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
                    public void onFailure(Call<BaseResponse<PageResponse<List<User>>>> call, Throwable t) {
                        Log.e("TAG", "onFailure: " + t.getMessage(), t);
                    }
                });
    }
    private void updateRecyclerView(List<User> userList) {
        if (userAdapter == null) {
            userAdapter = new UserAdapter(userList);
            rcvUser.setAdapter(userAdapter);
        } else {
            userAdapter.setUsers(userList);
            userAdapter.notifyDataSetChanged();
        }
    }
    private void toolbars(){
        Toolbar toolbar = findViewById(R.id.tool_bar_admin_user);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        ImageView backButton = findViewById(R.id.toolbar_back_admin_user);
        Drawable drawable = backButton.getDrawable();
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(
                    drawable,
                    ContextCompat.getColor(this, R.color.white)
            );
        }
        backButton.setImageDrawable(drawable);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this, AdminsActivity.class));
            }
        });
    }
    private void userList() {
        ApiService.apiService.getAllUsersWithSortBy(0, 10)
                .enqueue(new Callback<BaseResponse<PageResponse<List<User>>>>() {
                    @Override
                    public void onResponse(
                            Call<BaseResponse<PageResponse<List<User>>>> call,
                            Response<BaseResponse<PageResponse<List<User>>>> response
                    ) {
                        if (response.isSuccessful()) {
                            PageResponse<List<User>> pageResponse =
                                    response.body()
                                            .getData();
                            if (pageResponse != null) {
                                userList =
                                        pageResponse.getContent();
                                if (userList != null) {
                                    for (User userResponse : userList) {
                                        Log.d(
                                                "User Info",
                                                "First Name: " + userResponse.getFirstname() +
                                                        ", Last Name: " + userResponse.getLastName() +
                                                        ", Other Name: " + userResponse.getOtherName() +
                                                        ", Email: " + userResponse.getEmail()
                                        );
                                    }
                                    userAdapter = new UserAdapter(userList);
                                    rcvUser.setAdapter(userAdapter);
                                    userAdapter.notifyDataSetChanged();
                                }
                            }
                        } else {
                            try {
                                Log.d(
                                        "TAG",
                                        response.errorBody()
                                                .toString()
                                );
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<BaseResponse<PageResponse<List<User>>>> call,
                            Throwable t
                    ) {
                        Log.e("E", t.getMessage());
                    }
                });
    }

//    private void getAllUsersWithSortBy(String sortBy, String sortOrder) {
//        ApiService.apiService.getAllUsersWithSortBy1(0, 10, sortBy + "," + sortOrder)
//                .enqueue(new Callback<BaseResponse<List<User>>>() {
//                    @Override
//                    public void onResponse(Call<BaseResponse<List<User>>> call, Response<BaseResponse<List<User>>> response) {
//                        if (response.isSuccessful()) {
//                             userList = response.body().getData();
//                            updateRecyclerView(userList);
//                        } else {
//                            try {
//                                Log.d("TAG", response.errorBody().toString());
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<BaseResponse<List<User>>> call, Throwable t) {
//                        Log.e("E", t.getMessage());
//                    }
//                });
//    }
//
//    private void updateRecyclerView(List<User> userList) {
//        if (userAdapter == null) {
//            userAdapter = new UserAdapter(userList);
//            rcvUser.setAdapter(userAdapter);
//        } else {
//            userAdapter.setUsers(userList);
//            userAdapter.notifyDataSetChanged();
//        }
//    }



    @Override
    public void onDeleteClick(int position) {
        deleteUser(position);
    }

    private void deleteUser(int position) {
        // Gọi phương thức xóa của adapter
        if (userAdapter != null) {
            userAdapter.deleteTextViews(position);
        }
    }
}