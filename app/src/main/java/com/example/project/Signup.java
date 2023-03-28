package com.example.project;

import static java.lang.Math.sqrt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.project.Models.Userss;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;

public class Signup extends AppCompatActivity {
    ImageButton btnback;
    Button btnsignup;
    EditText editHovaTen;
    EditText editUserName;
    EditText editPassword;
    Double W;
    Double H;
    SQLiteDatabase db;
    ImageButton imggoogle;
    private GoogleSignInClient client;
    FirebaseAuth auth;
    FirebaseDatabase database;
    static int Id_user;

    private void saveUser()
    {

        db = openOrCreateDatabase(Login.DATABASE_NAME,MODE_PRIVATE,null);
        ContentValues values = new ContentValues();

        try {
            values.put("HovaTen",editHovaTen.getText()+"");
            values.put("TenDN",editUserName.getText()+"");
            values.put("MatKhau",editPassword.getText()+"");
            values.put("GioiTinh","Nam");
            values.put("CanNang",0.0);
            values.put("ChieuCao",0.0);
            values.put("BMI",0.0);
             db.insert("USER",null,values);

        }catch (Exception ex){
            Toast.makeText(this, "lỗi", Toast.LENGTH_SHORT).show();
        }

    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        db = openOrCreateDatabase(Login.DATABASE_NAME,MODE_PRIVATE,null);
        btnback = (ImageButton) findViewById(R.id.imgbtnback);
        editHovaTen = (EditText)findViewById(R.id.edithovaten);
        editUserName = (EditText)findViewById(R.id.editusername);
        editPassword = (EditText)findViewById(R.id.editpassword1);
        imggoogle = (ImageButton)findViewById(R.id.googleLoginofSignup);
        auth = FirebaseAuth.getInstance();

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        client = GoogleSignIn.getClient(this,options);
        database = FirebaseDatabase.getInstance("https://tnq-care-health-default-rtdb.firebaseio.com/");
        imggoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = client.getSignInIntent();
                startActivityForResult(i,123);
            }
        });
        btnsignup = (Button) findViewById(R.id.Signupbtn);
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String W1 = editWeight.getText().toString();
//                String H1 = editHeight.getText().toString();
//                W = Double.parseDouble(W1);
//                H  = Double.parseDouble(H1);
//                User newUser = new User(editHovaTen.getText()+"",editUserName.getText()+"",editPassword.getText()+"",W,H,W/(H*H));
//                Intent intent = new Intent(getBaseContext(), Login.class);
//                intent.putExtra("DataUser",newUser);

                if (editHovaTen.getText() + "" == "" || editUserName.getText() + "" == "" ||
                        editPassword.getText() + "" == "") {
                    Toast.makeText(Signup.this, "Bạn chưa nhập thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    Cursor kiemtraU = db.rawQuery("select * from USER where TenDN = ?", new String[]{editUserName.getText() + ""});
                    kiemtraU.moveToFirst();
                    if (kiemtraU.getCount() > 0) {
                        Toast.makeText(Signup.this, "Tên đăng nhập đã có.", Toast.LENGTH_SHORT).show();
                    } else {
                        saveUser();
                        Toast.makeText(Signup.this, "Bạn đã đăng kí thành công.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Signup.this, Login.class);
                        startActivity(intent);
                    }
                }
            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this,Login.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
                auth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if(task.isSuccessful())
                        {
                            // làm tiếp
                            FirebaseUser user = auth.getCurrentUser();
                            Userss users1 = new Userss("","","");
                            assert user  != null;
                            users1.setUserId(user.getUid());
                            users1.setUserName(user.getDisplayName());
                            users1.setEmail(user.getEmail());
                            database.getReference().child("Userss").child(user.getUid()).setValue(users1);
                            db = openOrCreateDatabase(Login.DATABASE_NAME,MODE_PRIVATE,null);
                            Cursor c = db.rawQuery("Select * from USER where TenDN = ?" , new String[]{users1.getEmail()+""});
                            c.moveToFirst();
                            if(c.getCount() < 1)
                            {
                                ContentValues values = new ContentValues();
                                values.put("HovaTen",users1.getUserName());
                                values.put("TenDN",users1.getEmail());
                                values.put("MatKhau","####");
                                values.put("GioiTinh","Nam");
                                values.put("CanNang",0.0);
                                values.put("ChieuCao",0.0);
                                values.put("BMI",0.0);
                                db.insert("USER",null,values);

                                Cursor tk = db.rawQuery("Select * from USER where TenDN = ?" , new String[]{users1.getEmail()+""});
                                tk.moveToFirst();
                                Login.ID_U = tk.getInt(0);
                                Login.BMI = 0.0;
                                Intent intent =new Intent(Signup.this,create_BMI.class);
                                startActivity(intent);
                            }else{
                                Intent intent = new Intent(Signup.this, MainActivity.class);

                                Login.ID_U =c.getInt(0);
                                Login.BMI = c.getDouble(7);
                                startActivity(intent);
                            }



                        }
                    }
                });

            } catch (Exception ex) {
                Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        }
    }
}