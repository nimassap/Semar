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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.semar5.ModelResponse.TampilCaraDaftar;
import com.example.semar5.ModelResponse.TampilSnK;
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

public class SnKActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button edit;
    private TextView namaprofile;
    private SharedPreferenceManager sharedPreferenceManager;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snk);

        //header logo
        ImageView imageView = findViewById(R.id.homesemar);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SnKActivity.this, MainActivity.class);
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


        //memanggil database
        RetrofitClient retrofitClient = RetrofitClient.getInstance(this);
        Api api = retrofitClient.getApi();

        Call<List<TampilSnK>> call = api.getDataSnK();
        call.enqueue(new Callback<List<TampilSnK>>() {
            @Override
            public void onResponse(Call<List<TampilSnK>> call, Response<List<TampilSnK>> response) {
                if (response.isSuccessful()) {
                    List<TampilSnK> dataItems = response.body();

                    if (dataItems != null && !dataItems.isEmpty()) {

                        TextView tvSnK_1 = findViewById(R.id.snk1);
                        TextView tvSnK_2 = findViewById(R.id.snk2);
                        TextView tvSnK_3 = findViewById(R.id.snk3);
                        TextView tvSnK_4 = findViewById(R.id.snk4);
                        TextView tvSnK_5 = findViewById(R.id.snk5);
                        TextView tvSnK_6 = findViewById(R.id.snk6);

                        TextView tvSnK_7 = findViewById(R.id.snk7);
                        TextView tvSnK_8 = findViewById(R.id.snk8);
                        TextView tvSnK_9 = findViewById(R.id.snk9);
                        TextView tvSnK_10 = findViewById(R.id.snk10);
                        TextView tvSnK_11 = findViewById(R.id.snk11);
                        TextView tvSnK_12 = findViewById(R.id.snk12);

                        TextView tvSnK_13 = findViewById(R.id.snk13);
                        TextView tvSnK_14 = findViewById(R.id.snk14);
                        TextView tvSnK_15 = findViewById(R.id.snk15);
                        TextView tvSnK_16 = findViewById(R.id.snk16);
                        TextView tvSnK_17 = findViewById(R.id.snk17);
                        TextView tvSnK_18 = findViewById(R.id.snk18);

                        TextView tvSnK_19 = findViewById(R.id.snk19);
                        TextView tvSnK_20 = findViewById(R.id.snk20);
                        TextView tvSnK_21 = findViewById(R.id.snk21);
                        TextView tvSnK_22 = findViewById(R.id.snk22);
                        TextView tvSnK_23 = findViewById(R.id.snk23);
                        TextView tvSnK_24 = findViewById(R.id.snk24);

                        TextView tvSnK_25 = findViewById(R.id.snk25);
                        TextView tvSnK_26 = findViewById(R.id.snk26);
                        TextView tvSnK_27 = findViewById(R.id.snk27);
                        TextView tvSnK_28 = findViewById(R.id.snk28);
                        TextView tvSnK_29 = findViewById(R.id.snk29);
                        TextView tvSnK_30 = findViewById(R.id.snk30);

                        TextView tvSnK_31 = findViewById(R.id.snk31);
                        TextView tvSnK_32 = findViewById(R.id.snk32);
                        TextView tvSnK_33 = findViewById(R.id.snk33);
                        TextView tvSnK_34 = findViewById(R.id.snk34);
                        TextView tvSnK_35 = findViewById(R.id.snk35);
                        TextView tvSnK_36 = findViewById(R.id.snk36);

                        TextView tvSnK_37 = findViewById(R.id.snk37);
                        TextView tvSnK_38 = findViewById(R.id.snk38);
                        TextView tvSnK_39 = findViewById(R.id.snk39);


                        for (TampilSnK item : dataItems) {
                            if (item.getId() == 1) {
                                tvSnK_1.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 2) {
                                tvSnK_2.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 3) {
                                tvSnK_3.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 4) {
                                tvSnK_4.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 5) {
                                tvSnK_5.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 6) {
                                tvSnK_6.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 7) {
                                tvSnK_7.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 8) {
                                tvSnK_8.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 9) {
                                tvSnK_9.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 10) {
                                tvSnK_10.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 11) {
                                tvSnK_11.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 12) {
                                tvSnK_12.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 13) {
                                tvSnK_13.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 14) {
                                tvSnK_14.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 15) {
                                tvSnK_15.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 16) {
                                tvSnK_16.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 17) {
                                tvSnK_17.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 18) {
                                tvSnK_18.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 19) {
                                tvSnK_19.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 20) {
                                tvSnK_20.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 21) {
                                tvSnK_21.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 22) {
                                tvSnK_22.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 23) {
                                tvSnK_23.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 24) {
                                tvSnK_24.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 25) {
                                tvSnK_25.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 26) {
                                tvSnK_26.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 27) {
                                tvSnK_27.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 28) {
                                tvSnK_28.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 29) {
                                tvSnK_29.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 30) {
                                tvSnK_30.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 31) {
                                tvSnK_31.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 32) {
                                tvSnK_32.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 33) {
                                tvSnK_33.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 34) {
                                tvSnK_34.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 35) {
                                tvSnK_35.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 36) {
                                tvSnK_36.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 37) {
                                tvSnK_37.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 38) {
                                tvSnK_38.setText(item.getSyarat_ketentuan());
                            } else if (item.getId() == 39) {
                                tvSnK_39.setText(item.getSyarat_ketentuan());
                            } 
                        }

                    } else {
                        Toast.makeText(SnKActivity.this, "Data tidak tersedia", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SnKActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TampilSnK>> call, Throwable t) {
                Toast.makeText(SnKActivity.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });


        //sesuaikan activity
        drawerLayout = findViewById(R.id.drawer_layout_snk);
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

        edit = findViewById(R.id.edit_snk);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SnKActivity.this, SnKEdit.class);
                startActivity(intent);
            }
        });

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
                edit.setVisibility(View.VISIBLE);

            } else if (role == 2) {
                menu.findItem(R.id.nav_logout).setVisible(true);
                menu.findItem(R.id.nav_ubahpass).setVisible(true);
                menu.findItem(R.id.nav_login).setVisible(false);
                menu.findItem(R.id.nav_daftar).setVisible(false);
                menu.findItem(R.id.nav_data_mahasiswa).setVisible(false);
                menu.findItem(R.id.nav_daftar_kamar).setVisible(false);
                logo.setVisibility(View.GONE);
                edit.setVisibility(View.GONE);

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
        if (itemId == R.id.nav_snk) {
        } else if (itemId == R.id.nav_home) {
            Intent intent1 = new Intent(SnKActivity.this, MainActivity.class);
            startActivity(intent1);
        } else if (itemId == R.id.nav_carabooking) {
            Intent intent2 = new Intent(SnKActivity.this, CaraDaftarActivity.class);
            startActivity(intent2);
        } else if (itemId == R.id.nav_galeri) {
            Intent intent3 = new Intent(SnKActivity.this, GaleriActivity.class);
            startActivity(intent3);
        } else if (itemId == R.id.nav_login) {
            Intent intent4 = new Intent(SnKActivity.this, Login.class);
            startActivity(intent4);
        } else if (itemId == R.id.nav_daftar) {
            Intent intent5 = new Intent(SnKActivity.this, Daftar.class);
            startActivity(intent5);
        } else if (itemId == R.id.nav_helpdesk) {
            Intent intent6 = new Intent(SnKActivity.this, Helpdesk.class);
            startActivity(intent6);
        } else if (itemId == R.id.nav_about) {
            Intent intent7 = new Intent(SnKActivity.this, About.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_data_mahasiswa) {
            Intent intent7 = new Intent(SnKActivity.this, DataMahasiswa.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_ubahpass) {
            Intent intent7 = new Intent(SnKActivity.this, UbahPassword.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_daftar_kamar) {
            Intent intent8 = new Intent(SnKActivity.this, DaftarKamar.class);
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
        Intent intent = new Intent(SnKActivity.this, Login.class);
        startActivity(intent);
        finishAffinity();
    }

}