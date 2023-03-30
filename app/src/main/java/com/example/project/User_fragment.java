package com.example.project;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class User_fragment extends Fragment {

    EditText Ten;
    EditText Gioitinh;
    EditText BMI;
    EditText Cannang;
    EditText ChieuCao;
    EditText CanNangupdate;
    EditText chieucaoafterupdate;
    Button btnCapnhat;
    Button btnHuy;
    Button btnCapnhatthongtin;
    ImageView imguser;
    CardView card;
    SQLiteDatabase db;

    private void signOut() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        // Firebase sign outG
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getContext(),gso);
        mGoogleSignInClient.signOut().addOnCompleteListener((Activity) getContext(),
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(getActivity(), Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
        // Google sign out

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_user_fragment, container, false);
        Ten = (EditText) rootview.findViewById(R.id.editTen);
        Gioitinh = (EditText) rootview.findViewById(R.id.editGender);
        BMI = (EditText) rootview.findViewById(R.id.editBMI);
        Cannang = (EditText) rootview.findViewById(R.id.editweight);
        ChieuCao = (EditText) rootview.findViewById(R.id.editHeight);
       btnCapnhat = (Button) rootview.findViewById(R.id.btnCapnhat);
       imguser = (ImageView)rootview.findViewById(R.id.imguser);


       db = getActivity().openOrCreateDatabase(Login.DATABASE_NAME, MODE_PRIVATE,null);
        Cursor c = db.rawQuery("Select * from USER where Id_User = ?", new String[]{Login.ID_U+""});
        c.moveToFirst();
        if(c.getString(4).equals("Nữ"))
        {
            String imgpath = "/data/data/com.example.project/file/woman.png";
            //String Viepath = "/data/data/com.example.project/file/gapbung.mp4";
            Uri uriimgdone = Uri.parse(imgpath);
            imguser.setImageURI(uriimgdone);
        }
        Ten.setText(c.getString(1)+"");
        Gioitinh.setText(c.getString(4)+"");
        BMI.setText((double)Math.round((c.getDouble(7)*100)/100)+"");
        Cannang.setText(c.getDouble(5)+"");
        ChieuCao.setText(c.getDouble(6)+"");
        ImageButton imgbtnLogout = (ImageButton)rootview.findViewById(R.id.imgbtnLogout);
        ImageButton imgbtnchange = (ImageButton)rootview.findViewById(R.id.imgbtnchange);
        imgbtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Cảnh báo!");
                builder.setIcon(R.drawable.warninglogout);
                builder.setMessage("Bạn có chắc chắn đăng xuất!!!");

                // add a button
                builder.setPositiveButton("Đồng ý", (dialog, which) -> {

                    Cursor c= db.rawQuery("Select * from USER where Id_User = ? and MatKhau=?", new String[]{Login.ID_U+"","####"});
                    c.moveToFirst();
//
                        if (c.getCount() == 1){
                            signOut();
                        }else
                        {
                    // send data from the AlertDialog to the Activity
                    Intent intent = new Intent(getContext(),Login.class);
                    startActivity(intent);
                        }

                });
                builder.setNegativeButton("Hủy", (dialog, which) ->{
                    dialog.dismiss();
                });
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        imgbtnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor c = db.rawQuery("Select MatKhau from USER where Id_User=?", new String[]{Login.ID_U + ""});
                c.moveToFirst();
                if (c.getString(0).equals("####")) {
                    Toast.makeText(getContext(), "Mail không thể đổi mật khẩu!!!", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Đổi mật khẩu");
                    builder.setIcon(R.drawable.key);

                    // set the custom layout
                    final View customLayout = getLayoutInflater().inflate(R.layout.change_pass, null);
                    builder.setView(customLayout);

                    // add a button
                    builder.setPositiveButton("Cập nhật", (dialog, which) -> {
                        // send data from the AlertDialog to the Activity
                        EditText MKCu = customLayout.findViewById(R.id.editmkCu);
                        Cursor checked = db.rawQuery("Select MatKhau from USER where Id_User=?", new String[]{Login.ID_U + ""});
                        checked.moveToFirst();
                        if (!checked.getString(0).equals(MKCu.getText() + "")) {
                            Toast.makeText(getContext(), "Mật khẩu cũ không đúng!", Toast.LENGTH_SHORT).show();
                        } else {
                            EditText MKmoi = customLayout.findViewById(R.id.editmkMoi);
                            ContentValues values = new ContentValues();
                            values.put("MatKhau", MKmoi.getText() + "");
                            db.update("USER", values, "Id_User=?", new String[]{Login.ID_U + ""});
                            Toast.makeText(getContext(), "Cập nhật mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                        }


                    });
                    builder.setNegativeButton("Hủy", (dialog, which) -> {
                        dialog.dismiss();
                    });
                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        btnCapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                card = (CardView)rootview.findViewById(R.id.cardthongtin);
            card.setVisibility(View.VISIBLE);
            CanNangupdate = (EditText) rootview.findViewById(R.id.editCanNangupdate);
            chieucaoafterupdate = (EditText) rootview.findViewById(R.id.editChieuCaoupdate);
            btnCapnhatthongtin = (Button) rootview.findViewById(R.id.Capnhatthongtin);
            btnHuy = (Button) rootview.findViewById(R.id.HuyUpdate);

            btnCapnhatthongtin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String cannang = CanNangupdate.getText()+"";
                    String chieucao = ChieuCao.getText()+"";
                    if(cannang.equals("") || chieucao.equals(""))
                    {
                        Toast.makeText(getActivity(), "Chưa nhập thông tin", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Cannang.setText(CanNangupdate.getText()+"");
                        ChieuCao.setText(chieucaoafterupdate.getText()+"");
                        Double Can = Double.parseDouble(Cannang.getText()+"");
                        Double height = Double.parseDouble(ChieuCao.getText()+"");

                        Double heights=height/100;
                        BMI.setText((double)Math.round(((Can/(heights*heights))*100)/100)+"");
                        ContentValues values = new ContentValues();
                        values.put("CanNang",Can);
                        values.put("ChieuCao",height);
                        values.put("BMI",BMI.getText()+"");
                        db.update("USER",values,"Id_User = ?", new String[]{Login.ID_U+""});
                        Cursor menuitem = db.rawQuery("select DISTINCT MENUItem.Id_Menu from MENU, MENUItem where MENU.Id_Menu = MENUItem.Id_Menu and Id_User = ?", new String[]{Login.ID_U+""});
                            db.delete("BAITAPCUATUNGID","Id_User = ?", new String[]{Login.ID_U+""});
//                            Baitap.moveToNext();
                        menuitem.moveToFirst();
                       while (!menuitem.isAfterLast()) {
                           db.delete("MENUItem", "Id_Menu=?",new String[]{menuitem.getInt(0)+""});
                          db.delete("THUCDONTUAN","Id_Menu=?", new String[]{menuitem.getInt(0)+""});
                           db.delete("MENU", "Id_Menu=?", new String[]{menuitem.getInt(0)+""});
                          menuitem.moveToNext();
                       }

                        Intent intent = new Intent(getContext(),MainActivity.class);
                        MainActivity.countshow = 2;
                        Login.BMI = (double)Math.round(((Can/(heights*heights))*100)/100);
                        startActivity(intent);


                    }
                    card.setVisibility(View.INVISIBLE);
                }
            });
            btnHuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    card.setVisibility(View.INVISIBLE);
                }
            });
                }catch (Exception ex)
                {
                    Toast.makeText(getContext(), "lỗi capnhat", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootview;
    }
}