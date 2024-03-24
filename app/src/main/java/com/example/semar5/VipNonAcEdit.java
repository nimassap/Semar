package com.example.semar5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.example.semar5.ModelResponse.TampilDeluxe5a;
import com.example.semar5.ModelResponse.TampilVipNonAc;
import com.example.semar5.ModelResponse.UpdateDeluxe5a;
import com.example.semar5.ModelResponse.UpdateVipNonAc;
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

public class VipNonAcEdit extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private TextView namaprofile;
    private SharedPreferenceManager sharedPreferenceManager;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_non_ac_edit);

        //toolbar logo semar
        ImageView imageView = findViewById(R.id.homesemar);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VipNonAcEdit.this, MainActivity.class);
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


        //sesuaikan activity
        drawerLayout = findViewById(R.id.drawer_layout_vipnonacedit);
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

        //button edit activity
        Button saveEdit = findViewById(R.id.save_vipnonac);
        saveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDataVipNonAc();
            }
        });

        tampilDataVipNonAc();
    }


    //menampilkan data dari database
    private void tampilDataVipNonAc() {

        EditText tvVipNonAc_1 = findViewById(R.id.tv_vipnonac_1);
        EditText tvVipNonAc_2 = findViewById(R.id.tv_vipnonac_2);
        EditText tvVipNonAc_3 = findViewById(R.id.tv_vipnonac_3);
        EditText tvVipNonAc_4 = findViewById(R.id.tv_vipnonac_4);
        EditText tvVipNonAc_5 = findViewById(R.id.tv_vipnonac_5);
        EditText tvVipNonAc_6 = findViewById(R.id.tv_vipnonac_6);
        EditText tvVipNonAc_7 = findViewById(R.id.tv_vipnonac_7);
        EditText tvVipNonAc_8 = findViewById(R.id.tv_vipnonac_8);
        EditText tvVipNonAc_9 = findViewById(R.id.tv_vipnonac_9);

        RetrofitClient retrofitClient = RetrofitClient.getInstance(this);
        Api api = retrofitClient.getApi();

        Call<List<TampilVipNonAc>> call = api.getDataVipNonAc();
        call.enqueue(new Callback<List<TampilVipNonAc>>() {
            @Override
            public void onResponse(Call<List<TampilVipNonAc>> call, Response<List<TampilVipNonAc>> response) {
                if (response.isSuccessful()) {
                    List<TampilVipNonAc> dataItems = response.body();

                    if (dataItems != null && !dataItems.isEmpty()) {
                        for (TampilVipNonAc item : dataItems) {
                            switch (item.getId()) {
                                case 1:
                                    tvVipNonAc_1.setText(item.getTextEdit());
                                    break;
                                case 2:
                                    tvVipNonAc_2.setText(item.getTextEdit());
                                    break;
                                case 3:
                                    tvVipNonAc_3.setText(item.getTextEdit());
                                    break;
                                case 4:
                                    tvVipNonAc_4.setText(item.getTextEdit());
                                    break;
                                case 5:
                                    tvVipNonAc_5.setText(item.getTextEdit());
                                    break;
                                case 6:
                                    tvVipNonAc_6.setText(item.getTextEdit());
                                    break;
                                case 7:
                                    tvVipNonAc_7.setText(item.getTextEdit());
                                    break;
                                case 8:
                                    tvVipNonAc_8.setText(item.getTextEdit());
                                    break;
                                case 9:
                                    tvVipNonAc_9.setText(item.getTextEdit());
                                    break;
                            }
                        }
                    } else {
                        Toast.makeText(VipNonAcEdit.this, "No data available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(VipNonAcEdit.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TampilVipNonAc>> call, Throwable t) {
                Toast.makeText(VipNonAcEdit.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }



    //edit data untuk disimpan kembali kedalam database
    private void editDataVipNonAc() {

        EditText tvVipNonAc_1 = findViewById(R.id.tv_vipnonac_1);
        EditText tvVipNonAc_2 = findViewById(R.id.tv_vipnonac_2);
        EditText tvVipNonAc_3 = findViewById(R.id.tv_vipnonac_3);
        EditText tvVipNonAc_4 = findViewById(R.id.tv_vipnonac_4);
        EditText tvVipNonAc_5 = findViewById(R.id.tv_vipnonac_5);
        EditText tvVipNonAc_6 = findViewById(R.id.tv_vipnonac_6);
        EditText tvVipNonAc_7 = findViewById(R.id.tv_vipnonac_7);
        EditText tvVipNonAc_8 = findViewById(R.id.tv_vipnonac_8);
        EditText tvVipNonAc_9 = findViewById(R.id.tv_vipnonac_9);

        String editText1 = tvVipNonAc_1.getText().toString();
        String editText2 = tvVipNonAc_2.getText().toString();
        String editText3 = tvVipNonAc_3.getText().toString();
        String editText4 = tvVipNonAc_4.getText().toString();
        String editText5 = tvVipNonAc_5.getText().toString();
        String editText6 = tvVipNonAc_6.getText().toString();
        String editText7 = tvVipNonAc_7.getText().toString();
        String editText8 = tvVipNonAc_8.getText().toString();
        String editText9 = tvVipNonAc_9.getText().toString();

        RetrofitClient retrofitClient = RetrofitClient.getInstance(this);
        Api api = retrofitClient.getApi();

        Call<UpdateVipNonAc> call1 = api.updateDataVipNonAc(1, editText1);
        Call<UpdateVipNonAc> call2 = api.updateDataVipNonAc(2, editText2);
        Call<UpdateVipNonAc> call3 = api.updateDataVipNonAc(3, editText3);
        Call<UpdateVipNonAc> call4 = api.updateDataVipNonAc(4, editText4);
        Call<UpdateVipNonAc> call5 = api.updateDataVipNonAc(5, editText5);
        Call<UpdateVipNonAc> call6 = api.updateDataVipNonAc(6, editText6);
        Call<UpdateVipNonAc> call7 = api.updateDataVipNonAc(7, editText7);
        Call<UpdateVipNonAc> call8 = api.updateDataVipNonAc(8, editText8);
        Call<UpdateVipNonAc> call9 = api.updateDataVipNonAc(9, editText9);

        call1.enqueue(updateResponseCallback);
        call2.enqueue(updateResponseCallback);
        call3.enqueue(updateResponseCallback);
        call4.enqueue(updateResponseCallback);
        call5.enqueue(updateResponseCallback);
        call6.enqueue(updateResponseCallback);
        call7.enqueue(updateResponseCallback);
        call8.enqueue(updateResponseCallback);
        call9.enqueue(updateResponseCallback);
    }

    private int successCount = 0;
    private int failureCount = 0;
    private final Callback<UpdateVipNonAc> updateResponseCallback = new Callback<UpdateVipNonAc>() {
        @Override
        public void onResponse(Call<UpdateVipNonAc> call, Response<UpdateVipNonAc> response) {
            if (response.isSuccessful()) {
                UpdateVipNonAc updateVipNonAc = response.body();
                String status = updateVipNonAc.getStatus();
                String message = updateVipNonAc.getMessage();
                if (status.equals("success")) {
                    successCount++;
                } else if (status.equals("error")) {
                    Toast.makeText(VipNonAcEdit.this, "Gagal Memperbarui Data" + message, Toast.LENGTH_SHORT).show();
                    failureCount++;
                }

                if (successCount + failureCount == 9) {
                    Toast.makeText(VipNonAcEdit.this, "Data Berhasil Diperbarui", Toast.LENGTH_SHORT).show();
                    successCount = 0;
                    failureCount = 0;

                    Intent intent = new Intent(VipNonAcEdit.this, VipNonAc.class);
                    startActivity(intent);
                }

            } else {
                Toast.makeText(VipNonAcEdit.this, "Error" + response.message(), Toast.LENGTH_SHORT).show();
                failureCount++;

                if (successCount + failureCount == 9) {
                    Toast.makeText(VipNonAcEdit.this, "Data Berhasil Diperbarui", Toast.LENGTH_SHORT).show();
                    successCount = 0;
                    failureCount = 0;

                    Intent intent = new Intent(VipNonAcEdit.this, VipNonAc.class);
                    startActivity(intent);
                }
            }
        }

        @Override
        public void onFailure(Call<UpdateVipNonAc> call, Throwable t) {
            Toast.makeText(VipNonAcEdit.this, "Kesalahan jaringan" + t.getMessage(), Toast.LENGTH_SHORT).show();
            failureCount++;

            if (successCount + failureCount == 9) {
                Toast.makeText(VipNonAcEdit.this, "Data Berhasil Diperbarui", Toast.LENGTH_SHORT).show();
                successCount = 0;
                failureCount = 0;

                Intent intent = new Intent(VipNonAcEdit.this, VipNonAc.class);
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
            NavigationView navigationView = findViewById(R.id.navdrawer_view);
            Menu menu = navigationView.getMenu();

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
        if (itemId == R.id.nav_home) {
            Intent intent1 = new Intent(VipNonAcEdit.this, MainActivity.class);
            startActivity(intent1);
        } else if (itemId == R.id.nav_carabooking) {
            Intent intent2 = new Intent(VipNonAcEdit.this, CaraDaftarActivity.class);
            startActivity(intent2);
        } else if (itemId == R.id.nav_galeri) {
            Intent intent3 = new Intent(VipNonAcEdit.this, GaleriActivity.class);
            startActivity(intent3);
        } else if (itemId == R.id.nav_snk) {
            Intent intent4 = new Intent(VipNonAcEdit.this, SnKActivity.class);
            startActivity(intent4);
        } else if (itemId == R.id.nav_login) {
            Intent intent5 = new Intent(VipNonAcEdit.this, Login.class);
            startActivity(intent5);
        } else if (itemId == R.id.nav_daftar) {
            Intent intent6 = new Intent(VipNonAcEdit.this, Daftar.class);
            startActivity(intent6);
        } else if (itemId == R.id.nav_helpdesk) {
            Intent intent7 = new Intent(VipNonAcEdit.this, Helpdesk.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_about) {
            Intent intent8 = new Intent(VipNonAcEdit.this, About.class);
            startActivity(intent8);
        } else if (itemId == R.id.nav_data_mahasiswa) {
            Intent intent7 = new Intent(VipNonAcEdit.this, DataMahasiswa.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_ubahpass) {
            Intent intent7 = new Intent(VipNonAcEdit.this, UbahPassword.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_daftar_kamar) {
            Intent intent8 = new Intent(VipNonAcEdit.this, DaftarKamar.class);
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
        Intent intent = new Intent(VipNonAcEdit.this, Login.class);
        startActivity(intent);
        finishAffinity();
    }
}