package com.example.project;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class workout_detail extends AppCompatActivity {

    VideoView videWO;
    TextView txtdetail,txttimecountdown;
    Button btnstarOrDone,btnclose;
    int i = 0;
    int vitri;
    ArrayList<Workout> arrWO = new ArrayList<Workout>();
    SQLiteDatabase db;
    private void getdata()
    {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
         vitri = (int)bundle.getInt("vitri");
         arrWO =   bundle.getParcelableArrayList("arrWO");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detail);
        videWO = (VideoView) findViewById(R.id.videovWO);
        txtdetail=(TextView) findViewById(R.id.detail);
        txttimecountdown = (TextView) findViewById(R.id.txttimedown);
        btnstarOrDone=(Button) findViewById(R.id.btnidbatdauvadone);
        btnclose=(Button) findViewById(R.id.btnthoat);
        getdata();
        String Viepath = arrWO.get(vitri).getVideview_practice();
        //String Viepath = "/data/data/com.example.project/file/gapbung.mp4";
        Uri uri = Uri.parse(Viepath);
        videWO.setVideoURI(uri);
        videWO.start();
        videWO.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        Date date = new Date(); // Create a new Date object
        Calendar calendar = Calendar.getInstance(); // Create a Calendar object
        calendar.setTime(date); // Set the Calendar object's time to the specified Date object
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // Get the day of the week as an integer value
        String[] daysOfWeek = {"8", "2", "3", "4", "5", "6", "7"};
        String dayOfWeekString = daysOfWeek[dayOfWeek - 1];
        String currentTime = new SimpleDateFormat("yyyy-MM-dd").format(date);

        txtdetail.setText(arrWO.get(vitri).getDetail());
        txttimecountdown.setText("00:"+arrWO.get(vitri).getTime());
        btnstarOrDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (i == 0) {
                        long a = arrWO.get(vitri).getTime() * 1000;
                        new CountDownTimer(a, 1000) {

                            public void onTick(long millisUntilFinished) {
                                txttimecountdown.setText("00:" + millisUntilFinished / 1000);
                                // logic to set the EditText could go here
                            }

                            public void onFinish() {
                                txttimecountdown.setText("Hết thời gian!");
                                btnstarOrDone.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        db = openOrCreateDatabase(Login.DATABASE_NAME, MODE_PRIVATE, null);
                                        ContentValues values = new ContentValues();
                                        values.put("Status", 1);
                                        db.update("BAITAPCUATUNGID", values, "ID_User=? and Id_Pratice=? and NgayTao=?", new String[]{MainActivity.ID_USER + "", arrWO.get(vitri).getId_practice() + "", currentTime + ""});

                                        //lam tiep
                                        Intent intent = new Intent(workout_detail.this, MainActivity.class);
                                        MainActivity.countshow = 1;
                                        startActivity(intent);
                                        Toast.makeText(workout_detail.this, "Đã hoàn thành bài tập", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                            }

                        }.start();

                        btnstarOrDone.setText("Xong");
                    } else {


//                            db = openOrCreateDatabase(Login.DATABASE_NAME, MODE_PRIVATE, null);
//                            ContentValues values = new ContentValues();
//                            values.put("Status", 1);
//                            db.update("BAITAPCUATUNGID", values, "ID_User=? and Id_Pratice=? and NgayTao=?", new String[]{MainActivity.ID_USER + "", arrWO.get(vitri).getId_practice() + "", currentTime + ""});
//
//                            //lam tiep
//                            Intent intent = new Intent(workout_detail.this, MainActivity.class);
//                            MainActivity.countshow = 1;
//                            startActivity(intent);
//                            Toast.makeText(workout_detail.this, "Đã hoàn thành bài tập", Toast.LENGTH_SHORT).show();
//                            finish();
                        }
                    i++;

                }catch (Exception ex)
                {
                    Toast.makeText(workout_detail.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(workout_detail.this,WorkoutActivityFragment.class);
                startActivity(intent);
            }
        });
    }
}