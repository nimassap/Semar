package com.example.semar5;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.semar5.ModelResponse.TampilDeluxe1;
import com.example.semar5.ModelResponse.TampilDeluxe2;
import com.example.semar5.ModelResponse.TampilDeluxe3;
import com.example.semar5.ModelResponse.TampilDeluxe4;
import com.example.semar5.ModelResponse.TampilDeluxe5a;
import com.example.semar5.ModelResponse.TampilDeluxe5b;
import com.example.semar5.ModelResponse.TampilGedungA;
import com.example.semar5.ModelResponse.TampilGedungB;
import com.example.semar5.ModelResponse.TampilGedungC;
import com.example.semar5.ModelResponse.TampilGedungD;
import com.example.semar5.ModelResponse.TampilGedungE;
import com.example.semar5.ModelResponse.TampilStandard;
import com.example.semar5.ModelResponse.TampilVipAc;
import com.example.semar5.ModelResponse.TampilVipNonAc;
import com.example.semar5.ModelResponse.User;
import com.example.semar5.Retrofit.Api;
import com.example.semar5.Retrofit.RetrofitClient;
import com.example.semar5.Retrofit.SharedPreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button buttpopdeluxe1, buttpopdeluxe2, buttpopdeluxe3, buttpopdeluxe4, buttroomdeluxe1, buttroomdeluxe2,
            buttroomdeluxe3, buttroomdeluxe4, buttroomdeluxe5a, buttroomdeluxe5b, buttroomstandard, buttroomvipac,
            buttroomvipnonac, buttgedunga, buttgedungb, buttgedungc, buttgedungd, buttgedunge;

    private TextView namaprofile;
    private SharedPreferenceManager sharedPreferenceManager;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //header logo
        ImageView imageView = findViewById(R.id.homesemar);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenttoolbar = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intenttoolbar);
            }
        });


        //menampilkan foto profile di side bar navigation
        navigationView = findViewById(R.id.navdrawer_view);
        View headerView = navigationView.getHeaderView(0);

        CircleImageView profileImage = headerView.findViewById(R.id.profile_image);

        SharedPreferenceManager sharedPreference = SharedPreferenceManager.getInstance(this);
        int userId = sharedPreference.getUser().getId_mahasiswa();
        String imageUriString = sharedPreference.getImageUri(userId);
        if (!imageUriString.isEmpty()) {
            Uri imageUri = Uri.parse(imageUriString);

            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_fotoprofile)
                    .error(R.drawable.logosemar);

            Glide.with(this)
                    .load(imageUri)
                    .apply(requestOptions)
                    .into(profileImage);
        } else {
            Log.e("Error", "Failed to load image");
        }



        //button details
        buttpopdeluxe1 = findViewById(R.id.popbutt_deluxe1);
        buttpopdeluxe1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentpopbuttdeluxe1 = new Intent(MainActivity.this, Deluxe1.class);
                startActivity(intentpopbuttdeluxe1);
            }
        });

        //menampilkan data dari database
        RetrofitClient retrofitClient1 = RetrofitClient.getInstance(this);
        Api api1 = retrofitClient1.getApi();

        Call<List<TampilDeluxe1>> call = api1.getDataDeluxe1();
        call.enqueue(new Callback<List<TampilDeluxe1>>() {
            @Override
            public void onResponse(Call<List<TampilDeluxe1>> call, Response<List<TampilDeluxe1>> response) {
                if (response.isSuccessful()) {
                    Log.d("API_CALL", "onResponse - Success");
                    List<TampilDeluxe1> dataItems = response.body();

                    if (dataItems != null && !dataItems.isEmpty()) {

                        TextView tvDeluxe1_1 = findViewById(R.id.tv_popdeluxe1_1);
                        TextView tvDeluxe1_2 = findViewById(R.id.tv_popdeluxe1_2);

                        for (TampilDeluxe1 item : dataItems) {
                            Log.d("API_CALL", "ID Deluxe1: " + item.getId());
                            Log.d("API_CALL", "Text Edit: " + item.getTextEdit());
                            if (item.getId() == 1) {
                                tvDeluxe1_1.setText(item.getTextEdit());
                            } else if (item.getId() == 2) {
                                tvDeluxe1_2.setText(item.getTextEdit());
                            }
                        }

                    } else {
                        Log.d("API_CALL", "Data is empty or null");
                        Toast.makeText(MainActivity.this, "Data tidak tersedia", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("API_CALL", "onResponse - Error: " + response.code());
                    Toast.makeText(MainActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TampilDeluxe1>> call, Throwable t) {
                Log.e("API_CALL", "onFailure: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });



        buttpopdeluxe2 = findViewById(R.id.popbutt_deluxe2);
        buttpopdeluxe2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentpopbuttdeluxe2 = new Intent(MainActivity.this, Deluxe2.class);
                startActivity(intentpopbuttdeluxe2);
            }
        });

        //menampilkan data dari database
        RetrofitClient retrofitClient2 = RetrofitClient.getInstance(this);
        Api api2 = retrofitClient2.getApi();

        Call<List<TampilDeluxe2>> call2 = api2.getDataDeluxe2();
        call2.enqueue(new Callback<List<TampilDeluxe2>>() {
            @Override
            public void onResponse(Call<List<TampilDeluxe2>> call, Response<List<TampilDeluxe2>> response) {
                if (response.isSuccessful()) {
                    Log.d("API_CALL", "onResponse - Success");
                    List<TampilDeluxe2> dataItems = response.body();

                    if (dataItems != null && !dataItems.isEmpty()) {

                        TextView tvDeluxe2_1 = findViewById(R.id.tv_popdeluxe2_1);
                        TextView tvDeluxe2_2 = findViewById(R.id.tv_popdeluxe2_2);

                        for (TampilDeluxe2 item : dataItems) {
                            Log.d("API_CALL", "ID Deluxe2: " + item.getId());
                            Log.d("API_CALL", "Text Edit: " + item.getTextEdit());
                            if (item.getId() == 1) {
                                tvDeluxe2_1.setText(item.getTextEdit());
                            } else if (item.getId() == 2) {
                                tvDeluxe2_2.setText(item.getTextEdit());
                            }
                        }

                    } else {
                        Log.d("API_CALL", "Data is empty or null");
                        Toast.makeText(MainActivity.this, "Data tidak tersedia", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("API_CALL", "onResponse - Error: " + response.code());
                    Toast.makeText(MainActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TampilDeluxe2>> call, Throwable t) {
                Log.e("API_CALL", "onFailure: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });



        buttpopdeluxe3 = findViewById(R.id.popbutt_deluxe3);
        buttpopdeluxe3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentpopbuttdeluxe3 = new Intent(MainActivity.this, Deluxe3.class);
                startActivity(intentpopbuttdeluxe3);
            }
        });

        //menampilkan data dari database
        RetrofitClient retrofitClient3 = RetrofitClient.getInstance(this);
        Api api3 = retrofitClient3.getApi();

        Call<List<TampilDeluxe3>> call3 = api3.getDataDeluxe3();
        call3.enqueue(new Callback<List<TampilDeluxe3>>() {
            @Override
            public void onResponse(Call<List<TampilDeluxe3>> call, Response<List<TampilDeluxe3>> response) {
                if (response.isSuccessful()) {
                    Log.d("API_CALL", "onResponse - Success");
                    List<TampilDeluxe3> dataItems = response.body();

                    if (dataItems != null && !dataItems.isEmpty()) {

                        TextView tvDeluxe3_1 = findViewById(R.id.tv_popdeluxe3_1);
                        TextView tvDeluxe3_2 = findViewById(R.id.tv_popdeluxe3_2);

                        for (TampilDeluxe3 item : dataItems) {
                            Log.d("API_CALL", "ID Deluxe3: " + item.getId());
                            Log.d("API_CALL", "Text Edit: " + item.getTextEdit());
                            if (item.getId() == 1) {
                                tvDeluxe3_1.setText(item.getTextEdit());
                            } else if (item.getId() == 2) {
                                tvDeluxe3_2.setText(item.getTextEdit());
                            }
                        }

                    } else {
                        Log.d("API_CALL", "Data is empty or null");
                        Toast.makeText(MainActivity.this, "Data tidak tersedia", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("API_CALL", "onResponse - Error: " + response.code());
                    Toast.makeText(MainActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TampilDeluxe3>> call, Throwable t) {
                Log.e("API_CALL", "onFailure: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });



        buttpopdeluxe4 = findViewById(R.id.popbutt_deluxe4);
        buttpopdeluxe4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentpopbuttdeluxe4 = new Intent(MainActivity.this, Deluxe4.class);
                startActivity(intentpopbuttdeluxe4);
            }
        });

        //menampilkan data dari database
        RetrofitClient retrofitClient4 = RetrofitClient.getInstance(this);
        Api api4 = retrofitClient4.getApi();

        Call<List<TampilDeluxe4>> call4 = api4.getDataDeluxe4();
        call4.enqueue(new Callback<List<TampilDeluxe4>>() {
            @Override
            public void onResponse(Call<List<TampilDeluxe4>> call, Response<List<TampilDeluxe4>> response) {
                if (response.isSuccessful()) {
                    Log.d("API_CALL", "onResponse - Success");
                    List<TampilDeluxe4> dataItems = response.body();

                    if (dataItems != null && !dataItems.isEmpty()) {

                        TextView tvDeluxe4_1 = findViewById(R.id.tv_popdeluxe4_1);
                        TextView tvDeluxe4_2 = findViewById(R.id.tv_popdeluxe4_2);

                        for (TampilDeluxe4 item : dataItems) {
                            Log.d("API_CALL", "ID Deluxe4: " + item.getId());
                            Log.d("API_CALL", "Text Edit: " + item.getTextEdit());
                            if (item.getId() == 1) {
                                tvDeluxe4_1.setText(item.getTextEdit());
                            } else if (item.getId() == 2) {
                                tvDeluxe4_2.setText(item.getTextEdit());
                            }
                        }

                    } else {
                        Log.d("API_CALL", "Data is empty or null");
                        Toast.makeText(MainActivity.this, "Data tidak tersedia", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("API_CALL", "onResponse - Error: " + response.code());
                    Toast.makeText(MainActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TampilDeluxe4>> call, Throwable t) {
                Log.e("API_CALL", "onFailure: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });



        buttroomdeluxe1 = findViewById(R.id.roombutt_deluxe1);
        buttroomdeluxe1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentroombuttdeluxe1 = new Intent(MainActivity.this, Deluxe1.class);
                startActivity(intentroombuttdeluxe1);
            }
        });

        //menampilkan data dari database
        RetrofitClient retrofitClient5 = RetrofitClient.getInstance(this);
        Api api5 = retrofitClient5.getApi();

        Call<List<TampilDeluxe1>> call5 = api5.getDataDeluxe1();
        call5.enqueue(new Callback<List<TampilDeluxe1>>() {
            @Override
            public void onResponse(Call<List<TampilDeluxe1>> call, Response<List<TampilDeluxe1>> response) {
                if (response.isSuccessful()) {
                    Log.d("API_CALL", "onResponse - Success");
                    List<TampilDeluxe1> dataItems = response.body();

                    if (dataItems != null && !dataItems.isEmpty()) {

                        TextView tvDeluxe1_1 = findViewById(R.id.tv_deluxe1_1);
                        TextView tvDeluxe1_2 = findViewById(R.id.tv_deluxe1_2);

                        for (TampilDeluxe1 item : dataItems) {
                            Log.d("API_CALL", "ID Deluxe1: " + item.getId());
                            Log.d("API_CALL", "Text Edit: " + item.getTextEdit());
                            if (item.getId() == 1) {
                                tvDeluxe1_1.setText(item.getTextEdit());
                            } else if (item.getId() == 2) {
                                tvDeluxe1_2.setText(item.getTextEdit());
                            }
                        }

                    } else {
                        Log.d("API_CALL", "Data is empty or null");
                        Toast.makeText(MainActivity.this, "Data tidak tersedia", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("API_CALL", "onResponse - Error: " + response.code());
                    Toast.makeText(MainActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TampilDeluxe1>> call, Throwable t) {
                Log.e("API_CALL", "onFailure: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });



        buttroomdeluxe2 = findViewById(R.id.roombutt_deluxe2);
        buttroomdeluxe2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentroombuttdeluxe2 = new Intent(MainActivity.this, Deluxe2.class);
                startActivity(intentroombuttdeluxe2);
            }
        });

        //menampilkan data dari database
        RetrofitClient retrofitClient6 = RetrofitClient.getInstance(this);
        Api api6 = retrofitClient6.getApi();

        Call<List<TampilDeluxe2>> call6 = api6.getDataDeluxe2();
        call6.enqueue(new Callback<List<TampilDeluxe2>>() {
            @Override
            public void onResponse(Call<List<TampilDeluxe2>> call, Response<List<TampilDeluxe2>> response) {
                if (response.isSuccessful()) {
                    Log.d("API_CALL", "onResponse - Success");
                    List<TampilDeluxe2> dataItems = response.body();

                    if (dataItems != null && !dataItems.isEmpty()) {

                        TextView tvDeluxe2_1 = findViewById(R.id.tv_deluxe2_1);
                        TextView tvDeluxe2_2 = findViewById(R.id.tv_deluxe2_2);

                        for (TampilDeluxe2 item : dataItems) {
                            Log.d("API_CALL", "ID Deluxe2: " + item.getId());
                            Log.d("API_CALL", "Text Edit: " + item.getTextEdit());
                            if (item.getId() == 1) {
                                tvDeluxe2_1.setText(item.getTextEdit());
                            } else if (item.getId() == 2) {
                                tvDeluxe2_2.setText(item.getTextEdit());
                            }
                        }

                    } else {
                        Log.d("API_CALL", "Data is empty or null");
                        Toast.makeText(MainActivity.this, "Data tidak tersedia", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("API_CALL", "onResponse - Error: " + response.code());
                    Toast.makeText(MainActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TampilDeluxe2>> call, Throwable t) {
                Log.e("API_CALL", "onFailure: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });



        buttroomdeluxe3 = findViewById(R.id.roombutt_deluxe3);
        buttroomdeluxe3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentroombuttdeluxe3 = new Intent(MainActivity.this, Deluxe3.class);
                startActivity(intentroombuttdeluxe3);
            }
        });

        //menampilkan data dari database
        RetrofitClient retrofitClient7 = RetrofitClient.getInstance(this);
        Api api7 = retrofitClient7.getApi();

        Call<List<TampilDeluxe3>> call7 = api7.getDataDeluxe3();
        call7.enqueue(new Callback<List<TampilDeluxe3>>() {
            @Override
            public void onResponse(Call<List<TampilDeluxe3>> call, Response<List<TampilDeluxe3>> response) {
                if (response.isSuccessful()) {
                    Log.d("API_CALL", "onResponse - Success");
                    List<TampilDeluxe3> dataItems = response.body();

                    if (dataItems != null && !dataItems.isEmpty()) {

                        TextView tvDeluxe3_1 = findViewById(R.id.tv_deluxe3_1);
                        TextView tvDeluxe3_2 = findViewById(R.id.tv_deluxe3_2);

                        for (TampilDeluxe3 item : dataItems) {
                            Log.d("API_CALL", "ID Deluxe3: " + item.getId());
                            Log.d("API_CALL", "Text Edit: " + item.getTextEdit());
                            if (item.getId() == 1) {
                                tvDeluxe3_1.setText(item.getTextEdit());
                            } else if (item.getId() == 2) {
                                tvDeluxe3_2.setText(item.getTextEdit());
                            }
                        }

                    } else {
                        Log.d("API_CALL", "Data is empty or null");
                        Toast.makeText(MainActivity.this, "Data tidak tersedia", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("API_CALL", "onResponse - Error: " + response.code());
                    Toast.makeText(MainActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TampilDeluxe3>> call, Throwable t) {
                Log.e("API_CALL", "onFailure: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });



        buttroomdeluxe4 = findViewById(R.id.roombutt_deluxe4);
        buttroomdeluxe4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentroombuttdeluxe4 = new Intent(MainActivity.this, Deluxe4.class);
                startActivity(intentroombuttdeluxe4);
            }
        });

        //menampilkan data dari database
        RetrofitClient retrofitClient8 = RetrofitClient.getInstance(this);
        Api api8 = retrofitClient8.getApi();

        Call<List<TampilDeluxe4>> call8 = api8.getDataDeluxe4();
        call8.enqueue(new Callback<List<TampilDeluxe4>>() {
            @Override
            public void onResponse(Call<List<TampilDeluxe4>> call, Response<List<TampilDeluxe4>> response) {
                if (response.isSuccessful()) {
                    Log.d("API_CALL", "onResponse - Success");
                    List<TampilDeluxe4> dataItems = response.body();

                    if (dataItems != null && !dataItems.isEmpty()) {

                        TextView tvDeluxe4_1 = findViewById(R.id.tv_deluxe4_1);
                        TextView tvDeluxe4_2 = findViewById(R.id.tv_deluxe4_2);

                        for (TampilDeluxe4 item : dataItems) {
                            Log.d("API_CALL", "ID Deluxe4: " + item.getId());
                            Log.d("API_CALL", "Text Edit: " + item.getTextEdit());
                            if (item.getId() == 1) {
                                tvDeluxe4_1.setText(item.getTextEdit());
                            } else if (item.getId() == 2) {
                                tvDeluxe4_2.setText(item.getTextEdit());
                            }
                        }

                    } else {
                        Log.d("API_CALL", "Data is empty or null");
                        Toast.makeText(MainActivity.this, "Data tidak tersedia", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("API_CALL", "onResponse - Error: " + response.code());
                    Toast.makeText(MainActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TampilDeluxe4>> call, Throwable t) {
                Log.e("API_CALL", "onFailure: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });



        buttroomdeluxe5a = findViewById(R.id.roombutt_deluxe5A);
        buttroomdeluxe5a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentroombuttdeluxe5a = new Intent(MainActivity.this, Deluxe5A.class);
                startActivity(intentroombuttdeluxe5a);
            }
        });

        //menampilkan data dari database
        RetrofitClient retrofitClient9 = RetrofitClient.getInstance(this);
        Api api9 = retrofitClient9.getApi();

        Call<List<TampilDeluxe5a>> call9 = api9.getDataDeluxe5a();
        call9.enqueue(new Callback<List<TampilDeluxe5a>>() {
            @Override
            public void onResponse(Call<List<TampilDeluxe5a>> call, Response<List<TampilDeluxe5a>> response) {
                if (response.isSuccessful()) {
                    Log.d("API_CALL", "onResponse - Success");
                    List<TampilDeluxe5a> dataItems = response.body();

                    if (dataItems != null && !dataItems.isEmpty()) {

                        TextView tvDeluxe5a_1 = findViewById(R.id.tv_deluxe5a_1);
                        TextView tvDeluxe5a_2 = findViewById(R.id.tv_deluxe5a_2);

                        for (TampilDeluxe5a item : dataItems) {
                            Log.d("API_CALL", "ID Deluxe5A: " + item.getId());
                            Log.d("API_CALL", "Text Edit: " + item.getTextEdit());
                            if (item.getId() == 1) {
                                tvDeluxe5a_1.setText(item.getTextEdit());
                            } else if (item.getId() == 2) {
                                tvDeluxe5a_2.setText(item.getTextEdit());
                            }
                        }

                    } else {
                        Log.d("API_CALL", "Data is empty or null");
                        Toast.makeText(MainActivity.this, "Data tidak tersedia", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("API_CALL", "onResponse - Error: " + response.code());
                    Toast.makeText(MainActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TampilDeluxe5a>> call, Throwable t) {
                Log.e("API_CALL", "onFailure: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });



        buttroomdeluxe5b = findViewById(R.id.roombutt_deluxe5B);
        buttroomdeluxe5b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentroombuttdeluxe5b = new Intent(MainActivity.this, Deluxe5B.class);
                startActivity(intentroombuttdeluxe5b);
            }
        });

        //menampilkan data dari database
        RetrofitClient retrofitClient10 = RetrofitClient.getInstance(this);
        Api api10 = retrofitClient10.getApi();

        Call<List<TampilDeluxe5b>> call10 = api10.getDataDeluxe5b();
        call10.enqueue(new Callback<List<TampilDeluxe5b>>() {
            @Override
            public void onResponse(Call<List<TampilDeluxe5b>> call, Response<List<TampilDeluxe5b>> response) {
                if (response.isSuccessful()) {
                    Log.d("API_CALL", "onResponse - Success");
                    List<TampilDeluxe5b> dataItems = response.body();

                    if (dataItems != null && !dataItems.isEmpty()) {

                        TextView tvDeluxe5b_1 = findViewById(R.id.tv_deluxe5b_1);
                        TextView tvDeluxe5b_2 = findViewById(R.id.tv_deluxe5b_2);

                        for (TampilDeluxe5b item : dataItems) {
                            Log.d("API_CALL", "ID Deluxe5B: " + item.getId());
                            Log.d("API_CALL", "Text Edit: " + item.getTextEdit());
                            if (item.getId() == 1) {
                                tvDeluxe5b_1.setText(item.getTextEdit());
                            } else if (item.getId() == 2) {
                                tvDeluxe5b_2.setText(item.getTextEdit());
                            }
                        }

                    } else {
                        Log.d("API_CALL", "Data is empty or null");
                        Toast.makeText(MainActivity.this, "Data tidak tersedia", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("API_CALL", "onResponse - Error: " + response.code());
                    Toast.makeText(MainActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TampilDeluxe5b>> call, Throwable t) {
                Log.e("API_CALL", "onFailure: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });



        buttroomstandard = findViewById(R.id.roombutt_standard);
        buttroomstandard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentroombuttstandard = new Intent(MainActivity.this, Standard.class);
                startActivity(intentroombuttstandard);
            }
        });

        //menampilkan data dari database
        RetrofitClient retrofitClient11 = RetrofitClient.getInstance(this);
        Api api11 = retrofitClient11.getApi();

        Call<List<TampilStandard>> call11 = api11.getDataStandard();
        call11.enqueue(new Callback<List<TampilStandard>>() {
            @Override
            public void onResponse(Call<List<TampilStandard>> call, Response<List<TampilStandard>> response) {
                if (response.isSuccessful()) {
                    Log.d("API_CALL", "onResponse - Success");
                    List<TampilStandard> dataItems = response.body();

                    if (dataItems != null && !dataItems.isEmpty()) {

                        TextView tvStandard_1 = findViewById(R.id.tv_standard_1);
                        TextView tvStandard_2 = findViewById(R.id.tv_standard_2);

                        for (TampilStandard item : dataItems) {
                            Log.d("API_CALL", "ID Standard: " + item.getId());
                            Log.d("API_CALL", "Text Edit: " + item.getTextEdit());
                            if (item.getId() == 1) {
                                tvStandard_1.setText(item.getTextEdit());
                            } else if (item.getId() == 2) {
                                tvStandard_2.setText(item.getTextEdit());
                            }
                        }

                    } else {
                        Log.d("API_CALL", "Data is empty or null");
                        Toast.makeText(MainActivity.this, "Data tidak tersedia", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("API_CALL", "onResponse - Error: " + response.code());
                    Toast.makeText(MainActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TampilStandard>> call, Throwable t) {
                Log.e("API_CALL", "onFailure: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });



        buttroomvipac = findViewById(R.id.roombutt_vipac);
        buttroomvipac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentroombuttvipac = new Intent(MainActivity.this, VipAc.class);
                startActivity(intentroombuttvipac);
            }
        });

        //menampilkan data dari database
        RetrofitClient retrofitClient12 = RetrofitClient.getInstance(this);
        Api api12 = retrofitClient12.getApi();

        Call<List<TampilVipAc>> call12 = api12.getDataVipAc();
        call12.enqueue(new Callback<List<TampilVipAc>>() {
            @Override
            public void onResponse(Call<List<TampilVipAc>> call, Response<List<TampilVipAc>> response) {
                if (response.isSuccessful()) {
                    Log.d("API_CALL", "onResponse - Success");
                    List<TampilVipAc> dataItems = response.body();

                    if (dataItems != null && !dataItems.isEmpty()) {

                        TextView tvVipAC_1 = findViewById(R.id.tv_vipac_1);
                        TextView tvVipAC_2 = findViewById(R.id.tv_vipac_2);

                        for (TampilVipAc item : dataItems) {
                            Log.d("API_CALL", "ID VipAC: " + item.getId());
                            Log.d("API_CALL", "Text Edit: " + item.getTextEdit());
                            if (item.getId() == 1) {
                                tvVipAC_1.setText(item.getTextEdit());
                            } else if (item.getId() == 2) {
                                tvVipAC_2.setText(item.getTextEdit());
                            }
                        }

                    } else {
                        Log.d("API_CALL", "Data is empty or null");
                        Toast.makeText(MainActivity.this, "Data tidak tersedia", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("API_CALL", "onResponse - Error: " + response.code());
                    Toast.makeText(MainActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TampilVipAc>> call, Throwable t) {
                Log.e("API_CALL", "onFailure: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });



        buttroomvipnonac = findViewById(R.id.roombutt_vipnonac);
        buttroomvipnonac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentroombuttvipnonac = new Intent(MainActivity.this, VipNonAc.class);
                startActivity(intentroombuttvipnonac);
            }
        });

        //menampilkan data dari database
        RetrofitClient retrofitClient13 = RetrofitClient.getInstance(this);
        Api api13 = retrofitClient13.getApi();

        Call<List<TampilVipNonAc>> call13 = api13.getDataVipNonAc();
        call13.enqueue(new Callback<List<TampilVipNonAc>>() {
            @Override
            public void onResponse(Call<List<TampilVipNonAc>> call, Response<List<TampilVipNonAc>> response) {
                if (response.isSuccessful()) {
                    Log.d("API_CALL", "onResponse - Success");
                    List<TampilVipNonAc> dataItems = response.body();

                    if (dataItems != null && !dataItems.isEmpty()) {

                        TextView tvVipNonAc_1 = findViewById(R.id.tv_vipnonac_1);
                        TextView tvVipNonAc_2 = findViewById(R.id.tv_vipnonac_2);

                        for (TampilVipNonAc item : dataItems) {
                            Log.d("API_CALL", "ID VipNonAc: " + item.getId());
                            Log.d("API_CALL", "Text Edit: " + item.getTextEdit());
                            if (item.getId() == 1) {
                                tvVipNonAc_1.setText(item.getTextEdit());
                            } else if (item.getId() == 2) {
                                tvVipNonAc_2.setText(item.getTextEdit());
                            }
                        }

                    } else {
                        Log.d("API_CALL", "Data is empty or null");
                        Toast.makeText(MainActivity.this, "Data tidak tersedia", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("API_CALL", "onResponse - Error: " + response.code());
                    Toast.makeText(MainActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TampilVipNonAc>> call, Throwable t) {
                Log.e("API_CALL", "onFailure: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });




        buttgedunga = findViewById(R.id.butt_gedungA);
        buttgedunga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentbuttgedunga = new Intent(MainActivity.this, GedungA.class);
                startActivity(intentbuttgedunga);
            }
        });

        //menampilkan data dari database
        RetrofitClient retrofitClient14 = RetrofitClient.getInstance(this);
        Api api14 = retrofitClient14.getApi();

        Call<List<TampilGedungA>> call14 = api14.getDataGedungA();
        call14.enqueue(new Callback<List<TampilGedungA>>() {
            @Override
            public void onResponse(Call<List<TampilGedungA>> call, Response<List<TampilGedungA>> response) {
                if (response.isSuccessful()) {
                    Log.d("API_CALL", "onResponse - Success");
                    List<TampilGedungA> dataItems = response.body();

                    if (dataItems != null && !dataItems.isEmpty()) {

                        TextView tvGedungA_1 = findViewById(R.id.tv_gedunga_1);
                        TextView tvGedungA_2 = findViewById(R.id.tv_gedunga_2);

                        for (TampilGedungA item : dataItems) {
                            Log.d("API_CALL", "ID GedungA: " + item.getId());
                            Log.d("API_CALL", "Text Edit: " + item.getTextEdit());
                            if (item.getId() == 1) {
                                tvGedungA_1.setText(item.getTextEdit());
                            } else if (item.getId() == 2) {
                                tvGedungA_2.setText(item.getTextEdit());
                            }
                        }

                    } else {
                        Log.d("API_CALL", "Data is empty or null");
                        Toast.makeText(MainActivity.this, "Data tidak tersedia", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("API_CALL", "onResponse - Error: " + response.code());
                    Toast.makeText(MainActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TampilGedungA>> call, Throwable t) {
                Log.e("API_CALL", "onFailure: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });



        buttgedungb = findViewById(R.id.butt_gedungB);
        buttgedungb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentbuttgedungb = new Intent(MainActivity.this, GedungB.class);
                startActivity(intentbuttgedungb);
            }
        });

        //menampilkan data dari database
        RetrofitClient retrofitClient15 = RetrofitClient.getInstance(this);
        Api api15 = retrofitClient15.getApi();

        Call<List<TampilGedungB>> call15 = api15.getDataGedungB();
        call15.enqueue(new Callback<List<TampilGedungB>>() {
            @Override
            public void onResponse(Call<List<TampilGedungB>> call, Response<List<TampilGedungB>> response) {
                if (response.isSuccessful()) {
                    Log.d("API_CALL", "onResponse - Success");
                    List<TampilGedungB> dataItems = response.body();

                    if (dataItems != null && !dataItems.isEmpty()) {

                        TextView tvGedungB_1 = findViewById(R.id.tv_gedungb_1);
                        TextView tvGedungB_2 = findViewById(R.id.tv_gedungb_2);

                        for (TampilGedungB item : dataItems) {
                            Log.d("API_CALL", "ID GedungB: " + item.getId());
                            Log.d("API_CALL", "Text Edit: " + item.getTextEdit());
                            if (item.getId() == 1) {
                                tvGedungB_1.setText(item.getTextEdit());
                            } else if (item.getId() == 2) {
                                tvGedungB_2.setText(item.getTextEdit());
                            }
                        }

                    } else {
                        Log.d("API_CALL", "Data is empty or null");
                        Toast.makeText(MainActivity.this, "Data tidak tersedia", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("API_CALL", "onResponse - Error: " + response.code());
                    Toast.makeText(MainActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TampilGedungB>> call, Throwable t) {
                Log.e("API_CALL", "onFailure: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });



        buttgedungc = findViewById(R.id.butt_gedungC);
        buttgedungc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentbuttgedungc = new Intent(MainActivity.this, GedungC.class);
                startActivity(intentbuttgedungc);
            }
        });

        //menampilkan data dari database
        RetrofitClient retrofitClient16 = RetrofitClient.getInstance(this);
        Api api16 = retrofitClient16.getApi();

        Call<List<TampilGedungC>> call16 = api16.getDataGedungC();
        call16.enqueue(new Callback<List<TampilGedungC>>() {
            @Override
            public void onResponse(Call<List<TampilGedungC>> call, Response<List<TampilGedungC>> response) {
                if (response.isSuccessful()) {
                    Log.d("API_CALL", "onResponse - Success");
                    List<TampilGedungC> dataItems = response.body();

                    if (dataItems != null && !dataItems.isEmpty()) {

                        TextView tvGedungC_1 = findViewById(R.id.tv_gedungc_1);
                        TextView tvGedungC_2 = findViewById(R.id.tv_gedungc_2);

                        for (TampilGedungC item : dataItems) {
                            Log.d("API_CALL", "ID GedungC: " + item.getId());
                            Log.d("API_CALL", "Text Edit: " + item.getTextEdit());
                            if (item.getId() == 1) {
                                tvGedungC_1.setText(item.getTextEdit());
                            } else if (item.getId() == 2) {
                                tvGedungC_2.setText(item.getTextEdit());
                            }
                        }

                    } else {
                        Log.d("API_CALL", "Data is empty or null");
                        Toast.makeText(MainActivity.this, "Data tidak tersedia", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("API_CALL", "onResponse - Error: " + response.code());
                    Toast.makeText(MainActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TampilGedungC>> call, Throwable t) {
                Log.e("API_CALL", "onFailure: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });



        buttgedungd = findViewById(R.id.butt_gedungD);
        buttgedungd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentbuttgedungd = new Intent(MainActivity.this, GedungD.class);
                startActivity(intentbuttgedungd);
            }
        });

        //menampilkan data dari database
        RetrofitClient retrofitClient17 = RetrofitClient.getInstance(this);
        Api api17 = retrofitClient17.getApi();

        Call<List<TampilGedungD>> call17 = api17.getDataGedungD();
        call17.enqueue(new Callback<List<TampilGedungD>>() {
            @Override
            public void onResponse(Call<List<TampilGedungD>> call, Response<List<TampilGedungD>> response) {
                if (response.isSuccessful()) {
                    Log.d("API_CALL", "onResponse - Success");
                    List<TampilGedungD> dataItems = response.body();

                    if (dataItems != null && !dataItems.isEmpty()) {

                        TextView tvGedungD_1 = findViewById(R.id.tv_gedungd_1);
                        TextView tvGedungD_2 = findViewById(R.id.tv_gedungd_2);

                        for (TampilGedungD item : dataItems) {
                            Log.d("API_CALL", "ID GedungD: " + item.getId());
                            Log.d("API_CALL", "Text Edit: " + item.getTextEdit());
                            if (item.getId() == 1) {
                                tvGedungD_1.setText(item.getTextEdit());
                            } else if (item.getId() == 2) {
                                tvGedungD_2.setText(item.getTextEdit());
                            }
                        }

                    } else {
                        Log.d("API_CALL", "Data is empty or null");
                        Toast.makeText(MainActivity.this, "Data tidak tersedia", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("API_CALL", "onResponse - Error: " + response.code());
                    Toast.makeText(MainActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TampilGedungD>> call, Throwable t) {
                Log.e("API_CALL", "onFailure: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });



        buttgedunge = findViewById(R.id.butt_gedungE);
        buttgedunge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentbuttgedunge = new Intent(MainActivity.this, GedungE.class);
                startActivity(intentbuttgedunge);
            }
        });

        //menampilkan data dari database
        RetrofitClient retrofitClient18 = RetrofitClient.getInstance(this);
        Api api18 = retrofitClient18.getApi();

        Call<List<TampilGedungE>> call18 = api18.getDataGedungE();
        call18.enqueue(new Callback<List<TampilGedungE>>() {
            @Override
            public void onResponse(Call<List<TampilGedungE>> call, Response<List<TampilGedungE>> response) {
                if (response.isSuccessful()) {
                    Log.d("API_CALL", "onResponse - Success");
                    List<TampilGedungE> dataItems = response.body();

                    if (dataItems != null && !dataItems.isEmpty()) {

                        TextView tvGedungE_1 = findViewById(R.id.tv_gedunge_1);
                        TextView tvGedungE_2 = findViewById(R.id.tv_gedunge_2);

                        for (TampilGedungE item : dataItems) {
                            Log.d("API_CALL", "ID GedungE: " + item.getId());
                            Log.d("API_CALL", "Text Edit: " + item.getTextEdit());
                            if (item.getId() == 1) {
                                tvGedungE_1.setText(item.getTextEdit());
                            } else if (item.getId() == 2) {
                                tvGedungE_2.setText(item.getTextEdit());
                            }
                        }

                    } else {
                        Log.d("API_CALL", "Data is empty or null");
                        Toast.makeText(MainActivity.this, "Data tidak tersedia", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("API_CALL", "onResponse - Error: " + response.code());
                    Toast.makeText(MainActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TampilGedungE>> call, Throwable t) {
                Log.e("API_CALL", "onFailure: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });



        //layout
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navdrawer_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);



        //hide or show item
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_logout).setVisible(false);
        menu.findItem(R.id.nav_ubahpass).setVisible(false);
        menu.findItem(R.id.nav_data_mahasiswa).setVisible(false);
        menu.findItem(R.id.nav_daftar_kamar).setVisible(false);



        //side bar navigation
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navdrawer_open,R.string.navdrawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        //ganti setiap activity
        navigationView.setCheckedItem(R.id.nav_home);



        //bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.bottom_home) {
                    if (sharedPreferenceManager.isLoggedIn()) {
                        int role = sharedPreferenceManager.getUser().getId_role();
                        if (role == 1) {
                            startActivity(new Intent(getApplicationContext(), NotifikasiAdminActivity.class));
                            overridePendingTransition(R.anim.slide_right_effect, R.anim.slide_left_effect);
                            return true;
                        } else if (role == 2) {
                            startActivity(new Intent(getApplicationContext(), NotifikasiActivity.class));
                            overridePendingTransition(R.anim.slide_right_effect, R.anim.slide_left_effect);
                            return true;
                        }
                    } else {
                        startActivity(new Intent(getApplicationContext(), NotifikasiActivity.class));
                        overridePendingTransition(R.anim.slide_right_effect, R.anim.slide_left_effect);
                        return true;
                    }
                } else if (itemId == R.id.bottom_notif) {
                    startActivity(new Intent(getApplicationContext(), NotifikasiActivity.class));
                    overridePendingTransition(R.anim.slide_right_effect, R.anim.slide_left_effect);
                    return true;
                } else if (itemId == R.id.bottom_history) {
                    startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                    overridePendingTransition(R.anim.slide_right_effect, R.anim.slide_left_effect);
                    return true;
                } else if (itemId == R.id.bottom_profile) {
                    if (sharedPreferenceManager.isLoggedIn()) {
                        int role = sharedPreferenceManager.getUser().getId_role();
                        if (role == 1) {
                            startActivity(new Intent(getApplicationContext(), ProfileActivityAdmin.class));
                            overridePendingTransition(R.anim.slide_right_effect, R.anim.slide_left_effect);
                            return true;
                        } else if (role == 2) {
                            startActivity(new Intent(getApplicationContext(), ProfileActivityAfter1.class));
                            overridePendingTransition(R.anim.slide_right_effect, R.anim.slide_left_effect);
                            return true;
                        }
                    } else {
                        startActivity(new Intent(getApplicationContext(), ProfileActivityBefore.class));
                        overridePendingTransition(R.anim.slide_right_effect, R.anim.slide_left_effect);
                        return true;
                    }
                }
                return false;
            }
        });

    }






    //side bar hide or show login logout
    @Override
    protected void onStart() {
        super.onStart();

        navigationView = findViewById(R.id.navdrawer_view);
        View headerView = navigationView.getHeaderView(0);

        namaprofile = headerView.findViewById(R.id.header_nameprofile);
        sharedPreferenceManager = SharedPreferenceManager.getInstance(this);
        user = sharedPreferenceManager.getUser();
        namaprofile.setText(user.getNama_mahasiswa());

        ImageView logo = headerView.findViewById(R.id.header_logo);
        CircleImageView fotoprofile = headerView.findViewById(R.id.profile_image);
        TextView namaprofile = headerView.findViewById(R.id.header_nameprofile);

        if (sharedPreferenceManager.isLoggedIn()) {
            int role = sharedPreferenceManager.getUser().getId_role();
            Menu menu = navigationView.getMenu();

            fotoprofile.setVisibility(View.VISIBLE);
            namaprofile.setVisibility(View.VISIBLE);

            if (role == 1) {
                menu.findItem(R.id.nav_logout).setVisible(true);
                menu.findItem(R.id.nav_ubahpass).setVisible(true);
                menu.findItem(R.id.nav_login).setVisible(false);
                menu.findItem(R.id.nav_daftar).setVisible(false);
                menu.findItem(R.id.nav_data_mahasiswa).setVisible(true);
                menu.findItem(R.id.nav_daftar_kamar).setVisible(true);
                logo.setVisibility(View.GONE);

            } else if (role == 2) {
                menu.findItem(R.id.nav_logout).setVisible(true);
                menu.findItem(R.id.nav_ubahpass).setVisible(true);
                menu.findItem(R.id.nav_login).setVisible(false);
                menu.findItem(R.id.nav_daftar).setVisible(false);
                menu.findItem(R.id.nav_data_mahasiswa).setVisible(false);
                menu.findItem(R.id.nav_daftar_kamar).setVisible(false);
                logo.setVisibility(View.GONE);

            }
        } else {
            logo.setVisibility(View.VISIBLE);
            fotoprofile.setVisibility(View.GONE);
            namaprofile.setVisibility(View.GONE);
        }
    }


    //keluar aplikasi
    private boolean isBackPressedOnce = false;
    private static final long BACK_PRESS_INTERVAL = 2000; // Waktu interval antara dua kali klik

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (isBackPressedOnce) {
                //super.onBackPressed();
                finishAffinity();
                return;
            }

            isBackPressedOnce = true;
            Toast.makeText(this, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isBackPressedOnce = false;
                }
            }, BACK_PRESS_INTERVAL);
        }
    }



    //side bar navigation
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
        } else if (itemId == R.id.nav_galeri) {
            Intent intent1 = new Intent(MainActivity.this, GaleriActivity.class);
            startActivity(intent1);
        } else if (itemId == R.id.nav_carabooking) {
            Intent intent2 = new Intent(MainActivity.this, CaraDaftarActivity.class);
            startActivity(intent2);
        } else if (itemId == R.id.nav_snk) {
            Intent intent3 = new Intent(MainActivity.this, SnKActivity.class);
            startActivity(intent3);
        } else if (itemId == R.id.nav_login) {
            Intent intent4 = new Intent(MainActivity.this, Login.class);
            startActivity(intent4);
        } else if (itemId == R.id.nav_daftar) {
            Intent intent5 = new Intent(MainActivity.this, Daftar.class);
            startActivity(intent5);
        } else if (itemId == R.id.nav_helpdesk) {
            Intent intent6 = new Intent(MainActivity.this, Helpdesk.class);
            startActivity(intent6);
        } else if (itemId == R.id.nav_about) {
            Intent intent7 = new Intent(MainActivity.this, About.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_data_mahasiswa) {
            Intent intent7 = new Intent(MainActivity.this, DataMahasiswa.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_ubahpass) {
            Intent intent7 = new Intent(MainActivity.this, UbahPassword.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_daftar_kamar) {
            Intent intent8 = new Intent(MainActivity.this, DaftarKamar.class);
            startActivity(intent8);
        } else if (itemId == R.id.nav_logout) {
            logoutUser();
            return true;
        }


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    //logout
    private void logoutUser() {
        sharedPreferenceManager.logout();

        Toast.makeText(this, "Logout Berhasil", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        finishAffinity();
    }
}