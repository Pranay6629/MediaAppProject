package com.monu.mediaapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PictureInPictureParams;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Rational;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    private VideoView videoView;
    private ImageButton picbtn;
    private MediaController mediaController;
    private ActionBar actionBar;
    private PictureInPictureParams.Builder pictureinPictureBuilder;
    private static final String TAG = "PIP_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        videoView = findViewById(R.id.videoview);
        picbtn = findViewById(R.id.picbtn);
        actionBar = getSupportActionBar();
        setVideoView(getIntent());// get and pass intentto amethod that will handel video playback, into contains urlof video

//        String data = getIntent().getStringExtra("videoURL");
//        System.out.println("Video URL is :"+data);
//        try {
//
//            mediaController = new MediaController(VideoActivity.this);
//            mediaController.setAnchorView(videoView);
//            videoView.setMediaController(mediaController);
//            Uri uri = Uri.parse(data);
//            videoView.setVideoURI(uri);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                videoView.start();
//            }
//        });
        // int PictureInPictureParams, reqired android O and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            pictureinPictureBuilder = new PictureInPictureParams.Builder();
        }

        picbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictureInPictureMode();
            }
        });
    }

    private void setVideoView(Intent intent) {
        String data = intent.getStringExtra("videoURL");
        System.out.println("Video URL is :"+data);

        try {
            // media controllet to play, pause, seekbar, time
            mediaController = new MediaController(VideoActivity.this);
            mediaController.setAnchorView(videoView);
            //see media controller ot videoview
            videoView.setMediaController(mediaController);
            Uri uri = Uri.parse(data);
            //set video uri to videoview
            videoView.setVideoURI(uri);
        }catch (Exception e){
            e.printStackTrace();
        }
        //add video prepare listener
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //when video is ready play it
                videoView.start();
            }
        });
    }

    private void pictureInPictureMode(){
        // reqired android O and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(TAG, "Pictureinpicturemood : support PIP");
            //setup height and weight of pip video
            Rational aspectRational = new Rational(videoView.getWidth(), videoView.getHeight());
            pictureinPictureBuilder.setAspectRatio(aspectRational).build();
            enterPictureInPictureMode(pictureinPictureBuilder.build());
        }else {
            Log.d(TAG, "Pictureinpicturemood : not support PIP");
        }
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        // called when user press home button,entre in pip mode, requires android N
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (!isInPictureInPictureMode()){
                Log.d(TAG, "onUserLeaveHint : was not in PIP");
                pictureInPictureMode();
            }else {
                Log.d(TAG, "onUserLeaveHint : Already in PIP");
            }
        }
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);

        if (isInPictureInPictureMode){
            Log.d(TAG, "onPictureInPictureModeChanged : Enter PIP");
            // hide pip button and action bar
            picbtn.setVisibility(View.GONE);
            actionBar.hide();
        }else {
            Log.d(TAG, "onPictureInPictureModeChanged : Exit PIP");
            // show pip button and action bar
            picbtn.setVisibility(View.VISIBLE);
            actionBar.show();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent : play new video");
        setVideoView(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (videoView.isPlaying()){
            videoView.stopPlayback();
        }
    }
}