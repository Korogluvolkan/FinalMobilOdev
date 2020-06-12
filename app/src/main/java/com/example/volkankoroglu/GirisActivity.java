package com.example.volkankoroglu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GirisActivity extends AppCompatActivity {
    EditText accountName, password;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        accountName = findViewById(R.id.kullanici);
        password = findViewById(R.id.sifre);
        button = findViewById(R.id.giris);
    }

    public void giris(View v) {
        String a = accountName.getText().toString();
        String b = password.getText().toString();
        if (b.equals("Admin") && a.equals("Admin")) {
            Intent i = new Intent(GirisActivity.this, RecycleView.class);
            startActivity(i);
            finish();

        } else {
            Toast.makeText(this, "Yanlış şifre veya kullanıcı adı", Toast.LENGTH_SHORT).show();
        }
    }
}
