package com.example.semar5;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.semar5.Adapter.NotificationAdapterAdmin;
import com.example.semar5.Adapter.NotificationAdapterUser;
import com.example.semar5.ModelResponse.NotificationModel;
import com.example.semar5.ModelResponse.Pemesanan;
import com.example.semar5.ModelResponse.PemesananResponse;
import com.example.semar5.ModelResponse.User;
import com.example.semar5.Retrofit.Api;
import com.example.semar5.Retrofit.RetrofitClient;
import com.example.semar5.Retrofit.SharedPreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotifikasiActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private TextView namaprofile;
    private SharedPreferenceManager sharedPreferenceManager;
    private User user;
    private NotificationAdapterUser notificationAdapterUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi);

        //header logo
        ImageView imageView = findViewById(R.id.homesemar);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotifikasiActivity.this, MainActivity.class);
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



        SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager.getInstance(this);
        SharedPreferenceManager.SharedPreferenceHelper sharedPreferenceHelper = sharedPreferenceManager.new SharedPreferenceHelper(this);

        boolean registrationSuccess = getIntent().getBooleanExtra("registration_success", false);

        List<NotificationModel> combinedNotifications = new ArrayList<>();

        if (registrationSuccess && sharedPreferenceManager.isLoggedIn()) {

            int mahasiswaId = sharedPreferenceManager.getUser().getId_mahasiswa();
            int userRoleId = sharedPreferenceManager.getUser().getId_role();

            List<NotificationModel> adminNotifications = sharedPreferenceHelper.getNotifications(1, mahasiswaId);
            List<NotificationModel> userNotifications = sharedPreferenceHelper.getNotifications(2, mahasiswaId);

            if (userRoleId == 1) {

            } else if (userRoleId == 2) {
                userNotifications.add(new NotificationModel(
                        "Pendaftaran Berhasil",
                        "Selamat! Pendaftaran kamar telah berhasil. \n\nKLIK DISINI untuk melanjutkan",
                        getCurrentTime())
                );

                sharedPreferenceHelper.saveNotifications(2, mahasiswaId, userNotifications);
                Log.d("Debug", "User notifications saved: " + userNotifications.size());
            }



            combinedNotifications.addAll(adminNotifications);
            combinedNotifications.addAll(userNotifications);

            RecyclerView recyclerView = findViewById(R.id.recylerview_notifikasi);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            notificationAdapterUser = new NotificationAdapterUser(combinedNotifications);

            notificationAdapterUser.setOnItemClickListener(new NotificationAdapterUser.OnItemClickListener() {
                @Override
                public void onItemClick(NotificationModel notification) {
                    Intent intent = new Intent(NotifikasiActivity.this, HistoryActivity.class);
                    startActivity(intent);
                }
            });

            recyclerView.setAdapter(notificationAdapterUser);

            Collections.sort(combinedNotifications, new Comparator<NotificationModel>() {
                @Override
                public int compare(NotificationModel n1, NotificationModel n2) {
                    return n2.getTime().compareTo(n1.getTime());
                }
            });

            notificationAdapterUser.notifyDataSetChanged();
        }



        //sesuaikan activity
        drawerLayout = findViewById(R.id.drawer_layout_notifikasi);
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
        bottomNavigationView.setSelectedItemId(R.id.bottom_notif);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.bottom_notif) {
                    startActivity(new Intent(getApplicationContext(), NotifikasiActivity.class));
                    overridePendingTransition(R.anim.slide_right_effect, R.anim.slide_left_effect);
                    return true;
                } else if (itemId == R.id.bottom_home) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
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



    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager.getInstance(this);
        SharedPreferenceManager.SharedPreferenceHelper sharedPreferenceHelper = sharedPreferenceManager.new SharedPreferenceHelper(this);

        int mahasiswaId = sharedPreferenceManager.getUser().getId_mahasiswa();
        int userRoleId = sharedPreferenceManager.getUser().getId_role();

        List<NotificationModel> adminNotifications = sharedPreferenceHelper.getNotifications(1, mahasiswaId);
        List<NotificationModel> userNotifications = sharedPreferenceHelper.getNotifications(2, mahasiswaId);

        List<NotificationModel> displayedNotifications;

        if (userRoleId == 1) {
            displayedNotifications = adminNotifications;
        } else if (userRoleId == 2) {
            displayedNotifications = userNotifications;
        } else {
            displayedNotifications = new ArrayList<>();
        }

        RecyclerView recyclerView = findViewById(R.id.recylerview_notifikasi);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notificationAdapterUser = new NotificationAdapterUser(displayedNotifications);
        notificationAdapterUser.setOnItemClickListener(new NotificationAdapterUser.OnItemClickListener() {
            @Override
            public void onItemClick(NotificationModel notification) {
                Intent intent = new Intent(NotifikasiActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(notificationAdapterUser);

        Collections.sort(displayedNotifications, new Comparator<NotificationModel>() {
            @Override
            public int compare(NotificationModel n1, NotificationModel n2) {
                return n2.getTime().compareTo(n1.getTime());
            }
        });

        notificationAdapterUser.notifyDataSetChanged();
    }



    private String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy   HH:mm:ss", Locale.getDefault());
        return dateFormat.format(new Date());
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
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            Intent intent1 = new Intent(NotifikasiActivity.this, MainActivity.class);
            startActivity(intent1);
        } else if (itemId == R.id.nav_carabooking) {
            Intent intent2 = new Intent(NotifikasiActivity.this, CaraDaftarActivity.class);
            startActivity(intent2);
        } else if (itemId == R.id.nav_galeri) {
            Intent intent3 = new Intent(NotifikasiActivity.this, GaleriActivity.class);
            startActivity(intent3);
        } else if (itemId == R.id.nav_snk) {
            Intent intent4 = new Intent(NotifikasiActivity.this, SnKActivity.class);
            startActivity(intent4);
        } else if (itemId == R.id.nav_login) {
            Intent intent5 = new Intent(NotifikasiActivity.this, Login.class);
            startActivity(intent5);
        } else if (itemId == R.id.nav_daftar) {
            Intent intent6 = new Intent(NotifikasiActivity.this, Daftar.class);
            startActivity(intent6);
        } else if (itemId == R.id.nav_helpdesk) {
            Intent intent7 = new Intent(NotifikasiActivity.this, Helpdesk.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_about) {
            Intent intent8 = new Intent(NotifikasiActivity.this, About.class);
            startActivity(intent8);
        } else if (itemId == R.id.nav_data_mahasiswa) {
            Intent intent7 = new Intent(NotifikasiActivity.this, DataMahasiswa.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_ubahpass) {
            Intent intent7 = new Intent(NotifikasiActivity.this, UbahPassword.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_daftar_kamar) {
            Intent intent8 = new Intent(NotifikasiActivity.this, DaftarKamar.class);
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
        Intent intent = new Intent(NotifikasiActivity.this, Login.class);
        startActivity(intent);
        finishAffinity();
    }

}