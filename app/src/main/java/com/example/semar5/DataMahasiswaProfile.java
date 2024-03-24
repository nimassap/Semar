package com.example.semar5;

import static android.content.ContentValues.TAG;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.semar5.CRUD.Mahasiswa;
import com.example.semar5.CRUD.MahasiswaProfileResponse;
import com.example.semar5.CRUD.MahasiswaResponse;
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

public class DataMahasiswaProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static final String EXTRA_MAHASISWA_ID = "extra_mahasiswa_id";
    private int mahasiswaId;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private TextView namaprofile;
    private SharedPreferenceManager sharedPreferenceManager;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_mahasiswa_profile);

        //header logo
        ImageView imageView = findViewById(R.id.homesemar);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenttoolbar = new Intent(DataMahasiswaProfile.this, MainActivity.class);
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
        drawerLayout = findViewById(R.id.drawer_layout_datamahasiswa_profile);
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
        if (intent != null && intent.hasExtra(EXTRA_MAHASISWA_ID)) {
            mahasiswaId = intent.getIntExtra(EXTRA_MAHASISWA_ID, -1);

            fetchProfileData(mahasiswaId);
        } else {
            Toast.makeText(this, "Invalid Mahasiswa ID", Toast.LENGTH_SHORT).show();
        }



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

    private void fetchProfileData(int mahasiswaId) {
        RetrofitClient retrofitClient = RetrofitClient.getInstance(this);
        Api apiService = retrofitClient.getApi();

        Call<MahasiswaProfileResponse> call = apiService.getMahasiswaDataId(mahasiswaId);
        call.enqueue(new Callback<MahasiswaProfileResponse>() {
            @Override
            public void onResponse(Call<MahasiswaProfileResponse> call, Response<MahasiswaProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MahasiswaProfileResponse mahasiswaResponse = response.body();

                    if (mahasiswaResponse.getError().equals("200")){
                        Mahasiswa mahasiswa = mahasiswaResponse.getMahasiswa();
                        LinearLayout listmahasiswa = findViewById(R.id.data_list);
                        listmahasiswa.setVisibility(View.VISIBLE);

                        TextView et_email = findViewById(R.id.profile_email);
                        TextView et_pass = findViewById(R.id.profile_password);
                        TextView et_namalengkap = findViewById(R.id.profile_namalengkap);
                        TextView et_nim = findViewById(R.id.profile_nim);
                        TextView et_fakultas = findViewById(R.id.profile_fakultas);
                        TextView et_prodi = findViewById(R.id.profile_prodi);
                        TextView et_tgllahir = findViewById(R.id.tl_teks);
                        TextView et_agama = findViewById(R.id.profile_agama);
                        TextView et_jk = findViewById(R.id.profile_jk);
                        TextView et_alamat = findViewById(R.id.profile_alamat);
                        TextView et_notelp = findViewById(R.id.profile_notelp);
                        TextView et_namaortu = findViewById(R.id.profile_namaortu);
                        TextView et_pekerjaanortu = findViewById(R.id.profile_pekerjaanortu);
                        TextView et_alamatortu = findViewById(R.id.alamat_ortu);
                        TextView et_notelportu = findViewById(R.id.profile_notelportu);

                        et_email.setText(mahasiswa.getEmail());
                        et_pass.setText(mahasiswa.getPassword());
                        et_namalengkap.setText(mahasiswa.getNamaMahasiswa());
                        et_nim.setText(mahasiswa.getNim());
                        et_fakultas.setText(mahasiswa.getNamaFakultas());
                        et_prodi.setText(mahasiswa.getNamaProdi());
                        et_tgllahir.setText(mahasiswa.getTglLahir());
                        et_agama.setText(mahasiswa.getAgama());
                        et_jk.setText(mahasiswa.getJenisKelamin());
                        et_alamat.setText(mahasiswa.getAlamat());
                        et_notelp.setText(mahasiswa.getNoTelp());
                        et_namaortu.setText(mahasiswa.getNamaOrangtua());
                        et_pekerjaanortu.setText(mahasiswa.getPekerjaanOrangtua());
                        et_alamatortu.setText(mahasiswa.getAlamatOrangtua());
                        et_notelportu.setText(mahasiswa.getNoTelpOrangtua());

                        String email = et_email.getText().toString();
                        String pass = et_pass.getText().toString();
                        String namaMhs = et_namalengkap.getText().toString();
                        String nim = et_nim.getText().toString();
                        String fakultas = et_fakultas.getText().toString();
                        String prodi = et_prodi.getText().toString();
                        String tl = et_tgllahir.getText().toString();
                        String agama = et_agama.getText().toString();
                        String jk = et_jk.getText().toString();
                        String alamat = et_alamat.getText().toString();
                        String notelp = et_notelp.getText().toString();
                        String namaOrtu = et_namaortu.getText().toString();
                        String pekerjaanOrtu = et_pekerjaanortu.getText().toString();
                        String alamatOrtu = et_alamatortu.getText().toString();
                        String notelpOrtu = et_notelportu.getText().toString();


                        Button editdataprofile = findViewById(R.id.data_kamarmhs_edit);
                        editdataprofile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent1 = new Intent(DataMahasiswaProfile.this, DataMahasiswaUpdate.class);
                                intent1.putExtra(DataMahasiswaUpdate.EXTRA_MAHASISWA_ID_PROFILE, mahasiswaId);
                                intent1.putExtra("email", email);
                                intent1.putExtra("pass", pass);
                                intent1.putExtra("namaMhs", namaMhs);
                                intent1.putExtra("nim", nim);
                                intent1.putExtra("fakultas", fakultas);
                                intent1.putExtra("prodi", prodi);
                                intent1.putExtra("tl", tl);
                                intent1.putExtra("agama", agama);
                                intent1.putExtra("jk", jk);
                                intent1.putExtra("alamat", alamat);
                                intent1.putExtra("notelp", notelp);
                                intent1.putExtra("namaOrtu", namaOrtu);
                                intent1.putExtra("pekerjaanOrtu", pekerjaanOrtu);
                                intent1.putExtra("alamatOrtu", alamatOrtu);
                                intent1.putExtra("notelpOrtu", notelpOrtu);
                                startActivity(intent1);
                            }
                        });


                        Button dataprofile = findViewById(R.id.data_kamarmhs);
                        dataprofile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent1 = new Intent(DataMahasiswaProfile.this, DataMahasiswaKamar.class);
                                intent1.putExtra(DataMahasiswaKamar.EXTRA_MAHASISWA_ID_PROFILE, mahasiswaId);
                                intent1.putExtra("email", email);
                                intent1.putExtra("namaMhs", namaMhs);
                                startActivity(intent1);
                            }
                        });

                    } else {
                        LinearLayout listmahasiswa = findViewById(R.id.data_list);
                        listmahasiswa.setVisibility(View.GONE);

                        Toast.makeText(DataMahasiswaProfile.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Mahasiswa object is null");
                    }
                } else {
                    Log.e(TAG, "Mahasiswa object is null");
                    Log.e(TAG, "Error: " + response.message());
                    Toast.makeText(DataMahasiswaProfile.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MahasiswaProfileResponse> call, Throwable t) {
                Log.e("RetrofitError", "Error: " + t.getMessage());
                Toast.makeText(DataMahasiswaProfile.this, "Kesalahan Jaringan. Gagal mengambil data", Toast.LENGTH_SHORT).show();
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
            Intent intent1 = new Intent(DataMahasiswaProfile.this, GaleriActivity.class);
            startActivity(intent1);
        } else if (itemId == R.id.nav_carabooking) {
            Intent intent2 = new Intent(DataMahasiswaProfile.this, CaraDaftarActivity.class);
            startActivity(intent2);
        } else if (itemId == R.id.nav_snk) {
            Intent intent3 = new Intent(DataMahasiswaProfile.this, SnKActivity.class);
            startActivity(intent3);
        } else if (itemId == R.id.nav_login) {
            Intent intent4 = new Intent(DataMahasiswaProfile.this, Login.class);
            startActivity(intent4);
        } else if (itemId == R.id.nav_daftar) {
            Intent intent5 = new Intent(DataMahasiswaProfile.this, Daftar.class);
            startActivity(intent5);
        } else if (itemId == R.id.nav_helpdesk) {
            Intent intent6 = new Intent(DataMahasiswaProfile.this, Helpdesk.class);
            startActivity(intent6);
        } else if (itemId == R.id.nav_about) {
            Intent intent7 = new Intent(DataMahasiswaProfile.this, About.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_data_mahasiswa) {
            Intent intent7 = new Intent(DataMahasiswaProfile.this, DataMahasiswa.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_ubahpass) {
            Intent intent7 = new Intent(DataMahasiswaProfile.this, UbahPassword.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_daftar_kamar) {
            Intent intent8 = new Intent(DataMahasiswaProfile.this, DaftarKamar.class);
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
        Intent intent = new Intent(DataMahasiswaProfile.this, Login.class);
        startActivity(intent);
        finishAffinity();
    }
}