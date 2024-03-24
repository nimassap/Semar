package com.example.semar5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.semar5.ModelResponse.TampilCaraDaftar;
import com.example.semar5.ModelResponse.TampilSnK;
import com.example.semar5.ModelResponse.UpdateCaraDaftar;
import com.example.semar5.ModelResponse.UpdateSnK;
import com.example.semar5.ModelResponse.User;
import com.example.semar5.Retrofit.Api;
import com.example.semar5.Retrofit.RetrofitClient;
import com.example.semar5.Retrofit.SharedPreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SnKEdit extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private TextView namaprofile;
    private SharedPreferenceManager sharedPreferenceManager;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snk_edit);

        //header logo
        ImageView imageView = findViewById(R.id.homesemar);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SnKEdit.this, MainActivity.class);
                startActivity(intent);
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



        //layout
        //sesuaikan activity
        drawerLayout = findViewById(R.id.drawer_layout_snk_edit);
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
        navigationView.setCheckedItem(R.id.nav_snk);



        //bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.bottom_home) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.anim.slide_right_effect, R.anim.slide_left_effect);
                    return true;
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

        //button save edit data
        Button saveEditCaraDaftar = findViewById(R.id.simpan_snk);
        saveEditCaraDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDataSnK();
            }
        });

        tampilDataSnK();

    }


    //menampilkan data dari database
    private void tampilDataSnK() {

        EditText tvSnK_1 = findViewById(R.id.snk1);
        EditText tvSnK_2 = findViewById(R.id.snk2);
        EditText tvSnK_3 = findViewById(R.id.snk3);
        EditText tvSnK_4 = findViewById(R.id.snk4);
        EditText tvSnK_5 = findViewById(R.id.snk5);
        EditText tvSnK_6 = findViewById(R.id.snk6);

        EditText tvSnK_7 = findViewById(R.id.snk7);
        EditText tvSnK_8 = findViewById(R.id.snk8);
        EditText tvSnK_9 = findViewById(R.id.snk9);
        EditText tvSnK_10 = findViewById(R.id.snk10);
        EditText tvSnK_11 = findViewById(R.id.snk11);
        EditText tvSnK_12 = findViewById(R.id.snk12);

        EditText tvSnK_13 = findViewById(R.id.snk13);
        EditText tvSnK_14 = findViewById(R.id.snk14);
        EditText tvSnK_15 = findViewById(R.id.snk15);
        EditText tvSnK_16 = findViewById(R.id.snk16);
        EditText tvSnK_17 = findViewById(R.id.snk17);
        EditText tvSnK_18 = findViewById(R.id.snk18);

        EditText tvSnK_19 = findViewById(R.id.snk19);
        EditText tvSnK_20 = findViewById(R.id.snk20);
        EditText tvSnK_21 = findViewById(R.id.snk21);
        EditText tvSnK_22 = findViewById(R.id.snk22);
        EditText tvSnK_23 = findViewById(R.id.snk23);
        EditText tvSnK_24 = findViewById(R.id.snk24);

        EditText tvSnK_25 = findViewById(R.id.snk25);
        EditText tvSnK_26 = findViewById(R.id.snk26);
        EditText tvSnK_27 = findViewById(R.id.snk27);
        EditText tvSnK_28 = findViewById(R.id.snk28);
        EditText tvSnK_29 = findViewById(R.id.snk29);
        EditText tvSnK_30 = findViewById(R.id.snk30);

        EditText tvSnK_31 = findViewById(R.id.snk31);
        EditText tvSnK_32 = findViewById(R.id.snk32);
        EditText tvSnK_33 = findViewById(R.id.snk33);
        EditText tvSnK_34 = findViewById(R.id.snk34);
        EditText tvSnK_35 = findViewById(R.id.snk35);
        EditText tvSnK_36 = findViewById(R.id.snk36);

        EditText tvSnK_37 = findViewById(R.id.snk37);
        EditText tvSnK_38 = findViewById(R.id.snk38);
        EditText tvSnK_39 = findViewById(R.id.snk39);

        RetrofitClient retrofitClient = RetrofitClient.getInstance(this);
        Api api = retrofitClient.getApi();

        Call<List<TampilSnK>> call = api.getDataSnK();
        call.enqueue(new Callback<List<TampilSnK>>() {
            @Override
            public void onResponse(Call<List<TampilSnK>> call, Response<List<TampilSnK>> response) {
                if (response.isSuccessful()) {
                    List<TampilSnK> dataItems = response.body();

                    if (dataItems != null && !dataItems.isEmpty()) {
                        // Populate EditText fields with fetched data
                        for (TampilSnK item : dataItems) {
                            switch (item.getId()) {
                                case 1:
                                    tvSnK_1.setText(item.getSyarat_ketentuan());
                                    break;
                                case 2:
                                    tvSnK_2.setText(item.getSyarat_ketentuan());
                                    break;
                                case 3:
                                    tvSnK_3.setText(item.getSyarat_ketentuan());
                                    break;
                                case 4:
                                    tvSnK_4.setText(item.getSyarat_ketentuan());
                                    break;
                                case 5:
                                    tvSnK_5.setText(item.getSyarat_ketentuan());
                                    break;
                                case 6:
                                    tvSnK_6.setText(item.getSyarat_ketentuan());
                                    break;
                                case 7:
                                    tvSnK_7.setText(item.getSyarat_ketentuan());
                                    break;
                                case 8:
                                    tvSnK_8.setText(item.getSyarat_ketentuan());
                                    break;
                                case 9:
                                    tvSnK_9.setText(item.getSyarat_ketentuan());
                                    break;
                                case 10:
                                    tvSnK_10.setText(item.getSyarat_ketentuan());
                                    break;
                                case 11:
                                    tvSnK_11.setText(item.getSyarat_ketentuan());
                                    break;
                                case 12:
                                    tvSnK_12.setText(item.getSyarat_ketentuan());
                                    break;
                                case 13:
                                    tvSnK_13.setText(item.getSyarat_ketentuan());
                                    break;
                                case 14:
                                    tvSnK_14.setText(item.getSyarat_ketentuan());
                                    break;
                                case 15:
                                    tvSnK_15.setText(item.getSyarat_ketentuan());
                                    break;
                                case 16:
                                    tvSnK_16.setText(item.getSyarat_ketentuan());
                                    break;
                                case 17:
                                    tvSnK_17.setText(item.getSyarat_ketentuan());
                                    break;
                                case 18:
                                    tvSnK_18.setText(item.getSyarat_ketentuan());
                                    break;
                                case 19:
                                    tvSnK_19.setText(item.getSyarat_ketentuan());
                                    break;
                                case 20:
                                    tvSnK_20.setText(item.getSyarat_ketentuan());
                                    break;
                                case 21:
                                    tvSnK_21.setText(item.getSyarat_ketentuan());
                                    break;
                                case 22:
                                    tvSnK_22.setText(item.getSyarat_ketentuan());
                                    break;
                                case 23:
                                    tvSnK_23.setText(item.getSyarat_ketentuan());
                                    break;
                                case 24:
                                    tvSnK_24.setText(item.getSyarat_ketentuan());
                                    break;
                                case 25:
                                    tvSnK_25.setText(item.getSyarat_ketentuan());
                                    break;
                                case 26:
                                    tvSnK_26.setText(item.getSyarat_ketentuan());
                                    break;
                                case 27:
                                    tvSnK_27.setText(item.getSyarat_ketentuan());
                                    break;
                                case 28:
                                    tvSnK_28.setText(item.getSyarat_ketentuan());
                                    break;
                                case 29:
                                    tvSnK_29.setText(item.getSyarat_ketentuan());
                                    break;
                                case 30:
                                    tvSnK_30.setText(item.getSyarat_ketentuan());
                                    break;
                                case 31:
                                    tvSnK_31.setText(item.getSyarat_ketentuan());
                                    break;
                                case 32:
                                    tvSnK_32.setText(item.getSyarat_ketentuan());
                                    break;
                                case 33:
                                    tvSnK_33.setText(item.getSyarat_ketentuan());
                                    break;
                                case 34:
                                    tvSnK_34.setText(item.getSyarat_ketentuan());
                                    break;
                                case 35:
                                    tvSnK_35.setText(item.getSyarat_ketentuan());
                                    break;
                                case 36:
                                    tvSnK_36.setText(item.getSyarat_ketentuan());
                                    break;
                                case 37:
                                    tvSnK_37.setText(item.getSyarat_ketentuan());
                                    break;
                                case 38:
                                    tvSnK_38.setText(item.getSyarat_ketentuan());
                                    break;
                                case 39:
                                    tvSnK_39.setText(item.getSyarat_ketentuan());
                                    break;
                                
                            }
                        }
                    } else {
                        Toast.makeText(SnKEdit.this, "No data available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SnKEdit.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TampilSnK>> call, Throwable t) {
                Toast.makeText(SnKEdit.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }



    //edit data dan simpan data cara daftar
    private void editDataSnK() {

        EditText tvSnK_1 = findViewById(R.id.snk1);
        EditText tvSnK_2 = findViewById(R.id.snk2);
        EditText tvSnK_3 = findViewById(R.id.snk3);
        EditText tvSnK_4 = findViewById(R.id.snk4);
        EditText tvSnK_5 = findViewById(R.id.snk5);
        EditText tvSnK_6 = findViewById(R.id.snk6);

        EditText tvSnK_7 = findViewById(R.id.snk7);
        EditText tvSnK_8 = findViewById(R.id.snk8);
        EditText tvSnK_9 = findViewById(R.id.snk9);
        EditText tvSnK_10 = findViewById(R.id.snk10);
        EditText tvSnK_11 = findViewById(R.id.snk11);
        EditText tvSnK_12 = findViewById(R.id.snk12);

        EditText tvSnK_13 = findViewById(R.id.snk13);
        EditText tvSnK_14 = findViewById(R.id.snk14);
        EditText tvSnK_15 = findViewById(R.id.snk15);
        EditText tvSnK_16 = findViewById(R.id.snk16);
        EditText tvSnK_17 = findViewById(R.id.snk17);
        EditText tvSnK_18 = findViewById(R.id.snk18);

        EditText tvSnK_19 = findViewById(R.id.snk19);
        EditText tvSnK_20 = findViewById(R.id.snk20);
        EditText tvSnK_21 = findViewById(R.id.snk21);
        EditText tvSnK_22 = findViewById(R.id.snk22);
        EditText tvSnK_23 = findViewById(R.id.snk23);
        EditText tvSnK_24 = findViewById(R.id.snk24);

        EditText tvSnK_25 = findViewById(R.id.snk25);
        EditText tvSnK_26 = findViewById(R.id.snk26);
        EditText tvSnK_27 = findViewById(R.id.snk27);
        EditText tvSnK_28 = findViewById(R.id.snk28);
        EditText tvSnK_29 = findViewById(R.id.snk29);
        EditText tvSnK_30 = findViewById(R.id.snk30);

        EditText tvSnK_31 = findViewById(R.id.snk31);
        EditText tvSnK_32 = findViewById(R.id.snk32);
        EditText tvSnK_33 = findViewById(R.id.snk33);
        EditText tvSnK_34 = findViewById(R.id.snk34);
        EditText tvSnK_35 = findViewById(R.id.snk35);
        EditText tvSnK_36 = findViewById(R.id.snk36);

        EditText tvSnK_37 = findViewById(R.id.snk37);
        EditText tvSnK_38 = findViewById(R.id.snk38);
        EditText tvSnK_39 = findViewById(R.id.snk39);


        String editText1 = tvSnK_1.getText().toString();
        String editText2 = tvSnK_2.getText().toString();
        String editText3 = tvSnK_3.getText().toString();
        String editText4 = tvSnK_4.getText().toString();
        String editText5 = tvSnK_5.getText().toString();
        String editText6 = tvSnK_6.getText().toString();

        String editText7 = tvSnK_7.getText().toString();
        String editText8 = tvSnK_8.getText().toString();
        String editText9 = tvSnK_9.getText().toString();
        String editText10 = tvSnK_10.getText().toString();
        String editText11 = tvSnK_11.getText().toString();
        String editText12 = tvSnK_12.getText().toString();

        String editText13 = tvSnK_13.getText().toString();
        String editText14 = tvSnK_14.getText().toString();
        String editText15 = tvSnK_15.getText().toString();
        String editText16 = tvSnK_16.getText().toString();
        String editText17 = tvSnK_17.getText().toString();
        String editText18 = tvSnK_18.getText().toString();

        String editText19 = tvSnK_19.getText().toString();
        String editText20 = tvSnK_20.getText().toString();
        String editText21 = tvSnK_21.getText().toString();
        String editText22 = tvSnK_22.getText().toString();
        String editText23 = tvSnK_23.getText().toString();
        String editText24 = tvSnK_24.getText().toString();

        String editText25 = tvSnK_25.getText().toString();
        String editText26 = tvSnK_26.getText().toString();
        String editText27 = tvSnK_27.getText().toString();
        String editText28 = tvSnK_28.getText().toString();
        String editText29 = tvSnK_29.getText().toString();
        String editText30 = tvSnK_30.getText().toString();

        String editText31 = tvSnK_31.getText().toString();
        String editText32 = tvSnK_32.getText().toString();
        String editText33 = tvSnK_33.getText().toString();
        String editText34 = tvSnK_34.getText().toString();
        String editText35 = tvSnK_35.getText().toString();
        String editText36 = tvSnK_36.getText().toString();

        String editText37 = tvSnK_37.getText().toString();
        String editText38 = tvSnK_38.getText().toString();
        String editText39 = tvSnK_39.getText().toString();
        

        RetrofitClient retrofitClient = RetrofitClient.getInstance(this);
        Api api = retrofitClient.getApi();

        Call<UpdateSnK> call1 = api.updateDataSnK(1, editText1);
        Call<UpdateSnK> call2 = api.updateDataSnK(2, editText2);
        Call<UpdateSnK> call3 = api.updateDataSnK(3, editText3);
        Call<UpdateSnK> call4 = api.updateDataSnK(4, editText4);
        Call<UpdateSnK> call5 = api.updateDataSnK(5, editText5);
        Call<UpdateSnK> call6 = api.updateDataSnK(6, editText6);

        Call<UpdateSnK> call7 = api.updateDataSnK(7, editText7);
        Call<UpdateSnK> call8 = api.updateDataSnK(8, editText8);
        Call<UpdateSnK> call9 = api.updateDataSnK(9, editText9);
        Call<UpdateSnK> call10 = api.updateDataSnK(10, editText10);
        Call<UpdateSnK> call11 = api.updateDataSnK(11, editText11);
        Call<UpdateSnK> call12 = api.updateDataSnK(12, editText12);

        Call<UpdateSnK> call13 = api.updateDataSnK(13, editText13);
        Call<UpdateSnK> call14 = api.updateDataSnK(14, editText14);
        Call<UpdateSnK> call15 = api.updateDataSnK(15, editText15);
        Call<UpdateSnK> call16 = api.updateDataSnK(16, editText16);
        Call<UpdateSnK> call17 = api.updateDataSnK(17, editText17);
        Call<UpdateSnK> call18 = api.updateDataSnK(18, editText18);

        Call<UpdateSnK> call19 = api.updateDataSnK(19, editText19);
        Call<UpdateSnK> call20 = api.updateDataSnK(20, editText20);
        Call<UpdateSnK> call21 = api.updateDataSnK(21, editText21);
        Call<UpdateSnK> call22 = api.updateDataSnK(22, editText22);
        Call<UpdateSnK> call23 = api.updateDataSnK(23, editText23);
        Call<UpdateSnK> call24 = api.updateDataSnK(24, editText24);

        Call<UpdateSnK> call25 = api.updateDataSnK(25, editText25);
        Call<UpdateSnK> call26 = api.updateDataSnK(26, editText26);
        Call<UpdateSnK> call27 = api.updateDataSnK(27, editText27);
        Call<UpdateSnK> call28 = api.updateDataSnK(28, editText28);
        Call<UpdateSnK> call29 = api.updateDataSnK(29, editText29);
        Call<UpdateSnK> call30 = api.updateDataSnK(30, editText30);

        Call<UpdateSnK> call31 = api.updateDataSnK(31, editText31);
        Call<UpdateSnK> call32 = api.updateDataSnK(32, editText32);
        Call<UpdateSnK> call33 = api.updateDataSnK(33, editText33);
        Call<UpdateSnK> call34 = api.updateDataSnK(34, editText34);
        Call<UpdateSnK> call35 = api.updateDataSnK(35, editText35);
        Call<UpdateSnK> call36 = api.updateDataSnK(36, editText36);

        Call<UpdateSnK> call37 = api.updateDataSnK(37, editText37);
        Call<UpdateSnK> call38 = api.updateDataSnK(38, editText38);
        Call<UpdateSnK> call39 = api.updateDataSnK(39, editText39);


        call1.enqueue(updateResponseCallback);
        call2.enqueue(updateResponseCallback);
        call3.enqueue(updateResponseCallback);
        call4.enqueue(updateResponseCallback);
        call5.enqueue(updateResponseCallback);
        call6.enqueue(updateResponseCallback);

        call7.enqueue(updateResponseCallback);
        call8.enqueue(updateResponseCallback);
        call9.enqueue(updateResponseCallback);
        call10.enqueue(updateResponseCallback);
        call11.enqueue(updateResponseCallback);
        call12.enqueue(updateResponseCallback);

        call13.enqueue(updateResponseCallback);
        call14.enqueue(updateResponseCallback);
        call15.enqueue(updateResponseCallback);
        call16.enqueue(updateResponseCallback);
        call17.enqueue(updateResponseCallback);
        call18.enqueue(updateResponseCallback);

        call19.enqueue(updateResponseCallback);
        call20.enqueue(updateResponseCallback);
        call21.enqueue(updateResponseCallback);
        call22.enqueue(updateResponseCallback);
        call23.enqueue(updateResponseCallback);
        call24.enqueue(updateResponseCallback);

        call25.enqueue(updateResponseCallback);
        call26.enqueue(updateResponseCallback);
        call27.enqueue(updateResponseCallback);
        call28.enqueue(updateResponseCallback);
        call29.enqueue(updateResponseCallback);
        call30.enqueue(updateResponseCallback);

        call31.enqueue(updateResponseCallback);
        call32.enqueue(updateResponseCallback);
        call33.enqueue(updateResponseCallback);
        call34.enqueue(updateResponseCallback);
        call35.enqueue(updateResponseCallback);
        call36.enqueue(updateResponseCallback);

        call37.enqueue(updateResponseCallback);
        call38.enqueue(updateResponseCallback);
        call39.enqueue(updateResponseCallback);


    }

    //simpan data cara daftar
    private int successCount = 0;
    private int failureCount = 0;
    private final Callback<UpdateSnK> updateResponseCallback = new Callback<UpdateSnK>() {
        @Override
        public void onResponse(Call<UpdateSnK> call, Response<UpdateSnK> response) {
            if (response.isSuccessful()) {
                UpdateSnK updateSnK = response.body();
                String status = updateSnK.getStatus();
                String message = updateSnK.getMessage();
                if (status.equals("success")) {
                    successCount++;
                } else if (status.equals("error")) {
                    Toast.makeText(SnKEdit.this, "Gagal Memperbarui Data" + message, Toast.LENGTH_SHORT).show();
                    failureCount++;
                }

                if (successCount + failureCount == 39) {
                    Toast.makeText(SnKEdit.this, "Data Berhasil Diperbarui", Toast.LENGTH_SHORT).show();
                    successCount = 0;
                    failureCount = 0;

                    Intent intent = new Intent(SnKEdit.this, SnKActivity.class);
                    startActivity(intent);
                }

            } else {
                Toast.makeText(SnKEdit.this, "Error" + response.message(), Toast.LENGTH_SHORT).show();
                failureCount++;

                if (successCount + failureCount == 39) {
                    Toast.makeText(SnKEdit.this, "Data Berhasil Diperbarui", Toast.LENGTH_SHORT).show();
                    successCount = 0;
                    failureCount = 0;

                    Intent intent = new Intent(SnKEdit.this, CaraDaftarActivity.class);
                    startActivity(intent);
                }
            }
        }

        @Override
        public void onFailure(Call<UpdateSnK> call, Throwable t) {
            Toast.makeText(SnKEdit.this, "Kesalahan jaringan" + t.getMessage(), Toast.LENGTH_SHORT).show();
            failureCount++;

            if (successCount + failureCount == 39) {
                Toast.makeText(SnKEdit.this, "Data Berhasil Diperbarui", Toast.LENGTH_SHORT).show();
                successCount = 0;
                failureCount = 0;

                Intent intent = new Intent(SnKEdit.this, CaraDaftarActivity.class);
                startActivity(intent);
            }
        }
    };



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


    //kembali ke activity sebelumnya
    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }


    //side bar navigation
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_carabooking) {
        } else if (itemId == R.id.nav_home) {
            Intent intent1 = new Intent(SnKEdit.this, MainActivity.class);
            startActivity(intent1);
        } else if (itemId == R.id.nav_galeri) {
            Intent intent2 = new Intent(SnKEdit.this, GaleriActivity.class);
            startActivity(intent2);
        } else if (itemId == R.id.nav_snk) {
            Intent intent3 = new Intent(SnKEdit.this, SnKActivity.class);
            startActivity(intent3);
        } else if (itemId == R.id.nav_login) {
            Intent intent4 = new Intent(SnKEdit.this, Login.class);
            startActivity(intent4);
        } else if (itemId == R.id.nav_daftar) {
            Intent intent5 = new Intent(SnKEdit.this, Daftar.class);
            startActivity(intent5);
        } else if (itemId == R.id.nav_helpdesk) {
            Intent intent6 = new Intent(SnKEdit.this, Helpdesk.class);
            startActivity(intent6);
        } else if (itemId == R.id.nav_about) {
            Intent intent7 = new Intent(SnKEdit.this, About.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_data_mahasiswa) {
            Intent intent7 = new Intent(SnKEdit.this, DataMahasiswa.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_ubahpass) {
            Intent intent7 = new Intent(SnKEdit.this, UbahPassword.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_daftar_kamar) {
            Intent intent8 = new Intent(SnKEdit.this, DaftarKamar.class);
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
        Intent intent = new Intent(SnKEdit.this, Login.class);
        startActivity(intent);
        finishAffinity();
    }
    
}