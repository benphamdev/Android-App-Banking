package com.example.demoapp.Activities.admin.post;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.HttpRequest.ApiService;
import com.example.demoapp.Models.dto.response.BaseResponse;
import com.example.demoapp.Models.dto.response.PageResponse;
import com.example.demoapp.Models.dto.response.PostResponse;
import com.example.demoapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends AppCompatActivity {

    public static Uri imageUri;
    List<PostResponse> postResponseList = new ArrayList<>();
    private RecyclerView rcvPost;
    private PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        rcvPost = findViewById(R.id.rcv_admin_post);
        rcvPost.setLayoutManager(new LinearLayoutManager(this));
        getAllPostBySort();
    }

    private void getAllPostBySort() {
        ApiService.apiService.getAllPostBySort(0, 10)
                             .enqueue(
                                     new Callback<BaseResponse<PageResponse<List<PostResponse>>>>() {
                                         @Override
                                         public void onResponse(
                                                 Call<BaseResponse<PageResponse<List<PostResponse>>>> call,
                                                 Response<BaseResponse<PageResponse<List<PostResponse>>>> response
                                         ) {
                                             if (response.isSuccessful()) {
                                                 PageResponse<List<PostResponse>> pageResponse =
                                                         response.body()
                                                                 .getData();
                                                 postResponseList = pageResponse.getItems();
                                                 postAdapter = new PostAdapter(postResponseList);
                                                 rcvPost.setAdapter(postAdapter);
                                             } else {
                                                 Log.e(
                                                         "PostActivity",
                                                         "onResponse: " + response.message()
                                                 );
                                             }
                                         }

                                         @Override
                                         public void onFailure(
                                                 Call<BaseResponse<PageResponse<List<PostResponse>>>> call,
                                                 Throwable t
                                         ) {
                                             Log.e("PostActivity", "onFailure: " + t.getMessage());
                                         }
                                     }
                             );
    }

}