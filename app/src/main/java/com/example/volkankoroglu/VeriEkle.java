package com.example.volkankoroglu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.volkankoroglu.api.ApiClass;
import com.example.volkankoroglu.models.ApiPostParam;
import com.example.volkankoroglu.models.DataList;

import java.util.ArrayList;
import java.util.List;

public class VeriEkle extends AppCompatActivity {
    private static final String TAG = VeriEkle.class.getSimpleName();

    private ApiClass apiClass = new ApiClass();

    ConstraintLayout AddBtn;
    EditText FullName,Job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veri_ekle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        AddBtn = findViewById(R.id.AddBtn);
        FullName = findViewById(R.id.FullName);
        Job = findViewById(R.id.Job);


        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                List<ApiPostParam> params = new ArrayList<ApiPostParam>();


                ApiPostParam name = new ApiPostParam("name",FullName.getText().toString());
                ApiPostParam job = new ApiPostParam("job",Job.getText().toString());
                params.add(name);
                params.add(job);

                apiClass.newUser(dataHnd,params);

            }
        });





    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }



    Handler dataHnd = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 505) {
                Log.i(TAG, "Error -> " + msg.obj);
                return;
            }

            AlertDialog alertDialog = new AlertDialog.Builder(VeriEkle.this)
                    .setTitle("Bilgi")
                    .setMessage("Veri Ekleme Başarılı.")
                    .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();

                        }
                    })
                    .show();

        }
    };
}