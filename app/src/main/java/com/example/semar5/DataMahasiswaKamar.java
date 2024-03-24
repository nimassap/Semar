package com.example.semar5;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
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
import com.example.semar5.CRUD.DataMahasiswaDetailAdapter;
import com.example.semar5.ModelResponse.Pemesanan;
import com.example.semar5.ModelResponse.PemesananResponse;
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

public class DataMahasiswaKamar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static final String EXTRA_MAHASISWA_ID_PROFILE = "extra_mahasiswa_id_profile";
    private int mahasiswaId;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private TextView namaprofile;
    private SharedPreferenceManager sharedPreferenceManager;
    private User user;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_mahasiswa_kamar);

        //header logo
        ImageView imageView = findViewById(R.id.homesemar);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenttoolbar = new Intent(DataMahasiswaKamar.this, MainActivity.class);
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


        //layout
        drawerLayout = findViewById(R.id.drawer_layout_datamahasiswa_detail);
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
        navigationView.setCheckedItem(R.id.nav_data_mahasiswa);



        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_MAHASISWA_ID_PROFILE)) {
            mahasiswaId = intent.getIntExtra(EXTRA_MAHASISWA_ID_PROFILE, -1);

            fetchPemesananData(mahasiswaId);
        } else {
            Toast.makeText(this, "Invalid Mahasiswa ID", Toast.LENGTH_SHORT).show();
        }


        Intent intent1 = getIntent();
        String namaMhs = intent1.getStringExtra("namaMhs");
        String email = intent1.getStringExtra("email");


        TextView tambah = findViewById(R.id.tambah_datamhs_kamar);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(DataMahasiswaKamar.this, DataMahasiswaKamarTambah.class);
                intent2.putExtra(DataMahasiswaKamarTambah.EXTRA_MAHASISWA_ID_KAMAR_TAMBAH, mahasiswaId);
                intent2.putExtra("namaMhs", namaMhs);
                intent2.putExtra("email", email);
                startActivity(intent2);
            }
        });


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


    //memanggil data pesanan mahasiswa dari database
    private void fetchPemesananData(int idMahasiswa) {

        sharedPreferenceManager = SharedPreferenceManager.getInstance(this);
        User user = sharedPreferenceManager.getUser();

        recyclerView = findViewById(R.id.recyclerview_detailpesanan_mhs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RetrofitClient retrofitClient = RetrofitClient.getInstance(this);
        Api api = retrofitClient.getApi();

        Call<PemesananResponse> call = api.getPemesananData(idMahasiswa);
        call.enqueue(new Callback<PemesananResponse>() {
            @Override
            public void onResponse(Call<PemesananResponse> call, Response<PemesananResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PemesananResponse pemesananResponse = response.body();
                    Log.d("Debug", "API Response: " + pemesananResponse.toString());

                    if (pemesananResponse.getError().equals("200")) {
                        List<Pemesanan> pemesananList = pemesananResponse.getPemesananList();

                        if (!pemesananList.isEmpty()) {
                            Log.d("Debug", "belumBayarList size: " + pemesananList.size());
                            String imageBaseUrl = "https://semarundip23.000webhostapp.com/qris/"; // Your image base URL
                            DataMahasiswaDetailAdapter adapter = new DataMahasiswaDetailAdapter(pemesananList, imageBaseUrl);
                            recyclerView.setAdapter(adapter);

                            recyclerView.setVisibility(View.VISIBLE);

                            TextView textnull = findViewById(R.id.null_datamahasiswa);
                            textnull.setVisibility(View.GONE);
                        }

                    } else {
                        //Toast.makeText(BelumBayarFragment.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Mahasiswa object is null");

                        recyclerView.setVisibility(View.GONE);

                        TextView textnull = findViewById(R.id.null_datamahasiswa);
                        textnull.setVisibility(View.VISIBLE);
                    }

                } else {
                    Log.e(TAG, "Mahasiswa object is null");
                    Log.e(TAG, "API Response is null or not successful");
                    Log.e(TAG, "Error: " + response.message());
                    //Toast.makeText(BelumBayarFragment.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                    recyclerView.setVisibility(View.GONE);

                    TextView textnull = findViewById(R.id.null_datamahasiswa);
                    textnull.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<PemesananResponse> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
                recyclerView.setVisibility(View.GONE);

                TextView textnull = findViewById(R.id.null_datamahasiswa);
                textnull.setVisibility(View.VISIBLE);
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
        } else if (itemId == R.id.nav_galeri) {
            Intent intent1 = new Intent(DataMahasiswaKamar.this, GaleriActivity.class);
            startActivity(intent1);
        } else if (itemId == R.id.nav_carabooking) {
            Intent intent2 = new Intent(DataMahasiswaKamar.this, CaraDaftarActivity.class);
            startActivity(intent2);
        } else if (itemId == R.id.nav_snk) {
            Intent intent3 = new Intent(DataMahasiswaKamar.this, SnKActivity.class);
            startActivity(intent3);
        } else if (itemId == R.id.nav_login) {
            Intent intent4 = new Intent(DataMahasiswaKamar.this, Login.class);
            startActivity(intent4);
        } else if (itemId == R.id.nav_daftar) {
            Intent intent5 = new Intent(DataMahasiswaKamar.this, Daftar.class);
            startActivity(intent5);
        } else if (itemId == R.id.nav_helpdesk) {
            Intent intent6 = new Intent(DataMahasiswaKamar.this, Helpdesk.class);
            startActivity(intent6);
        } else if (itemId == R.id.nav_about) {
            Intent intent7 = new Intent(DataMahasiswaKamar.this, About.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_data_mahasiswa) {
            Intent intent7 = new Intent(DataMahasiswaKamar.this, DataMahasiswa.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_ubahpass) {
            Intent intent7 = new Intent(DataMahasiswaKamar.this, UbahPassword.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_daftar_kamar) {
            Intent intent8 = new Intent(DataMahasiswaKamar.this, DaftarKamar.class);
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
        Intent intent = new Intent(DataMahasiswaKamar.this, Login.class);
        startActivity(intent);
        finishAffinity();
    }

}