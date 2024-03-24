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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.semar5.ModelResponse.Pemesanan;
import com.example.semar5.ModelResponse.PemesananResponse;
import com.example.semar5.ModelResponse.User;
import com.example.semar5.Retrofit.Api;
import com.example.semar5.Retrofit.RetrofitClient;
import com.example.semar5.Retrofit.SharedPreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataMahasiswaTampil extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static final String EXTRA_MAHASISWA_ID_DETAIL = "extra_mahasiswa_id";
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
        setContentView(R.layout.activity_data_mahasiswa_tampil);

        //header logo
        ImageView imageView = findViewById(R.id.homesemar);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenttoolbar = new Intent(DataMahasiswaTampil.this, MainActivity.class);
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
        drawerLayout = findViewById(R.id.drawer_layout_datamahasiswa_tampil);
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
        if (intent != null && intent.hasExtra(EXTRA_MAHASISWA_ID_DETAIL)) {
            mahasiswaId = intent.getIntExtra(EXTRA_MAHASISWA_ID_DETAIL, -1);
            Log.d("Debug", "Received idMahasiswa: " + mahasiswaId);

            fetchPemesananData(mahasiswaId);
        } else {
            Toast.makeText(this, "Invalid Mahasiswa ID", Toast.LENGTH_SHORT).show();
        }


        Intent intent1 = getIntent();
        int idPemesanan = intent1.getIntExtra("idPemesanan", -1);
        String namaPemesan = intent1.getStringExtra("namaPemesan");
        String jalur = intent1.getStringExtra("jalur");
        String opsiPembayaran = intent1.getStringExtra("opsiPembayaran");
        String jumlahPenghuni = intent1.getStringExtra("jumlahPenghuni");
        String jenisKelamin = intent1.getStringExtra("jenisKelamin");
        String gedung = intent1.getStringExtra("gedung");
        String kamar = intent1.getStringExtra("kamar");
        String harga = intent1.getStringExtra("harga");
        String tanggalMasuk = intent1.getStringExtra("tanggalMasuk");
        String tanggalKeluar = intent1.getStringExtra("tanggalKeluar");


        Button edit = findViewById(R.id.edit_datamhs);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DataMahasiswaTampil.this, DataMahasiswaUpdateKamar.class);
                intent.putExtra("idMahasiswa", mahasiswaId);
                Log.d("API_RESPONSE", "idMahasiswa: " + mahasiswaId);
                intent.putExtra("idPemesanan", idPemesanan);
                Log.d("API_RESPONSE", "idPemesanan: " + idPemesanan);

                intent.putExtra("namaPemesan1", namaPemesan);
                intent.putExtra("jalur1", jalur);
                intent.putExtra("opsiPembayaran1", opsiPembayaran);
                intent.putExtra("jumlahPenghuni1", jumlahPenghuni);
                intent.putExtra("jenisKelamin1", jenisKelamin);
                intent.putExtra("gedung1", gedung);
                intent.putExtra("kamar1", kamar);
                intent.putExtra("harga1", harga);
                intent.putExtra("tanggalMasuk1", tanggalMasuk);
                intent.putExtra("tanggalKeluar1", tanggalKeluar);
                startActivity(intent);
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


    //memanggil data pesanan mahasiswa dari database
    private void fetchPemesananData(int idMahasiswa) {

        RetrofitClient retrofitClient = RetrofitClient.getInstance(this);
        Api api = retrofitClient.getApi();

        Call<PemesananResponse> call = api.getPemesananData(idMahasiswa);
        call.enqueue(new Callback<PemesananResponse>() {
            @Override
            public void onResponse(Call<PemesananResponse> call, Response<PemesananResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PemesananResponse pemesananResponse = response.body();

                    if (pemesananResponse.getError().equals("200")) {
                        List<Pemesanan> pemesananList = pemesananResponse.getPemesananList();
                        if (!pemesananList.isEmpty()) {
                            Pemesanan pemesanan = pemesananList.get(0);

                            LinearLayout listmahasiswa = findViewById(R.id.data_list);
                            listmahasiswa.setVisibility(View.VISIBLE);

                            TextView textnull = findViewById(R.id.null_datamahasiswa);
                            textnull.setVisibility(View.GONE);

                            TextView namaLengkapTextView = findViewById(R.id.mhs_namalengkap);
                            TextView jalurTextView = findViewById(R.id.mhs_jalur);
                            TextView opsiPembayaranTextView = findViewById(R.id.mhs_opsipembayaran);
                            TextView jumlahPenghuniTextView = findViewById(R.id.mhs_jumlahpenghuni);
                            TextView jenisKelaminTextView = findViewById(R.id.mhs_jeniskelamin);
                            TextView gedungTextView = findViewById(R.id.mhs_gedung);
                            TextView kamarTextView = findViewById(R.id.mhs_kamar);
                            TextView hargaTextView = findViewById(R.id.mhs_harga);
                            TextView tanggalMasukTextView = findViewById(R.id.mhs_tanggalmasuk);
                            TextView tanggalKeluarTextView = findViewById(R.id.mhs_tanggalkeluar);

                            namaLengkapTextView.setText(pemesanan.getNamaMahasiswa());
                            jalurTextView.setText(pemesanan.getJalur());
                            opsiPembayaranTextView.setText(pemesanan.getOpsiPembayaran());
                            jumlahPenghuniTextView.setText(pemesanan.getJumlahPenghuni());
                            jenisKelaminTextView.setText(pemesanan.getJenisKelamin());
                            gedungTextView.setText(pemesanan.getGedung());
                            kamarTextView.setText(pemesanan.getKodeKamar());
                            hargaTextView.setText(pemesanan.getHarga());

                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                            String tanggalMasukFormatted = dateFormat.format(pemesanan.getTanggalMasuk());
                            String tanggalKeluarFormatted = dateFormat.format(pemesanan.getTanggalKeluar());

                            tanggalMasukTextView.setText(tanggalMasukFormatted);
                            tanggalKeluarTextView.setText(tanggalKeluarFormatted);
                        }

                    } else {
                        LinearLayout listmahasiswa = findViewById(R.id.data_list);
                        listmahasiswa.setVisibility(View.GONE);

                        TextView textnull = findViewById(R.id.null_datamahasiswa);
                        textnull.setVisibility(View.VISIBLE);

                        Toast.makeText(DataMahasiswaTampil.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Mahasiswa object is null");
                    }

                } else {
                    Log.e(TAG, "Mahasiswa object is null");
                    Log.e(TAG, "Error: " + response.message());
                    Toast.makeText(DataMahasiswaTampil.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PemesananResponse> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
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
            Intent intent1 = new Intent(DataMahasiswaTampil.this, GaleriActivity.class);
            startActivity(intent1);
        } else if (itemId == R.id.nav_carabooking) {
            Intent intent2 = new Intent(DataMahasiswaTampil.this, CaraDaftarActivity.class);
            startActivity(intent2);
        } else if (itemId == R.id.nav_snk) {
            Intent intent3 = new Intent(DataMahasiswaTampil.this, SnKActivity.class);
            startActivity(intent3);
        } else if (itemId == R.id.nav_login) {
            Intent intent4 = new Intent(DataMahasiswaTampil.this, Login.class);
            startActivity(intent4);
        } else if (itemId == R.id.nav_daftar) {
            Intent intent5 = new Intent(DataMahasiswaTampil.this, Daftar.class);
            startActivity(intent5);
        } else if (itemId == R.id.nav_helpdesk) {
            Intent intent6 = new Intent(DataMahasiswaTampil.this, Helpdesk.class);
            startActivity(intent6);
        } else if (itemId == R.id.nav_about) {
            Intent intent7 = new Intent(DataMahasiswaTampil.this, About.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_data_mahasiswa) {
            Intent intent7 = new Intent(DataMahasiswaTampil.this, DataMahasiswa.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_ubahpass) {
            Intent intent7 = new Intent(DataMahasiswaTampil.this, UbahPassword.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_daftar_kamar) {
            Intent intent8 = new Intent(DataMahasiswaTampil.this, DaftarKamar.class);
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
        Intent intent = new Intent(DataMahasiswaTampil.this, Login.class);
        startActivity(intent);
        finishAffinity();
    }

}