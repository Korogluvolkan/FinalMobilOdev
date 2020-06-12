package com.example.volkankoroglu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.volkankoroglu.api.ApiClass;
import com.example.volkankoroglu.models.DataList;
import com.example.volkankoroglu.models.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class RecycleView extends AppCompatActivity {

    private static final String TAG = RecycleView.class.getSimpleName();

    private Button btn;
    private RecyclerView rv;
    private AppCompatEditText veri;
    private TextView bas, uTv;
    private RecyclerView.Adapter adapRv;
    private RecyclerView.LayoutManager layoutManager;


    ApiClass apiClass = new ApiClass();


    DataList dataList = (DataList) new DataList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);
        calistir();

        getList();


    }

    private void getList() {
        /*JSONObject jo = new JSONObject();
        jo.put("collection", "complaintTypes");
        jo.put("reportType", "comment");
        isBlog = false;
        */
        apiClass.getDataList(dataHnd);


    }

    private void calistir() {
        veri = findViewById(R.id.ilkEt);
        bas = findViewById(R.id.baslikTv);
        uTv = findViewById(R.id.urlTv);
        btn = findViewById(R.id.btn);
        rv = findViewById(R.id.recyclerView);
        rv.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);





        basınca();
    }

    private void basınca() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GithubSearchQuery();
            }
        });
    }

    public class adaptor extends RecyclerView.Adapter<adaptor.ViewHolder> {
        private List<User> values;

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView img;
            TextView fullName;
            TextView email;
            View layout;

            ViewHolder(View v) {
                super(v);
                layout = v;
                img = v.findViewById(R.id.resim);
                fullName = v.findViewById(R.id.FullName);
                email = v.findViewById(R.id.Email);
            }
        }

        adaptor(List<User> myDataset) {
            values = myDataset;
        }

        @Override
        public adaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.rvsatir_layout, parent, false);
            ViewHolder tutucu = new ViewHolder(v);
            return tutucu;
        }

        @Override
        public void onBindViewHolder(ViewHolder connect, final int position) {
            final User user = values.get(position);
            connect.fullName.setText(user.getFirstName() + " " + user.getLastName());
            connect.email.setText("Email : " + user.getEmail());

            Glide
                    .with(RecycleView.this)
                    .load(user.getAvatar())
                    .centerCrop()
                    .circleCrop()
                    .into(connect.img);
            connect.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });

            connect.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toDetail = new Intent(getApplicationContext(), DetaySayfasi.class);

                    toDetail.putExtra("FullName", user.getFirstName() + " " + user.getLastName());
                    toDetail.putExtra("Image", user.getAvatar());
                    toDetail.putExtra("Email", "Email : " + user.getEmail());
                    toDetail.putExtra("UserId", user.getId());
                    startActivity(toDetail);
                }
            });
            /*
            connect..setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toDetail = new Intent(getApplicationContext(), DetaySayfasi.class);
                    toDetail.putExtra("HEADER", name);
                    toDetail.putExtra("DESCRIPTION", description);
                    startActivity(toDetail);
                }
            });
            */
        }

        @Override
        public int getItemCount() {
            return values.size();
        }

    }

    @SuppressLint("SetTextI18n")
    private void GithubSearchQuery() {
        String githubQuery = veri.getText().toString();
        URL githubSearchUrl = VeriClass.buildUrl(githubQuery);
        uTv.setText("URL: " + githubSearchUrl.toString());

        String githubSearchResult = null;

        try {
            githubSearchResult = VeriClass.getResponseFromHttpUrl(githubSearchUrl);
            bas.setText(githubSearchResult);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Hata! \n\n" + e.toString(), Toast.LENGTH_LONG).show();
        }
    }




/*
 runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.getAdapter().notifyDataSetChanged();
                        }
                    });
 */


    Handler dataHnd = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 505) {
                Log.i(TAG, "Error -> " + msg.obj);
                return;
            }
            dataList = (DataList) msg.obj;
            Log.i(TAG, dataList.getUsers().size() + "");
           //rv.getAdapter().notifyDataSetChanged();
            adapRv = new adaptor(dataList.getUsers());
            rv.setAdapter(adapRv);
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.detail, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_new) {
            //Ekleme Sayfası
            Intent toSettingsActivity = new Intent(this, VeriEkle.class);
            startActivity(toSettingsActivity);
//            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }



}
