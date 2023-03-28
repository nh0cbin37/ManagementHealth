package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateMenu extends Activity {

    Spinner spindamsang ;
    Spinner spinbotsang;
    Spinner spinxosang;
    Spinner spinbottrua ;
    Spinner spinbeotrua ;
    Spinner spindamtrua ;
    Spinner spindamchieu ;
    Spinner spinbotchieu;
    Spinner spinxochieu ;
    Spinner spintraicay1;
    Spinner spintraicay2;
    Spinner spintraicay3;
    Cursor dam;
    Cursor Xo;
    Cursor Bot;
    Cursor Beo;
    SQLiteDatabase db;
    private void initDB()
    {   try {
        db = openOrCreateDatabase(Login.DATABASE_NAME,MODE_PRIVATE,null);
//        dam = db.rawQuery("Select * from THUCAC where Loai = ?",new String[]{"D"},null);
//        Xo = db.rawQuery("Select * from THUCAC where Loai = ?",new String[]{"X"},null);
//        Bot = db.rawQuery("Select * from THUCAC where Loai = ?",new String[]{"Bot"},null);
//        Beo = db.rawQuery("Select * from THUCAC where Loai = ?",new String[]{"Beo"},null);

        int i = 0;
        String loai = "";
        while(i < 5) {
            if(i == 0) loai = "D";
                    else if(i==1) loai = "X";
                        else if(i==2) loai ="Bot";
                            else if(i==3) loai = "Beo";
                                else if(i==4) loai = "Traicay";
           Cursor ctrua = db.rawQuery("Select * from THUCAN where Loai = ?"+MainActivity.result,new String[]{loai+"",1+""},null);
            ctrua.moveToFirst();
            ArrayList<ThucAn> listthucan = new ArrayList<ThucAn>();
            ArrayAdapter adapter;
            while (!ctrua.isAfterLast()) {
                listthucan.add( new ThucAn(ctrua.getString(1)+"",ctrua.getString(2)+"",ctrua.getInt(3),ctrua.getDouble(4),ctrua.getDouble(5),ctrua.getDouble(6),ctrua.getDouble(7),3,ctrua.getString(11)+"",ctrua.getString(12),MainActivity.LoaiBMI));
                ctrua.moveToNext();
            }
            adapter = new ArrayAdapter<ThucAn>(this, android.R.layout.simple_spinner_item, listthucan);
            adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
            if(i==0){
                spindamsang.setAdapter(adapter);
                spindamchieu.setAdapter(adapter);
                spindamtrua.setAdapter(adapter);}
                else if(i==1)
            {
                spinxochieu.setAdapter(adapter);
                spinxosang.setAdapter(adapter);
            }else if(i==2)
            {
                spinbotsang.setAdapter(adapter);
                spinbottrua.setAdapter(adapter);
                spinbotchieu.setAdapter(adapter);
            }else if(i==3) spinbeotrua.setAdapter(adapter);
                else if(i==4)
            {
                spintraicay1.setAdapter(adapter);
                spintraicay2.setAdapter(adapter);
                spintraicay3.setAdapter(adapter);
            }
            i++;

        }
        }catch(Exception exception){
            Toast.makeText(getApplication(), "Lỗi "+ exception.getMessage(), Toast.LENGTH_LONG).show();
        }
    }



    int[] Luumenu;
    int[] LuuIDmenu;


    private boolean saveStudent(int id_menu, int idthucanodl,String tenthucannew){
        db = openOrCreateDatabase (Login.DATABASE_NAME, MODE_PRIVATE, null);
        ContentValues values = new ContentValues();

        Cursor c = db.rawQuery("Select Id_food from THUCAN where TenThucAn = ? ",new String[]{tenthucannew+""});
        try {
            c.moveToFirst();
            int id_food = c.getInt(0);
            values.put("Id_food",id_food);
            if(db.update("MENUItem",values,"Id_Menu=? and Id_food = ? ",new String[]{id_menu+"",idthucanodl+""})!=-1){
                return true;
            }
        }catch(Exception exception){
            Toast.makeText(this, "Lỗi "+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    int posspindamsang=-1;
    int posspinbotsang=-1;
    int posspinxosang=-1;
    int posspinbottrua=-1;
    int posspinbeotrua=-1;
    int posspindamtrua=-1;
    int posspindamchieu=-1;
    int posspinbotchieu=-1;
    int posspinxochieu=-1;
    int posspintraicay1=-1;
    int posspintraicay2=-1;
    int posspintraicay3=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_menu);
         spindamsang = (Spinner) findViewById(R.id.spinmondambuoisang);
         spinbotsang = (Spinner) findViewById(R.id.spinmonBotbuoisang);
         spinxosang = (Spinner) findViewById(R.id.spinmonXobuoisang);
         spinbottrua = (Spinner) findViewById(R.id.spinmonBotbuoitrua);
         spinbeotrua = (Spinner) findViewById(R.id.spinmonBeobuoiTrua);
         spindamtrua = (Spinner) findViewById(R.id.spinmonDambuoiTrua);
         spindamchieu = (Spinner) findViewById(R.id.spinmondambuoiChieu);
         spinbotchieu = (Spinner) findViewById(R.id.spinmonBotbuoiChieu);
         spinxochieu = (Spinner) findViewById(R.id.spinmonXobuoiChieu);
         spintraicay1 = (Spinner)findViewById(R.id.spintraicay1);
        spintraicay2 = (Spinner)findViewById(R.id.spintraicay2);
        spintraicay3 = (Spinner)findViewById(R.id.spintraicay3);
        Button btnSave = (Button)findViewById(R.id.btnsave);
        Button btnclose = (Button)findViewById(R.id.closeCrMe);



        initDB();
        //getdata();
        String[]tenthucannew = new String[12];
        tenthucannew[0] = spindamsang.getSelectedItem().toString();
        tenthucannew[1] = spinxosang.getSelectedItem().toString();
        tenthucannew[2] = spinbotsang.getSelectedItem().toString();
        tenthucannew[3] = spindamtrua.getSelectedItem().toString();
        tenthucannew[4] = spinbottrua.getSelectedItem().toString();
        tenthucannew[5] = spinbeotrua.getSelectedItem().toString();
        tenthucannew[6] = spindamchieu.getSelectedItem().toString();
        tenthucannew[7] = spinxochieu.getSelectedItem().toString();
        tenthucannew[8] = spinbotchieu.getSelectedItem().toString();
        tenthucannew[9] = spintraicay1.getSelectedItem().toString();
        tenthucannew[10] = spintraicay2.getSelectedItem().toString();
        tenthucannew[11] = spintraicay3.getSelectedItem().toString();
        spindamsang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tenthucannew[0] = spindamsang.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                posspindamsang=-1;
            }
        });
        spinxosang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tenthucannew[1] = spinxosang.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                posspinxosang=-1;
            }
        });
        spinbotsang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tenthucannew[2] = spinbotsang.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                posspinbotsang=-1;
            }
        });
        spindamtrua.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tenthucannew[3] = spindamtrua.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                posspindamtrua=-1;
            }
        });

        spinbottrua.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tenthucannew[4] = spinbottrua.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                posspinbottrua=-1;
            }
        });
        spinbeotrua.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tenthucannew[5] = spinbeotrua.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            posspinbeotrua=-1;
            }
        });
        spindamchieu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tenthucannew[6] = spindamchieu.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            posspindamchieu=-1;
            }
        });
        spinxochieu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tenthucannew[7] = spinxochieu.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            posspinxochieu=-1;
            }
        });
        spinbotchieu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tenthucannew[8] = spinbotchieu.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            posspinbotchieu=-1;
            }
        });
        spintraicay1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tenthucannew[9] = spintraicay1.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                posspinbeotrua=-1;
            }
        });
        spintraicay2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tenthucannew[10] = spintraicay2.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                posspinbeotrua=-1;
            }
        });
        spintraicay3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tenthucannew[11] = spintraicay3.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                posspinbeotrua=-1;
            }
        });
        try {
            btnSave.setOnClickListener(new View.OnClickListener() {
                int j = 0;
                ThucAn[] thucAns = new ThucAn[12];

                @Override
                public void onClick(View v) {
                    for (int i = 0; i < 12; i++) {
                        if (i < 3) j = 1;
                        else if (j < 6) j = 2;
                        else if (j < 9) j = 3;
                        else j = 4;
                        Cursor ctrua = db.rawQuery("Select * from THUCAN where TenThucAn = ?", new String[]{tenthucannew[i]});
                        ctrua.moveToFirst();
                        thucAns[i] = new ThucAn(ctrua.getInt(0), ctrua.getString(1) + "", ctrua.getString(2) + "", ctrua.getInt(3), ctrua.getDouble(4), ctrua.getDouble(5), ctrua.getDouble(6), ctrua.getDouble(7), j, ctrua.getString(11) + "", ctrua.getString(12), MainActivity.LoaiBMI);
                    }
                    Bundle bundle = new Bundle();
                    Intent intent = getIntent();
                    bundle.putSerializable("id_foodnew", thucAns);

                    intent.putExtra("data", bundle);
                    setResult(FoodActivityFragment.SAVE_Item, intent);
                    Toast.makeText(getApplication(), "Cập nhật thực đơn thành công", Toast.LENGTH_LONG).show();

                    finish();


                }

            });
        }catch (Exception ex)
        {
            Toast.makeText(this, "lỗi", Toast.LENGTH_SHORT).show();
        }
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}