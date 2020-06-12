package com.example.volkankoroglu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.volkankoroglu.api.ApiClass;
import com.example.volkankoroglu.models.ApiPostParam;

import java.util.ArrayList;
import java.util.List;

public class LoginRegister extends AppCompatActivity {
    private static final String TAG = LoginRegister.class.getSimpleName();

    private Boolean modeLogin = true;

    TextView AltLabel,UstLabel,SubmitLabel;
    EditText email,password;
    ConstraintLayout SubmitBtn;


    String registerEmail = "eve.holt@reqres.in";
    String registerPassword = "pistol";

    String loginEmail = "eve.holt@reqres.in";
    String loginPassword = "cityslicka";
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        getSupportActionBar().hide(); //hide the title bar

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        Boolean savedChecked = sharedPref.getBoolean("isChecked",false);

        if (savedChecked){
            Intent intent = new Intent(LoginRegister.this,RecycleView.class);
            startActivity(intent);
            finish();
        }


        AltLabel = findViewById(R.id.AltLabel);
        UstLabel = findViewById(R.id.UstLabel);
        SubmitLabel = findViewById(R.id.SubmitLabel);
        SubmitBtn = findViewById(R.id.SubmitBtn);
        password = findViewById(R.id.password);
        email = findViewById(R.id.FullName);

        email.setText(loginEmail);
        password.setText(loginPassword);
        UstLabel.setText("Giriş Yap");
        AltLabel.setText("Hesabın Yok mu ? Hemen Kayıt Ol.");

        modeLogin = true;

        AltLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (modeLogin){
                    email.setText(registerEmail);
                    password.setText(registerPassword);
                    UstLabel.setText("Kayıt Ol");
                    SubmitLabel.setText("Kayıt Ol");
                    AltLabel.setText("Hesabın Var mı ? Hemen Giriş Yap.");
                    modeLogin = false;
                }else{
                    email.setText(loginEmail);
                    password.setText(loginPassword);
                    UstLabel.setText("Giriş Yap");
                    SubmitLabel.setText("Giriş Yap");
                    AltLabel.setText("Hesabın Yok mu ? Hemen Kayıt Ol.");
                    modeLogin = true;
                }



            }
        });



        SubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (modeLogin){
                    Log.i(TAG,"Login -> "+modeLogin);
                    Log.i(TAG,"Login -> "+email.getText().toString() + " == "+loginEmail);
                    Log.i(TAG,"Login -> "+password.getText().toString() + " == "+loginPassword);

                    if (email.getText().toString().equals(loginEmail) &&  password.getText().toString().equals(loginPassword)){
                        //Giriş Başarılı
                        goLoginRegister(modeLogin);
                    }else{
                        //Hatalı Kullanıcı Adı veya Şifre
                        goError();
                    }


                }else{
                    Log.i(TAG,"Register -> "+!modeLogin);
                    Log.i(TAG,"Login -> "+email.getText().toString() + " == "+registerEmail);
                    Log.i(TAG,"Login -> "+password.getText().toString() + " == "+registerPassword);

                    if (email.getText().toString().equals(registerEmail) &&  password.getText().toString().equals(registerPassword)){
                        //Giriş Başarılı

                        goLoginRegister(modeLogin);

                    }else{
                        //Hatalı Kullanıcı Adı veya Şifre
                        goError();
                    }
                }

            }
        });





    }


    private void goLoginRegister(Boolean isLogin){
        List<ApiPostParam> params = new ArrayList<ApiPostParam>();


        ApiPostParam name = new ApiPostParam("email",email.getText().toString());
        ApiPostParam job = new ApiPostParam("password",password.getText().toString());
        params.add(name);
        params.add(job);

        ApiClass apiClass = new ApiClass();
        apiClass.loginOrRegister(dataHnd,params,isLogin);
    }



    Handler dataHnd = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 505) {
                Log.i(TAG, "Error -> " + msg.obj);
                goError();
                return;
            }

            if (!msg.obj.equals("false")){
                goMain();
            }else{
                goError();
            }



        }
    };

    public void goMain(){

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("isChecked",true); //boolean değer ekleniyor
        editor.commit();

        Intent intent = new Intent(LoginRegister.this,RecycleView.class);
        startActivity(intent);
        finish();

    }

    public void goError(){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Uyarı")
                .setMessage("Geçersiz Kullanıcı Adı veya Şifre")
                .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                    }
                })
                .show();
    }
}