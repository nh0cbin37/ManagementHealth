package com.example.project;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link WorkoutActivityFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class WorkoutActivityFragment extends Fragment {

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public WorkoutActivityFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment WorkoutActivityFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static WorkoutActivityFragment newInstance(String param1, String param2) {
//        WorkoutActivityFragment fragment = new WorkoutActivityFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
static final int OPEN =113;
    static final int DONE = 114;
ListView lstworkoutDe,lstworkoutTB,lstworkoutKho;
    Button btnOpenStudent;
    ArrayList<Workout> WorkoutlistDe = new ArrayList<Workout>();
    ArrayList<Workout> WorkoutlistTB = new ArrayList<Workout>();
    ArrayList<Workout> WorkoutlistKho = new ArrayList<Workout>();
//    ArrayList<Workout_ID_Users> WorkoutlistofUser = new ArrayList<Workout_ID_Users>();
    MyclassWorkout adapterWorkout;
    SQLiteDatabase db;
    int poseleced = -1;
    Cursor getBTcuaUser;
    public  void getDl()
    {
        Date date = new Date(); // Create a new Date object
        Calendar calendar = Calendar.getInstance(); // Create a Calendar object
        calendar.setTime(date); // Set the Calendar object's time to the specified Date object
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // Get the day of the week as an integer value
        String[] daysOfWeek = {"8", "2", "3", "4", "5", "6", "7"};
        String dayOfWeekString = daysOfWeek[dayOfWeek - 1]; // Subtract 1 from dayOfWeek to get the array index
        String currentTime = new SimpleDateFormat("yyyy-MM-dd").format(date);
        getWorkoutListDe(currentTime);
        getWorkoutListTB(currentTime);
        getWorkoutListKho(currentTime);
    }
public void getWorkoutListDe(String currentime) {
    try {
        //Studentlist.add(new Room(""));

//        db = getActivity().openOrCreateDatabase(Login.DATABASE_NAME, MODE_PRIVATE, null);
//        getBTcuaUser = db.rawQuery("Select * from BAITAPCUATUNGID where ID_User = ?",new String[]{MainActivity.ID_USER+""},null);
//        getBTcuaUser.moveToFirst();
//        while(!getBTcuaUser.isAfterLast())
//        {
//            WorkoutlistofUser.add( new Workout_ID_Users(getBTcuaUser.getInt(0) , getBTcuaUser.getInt(1) , getBTcuaUser.getInt(2)));
//            getBTcuaUser.moveToNext();
//        }
        db = getActivity().openOrCreateDatabase(Login.DATABASE_NAME,MODE_PRIVATE,null);
        Cursor c = db.rawQuery("select BAITAP.* from BAITAP,BAITAPCUATUNGID where BAITAP.Id_Practice = BAITAPCUATUNGID.Id_Pratice and Id_User = ? and Rang = ? and NgayTao = ? ",new String[]{MainActivity.ID_USER+"","De",currentime}, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            WorkoutlistDe.add(new Workout(c.getInt(0) , c.getString(1) + "", c.getInt(2), c.getString(3) + "", c.getString(4) + "",c.getString(5),c.getString(6)+""));
            c.moveToNext();
        }
        adapterWorkout = new MyclassWorkout(getContext(), android.R.layout.simple_list_item_1, WorkoutlistDe);
        lstworkoutDe.setAdapter(adapterWorkout);
    } catch (Exception ex) {
        Toast.makeText(getActivity(), "Lỗi1", Toast.LENGTH_SHORT).show();

    }
}
    public void getWorkoutListTB(String currentime) {
        try {
            //Studentlist.add(new Room(""));

            db = getActivity().openOrCreateDatabase(Login.DATABASE_NAME, MODE_PRIVATE, null);
            Cursor c = db.rawQuery("select BAITAP.* from BAITAP,BAITAPCUATUNGID where BAITAP.Id_Practice = BAITAPCUATUNGID.Id_Pratice and Id_User = ? and Rang=? and NgayTao = ?",new String[]{MainActivity.ID_USER+"","TB",currentime}, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                WorkoutlistTB.add(new Workout(c.getInt(0) , c.getString(1) + "", c.getInt(2), c.getString(3) + "", c.getString(4) + "",c.getString(5),c.getString(6)+""));
                c.moveToNext();
            }
            adapterWorkout = new MyclassWorkout(getContext(), android.R.layout.simple_list_item_1, WorkoutlistTB);
            lstworkoutTB.setAdapter(adapterWorkout);
        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Lỗi1", Toast.LENGTH_SHORT).show();

        }
    }
    public void getWorkoutListKho(String currentime) {
        try {
            //Studentlist.add(new Room(""));

            db = getActivity().openOrCreateDatabase(Login.DATABASE_NAME, MODE_PRIVATE, null);
            Cursor c = db.rawQuery("select BAITAP.* from BAITAP,BAITAPCUATUNGID where BAITAP.Id_Practice = BAITAPCUATUNGID.Id_Pratice and Id_User = ? and Rang=? and NgayTao = ?",new String[]{MainActivity.ID_USER+"","Kho",currentime}, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                WorkoutlistKho.add(new Workout(c.getInt(0), c.getString(1) + "", c.getInt(2), c.getString(3) + "", c.getString(4) + "",c.getString(5),c.getString(6)+""));
                c.moveToNext();
            }
            adapterWorkout = new MyclassWorkout(getContext(), android.R.layout.simple_list_item_1, WorkoutlistKho);
            lstworkoutKho.setAdapter(adapterWorkout);
        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Lỗi1", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
//        int id_food= -1;
//        int j = 0;
//        int block = 0;
        super.onActivityResult(requestCode, resultCode, data);
        Date date = new Date(); // Create a new Date object
        Calendar calendar = Calendar.getInstance(); // Create a Calendar object
        calendar.setTime(date); // Set the Calendar object's time to the specified Date object
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // Get the day of the week as an integer value
        String[] daysOfWeek = {"8", "2", "3", "4", "5", "6", "7"};
        String currentTime = new SimpleDateFormat("yyyy-MM-dd").format(date);
        try {
            switch (resultCode) {
                case DONE:
//                    Bundle bundle = data.getBundleExtra("data");
                    getWorkoutListDe(currentTime);
                    getWorkoutListTB(currentTime);
                    getWorkoutListKho(currentTime);
                    break;
            }

        } catch (Exception ex) {
            Toast.makeText(getContext(), "Lỗi insert", Toast.LENGTH_SHORT).show();
        }
    }

    static int Posit = -1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        super.onCreate(savedInstanceState);

        View rootview = inflater.inflate(R.layout.fragment_workout_activity, container, false);
        Date date = new Date(); // Create a new Date object
        Calendar calendar = Calendar.getInstance(); // Create a Calendar object
        calendar.setTime(date); // Set the Calendar object's time to the specified Date object
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // Get the day of the week as an integer value
        String[] daysOfWeek = {"8", "2", "3", "4", "5", "6", "7"};
        String currentTime = new SimpleDateFormat("yyyy-MM-dd").format(date);

        try {


            TextView tt = (TextView)rootview.findViewById(R.id.txtDe) ;
            lstworkoutDe = (ListView) rootview.findViewById(R.id.lsvide);
            lstworkoutTB = (ListView) rootview.findViewById(R.id.lsviTB);
            lstworkoutKho = (ListView) rootview.findViewById(R.id.lsviKho);

            getWorkoutListDe(currentTime);
            getWorkoutListTB(currentTime);
            getWorkoutListKho(currentTime);

//            VideoView videov = (VideoView) rootview.findViewById(R.id.Vi)
        }catch (Exception ex)
        {
            Toast.makeText(getActivity(), "Lỗi2", Toast.LENGTH_SHORT).show();
        }
        return rootview;

    }
}