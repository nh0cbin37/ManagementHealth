package com.example.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    Button login;
    EditText userName;
    EditText Password;
    TextView signUp;
    ArrayList<User> listUser = new ArrayList<User>();
    SQLiteDatabase db;
    static Double BMI;
    static int ID_U;
    ImageButton imggoogle;
    private GoogleSignInClient client;
     FirebaseAuth auth;
    FirebaseDatabase database;

    public static final String DATABASE_NAME = "PMChamSocSucKhoe.db";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = (EditText) findViewById(R.id.editusername1);
        Password = (EditText) findViewById(R.id.editpassword1);
        login = (Button) findViewById(R.id.btnloginsuubmit);
        signUp = (TextView)findViewById(R.id.signup);
        imggoogle = (ImageButton)findViewById(R.id.googleLogin);
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
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,Signup.class);
                startActivity(intent);
                return;
            }
        });
        signUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    signUp.setTextColor(Color.parseColor("#ff0000"));
                }
                return false;
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    db = openOrCreateDatabase(Login.DATABASE_NAME, MODE_PRIVATE, null);
                    String enteredUser = userName.getText().toString();
                    String enteredPass = Password.getText().toString();
                    Cursor c = db.rawQuery("Select * from USER WHERE TenDN = ? and MatKhau = ?", new String[]{enteredUser, enteredPass}, null);
                    c.moveToFirst();

                    while (!c.isAfterLast()) {
                        listUser.add(new User(c.getInt(0), c.getString(1) + "", c.getString(2) + "", c.getString(3) + "", c.getString(4) + "", c.getDouble(5), c.getDouble(6), c.getDouble(7)));
                        c.moveToNext();

                    }
//

                    if (listUser.size() == 1) {

                        if (listUser.get(0).getBMI()!= 0.0) {
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            intent.putExtra("ID_user", listUser.get(0).getId());
                            intent.putExtra("BMI", listUser.get(0).getBMI());
                            ID_U = listUser.get(0).getId();
                            BMI = listUser.get(0).getBMI();
                            startActivity(intent);
                            return;
                        } else {
                            Intent intent = new Intent(Login.this, create_BMI.class);

                            ID_U = listUser.get(0).getId();
                            BMI = 0.0;
                            startActivity(intent);
                            return;
                        }


                    }
                    Toast.makeText(Login.this, "Bạn đã nhập sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }catch (Exception ex)
                {
                    Toast.makeText(Login.this, "Lỗi", Toast.LENGTH_SHORT).show();
                }

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
                            db = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
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
                                BMI = 0.0;
                                Intent intent =new Intent(Login.this,create_BMI.class);
                                startActivity(intent);
                            }else{
                                Intent intent = new Intent(Login.this, MainActivity.class);

                                ID_U =c.getInt(0);
                                BMI = c.getDouble(7);
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