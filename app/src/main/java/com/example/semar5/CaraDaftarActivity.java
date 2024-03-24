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
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
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
import com.example.semar5.ModelResponse.TampilCaraDaftar;
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

public class CaraDaftarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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
        setContentView(R.layout.activity_cara_daftar);

        //header logo
        ImageView imageView = findViewById(R.id.homesemar);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CaraDaftarActivity.this, MainActivity.class);
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

        Call<List<TampilCaraDaftar>> call = api.getDataCaraDaftar();
        call.enqueue(new Callback<List<TampilCaraDaftar>>() {
            @Override
            public void onResponse(Call<List<TampilCaraDaftar>> call, Response<List<TampilCaraDaftar>> response) {
                if (response.isSuccessful()) {
                    List<TampilCaraDaftar> dataItems = response.body();

                    if (dataItems != null && !dataItems.isEmpty()) {

                        TextView tvCaraDaftar_1 = findViewById(R.id.hyperlink_sso);
                        TextView tvCaraDaftar_2 = findViewById(R.id.hyperlink_profil);
                        TextView tvCaraDaftar_3 = findViewById(R.id.hyperlink_home);
                        TextView tvCaraDaftar_4 = findViewById(R.id.tv_caradaftar4);
                        TextView tvCaraDaftar_5 = findViewById(R.id.hyperlink_booknow);
                        TextView tvCaraDaftar_6 = findViewById(R.id.hyperlink_history);

                        for (TampilCaraDaftar item : dataItems) {
                            if (item.getId() == 1) {
                                tvCaraDaftar_1.setText(item.getCara_daftar());
                            } else if (item.getId() == 2) {
                                tvCaraDaftar_2.setText(item.getCara_daftar());
                            } else if (item.getId() == 3) {
                                tvCaraDaftar_3.setText(item.getCara_daftar());
                            } else if (item.getId() == 4) {
                                tvCaraDaftar_4.setText(item.getCara_daftar());
                            } else if (item.getId() == 5) {
                                tvCaraDaftar_5.setText(item.getCara_daftar());
                            } else if (item.getId() == 6) {
                                tvCaraDaftar_6.setText(item.getCara_daftar());
                            }
                        }

                    } else {
                        Toast.makeText(CaraDaftarActivity.this, "Data tidak tersedia", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CaraDaftarActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TampilCaraDaftar>> call, Throwable t) {
                Toast.makeText(CaraDaftarActivity.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });


        //hyperlink url
        TextView sso1 = findViewById(R.id.hyperlink_sso);
        sso1.setMovementMethod(LinkMovementMethod.getInstance());


        //hyperlink home
        TextView home = findViewById(R.id.hyperlink_home);
        String texthome = "03. Pilih kamar di halaman Home sesuai jenis kelamin. Kamar tidak akan tampil jika memilih kamar tidak sesuai jenis kelamin.";
        SpannableString home1 = new SpannableString(texthome);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent2 = new Intent(CaraDaftarActivity.this, MainActivity.class);
                startActivity(intent2);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };

        home1.setSpan(clickableSpan1, 27, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        home.setText(home1);
        home.setMovementMethod(LinkMovementMethod.getInstance());


        //hyperlink booking sekarang
        TextView booknow = findViewById(R.id.hyperlink_booknow);
        String textbooknow = "05. Tekan tombol Daftar Sekarang untuk menampilkan detail pesanan.";
        SpannableString booknow1 = new SpannableString(textbooknow);
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent3 = new Intent(CaraDaftarActivity.this, BookingSekarang.class);
                startActivity(intent3);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };

        booknow1.setSpan(clickableSpan2, 17, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        booknow.setText(booknow1);
        booknow.setMovementMethod(LinkMovementMethod.getInstance());


        //hyperlink proses pesanan
        TextView proses = findViewById(R.id.hyperlink_history);
        String textproses = "06. Setelah tampil detail pesanan, tekan tombol Proses Pesanan Saya dan lanjutkan proses pembayaran";
        SpannableString proses1 = new SpannableString(textproses);
        ClickableSpan clickableSpan3 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent4 = new Intent(CaraDaftarActivity.this, HistoryActivity.class);
                startActivity(intent4);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };

        proses1.setSpan(clickableSpan3, 48, 67, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        proses.setText(proses1);
        proses.setMovementMethod(LinkMovementMethod.getInstance());



        //layout
        //sesuaikan activity
        drawerLayout = findViewById(R.id.drawer_layout_cara_booking);
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

        edit = findViewById(R.id.edit_caradaftar);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CaraDaftarActivity.this, CaraDaftarEdit.class);
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
        if (itemId == R.id.nav_carabooking) {
        } else if (itemId == R.id.nav_home) {
            Intent intent1 = new Intent(CaraDaftarActivity.this, MainActivity.class);
            startActivity(intent1);
        } else if (itemId == R.id.nav_galeri) {
            Intent intent2 = new Intent(CaraDaftarActivity.this, GaleriActivity.class);
            startActivity(intent2);
        } else if (itemId == R.id.nav_snk) {
            Intent intent3 = new Intent(CaraDaftarActivity.this, SnKActivity.class);
            startActivity(intent3);
        } else if (itemId == R.id.nav_login) {
            Intent intent4 = new Intent(CaraDaftarActivity.this, Login.class);
            startActivity(intent4);
        } else if (itemId == R.id.nav_daftar) {
            Intent intent5 = new Intent(CaraDaftarActivity.this, Daftar.class);
            startActivity(intent5);
        } else if (itemId == R.id.nav_helpdesk) {
            Intent intent6 = new Intent(CaraDaftarActivity.this, Helpdesk.class);
            startActivity(intent6);
        } else if (itemId == R.id.nav_about) {
            Intent intent7 = new Intent(CaraDaftarActivity.this, About.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_data_mahasiswa) {
            Intent intent7 = new Intent(CaraDaftarActivity.this, DataMahasiswa.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_ubahpass) {
            Intent intent7 = new Intent(CaraDaftarActivity.this, UbahPassword.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_daftar_kamar) {
            Intent intent8 = new Intent(CaraDaftarActivity.this, DaftarKamar.class);
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
        Intent intent = new Intent(CaraDaftarActivity.this, Login.class);
        startActivity(intent);
        finishAffinity();
    }

}