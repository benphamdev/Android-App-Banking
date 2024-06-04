package com.example.demoapp.Activities;

import static com.example.demoapp.Utils.RedirectActivity.redirectActivity;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demoapp.R;
import com.example.demoapp.Utils.StringResourceHelper;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private ExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        preferences = getSharedPreferences(
                StringResourceHelper.getUserDetailPrefName(),
                MODE_PRIVATE
        );

        // Setup ExoPlayer
        PlayerView playerView = findViewById(R.id.video_screen);
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash_screen);
        MediaItem mediaItem = MediaItem.fromUri(videoUri);
        player.setMediaItem(mediaItem);
        player.setRepeatMode(Player.REPEAT_MODE_ALL);
        player.prepare();
        player.play();

        new Handler().postDelayed(() -> {
            redirectToAppropriateActivity();
            finish();
        }, 5000); // Time for displaying Splash Screen (5 seconds)
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
        }
    }

    private void redirectToAppropriateActivity() {
        if (preferences.getBoolean("authenticated", false)) {
            redirectActivity(SplashScreenActivity.this, SignUp.class);
        } else {
            redirectActivity(SplashScreenActivity.this, SignIn.class);
        }
    }
}
