package com.example.demoapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.demoapp.Activities.chat.SplashActivity;
import com.example.demoapp.Fragments.HomeFragment;
import com.example.demoapp.Fragments.ProfileFragment;
import com.example.demoapp.Fragments.ScanQRFragment;
import com.example.demoapp.Fragments.SearchFragment;
import com.example.demoapp.R;
import com.example.demoapp.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        binding.bottomnavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.home){
                    replaceFragment(new HomeFragment());
                } else if (id == R.id.search) {
                    startActivity(new Intent(MainActivity.this, SplashActivity.class));
                } else if (id == R.id.quetmaQR) {
                    replaceFragment(new ScanQRFragment());
                } else if (id == R.id.thongbao) {
                    startActivity(new Intent(MainActivity.this, Notification.class));
                } else if (id == R.id.profile) {
                    replaceFragment(new ProfileFragment());
                }else {

                }
                return true;
            }
        });

    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

}