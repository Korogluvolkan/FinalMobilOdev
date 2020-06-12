package com.example.volkankoroglu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.volkankoroglu.api.ApiClass;
import com.example.volkankoroglu.models.DataList;
import com.example.volkankoroglu.models.User;

public class DetaySayfasi extends AppCompatActivity {
    private static final String TAG = DetaySayfasi.class.getSimpleName();

    TextView api_email, api_fullName;
    TextView normal_email, normal_full_name;
    ImageView api_image,normal_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detay_sayfasi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        api_email = findViewById(R.id.api_email);
        api_fullName = findViewById(R.id.api_fullName);
        api_image = findViewById(R.id.api_image);

        normal_email = findViewById(R.id.normal_email);
        normal_full_name = findViewById(R.id.normal_fullName);
        normal_image = findViewById(R.id.normal_image);

        Intent getData = getIntent();

        Bundle extras = getData.getExtras();
        if (extras != null)
        {

            int userId = getData.getIntExtra("UserId",0);
            String fullName = getData.getStringExtra("FullName");
            String image = getData.getStringExtra("Image");
            String email = getData.getStringExtra("Email");
            normal_email.setText(email);
            normal_full_name.setText(fullName);

            Glide
                    .with(DetaySayfasi.this)
                    .load(image)
                    .centerCrop()
                    .circleCrop()
                    .into(normal_image);
            ApiClass apiClass = new ApiClass();
            apiClass.getUser(dataHnd,userId);
        }else{
            finish();
        }







    }




    Handler dataHnd = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 505) {
                Log.i(TAG, "Error -> " + msg.obj);
                return;
            }

            User user = (User) msg.obj;

            api_email.setText("Email : " + user.getEmail());
            api_fullName.setText(user.getFirstName() + " " + user.getLastName());

            Glide
                    .with(DetaySayfasi.this)
                    .load(user.getAvatar())
                    .centerCrop()
                    .circleCrop()
                    .into(api_image);
        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
