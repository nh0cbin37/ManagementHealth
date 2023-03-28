package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class WorkoutActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
       // MediaController m = new MediaController(this);
//      //  Uri uri = Uri.parse(R.raw.video1);
//        VideoView v = (VideoView) findViewById(R.id.VideoWO1De);
//        v.setVideoURI(uri);
//        v.start();
    }
}