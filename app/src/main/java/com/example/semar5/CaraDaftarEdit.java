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
import com.example.semar5.ModelResponse.TampilDeluxe1;
import com.example.semar5.ModelResponse.UpdateCaraDaftar;
import com.example.semar5.ModelResponse.UpdateDeluxe1;
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

public class CaraDaftarEdit extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private TextView namaprofile;
    private SharedPreferenceManager sharedPreferenceManager;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cara_daftar_edit);

        //header logo
        ImageView imageView = findViewById(R.id.homesemar);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CaraDaftarEdit.this, MainActivity.class);
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
        drawerLayout = findViewById(R.id.drawer_layout_cara_booking_edit);
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
        navigationView.setCheckedItem(R.id.nav_carabooking);



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



        //button save edit data
        Button saveEditCaraDaftar = findViewById(R.id.simpan_caradaftar);
        saveEditCaraDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDataCaraDaftar();
            }
        });

        tampilDataCaraDaftar();

    }


    //menampilkan data dari database
    private void tampilDataCaraDaftar() {

        EditText tvCaraDaftar_1 = findViewById(R.id.hyperlink_sso);
        EditText tvCaraDaftar_2 = findViewById(R.id.hyperlink_profil);
        EditText tvCaraDaftar_3 = findViewById(R.id.hyperlink_home);
        EditText tvCaraDaftar_4 = findViewById(R.id.et_caradaftar4);
        EditText tvCaraDaftar_5 = findViewById(R.id.hyperlink_booknow);
        EditText tvCaraDaftar_6 = findViewById(R.id.hyperlink_history);

        RetrofitClient retrofitClient = RetrofitClient.getInstance(this);
        Api api = retrofitClient.getApi();

        Call<List<TampilCaraDaftar>> call = api.getDataCaraDaftar();
        call.enqueue(new Callback<List<TampilCaraDaftar>>() {
            @Override
            public void onResponse(Call<List<TampilCaraDaftar>> call, Response<List<TampilCaraDaftar>> response) {
                if (response.isSuccessful()) {
                    List<TampilCaraDaftar> dataItems = response.body();

                    if (dataItems != null && !dataItems.isEmpty()) {
                        for (TampilCaraDaftar item : dataItems) {
                            switch (item.getId()) {
                                case 1:
                                    tvCaraDaftar_1.setText(item.getCara_daftar());
                                    break;
                                case 2:
                                    tvCaraDaftar_2.setText(item.getCara_daftar());
                                    break;
                                case 3:
                                    tvCaraDaftar_3.setText(item.getCara_daftar());
                                    break;
                                case 4:
                                    tvCaraDaftar_4.setText(item.getCara_daftar());
                                    break;
                                case 5:
                                    tvCaraDaftar_5.setText(item.getCara_daftar());
                                    break;
                                case 6:
                                    tvCaraDaftar_6.setText(item.getCara_daftar());
                                    break;
                            }
                        }
                    } else {
                        Toast.makeText(CaraDaftarEdit.this, "No data available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CaraDaftarEdit.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TampilCaraDaftar>> call, Throwable t) {
                Toast.makeText(CaraDaftarEdit.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }



    //edit data dan simpan data cara daftar
    private void editDataCaraDaftar() {

        EditText tvCaraDaftar_1 = findViewById(R.id.hyperlink_sso);
        EditText tvCaraDaftar_2 = findViewById(R.id.hyperlink_profil);
        EditText tvCaraDaftar_3 = findViewById(R.id.hyperlink_home);
        EditText tvCaraDaftar_4 = findViewById(R.id.et_caradaftar4);
        EditText tvCaraDaftar_5 = findViewById(R.id.hyperlink_booknow);
        EditText tvCaraDaftar_6 = findViewById(R.id.hyperlink_history);

        String editText1 = tvCaraDaftar_1.getText().toString();
        String editText2 = tvCaraDaftar_2.getText().toString();
        String editText3 = tvCaraDaftar_3.getText().toString();
        String editText4 = tvCaraDaftar_4.getText().toString();
        String editText5 = tvCaraDaftar_5.getText().toString();
        String editText6 = tvCaraDaftar_6.getText().toString();

        RetrofitClient retrofitClient = RetrofitClient.getInstance(this);
        Api api = retrofitClient.getApi();

        Call<UpdateCaraDaftar> call1 = api.updateDataCaraDaftar(1, editText1);
        Call<UpdateCaraDaftar> call2 = api.updateDataCaraDaftar(2, editText2);
        Call<UpdateCaraDaftar> call3 = api.updateDataCaraDaftar(3, editText3);
        Call<UpdateCaraDaftar> call4 = api.updateDataCaraDaftar(4, editText4);
        Call<UpdateCaraDaftar> call5 = api.updateDataCaraDaftar(5, editText5);
        Call<UpdateCaraDaftar> call6 = api.updateDataCaraDaftar(6, editText6);

        call1.enqueue(updateResponseCallback);
        call2.enqueue(updateResponseCallback);
        call3.enqueue(updateResponseCallback);
        call4.enqueue(updateResponseCallback);
        call5.enqueue(updateResponseCallback);
        call6.enqueue(updateResponseCallback);

    }

    //simpan data cara daftar
    private int successCount = 0;
    private int failureCount = 0;
    private final Callback<UpdateCaraDaftar> updateResponseCallback = new Callback<UpdateCaraDaftar>() {
        @Override
        public void onResponse(Call<UpdateCaraDaftar> call, Response<UpdateCaraDaftar> response) {
            if (response.isSuccessful()) {
                UpdateCaraDaftar updateCaraDaftar = response.body();
                String status = updateCaraDaftar.getStatus();
                String message = updateCaraDaftar.getMessage();
                if (status.equals("success")) {
                    successCount++;
                } else if (status.equals("error")) {
                    Toast.makeText(CaraDaftarEdit.this, "Gagal Memperbarui Data" + message, Toast.LENGTH_SHORT).show();
                    failureCount++;
                }

                if (successCount + failureCount == 6) {
                    Toast.makeText(CaraDaftarEdit.this, "Data Berhasil Diperbarui", Toast.LENGTH_SHORT).show();
                    successCount = 0;
                    failureCount = 0;

                    Intent intent = new Intent(CaraDaftarEdit.this, CaraDaftarActivity.class);
                    startActivity(intent);
                }

            } else {
                Toast.makeText(CaraDaftarEdit.this, "Error" + response.message(), Toast.LENGTH_SHORT).show();
                failureCount++;

                if (successCount + failureCount == 6) {
                    Toast.makeText(CaraDaftarEdit.this, "Data Berhasil Diperbarui", Toast.LENGTH_SHORT).show();
                    successCount = 0;
                    failureCount = 0;

                    Intent intent = new Intent(CaraDaftarEdit.this, CaraDaftarActivity.class);
                    startActivity(intent);
                }
            }
        }

        @Override
        public void onFailure(Call<UpdateCaraDaftar> call, Throwable t) {
            Toast.makeText(CaraDaftarEdit.this, "Kesalahan jaringan" + t.getMessage(), Toast.LENGTH_SHORT).show();
            failureCount++;

            if (successCount + failureCount == 6) {
                Toast.makeText(CaraDaftarEdit.this, "Data Berhasil Diperbarui", Toast.LENGTH_SHORT).show();
                successCount = 0;
                failureCount = 0;

                Intent intent = new Intent(CaraDaftarEdit.this, CaraDaftarActivity.class);
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
            Intent intent1 = new Intent(CaraDaftarEdit.this, MainActivity.class);
            startActivity(intent1);
        } else if (itemId == R.id.nav_galeri) {
            Intent intent2 = new Intent(CaraDaftarEdit.this, GaleriActivity.class);
            startActivity(intent2);
        } else if (itemId == R.id.nav_snk) {
            Intent intent3 = new Intent(CaraDaftarEdit.this, SnKActivity.class);
            startActivity(intent3);
        } else if (itemId == R.id.nav_login) {
            Intent intent4 = new Intent(CaraDaftarEdit.this, Login.class);
            startActivity(intent4);
        } else if (itemId == R.id.nav_daftar) {
            Intent intent5 = new Intent(CaraDaftarEdit.this, Daftar.class);
            startActivity(intent5);
        } else if (itemId == R.id.nav_helpdesk) {
            Intent intent6 = new Intent(CaraDaftarEdit.this, Helpdesk.class);
            startActivity(intent6);
        } else if (itemId == R.id.nav_about) {
            Intent intent7 = new Intent(CaraDaftarEdit.this, About.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_data_mahasiswa) {
            Intent intent7 = new Intent(CaraDaftarEdit.this, DataMahasiswa.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_ubahpass) {
            Intent intent7 = new Intent(CaraDaftarEdit.this, UbahPassword.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_daftar_kamar) {
            Intent intent8 = new Intent(CaraDaftarEdit.this, DaftarKamar.class);
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
        Intent intent = new Intent(CaraDaftarEdit.this, Login.class);
        startActivity(intent);
        finishAffinity();
    }

}