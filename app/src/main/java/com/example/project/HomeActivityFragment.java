package com.example.project;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link HomeActivityFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class HomeActivityFragment extends Fragment {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public HomeActivityFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment HomeActivityFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static HomeActivityFragment newInstance(String param1, String param2) {
//        HomeActivityFragment fragment = new HomeActivityFragment();
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
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home_activity, container, false);
//    }
//}
public class HomeActivityFragment extends Fragment {

    public HomeActivityFragment() {
        // Required empty public constructor
    }
    public static int calosang ;
    public static int calotrua ;
    public static int calochieu;
    TextView txtcountdam;
    TextView txtcountBot;
    TextView txtcountBeo;
    TextView txtcountXo;
    TextView txtcountCalo;
    ProgressBar proDam;
    ProgressBar proBot;
    ProgressBar proBeo;
    ProgressBar proXo;
    ProgressBar proCalo;
    int total = 0;
    int calotong = 0;
    SQLiteDatabase db;
    ImageView water1;
    ImageView water2;
    ImageView water3;
    ImageView water4;
    ImageView water5;
    ImageView water6;
    ImageView water7;
    ImageView water8;
    TextView txtsolitnc;
    private void thietlap(CaloUsers userA,int Hour,int minute,String dayofweek)
    {
        txtcountdam.setText("0/"+userA.getDam());
        txtcountXo.setText("0/"+userA.getXo());
        txtcountBeo.setText("0/"+userA.getBeo());
        txtcountBot.setText("0/"+userA.getBot());
        txtcountCalo.setText("0");
        proDam.setProgress(0);
        proXo.setProgress(0);
        proBeo.setProgress(0);
        proBot.setProgress(0);
        proCalo.setProgress(0);
        double dam =0;
        double bot = 0;
        double xo =0;
        double beo = 0;
        int prodam = 0;
        int probot = 0;
        int proxo = 0;
        int probeo = 0;
        String imgpath = "/data/data/com.example.project/file/cupwater.png";
        Uri uriimgdone = Uri.parse(imgpath);

        if(Hour >= 8 && minute > 0)
        {
            txtsolitnc.setText("750/2000");
            Cursor c = db.rawQuery("Select Sum(Dam),Sum(Beo),Sum(Bot),Sum(Xo),THUCDONTUAN.Id_Menu from THUCAN,MENUItem,THUCDONTUAN,MENU WHERE MENU.Id_Menu = MENUItem.Id_Menu and  THUCAN.Id_food = MENUItem.Id_food and MENUItem.Id_Menu = THUCDONTUAN.Id_Menu and NgaySudung=? and THUCDONTUAN.Buoi=? and Id_User = ?  ",new String[]
                    {dayofweek+"",1+"",Login.ID_U+""});
            c.moveToFirst();
            water1.setImageURI(uriimgdone);
            water2.setImageURI(uriimgdone);
            water3.setImageURI(uriimgdone);
            dam+=c.getDouble(0);
            beo+=c.getDouble(1);
            xo+=c.getDouble(3);
            bot+=c.getDouble(2);
            txtcountdam.setText((Math.round(dam*10)/10) +"/"+userA.dam);
            txtcountXo.setText((Math.round(xo*10)/10)+"/"+userA.xo);
            txtcountBeo.setText((Math.round(beo*10)/10)+"/"+userA.beo);
            txtcountBot.setText((Math.round(bot*10)/10)+"/"+userA.bot);
            Cursor calo = db.rawQuery("Select TongCalo From THUCDONTUAN where Id_Menu = ?", new String[]{c.getInt(4)+""});
            calo.moveToFirst();
            calotong+=calo.getInt(0);
            txtcountCalo.setText(calotong+"");
            prodam+=(dam/userA.dam)*100;
            probeo+=(beo/userA.beo)*100;
            proxo+=(xo/userA.xo)*100;
            probot+=(bot/userA.bot)*100;
            txtcountBot.setText(Math.round(bot)+"/"+userA.bot);
            proDam.setProgress(prodam);
            proXo.setProgress(proxo);
            proBeo.setProgress(probeo);
            proBot.setProgress(probot);
            total =(calotong*100)/userA.getCalo();
            proCalo.setProgress(total);
        }
        if(Hour >= 12 && minute > 0)
        {
            txtsolitnc.setText("1500/2000");
            Cursor c = db.rawQuery("Select Sum(Dam),Sum(Beo),Sum(Bot),Sum(Xo),THUCDONTUAN.Id_Menu from THUCAN,MENUItem,THUCDONTUAN,MENU WHERE MENU.Id_Menu = MENUItem.Id_Menu and  THUCAN.Id_food = MENUItem.Id_food and MENUItem.Id_Menu = THUCDONTUAN.Id_Menu and NgaySudung=? and THUCDONTUAN.Buoi=? and Id_User = ?  ",new String[]
                    {dayofweek+"",2+"",Login.ID_U+""});
            c.moveToFirst();
            water4.setImageURI(uriimgdone);
            water5.setImageURI(uriimgdone);
            water6.setImageURI(uriimgdone);
            dam+=c.getDouble(0);
            beo+=c.getDouble(1);
            xo+=c.getDouble(3);
            bot+=c.getDouble(2);
            txtcountdam.setText(Math.round(dam) +"/"+userA.dam);
            txtcountXo.setText(Math.round(xo)+"/"+userA.xo);
            txtcountBeo.setText(Math.round(beo)+"/"+userA.beo);
            txtcountBot.setText(Math.round(bot)+"/"+userA.bot);
            Cursor calo = db.rawQuery("Select TongCalo From THUCDONTUAN where Id_Menu = ?", new String[]{c.getInt(4)+""});
            calo.moveToFirst();
            calotong+=calo.getInt(0);
            txtcountCalo.setText(calotong+"");
            prodam+=(dam/userA.dam)*100;
            probeo+=(beo/userA.beo)*100;
            proxo+=(xo/userA.xo)*100;
            probot+=(bot/userA.bot)*100;
            proDam.setProgress(prodam);
            proXo.setProgress(proxo);
            proBeo.setProgress(probeo);
            proBot.setProgress(probot);
            total =(calotong*100)/userA.getCalo();
            proCalo.setProgress(total);
        }
        if(Hour >= 19 && minute > 0)
        {
            txtsolitnc.setText("2000/2000");
            Cursor c = db.rawQuery("Select Sum(Dam),Sum(Beo),Sum(Bot),Sum(Xo),THUCDONTUAN.Id_Menu from THUCAN,MENUItem,THUCDONTUAN,MENU WHERE MENU.Id_Menu = MENUItem.Id_Menu and  THUCAN.Id_food = MENUItem.Id_food and MENUItem.Id_Menu = THUCDONTUAN.Id_Menu and NgaySudung=? and THUCDONTUAN.Buoi=? and Id_User = ?  ",new String[]
                    {dayofweek+"",3+"",Login.ID_U+""});
            c.moveToFirst();
            water7.setImageURI(uriimgdone);
            water8.setImageURI(uriimgdone);
            dam+=c.getDouble(0);
            beo+=c.getDouble(1);
            xo+=c.getDouble(3);
            bot+=c.getDouble(2);

            txtcountdam.setText(Math.round(dam) +"/"+userA.dam);
            txtcountXo.setText(Math.round(xo)+"/"+userA.xo);
            txtcountBeo.setText(Math.round(beo)+"/"+userA.beo);
            txtcountBot.setText(Math.round(bot)+"/"+userA.bot);
            Cursor calo = db.rawQuery("Select TongCalo From THUCDONTUAN where Id_Menu = ?", new String[]{c.getInt(4)+""});
            calo.moveToFirst();
            calotong+=calo.getInt(0);
            txtcountCalo.setText(calotong+"");
            prodam+=(dam/userA.dam)*100;
            probeo+=(beo/userA.beo)*100;
            proxo+=(xo/userA.xo)*100;
            probot+=(bot/userA.bot)*100;
            proDam.setProgress(prodam);
            proXo.setProgress(proxo);
            proBeo.setProgress(probeo);
            proBot.setProgress(probot);
            total =(calotong*100)/userA.getCalo();
            if(total > 100) total =100;
            proCalo.setProgress(total);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_home_activity, container, false);
        Date date = new Date(); // Create a new Date object
        Calendar calendar = Calendar.getInstance(); // Create a Calendar object
        calendar.setTime(date); // Set the Calendar object's time to the specified Date object
        int Hour = calendar.get(Calendar.HOUR_OF_DAY);
        int Minutes = calendar.get(Calendar.MINUTE);
        int dayofweek = calendar.get(Calendar.DAY_OF_WEEK);
        String currentTime = new SimpleDateFormat("yyyy-MM-dd").format(date);
        txtcountdam = (TextView) rootview.findViewById(R.id.txtCountDam);
        txtcountBot = (TextView) rootview.findViewById(R.id.txtCountBot_Duong);
        txtcountBeo = (TextView) rootview.findViewById(R.id.txtCOuntBeo);
        txtcountXo = (TextView) rootview.findViewById(R.id.txtCountXo);
        txtcountCalo = (TextView) rootview.findViewById(R.id.txtCaloHome);
        proDam = (ProgressBar)rootview.findViewById(R.id.progrDam);
        proBot=(ProgressBar)rootview.findViewById(R.id.progrBot_Sugar);
        proBeo = (ProgressBar)rootview.findViewById(R.id.progrBeo);
        proXo=(ProgressBar)rootview.findViewById(R.id.progrXo);
        proCalo = (ProgressBar)rootview.findViewById(R.id.progrCalo);
         water1 = (ImageView)rootview.findViewById(R.id.imgcupwater1);
         water2= (ImageView)rootview.findViewById(R.id.imgcupwater2);
        water3= (ImageView)rootview.findViewById(R.id.imgcupwater3);
        water4= (ImageView)rootview.findViewById(R.id.imgcupwater4);
        water5= (ImageView)rootview.findViewById(R.id.imgcupwater5);
        water6= (ImageView)rootview.findViewById(R.id.imgcupwater6);
        water7= (ImageView)rootview.findViewById(R.id.imgcupwater7);
        water8= (ImageView)rootview.findViewById(R.id.imgcupwater8);
        txtsolitnc = (TextView)rootview.findViewById(R.id.txtSolitnuoc);
        db = getActivity().openOrCreateDatabase(Login.DATABASE_NAME, MODE_PRIVATE, null);

        // Inflate the layout for this fragment
       CaloUsers userA = new CaloUsers("A",2600,325,35,195,58);
        CaloUsers userB = new CaloUsers("B",2300,287.5,31,172.5,51.1);
        CaloUsers userC = new CaloUsers("C",2000,250,27,150,44.5);
        CaloUsers userD = new CaloUsers("D",1800,225,25.5,135,40);
        CaloUsers userE = new CaloUsers("E",1600,200,23,120,35.6);
        try {
             if(MainActivity.LoaiBMI.equals("A"))
            {
                thietlap(userA,Hour,Minutes,currentTime);
            }else if(MainActivity.LoaiBMI == "B"){ thietlap(userB,Hour,Minutes,currentTime); }
            else if(MainActivity.LoaiBMI == "C") {thietlap(userC,Hour,Minutes,currentTime);}
            else if(MainActivity.LoaiBMI == "D"){ thietlap(userD,Hour,Minutes,currentTime);}
            else if(MainActivity.LoaiBMI == "E"){ thietlap(userE,Hour,Minutes,currentTime);}
        }catch (Exception ex)
        {
            Toast.makeText(getActivity(), "Lỗi home", Toast.LENGTH_SHORT).show();
        }

        return rootview;
    }
}
