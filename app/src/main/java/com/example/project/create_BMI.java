package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class create_BMI extends AppCompatActivity {

    EditText editweight;
    EditText editHeight;
    Button btnxacnhan;
    Button btnthoat;
    RadioGroup radiGr;
    int idChecked, gender = 0;
    String gioitinh = "Nữ";
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = openOrCreateDatabase(Login.DATABASE_NAME,MODE_PRIVATE,null);
        setContentView(R.layout.activity_create_bmi);
        editweight = (EditText)findViewById(R.id.editweight);
        editHeight = (EditText)findViewById(R.id.editHeight);
        btnxacnhan = (Button) findViewById(R.id.btnxacnhan);
        btnthoat = (Button) findViewById(R.id.thoat);
        radiGr = (RadioGroup)findViewById(R.id.radiGoGender);
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                idChecked = radiGr.getCheckedRadioButtonId();
                if((idChecked!=R.id.rabtnFeMale && idChecked!=R.id.rabtnFeMale) || editweight.getText()+"" ==""||
                editHeight.getText()+"" == ""){
                    Toast.makeText(create_BMI.this, "Bạn phải nhập thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    if (idChecked == R.id.rabtnMale) {
                        gioitinh = "Nam";
                        gender = 1;
                    }
                    ContentValues values = new ContentValues();
                    values.put("GioiTinh", gioitinh);
                    values.put("CanNang", editweight.getText() + "");
                    values.put("ChieuCao", editHeight.getText() + "");
                    Double weight = Double.parseDouble(editweight.getText() + "");
                    Double height = Double.parseDouble(editHeight.getText() + "") / 100;
                    Double totalBMI = weight / (height * height);
                    values.put("BMI", totalBMI);
                    db.update("USER", values, "Id_User=?", new String[]{Login.ID_U + ""});
                    Login.BMI = totalBMI;
                    Intent intent = new Intent(create_BMI.this, MainActivity.class);
                    intent.putExtra("ID_user", Login.ID_U);
                    intent.putExtra("BMI", Login.BMI);
                    startActivity(intent);
                }
            }
        });


    }
}