package com.example.project;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyclassWorkout extends ArrayAdapter<Workout> {

    ArrayList<Workout> Workoutlist = new ArrayList<Workout>();
    static int posi = -1;
    SQLiteDatabase db;
    static ArrayList<Workout> arrlstWO = new ArrayList<Workout>();
    public MyclassWorkout(@NonNull Context context, int resource, @NonNull ArrayList<Workout> objects) {
        super(context, resource, objects);

        this.Workoutlist = objects;
    }








    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.my_workout,null);
        VideoView viview = (VideoView)v.findViewById(R.id.VideoVie);
        TextView txtname = (TextView) v.findViewById(R.id.txttenbaitap);
        TextView txttime = (TextView) v.findViewById(R.id.txtthoigiantap);
        ImageButton imgbtn = (ImageButton)v.findViewById(R.id.imageWOdoneormove);
        ImageView imgvdone = (ImageView)v.findViewById(R.id.imgvidone);
        Date date = new Date(); // Create a new Date object
        Calendar calendar = Calendar.getInstance(); // Create a Calendar object
        calendar.setTime(date); // Set the Calendar object's time to the specified Date object
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // Get the day of the week as an integer value
        String[] daysOfWeek = {"8", "2", "3", "4", "5", "6", "7"};
        String dayOfWeekString = daysOfWeek[dayOfWeek - 1];
        String currentTime = new SimpleDateFormat("yyyy-MM-dd").format(date);
        try {
            imgbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int t = position;
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(MyclassWorkout.this.getContext(), workout_detail.class);
                    bundle.putInt("vitri", t);
                    bundle.putParcelableArrayList("arrWO", Workoutlist);
                    intent.putExtra("data", bundle);
//                ((Activity)MyclassWorkout.this.getContext()).startActivityForResult(intent,OPEN);

                    MyclassWorkout.this.getContext().startActivity(intent);
                   // MainActivity.w.getActivity().finish();

//                MyclassWorkout.this.getContext().startActivity(intent);
                }
            });
            //set du lieu
            String Viepath = Workoutlist.get(position).getVideview_practice();
            //String Viepath = "/data/data/com.example.project/file/gapbung.mp4";
            Uri uri = Uri.parse(Viepath);
            viview.setVideoURI(uri);

            viview.start();
            viview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                }
            });
            txtname.setText(Workoutlist.get(position).getName_practice());
            txttime.setText("00:" + Workoutlist.get(position).getTime() + "");
            db = getContext().openOrCreateDatabase(Login.DATABASE_NAME, MODE_PRIVATE, null);
            Cursor c = db.rawQuery("Select * from BAITAPCUATUNGID where ID_User=? and Id_Pratice=? and NgayTao=?", new String[]{
                    MainActivity.ID_USER + "", Workoutlist.get(position).id_practice + "", currentTime + ""});
                c.moveToFirst();
            int t = c.getInt(2);
            if (t == 1)
                imgvdone.setVisibility(View.VISIBLE);
        }catch (Exception ex)
        {
            Toast.makeText(MyclassWorkout.this.getContext(), "lỗi", Toast.LENGTH_SHORT).show();
        }
    return v;
    }



}
