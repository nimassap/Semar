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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.semar5.Adapter.SliderDeluxe5AAdapter;
import com.example.semar5.Adapter.SliderVipNonAcAdapter;
import com.example.semar5.ModelResponse.TampilVipNonAc;
import com.example.semar5.ModelResponse.User;
import com.example.semar5.Retrofit.Api;
import com.example.semar5.Retrofit.RetrofitClient;
import com.example.semar5.Retrofit.SharedPreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VipNonAc extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private TextView namaprofile;
    private SharedPreferenceManager sharedPreferenceManager;
    private User user;
    SliderView sliderView;
    List<String> imageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_non_ac);

        //toolbar logo semar
        ImageView imageView = findViewById(R.id.homesemar);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VipNonAc.this, MainActivity.class);
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



        RetrofitClient retrofitClient = RetrofitClient.getInstance(this);
        Api api = retrofitClient.getApi();

        Call<List<String>> call1 = api.getGambarVipNonAc();

        call1.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    imageUrls = response.body();
                    Log.d("ImageUrlsDebug", "Total images: " + imageUrls.size());
                    for (String imageUrl : imageUrls) {
                        Log.d("ImageUrlsDebug", imageUrl);
                    }
                    displayImages(imageUrls);
                } else {
                    Log.e("API Response", "Unsuccessful response: " + response.code());
                    Toast.makeText(VipNonAc.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("API Failure", "Error: " + t.getMessage());
                Toast.makeText(VipNonAc.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });



        //sesuaikan activity
        drawerLayout = findViewById(R.id.drawer_layout_vipnonac);
        navigationView = findViewById(R.id.navdrawer_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        //hide or show item
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_logout).setVisible(false);
        menu.findItem(R.id.nav_ubahpass).setVisible(false);
        menu.findItem(R.id.nav_data_mahasiswa).setVisible(false);
        menu.findItem(R.id.nav_daftar_kamar).setVisible(false);


        //menampilkan data dari database
        RetrofitClient retrofitClient1 = RetrofitClient.getInstance(this);
        Api api1 = retrofitClient1.getApi();

        Call<List<TampilVipNonAc>> call = api1.getDataVipNonAc();
        call.enqueue(new Callback<List<TampilVipNonAc>>() {
            @Override
            public void onResponse(Call<List<TampilVipNonAc>> call, Response<List<TampilVipNonAc>> response) {
                if (response.isSuccessful()) {
                    Log.d("API_CALL", "onResponse - Success");
                    List<TampilVipNonAc> dataItems = response.body();

                    if (dataItems != null && !dataItems.isEmpty()) {

                        TextView tvVipNonAc_1 = findViewById(R.id.tv_vipnonac_1);
                        TextView tvVipNonAc_2 = findViewById(R.id.tv_vipnonac_2);
                        TextView tvVipNonAc_3 = findViewById(R.id.tv_vipnonac_3);
                        TextView tvVipNonAc_4 = findViewById(R.id.tv_vipnonac_4);
                        TextView tvVipNonAc_5 = findViewById(R.id.tv_vipnonac_5);
                        TextView tvVipNonAc_6 = findViewById(R.id.tv_vipnonac_6);
                        TextView tvVipNonAc_7 = findViewById(R.id.tv_vipnonac_7);
                        TextView tvVipNonAc_8 = findViewById(R.id.tv_vipnonac_8);
                        TextView tvVipNonAc_9 = findViewById(R.id.tv_vipnonac_9);

                        for (TampilVipNonAc item : dataItems) {
                            Log.d("API_CALL", "ID VipNonAc: " + item.getId());
                            Log.d("API_CALL", "Text Edit: " + item.getTextEdit());
                            if (item.getId() == 1) {
                                tvVipNonAc_1.setText(item.getTextEdit());
                            } else if (item.getId() == 2) {
                                tvVipNonAc_2.setText(item.getTextEdit());
                            } else if (item.getId() == 3) {
                                tvVipNonAc_3.setText(item.getTextEdit());
                            } else if (item.getId() == 4) {
                                tvVipNonAc_4.setText(item.getTextEdit());
                            } else if (item.getId() == 5) {
                                tvVipNonAc_5.setText(item.getTextEdit());
                            } else if (item.getId() == 6) {
                                tvVipNonAc_6.setText(item.getTextEdit());
                            } else if (item.getId() == 7) {
                                tvVipNonAc_7.setText(item.getTextEdit());
                            } else if (item.getId() == 8) {
                                tvVipNonAc_8.setText(item.getTextEdit());
                            } else if (item.getId() == 9) {
                                tvVipNonAc_9.setText(item.getTextEdit());
                            }
                        }

                    } else {
                        Log.d("API_CALL", "Data is empty or null");
                        Toast.makeText(VipNonAc.this, "Data tidak tersedia", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("API_CALL", "onResponse - Error: " + response.code());
                    Toast.makeText(VipNonAc.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TampilVipNonAc>> call, Throwable t) {
                Log.e("API_CALL", "onFailure: " + t.getMessage());
                Toast.makeText(VipNonAc.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });


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
    }

    private void displayImages(List<String> imageUrls) {
        //image slider
        sliderView = findViewById(R.id.image_slider_vipnonac);

        SliderVipNonAcAdapter sliderAdapter = new SliderVipNonAcAdapter(imageUrls, VipNonAc.this);

        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

        sliderAdapter.setOnSliderItemClickListener(new SliderVipNonAcAdapter.OnSliderItemClickListener() {
            @Override
            public void onSliderItemClick(int position) {
                Intent intent = new Intent(VipNonAc.this, ViewSliderPicVipNonAc.class);
                intent.putExtra("imagePosition", position);
                intent.putStringArrayListExtra("imageUrls", new ArrayList<>(imageUrls));
                startActivity(intent);
            }
        });

        sliderView.setSliderAdapter(sliderAdapter);
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

        Button edit = findViewById(R.id.edit_vipnonac);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VipNonAc.this, VipNonAcEdit.class);
                startActivity(intent);
            }
        });

        //button pindah activity ke pendaftaran kamar
        Button buttbookingnow = findViewById(R.id.booknow_vipnonac);
        buttbookingnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VipNonAc.this, BookingSekarang.class);
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
                buttbookingnow.setVisibility(View.GONE);

            } else if (role == 2) {
                menu.findItem(R.id.nav_logout).setVisible(true);
                menu.findItem(R.id.nav_ubahpass).setVisible(true);
                menu.findItem(R.id.nav_login).setVisible(false);
                menu.findItem(R.id.nav_daftar).setVisible(false);
                menu.findItem(R.id.nav_data_mahasiswa).setVisible(false);
                menu.findItem(R.id.nav_daftar_kamar).setVisible(false);
                logo.setVisibility(View.GONE);
                edit.setVisibility(View.GONE);
                buttbookingnow.setVisibility(View.VISIBLE);

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
            Intent intent1 = new Intent(VipNonAc.this, MainActivity.class);
            startActivity(intent1);
        } else if (itemId == R.id.nav_carabooking) {
            Intent intent2 = new Intent(VipNonAc.this, CaraDaftarActivity.class);
            startActivity(intent2);
        } else if (itemId == R.id.nav_galeri) {
            Intent intent3 = new Intent(VipNonAc.this, GaleriActivity.class);
            startActivity(intent3);
        } else if (itemId == R.id.nav_snk) {
            Intent intent4 = new Intent(VipNonAc.this, SnKActivity.class);
            startActivity(intent4);
        } else if (itemId == R.id.nav_login) {
            Intent intent5 = new Intent(VipNonAc.this, Login.class);
            startActivity(intent5);
        } else if (itemId == R.id.nav_daftar) {
            Intent intent6 = new Intent(VipNonAc.this, Daftar.class);
            startActivity(intent6);
        } else if (itemId == R.id.nav_helpdesk) {
            Intent intent7 = new Intent(VipNonAc.this, Helpdesk.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_about) {
            Intent intent8 = new Intent(VipNonAc.this, About.class);
            startActivity(intent8);
        } else if (itemId == R.id.nav_data_mahasiswa) {
            Intent intent7 = new Intent(VipNonAc.this, DataMahasiswa.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_ubahpass) {
            Intent intent7 = new Intent(VipNonAc.this, UbahPassword.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_daftar_kamar) {
            Intent intent8 = new Intent(VipNonAc.this, DaftarKamar.class);
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
        Intent intent = new Intent(VipNonAc.this, Login.class);
        startActivity(intent);
        finishAffinity();
    }

}