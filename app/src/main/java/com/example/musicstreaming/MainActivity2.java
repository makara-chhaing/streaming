package com.example.musicstreaming;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.webkit.URLUtil;
import android.widget.MediaController;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity2 extends AppCompatActivity {

    int currentPos;
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        videoView = findViewById(R.id.videoView);

        MediaController controller = new MediaController(this);
        videoView.setMediaController(controller);
        controller.setMediaPlayer(videoView);

        if(savedInstanceState!=null){
            currentPos = savedInstanceState.getInt("position", 0);
        }

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.musicstreaming", MODE_PRIVATE);
        if(sharedPreferences.contains("position")){
            currentPos = sharedPreferences.getInt("position", 0);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Uri uri;
        String path = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4";

        if(URLUtil.isValidUrl(path)){
            uri = Uri.parse(path);
            videoView.setVideoURI(uri);

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    videoView.seekTo(currentPos);
                    videoView.start();
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        currentPos = videoView.getCurrentPosition();

    }

    @Override
    protected void onStop() {
        super.onStop();
        videoView.stopPlayback();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", currentPos);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.musicstreaming", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("position", currentPos);
        editor.apply();
    }
}