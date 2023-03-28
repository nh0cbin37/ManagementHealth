package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.app.TabActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    TabHost mytab;
    Intent intent;
    SQLiteDatabase db;
    public static String LoaiBMI;
    public static  int ID_USER = -1;
    ArrayList<Workout> workoutsList = new ArrayList<Workout>();
    ArrayList<Workout_ID_Users> workoutsListofUser = new ArrayList<Workout_ID_Users>();
    static String result = "";
    Cursor calohome;

    private void setcursor(int buoi)
    {
        Date date = new Date(); // Create a new Date object
        Calendar calendar = Calendar.getInstance(); // Create a Calendar object
        calendar.setTime(date); // Set the Calendar object's time to the specified Date object
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // Get the day of the week as an integer value
        String[] daysOfWeek = {"8", "2", "3", "4", "5", "6", "7"};
        String dayOfWeekString = daysOfWeek[dayOfWeek - 1];
        String currentTime = new SimpleDateFormat("yyyy-MM-dd").format(date);
         calohome = db.rawQuery("Select TongCalo from THUCDONTUAN where Buoi = ? and NgaySudung=? ", new String[]{buoi+"",currentTime + ""});
        calohome.moveToFirst();
    }


    private void luucalohome()
    {
        int count = 0;
        int buoi = -1 ;
        while(count<3) {
            if (count == 0) {
                buoi = 1;
                setcursor(buoi);
                int totalcalo = calohome.getInt(0);
                HomeActivityFragment.calosang = totalcalo;
            } else if (count == 1) {
                buoi = 2;
                setcursor(buoi);
                int totalcalo = calohome.getInt(0);
                HomeActivityFragment.calotrua = totalcalo;
            } else if (count == 2) {
                buoi = 3;
                setcursor(buoi);
                int totalcalo = calohome.getInt(0);
                HomeActivityFragment.calochieu = totalcalo;
            }

            count++;
        }
    }
    ///




    static  int countshow = 0 ;
    Double BMI;
    int Id_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    try {
        if (countshow == 1) {
            ID_USER = Login.ID_U;
            Id_user = ID_USER;
            BMI = Login.BMI;
            showWO();
            countshow = 0;
//            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_container,w).commit();
        } else if (countshow == 2)
        {
            ID_USER = Login.ID_U;
            Id_user = ID_USER;
            BMI = Login.BMI;
            if (BMI < 18.5) {
                LoaiBMI = "A";
                result = "and LoaiBMIS_A=?";
            } else if (BMI <= 24.9) {
                LoaiBMI = "B";
                result = "and LoaiBMIS_B=?";
            } else if (BMI <= 29.9) {
                LoaiBMI = "C";
                result = "and LoaiBMIS_C=?";
            } else if (BMI <= 34.9) {
                LoaiBMI = "D";
                result = "and LoaiBMIS_D=?";
            } else {
                LoaiBMI = "E";
                result = "and LoaiBMIS_E=?";
            }
            showUser();
            countshow =0;
        }else if (countshow == 0) {

            Intent intent = getIntent();
            Id_user = Login.ID_U;
            BMI = Login.BMI;
            ID_USER = Id_user;
            show();
        }

        db = openOrCreateDatabase(Login.DATABASE_NAME, MODE_PRIVATE, null);
        if (BMI < 18.5) {
            LoaiBMI = "A";
            result = "and LoaiBMIS_A=?";
        } else if (BMI <= 24.9) {
            LoaiBMI = "B";
            result = "and LoaiBMIS_B=?";
        } else if (BMI <= 29.9) {
            LoaiBMI = "C";
            result = "and LoaiBMIS_C=?";
        } else if (BMI <= 34.9) {
            LoaiBMI = "D";
            result = "and LoaiBMIS_D=?";
        } else {
            LoaiBMI = "E";
            result = "and LoaiBMIS_E=?";
        }

    }catch (Exception ex)
    { Toast.makeText(this, "Lỗi on", Toast.LENGTH_SHORT).show();}
        Date date = new Date(); // Create a new Date object
        Calendar calendar = Calendar.getInstance(); // Create a Calendar object
        calendar.setTime(date); // Set the Calendar object's time to the specified Date object
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // Get the day of the week as an integer value
        String[] daysOfWeek = {"8", "2", "3", "4", "5", "6", "7"};
        String dayOfWeekString = daysOfWeek[dayOfWeek - 1];
        String currentTime = new SimpleDateFormat("yyyy-MM-dd").format(date);
        try {
           // insertBaiTap(BMI, Id_user);
            Cursor cr = db.rawQuery("Select THUCDONTUAN.* from THUCDONTUAN,MENU where  MENU.Id_Menu = THUCDONTUAN.Id_Menu and Id_User = ? and THUCDONTUAN.NgaySudung = ?",new String[]{Id_user+"",currentTime+""},null);
            Cursor remove = db.rawQuery("select Id_Menu from MENU WHERE Id_User = ? ", new String[]
                    {Login.ID_U+""});
            remove.moveToFirst();
            if(cr.getCount() < 1) {
                while (!remove.isAfterLast()) {
                    db.delete("MENU", "Id_Menu=?", new String[]{remove.getInt(0)+""});
                    db.delete("MENUItem","Id_Menu=?", new String[]{remove.getInt(0)+""});
                    db.delete("THUCDONTUAN","Id_Menu=?",new String[]{remove.getInt(0)+""});
                    remove.moveToNext();
                }
                insertBaiTap(Id_user);
                insertMenu( Id_user);
                insertMenuTrua( Id_user);
                insertMenuChieu(Id_user);
                insertTraicay(Id_user);
                luucalohome();
            }else{
                luucalohome();
//
                }

        }catch (Exception ex)
        {
            Toast.makeText(this, "Lỗi main", Toast.LENGTH_SHORT).show();
        }

    }


    private void insertBaiTap( int id_user)
    {
        Date date = new Date(); // Create a new Date object
        Calendar calendar = Calendar.getInstance(); // Create a Calendar object
        calendar.setTime(date); // Set the Calendar object's time to the specified Date object
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // Get the day of the week as an integer value
        String[] daysOfWeek = {"8", "2", "3", "4", "5", "6", "7"};
        String dayOfWeekString = daysOfWeek[dayOfWeek - 1];

        Cursor c;
        //coi nguoi nay co bai tap chua
        int cnt = Integer.parseInt(dayOfWeekString);
        int tangngay = date.getDate();
        int[] kiemtra = {0} ;

        while (8-cnt+1 > 0)
        {
            String currentTime = new SimpleDateFormat("yyyy-MM-dd").format(date);
            int i = 0;
            String rang = "";
            while (i<3) {
                rang=i==0?"De":i==1?"TB":i==2?"Kho":"";
                c = db.rawQuery("select * from BAITAP where LoaiBMI = ? and Rang =?  limit 1", new String[]{LoaiBMI,rang}, null);
                c.moveToFirst();
                while (!c.isAfterLast()) {
                    workoutsList.add(new Workout(c.getInt(0), c.getString(1) + "", c.getInt(2), c.getString(3) + "", c.getString(4) + "", c.getString(5), c.getString(6) + ""));
                    c.moveToNext();
                }

                i++;
            }
            for (Workout WO :
                    workoutsList) {
                ContentValues values = new ContentValues();

                values.put("Id_Pratice", WO.getId_practice());
                values.put("ID_User", id_user);
                values.put("Status", 0);
                values.put("NgayTao",currentTime);
                db.insert("BAITAPCUATUNGID", null, values);

        }
            tangngay++;
            date.setDate(tangngay);
            cnt++;

        }

    }


    private void insertMenu(int id_user) {
        Date date = new Date(); // Create a new Date object
        Calendar calendar = Calendar.getInstance(); // Create a Calendar object
        calendar.setTime(date); // Set the Calendar object's time to the specified Date object
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // Get the day of the week as an integer value
        String[] daysOfWeek = {"8", "2", "3", "4", "5", "6", "7"};
        String dayOfWeekString = daysOfWeek[dayOfWeek - 1]; // Subtract 1 from dayOfWeek to get the array index
        int cnt = Integer.parseInt(dayOfWeekString);
        int tangngay = date.getDate();
        int[] kiemtra = {0};

        Cursor c;
        try{
        while (8 - cnt + 1 > 0) {


            String a = "THUCAN";
            ContentValues values = new ContentValues();
            values.put("Id_User", id_user);

            String currentTime = new SimpleDateFormat("yyyy-MM-dd").format(date);

            values.put("NgayTao", currentTime + "");
            values.put("Buoi", 1);
            db.insert("MENU", null, values);
            Cursor t = db.rawQuery("Select * from MENU where NgayTao = ? and Buoi=? and Id_User=?", new String[]{currentTime + "", 1 + "", id_user + ""}, null);
            ArrayList<ThucAn> arrbuoisang = new ArrayList<ThucAn>();
            // c.moveToFirst();
            t.moveToFirst();
            int idfood = -1;
            int idmenu = -1;
            idmenu = t.getInt(0);
            int count = 0;
            String loai = "";
            while (count < 3) {
                if (count == 0) loai = "D";
                else if (count == 1) loai = "Bot";
                else if (count == 2) loai = "X";
                c = db.rawQuery("Select * from THUCAN where Loai = ? and BuoiSang= ?" + result + "order by random() limit 1", new String[]{loai, 1 + "", 1 + ""});
                c.moveToFirst();
                if (c.getCount() > 0) {
                    idfood = c.getInt(0);

                    String soluong = c.getString(2);
                    ContentValues valuemenuitem = new ContentValues();
                    valuemenuitem.put("Id_Menu", idmenu);
                    valuemenuitem.put("Id_food", idfood);
                    valuemenuitem.put("SoLuong", soluong);
                    db.insert("MENUItem", null, valuemenuitem);

                }
                count++;
            }
            Cursor calo = db.rawQuery("SELECT Sum(Calo) from MENUItem,THUCAN where MENUItem.Id_food = THUCAN.Id_food and  MENUItem.Id_Menu = ?", new String[
                    ]{idmenu + ""}, null);
            calo.moveToFirst();
            int totalcalo = calo.getInt(0);

            ContentValues valueaddmenutuan = new ContentValues();
            valueaddmenutuan.put("Id_Menu", idmenu);
            valueaddmenutuan.put("Buoi", 1);
            valueaddmenutuan.put("TongCalo", totalcalo);
            valueaddmenutuan.put("NgaySudung", currentTime + "");
            db.insert("THUCDONTUAN", null, valueaddmenutuan);
            tangngay++;
            date.setDate(tangngay);
            cnt++;
        }
    }catch (Exception ex)
        {
            Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show();
        }
    }
    private void insertMenuTrua(int id_user)
    {
        Date date = new Date(); // Create a new Date object
        Calendar calendar = Calendar.getInstance(); // Create a Calendar object
        calendar.setTime(date); // Set the Calendar object's time to the specified Date object
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // Get the day of the week as an integer value
        String[] daysOfWeek = {"8", "2", "3", "4", "5", "6", "7"};
        String dayOfWeekString = daysOfWeek[dayOfWeek - 1]; // Subtract 1 from dayOfWeek to get the array index
        int cnt = Integer.parseInt(dayOfWeekString);
        int tangngay = date.getDate();

        Cursor c;
        while (8-cnt+1 > 0)
        {
            String a = "THUCAN";
            ContentValues values = new ContentValues();
            values.put("Id_User",id_user);

            String currentTime = new SimpleDateFormat("yyyy-MM-dd").format(date);

            values.put("NgayTao",currentTime+"");
            values.put("Buoi",2);
            db.insert("MENU",null,values);
            Cursor t = db.rawQuery("Select * from MENU where NgayTao = ? and Buoi=? and Id_User=?",new String[]{currentTime+"",2+"",id_user+""},null);
            ArrayList<ThucAn> arrbuoisang = new ArrayList<ThucAn>();
          //  c.moveToFirst();
            t.moveToFirst();
            int idfood = -1;
            int idmenu=-1;
            idmenu = t.getInt(0);
            int count = 0;
            String loai = "" ;
            while(count < 3)
            {
                if(count == 0) loai = "D";
                else if(count == 1 ) loai = "Bot";
                else if(count == 2) loai = "Beo";
                c = db.rawQuery("Select * from THUCAN where Loai = ? and BuoiTrua= ?"+result+"order by random() limit 1", new String[]{loai,1+"",1+""});
                c.moveToFirst();
                if(c.getCount()>0) {
                    idfood = c.getInt(0);
                    String soluong = c.getString(2);
                    ContentValues valuemenuitem = new ContentValues();
                    valuemenuitem.put("Id_Menu", idmenu);
                    valuemenuitem.put("Id_food", idfood);
                    valuemenuitem.put("SoLuong", soluong);
                    db.insert("MENUItem", null, valuemenuitem);
                }
                count++;
            }

            Cursor calo = db.rawQuery("SELECT Sum(Calo) from MENUItem,THUCAN where MENUItem.Id_food = THUCAN.Id_food and  MENUItem.Id_Menu = ?",new String[
                    ]{idmenu+""},null);
            calo.moveToFirst();
            int totalcalo = calo.getInt(0);
            ContentValues valueaddmenutuan = new ContentValues();
            valueaddmenutuan.put("Id_Menu",idmenu);
            valueaddmenutuan.put("Buoi",2);
            valueaddmenutuan.put("TongCalo",totalcalo);
            valueaddmenutuan.put("NgaySudung",currentTime+"");
            db.insert("THUCDONTUAN",null,valueaddmenutuan);
            tangngay++;
            date.setDate(tangngay);
            cnt++;
        }
    }
    private void insertMenuChieu(int id_user)
    {
        Date date = new Date(); // Create a new Date object
        Calendar calendar = Calendar.getInstance(); // Create a Calendar object
        calendar.setTime(date); // Set the Calendar object's time to the specified Date object
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // Get the day of the week as an integer value
        String[] daysOfWeek = {"8", "2", "3", "4", "5", "6", "7"};
        String dayOfWeekString = daysOfWeek[dayOfWeek - 1]; // Subtract 1 from dayOfWeek to get the array index
        int cnt = Integer.parseInt(dayOfWeekString);
        int tangngay = date.getDate();
        Cursor c;
        while (8-cnt+1 > 0)
        {

            String a = "THUCAN";
            ContentValues values = new ContentValues();
            values.put("Id_User",id_user);

            String currentTime = new SimpleDateFormat("yyyy-MM-dd").format(date);

            values.put("NgayTao",currentTime+"");
            values.put("Buoi",3);
            db.insert("MENU",null,values);
            Cursor t = db.rawQuery("Select * from MENU where NgayTao = ? and Buoi=? and Id_User=?",new String[]{currentTime+"",3+"",id_user+""},null);
            ArrayList<ThucAn> arrbuoisang = new ArrayList<ThucAn>();
            t.moveToFirst();
            int idfood = 0;
            int idmenu=-1;
            idmenu = t.getInt(0);
            int count = 0;
            String loai = "" ;
            while(count < 3)
            {
                if(count == 0) loai = "D";
                else if(count == 1 ) loai = "Bot";
                else if(count == 2) loai = "X";
                c = db.rawQuery("Select * from THUCAN where Loai = ? and BuoiChieu= ?"+result+"order by random() limit 1", new String[]{loai,1+"",1+""});
                c.moveToFirst();
                if(c.getCount()>0) {
                    idfood = c.getInt(0);
                    String soluong = c.getString(2);
                    ContentValues valuemenuitem = new ContentValues();
                    valuemenuitem.put("Id_Menu", idmenu);
                    valuemenuitem.put("Id_food", idfood);
                    valuemenuitem.put("SoLuong", soluong);
                    db.insert("MENUItem", null, valuemenuitem);
                }
                count++;
            }
            Cursor calo = db.rawQuery("SELECT Sum(Calo) from MENUItem,THUCAN where MENUItem.Id_food = THUCAN.Id_food and  MENUItem.Id_Menu = ?",new String[
                    ]{idmenu+""},null);
            calo.moveToFirst();
            int totalcalo = calo.getInt(0);
            ContentValues valueaddmenutuan = new ContentValues();
            valueaddmenutuan.put("Id_Menu",idmenu);
            valueaddmenutuan.put("Buoi",3);
            valueaddmenutuan.put("TongCalo",totalcalo);
            valueaddmenutuan.put("NgaySudung",currentTime+"");
            db.insert("THUCDONTUAN",null,valueaddmenutuan);
            tangngay++;
            date.setDate(tangngay);
            cnt++;
        }
    }

    private void insertTraicay( int id_user)
    {
        Date date = new Date(); // Create a new Date object
        Calendar calendar = Calendar.getInstance(); // Create a Calendar object
        calendar.setTime(date); // Set the Calendar object's time to the specified Date object
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // Get the day of the week as an integer value
        String[] daysOfWeek = {"8", "2", "3", "4", "5", "6", "7"};
        String dayOfWeekString = daysOfWeek[dayOfWeek - 1]; // Subtract 1 from dayOfWeek to get the array index
        int cnt = Integer.parseInt(dayOfWeekString);
        int tangngay = date.getDate();
        Cursor c;
        while (8-cnt+1 > 0)
        {

            ContentValues values = new ContentValues();
            values.put("Id_User",id_user);

            String currentTime = new SimpleDateFormat("yyyy-MM-dd").format(date);

            values.put("NgayTao",currentTime+"");
            values.put("Buoi",4);
            db.insert("MENU",null,values);
            Cursor t = db.rawQuery("Select * from MENU where NgayTao = ? and Buoi=? and Id_User=?",new String[]{currentTime+"",4+"",id_user+""},null);
            ArrayList<ThucAn> arrbuoisang = new ArrayList<ThucAn>();
            //   c.moveToFirst();
            t.moveToFirst();
            int idfood = 0;
            int idmenu=-1;
            idmenu = t.getInt(0);
            int count = 0;
            String loai = "" ;

                c = db.rawQuery("Select * from THUCAN where Loai = ? order by random() limit 3", new String[]{"Traicay"});
                c.moveToFirst();
                while (!c.isAfterLast()) {
                    idfood = c.getInt(0);
                    String soluong = c.getString(2);
                    ContentValues valuemenuitem = new ContentValues();
                    valuemenuitem.put("Id_Menu", idmenu);
                    valuemenuitem.put("Id_food", idfood);
                    valuemenuitem.put("SoLuong", soluong);
                    db.insert("MENUItem", null, valuemenuitem);
                    c.moveToNext();
                }
            Cursor calo = db.rawQuery("SELECT Sum(Calo) from MENUItem,THUCAN where MENUItem.Id_food = THUCAN.Id_food and  MENUItem.Id_Menu = ?",new String[
                    ]{idmenu+""},null);
            calo.moveToFirst();
            int totalcalo = calo.getInt(0);
            ContentValues valueaddmenutuan = new ContentValues();
            valueaddmenutuan.put("Id_Menu",idmenu);
            valueaddmenutuan.put("Buoi",4);
            valueaddmenutuan.put("TongCalo",totalcalo);
            valueaddmenutuan.put("NgaySudung",currentTime+"");
            db.insert("THUCDONTUAN",null,valueaddmenutuan);
            tangngay++;
            date.setDate(tangngay);
            cnt++;
        }
    }


    private void showUser()
    {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout_container, new User_fragment()).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_user);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                // Show the HomeActivity in the FrameLayout container
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frame_layout_container, new HomeActivityFragment()).commit();
                                return true;
                            case R.id.action_food:
                                // Show the SearchActivity in the FrameLayout container
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frame_layout_container, new FoodActivityFragment()).commit();
                                return true;
                            case R.id.action_workout:
                                // Show the NotificationsActivity in the FrameLayout container
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frame_layout_container, new WorkoutActivityFragment()).commit();
                                return true;
                            case R.id.action_user:
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frame_layout_container, new User_fragment()).commit();
                                return true;
                        }
                        return false;
                    }
                });


    }
    private void showWO()
    {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout_container, new WorkoutActivityFragment()).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_workout);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                // Show the HomeActivity in the FrameLayout container
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frame_layout_container, new HomeActivityFragment()).commit();
                                return true;
                            case R.id.action_food:
                                // Show the SearchActivity in the FrameLayout container
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frame_layout_container, new FoodActivityFragment()).commit();
                                return true;
                            case R.id.action_workout:
                                // Show the NotificationsActivity in the FrameLayout container
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frame_layout_container, new WorkoutActivityFragment()).commit();
                                return true;
                            case R.id.action_user:
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frame_layout_container, new User_fragment()).commit();
                                return true;
                        }
                        return false;
                    }
                });


    }

    private void show()
    {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout_container, new HomeActivityFragment()).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                // Show the HomeActivity in the FrameLayout container
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frame_layout_container, new HomeActivityFragment()).commit();
                                return true;
                            case R.id.action_food:
                                // Show the SearchActivity in the FrameLayout container
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frame_layout_container, new FoodActivityFragment()).commit();
                                return true;
                            case R.id.action_workout:
                                // Show the NotificationsActivity in the FrameLayout container
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frame_layout_container, new WorkoutActivityFragment()).commit();
                                return true;
                            case R.id.action_user:
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frame_layout_container, new User_fragment()).commit();
                                return true;
                        }
                        return false;
                    }
                });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}



