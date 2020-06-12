package com.example.volkankoroglu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    public static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        Boolean savedChecked = sharedPref.getBoolean("isChecked",false);

        if (savedChecked){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this,RecycleView.class);
                    startActivity(intent);
                    finish();
                }
            },SPLASH_TIME_OUT);
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this,LoginRegister.class);
                    startActivity(intent);
                    finish();
                }
            },SPLASH_TIME_OUT);
        }




    }
}