package com.example.demoapp.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.Activities.admin.user.User;
import com.example.demoapp.Adapters.NotificationPersonalPromotion;
import com.example.demoapp.Adapters.PersonalAlertAdapter;
import com.example.demoapp.HttpRequest.ApiService;
import com.example.demoapp.Models.Dto.Response.BaseResponse;
import com.example.demoapp.Models.Dto.Response.PageResponse;
import com.example.demoapp.Models.Dto.Response.PostResponse;
import com.example.demoapp.R;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromotionAlertFragment extends Fragment {

    View rootView;
    PostResponse postResponse;
    private ArrayList<NotificationPersonalPromotion> notificationList = new ArrayList<>();
    private RecyclerView rcvPromotions;
    private PersonalAlertAdapter adapter;
    private ImageView btnFilter;
    private ImageView searchIcon, micIcon;
    private SearchView searchView;
    private ActivityResultLauncher<Intent> speechResultLauncher;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        rootView = inflater.inflate(R.layout.fragment_promotion_alert, container, false);
        rcvPromotions = rootView.findViewById(R.id.rcv_notification_promotion);
        rcvPromotions.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new PersonalAlertAdapter(notificationList);
        rcvPromotions.setAdapter(adapter);
        btnFilter = rootView.findViewById(R.id.img_btn_filter_promotion);
        searchIcon = rootView.findViewById(R.id.search_icon_promotion);
        searchView = rootView.findViewById(R.id.search_view_promotion);
        final Animation slideIn = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in);
        micIcon = rootView.findViewById(R.id.mic_icon_promotion);
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.startAnimation(slideIn);
                searchView.setVisibility(View.VISIBLE);
                micIcon.startAnimation(slideIn);
                micIcon.setVisibility(View.VISIBLE);
                searchView.setIconified(false);
                searchView.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                searchIcon.setVisibility(View.GONE);
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
        getPhoto();
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
                getAllPostBySort();
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
                    getActivity(),
                    android.Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        getActivity(),
                        new String[]{android.Manifest.permission.RECORD_AUDIO},
                        1
                );
            } else {
                speakNow();
            }
        });
        //getCommend();
        return rootView;
    }
    private void search(String string){
        List<NotificationPersonalPromotion> filteredNotifications = new ArrayList<>();
        for(NotificationPersonalPromotion notification : notificationList){
            String name = notification.getName();
            String content = notification.getDescription();
            // Kiểm tra xem tên hoặc nội dung của thông báo có chứa chuỗi tìm kiếm không
            if(name != null && name.toLowerCase().contains(string.toLowerCase()) ||
                    content != null && content.toLowerCase().contains(string.toLowerCase())) {
                filteredNotifications.add(notification);
            }
        }
        if(filteredNotifications.isEmpty()){
            Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setFilter(filteredNotifications);
        }
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


    private void getPhoto() {
        ApiService.apiService.getPosts()
                .enqueue(new Callback<BaseResponse<List<PostResponse>>>() {
                    @Override
                    public void onResponse(
                            Call<BaseResponse<List<PostResponse>>> call,
                            Response<BaseResponse<List<PostResponse>>> response
                    ) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<PostResponse> postResponses = response.body()
                                    .getData();
                            for (PostResponse postResponse : postResponses) {
                                notificationList.add(new NotificationPersonalPromotion(
                                        postResponse.getName(),
                                        postResponse.getThumbnail(),
                                        postResponse.getContent(),
                                        postResponse.getLikeCount()
                                ));
                                adapter.notifyDataSetChanged();
                                Log.d(
                                        "size",
                                        String.valueOf(postResponse.getThumbnail())
                                );
                            }
                            Log.d("size", String.valueOf(notificationList.size()));
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<BaseResponse<List<PostResponse>>> call, Throwable t
                    ) {
                        // Handle API call failure
                        Log.e("E", t.getMessage());
                    }
                });
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
                            List<PostResponse> postResponses = pageResponse.getItems();
                            for (PostResponse postResponse : postResponses) {
                                Log.d("Post", postResponse.getId().toString());
                            }
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