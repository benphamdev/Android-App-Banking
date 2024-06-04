package com.example.demoapp.Activities.admin.post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.demoapp.HttpRequest.ApiService;
import com.example.demoapp.HttpRequest.RetrofitClient;
import com.example.demoapp.Models.Dto.Requests.PostCreationRequest;
import com.example.demoapp.Models.Dto.Response.BaseResponse;
import com.example.demoapp.Models.Dto.Response.PageResponse;
import com.example.demoapp.Models.Dto.Response.PostResponse;
import com.example.demoapp.R;
import com.example.demoapp.Utils.RealPathUtil;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PostActivity extends AppCompatActivity {

    private RecyclerView rcvPost;
    private PostAdapter postAdapter;
    public static Uri imageUri;
    List<PostResponse> postResponseList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        rcvPost = findViewById(R.id.rcv_admin_post);
        rcvPost.setLayoutManager(new LinearLayoutManager(this));
        getAllPostBySort();
    }
    private void getAllPostBySort() {
        ApiService.apiService.getAllPostBySort(0, 10).enqueue(
                new Callback<BaseResponse<PageResponse<List<PostResponse>>>>() {
                    @Override
                    public void onResponse(
                            Call<BaseResponse<PageResponse<List<PostResponse>>>> call,
                            Response<BaseResponse<PageResponse<List<PostResponse>>>> response
                    ) {
                        if (response.isSuccessful()) {
                            PageResponse<List<PostResponse>> pageResponse = response.body().getData();
                            postResponseList = pageResponse.getItems();
                            postAdapter = new PostAdapter(postResponseList);
                            rcvPost.setAdapter(postAdapter);
                        } else {
                            Log.e("PostActivity", "onResponse: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<BaseResponse<PageResponse<List<PostResponse>>>> call, Throwable t
                    ) {
                        Log.e("PostActivity", "onFailure: " + t.getMessage());
                    }
                }
        );
    }


}