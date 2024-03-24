package com.example.semar5;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.semar5.Adapter.NotificationAdapterUser;
import com.example.semar5.ModelResponse.Gedung;
import com.example.semar5.ModelResponse.Harga;
import com.example.semar5.ModelResponse.Jalur;
import com.example.semar5.ModelResponse.JenisKelamin;
import com.example.semar5.ModelResponse.JumlahPenghuni;
import com.example.semar5.ModelResponse.Kamar;
import com.example.semar5.ModelResponse.NotificationModel;
import com.example.semar5.ModelResponse.OpsiPembayaran;
import com.example.semar5.ModelResponse.Pemesanan;
import com.example.semar5.ModelResponse.User;
import com.example.semar5.Retrofit.Api;
import com.example.semar5.Retrofit.RetrofitClient;
import com.example.semar5.Retrofit.SharedPreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingSekarang extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    private TextView namapemesan, namaprofile;
    private SharedPreferenceManager sharedPreferenceManager;
    private User user;
    Pemesanan pemesanan;
    private Spinner spinnerJalur;
    private Spinner spinnerOpsiPembayaran;
    private Spinner spinnerJumlahPenghuni;
    private Spinner spinnerJenisKelamin;
    private Spinner spinnerGedung;
    private Spinner spinnerKamar;

    private Api api;

    private List<Jalur> jalurList;
    private List<OpsiPembayaran> opsiPembayaranList;
    private List<JumlahPenghuni> jumlahPenghuniList;
    private List<JenisKelamin> jenisKelaminList;
    private List<Gedung> gedungList;
    private List<Kamar> kamarList;

    private ArrayAdapter<Jalur> jalurAdapter;
    private ArrayAdapter<OpsiPembayaran> opsiPembayaranAdapter;
    private ArrayAdapter<JumlahPenghuni> jumlahPenghuniAdapter;
    private ArrayAdapter<JenisKelamin> jenisKelaminAdapter;
    private ArrayAdapter<Gedung> gedungAdapter;
    private ArrayAdapter<Kamar> kamarAdapter;
    List<Pemesanan> notificationAdmin = new ArrayList<>();
    private List<NotificationModel> notificationList = new ArrayList<>();
    private NotificationAdapterUser notificationAdapter;
    private List<Pemesanan> daftarPemesanan = new ArrayList<>();
    private List<String> bookedRoomCodes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_sekarang);

        //header logo
        ImageView imageView = findViewById(R.id.homesemar);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingSekarang.this, MainActivity.class);
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


        //api retrofit untuk memanggil database
        api = RetrofitClient.getInstance(getApplicationContext()).getApi();

        //fetch nama pemesan
        sharedPreferenceManager = SharedPreferenceManager.getInstance(this);
        namapemesan = findViewById(R.id.nama_pemesan);
        user = sharedPreferenceManager.getUser();
        namapemesan.setText(user.getNama_mahasiswa());

        //spinner
        spinnerJalur = findViewById(R.id.spinner_jalur);
        spinnerOpsiPembayaran = findViewById(R.id.spinner_opsipembayaran);
        spinnerJumlahPenghuni = findViewById(R.id.spinner_jmlpenghuni);
        spinnerJenisKelamin = findViewById(R.id.spinner_jk);
        spinnerGedung = findViewById(R.id.spinner_gedung);
        spinnerKamar = findViewById(R.id.spinner_kamar);

        jalurAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        jalurAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJalur.setAdapter(jalurAdapter);

        opsiPembayaranAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        opsiPembayaranAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOpsiPembayaran.setAdapter(opsiPembayaranAdapter);

        jumlahPenghuniAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        jumlahPenghuniAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJumlahPenghuni.setAdapter(jumlahPenghuniAdapter);

        jenisKelaminAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        jenisKelaminAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJenisKelamin.setAdapter(jenisKelaminAdapter);

        gedungAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        gedungAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGedung.setAdapter(gedungAdapter);

        kamarAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        kamarAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKamar.setAdapter(kamarAdapter);

        //spinner
        //jalur
        spinnerJalur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Jalur selectedJalur = jalurAdapter.getItem(position);
                spinnerOpsiPembayaran.setSelection(0);
                clearHarga();
                if (selectedJalur != null) {

                    //opsi pembayaran
                    spinnerOpsiPembayaran.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                            OpsiPembayaran selectedOpsiPembayaran = opsiPembayaranAdapter.getItem(position);
                            spinnerJumlahPenghuni.setSelection(0);
                            clearHarga();
                            if (selectedOpsiPembayaran != null) {

                                //jumlah penghuni
                                spinnerJumlahPenghuni.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        JumlahPenghuni selectedJumlahPenghuni = jumlahPenghuniAdapter.getItem(position);
                                        spinnerJenisKelamin.setSelection(0);
                                        clearHarga();
                                        if (selectedJumlahPenghuni != null){

                                            //jenis kelamin
                                            spinnerJenisKelamin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                    JenisKelamin selectedJenisKelamin = jenisKelaminAdapter.getItem(position);
                                                    spinnerGedung.setSelection(0);
                                                    clearHarga();
                                                    if (selectedJenisKelamin != null){

                                                        //gedung
                                                        spinnerGedung.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                            @Override
                                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                                Gedung selectedGedung = gedungAdapter.getItem(position);
                                                                spinnerKamar.setSelection(0);
                                                                clearHarga();
                                                                if (selectedGedung != null){

                                                                    //kamar
                                                                    spinnerKamar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                        @Override
                                                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                                            Kamar selectedKamar = kamarAdapter.getItem(position);
                                                                            clearHarga();
                                                                            if (selectedKamar != null) {
                                                                                String opsiPembayaran = spinnerOpsiPembayaran.getSelectedItem().toString();
                                                                                String jumlahPenghuni = spinnerJumlahPenghuni.getSelectedItem().toString();
                                                                                String kodeKamar = selectedKamar.getKode_kamar();
                                                                                fetchHarga(opsiPembayaran, jumlahPenghuni, kodeKamar);
                                                                            }
                                                                        }

                                                                        @Override
                                                                        public void onNothingSelected(AdapterView<?> parent) {
                                                                        }
                                                                    });

                                                                    fetchKamar(selectedJumlahPenghuni.getJumlah_penghuni(), selectedGedung.getGedung());
                                                                }
                                                            }

                                                            @Override
                                                            public void onNothingSelected(AdapterView<?> parent) {

                                                            }
                                                        });
                                                        fetchGedung(selectedJenisKelamin.getJenisKelamin());
                                                    }
                                                }

                                                @Override
                                                public void onNothingSelected(AdapterView<?> parent) {

                                                }
                                            });
                                            fetchJenisKelamin(selectedJumlahPenghuni.getJumlah_penghuni());
                                        }

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                                //jumlah penghuni
                                fetchJumlahPenghuni(selectedOpsiPembayaran.getOpsiPembayaran());
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });

                    fetchOpsiPembayaran(selectedJalur.getJalur());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        fetchJalurData();

        //tanggal check-in dan check-out
        TextView checkinTextView = findViewById(R.id.checkin_teks);
        TextView checkoutTextView = findViewById(R.id.checkout_teks);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date checkInDate = calendar.getTime();
        String tanggalMasuk = dateFormat.format(checkInDate);

        checkinTextView.setText(tanggalMasuk);

        calendar.add(Calendar.YEAR, 1);
        Date checkOutDate = calendar.getTime();
        String tanggalKeluar = dateFormat.format(checkOutDate);

        checkoutTextView.setText(tanggalKeluar);

        notificationAdapter = new NotificationAdapterUser(notificationList);

        //button daftar
        Button daftarsekarang = findViewById(R.id.daftar_sekarang);
        daftarsekarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferenceManager.isLoggedIn()) {

                    Jalur selectedJalur = jalurAdapter.getItem(spinnerJalur.getSelectedItemPosition());
                    OpsiPembayaran selectedOpsiPembayaran = opsiPembayaranAdapter.getItem(spinnerOpsiPembayaran.getSelectedItemPosition());
                    JumlahPenghuni selectedJumlahPenghuni = jumlahPenghuniAdapter.getItem(spinnerJumlahPenghuni.getSelectedItemPosition());
                    JenisKelamin selectedJenisKelamin = jenisKelaminAdapter.getItem(spinnerJenisKelamin.getSelectedItemPosition());
                    Gedung selectedGedung = gedungAdapter.getItem(spinnerGedung.getSelectedItemPosition());
                    Kamar selectedKamar = kamarAdapter.getItem(spinnerKamar.getSelectedItemPosition());

                    if (selectedJalur.getJalur().equals("-- Pilih Jalur --") ||
                            selectedOpsiPembayaran.getOpsiPembayaran().equals("-- Pilih Opsi Pembayaran --") ||
                            selectedJumlahPenghuni.getJumlah_penghuni().equals("-- Pilih Jumlah Penghuni --") ||
                            selectedJenisKelamin.getJenisKelamin().equals("-- Pilih Jenis Kelamin --") ||
                            selectedGedung.getGedung().equals("-- Pilih Gedung --") ||
                            selectedKamar.getKode_kamar().equals("-- Pilih Kamar --")) {

                        Toast.makeText(getApplicationContext(), "Lengkapi Semua Opsi", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    TextView nama = findViewById(R.id.nama_pemesan);
                    String namapemesan = nama.getText().toString();
                    TextView harga = findViewById(R.id.harga_kamar);
                    String id_harga = harga.getText().toString();
                    simpanDataPemesanan(namapemesan, selectedJalur, selectedOpsiPembayaran, selectedJumlahPenghuni, selectedJenisKelamin, selectedGedung, selectedKamar, id_harga);
                } else {
                    Toast.makeText(getApplicationContext(), "Silahkan Login Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //layout
        //sesuaikan activity
        drawerLayout = findViewById(R.id.drawer_layout_bookingsekarang);
        navigationView = findViewById(R.id.navdrawer_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        //hide or show item side bar navigation
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

    //memanggil retrofit untuk spinner
    //jalur
    private Jalur selectedJalur;
    private void fetchJalurData() {
        Call<List<Jalur>> call = api.getJalurData();
        call.enqueue(new Callback<List<Jalur>>() {
            @Override
            public void onResponse(Call<List<Jalur>> call, retrofit2.Response<List<Jalur>> response) {
                if (response.isSuccessful()) {
                    jalurList = response.body();
                    if (jalurList != null) {
                        Jalur selectJalur = new Jalur();
                        selectJalur.setIdJalur(-1);
                        selectJalur.setJalur("-- Pilih Jalur --");
                        jalurList.add(0, selectJalur);

                        selectedJalur = null;
                        jalurAdapter.clear();
                        jalurAdapter.addAll(jalurList);

                        if (selectedJalur != null) {
                            fetchOpsiPembayaran(selectedJalur.getJalur());
                        }
                    }
                } else {
                    Toast.makeText(BookingSekarang.this, "Failed to fetch Jalur data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Jalur>> call, Throwable t) {
                Toast.makeText(BookingSekarang.this, "Failed to fetch Jalur data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //opsi pembayaran
    private OpsiPembayaran selectedOpsiPembayaran;
    private void fetchOpsiPembayaran(String selectedJalur) {
        Call<List<OpsiPembayaran>> call = api.getOpsiPembayaranData(selectedJalur);
        call.enqueue(new Callback<List<OpsiPembayaran>>() {
            @Override
            public void onResponse(Call<List<OpsiPembayaran>> call, retrofit2.Response<List<OpsiPembayaran>> response) {
                if (response.isSuccessful()) {
                    opsiPembayaranList = response.body();
                    if (opsiPembayaranList != null) {
                        OpsiPembayaran selectOpsiPembayaran = new OpsiPembayaran();
                        selectOpsiPembayaran.setIdOpsiPembayaran(-1);
                        selectOpsiPembayaran.setOpsiPembayaran("-- Pilih Opsi Pembayaran --");
                        opsiPembayaranList.add(0, selectOpsiPembayaran);

                        selectedOpsiPembayaran = null;
                        opsiPembayaranAdapter.clear();
                        opsiPembayaranAdapter.addAll(opsiPembayaranList);

                        if (selectedOpsiPembayaran != null) {
                            fetchJumlahPenghuni(selectedOpsiPembayaran.getOpsiPembayaran());
                        }

                    }
                } else {
                    Toast.makeText(BookingSekarang.this, "Failed to fetch Opsi Pembayaran data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<OpsiPembayaran>> call, Throwable t) {
                Toast.makeText(BookingSekarang.this, "Failed to fetch Opsi Pembayaran data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //jumlah penghuni
    private JumlahPenghuni selectedJumlahPenghuni;
    private void fetchJumlahPenghuni(String selectedOpsiPembayaran) {
        Call<List<JumlahPenghuni>> call = api.getJumlahPenghuniData(selectedOpsiPembayaran);
        call.enqueue(new Callback<List<JumlahPenghuni>>() {
            @Override
            public void onResponse(Call<List<JumlahPenghuni>> call, Response<List<JumlahPenghuni>> response) {
                if (response.isSuccessful()) {
                    jumlahPenghuniList = response.body();
                    if (jumlahPenghuniList != null) {
                        JumlahPenghuni selectJumlahPenghuni = new JumlahPenghuni();
                        selectJumlahPenghuni.setId_jumlahpenghuni(-1);
                        selectJumlahPenghuni.setJumlah_penghuni("-- Pilih Jumlah Penghuni --");
                        jumlahPenghuniList.add(0, selectJumlahPenghuni);

                        selectedJumlahPenghuni = null;
                        jumlahPenghuniAdapter.clear();
                        jumlahPenghuniAdapter.addAll(jumlahPenghuniList);

                        if (selectedJumlahPenghuni != null) {
                            fetchJenisKelamin(selectedJumlahPenghuni.getJumlah_penghuni());
                        }
                    }
                } else {
                    Toast.makeText(BookingSekarang.this, "Failed to fetch Jumlah Penghuni data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<JumlahPenghuni>> call, Throwable t) {
                Toast.makeText(BookingSekarang.this, "Failed to fetch Jumlah Penghuni data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //jenis kelamin
    private JenisKelamin selectedJenisKelamin;
    private void fetchJenisKelamin(String selectedJumlahPenghuni) {
        Call<List<JenisKelamin>> call = api.getJenisKelaminData(selectedJumlahPenghuni);
        call.enqueue(new Callback<List<JenisKelamin>>() {
            @Override
            public void onResponse(Call<List<JenisKelamin>> call, retrofit2.Response<List<JenisKelamin>> response) {
                if (response.isSuccessful()) {
                    jenisKelaminList = response.body();
                    if (jenisKelaminList != null) {
                        JenisKelamin selectJenisKelamin = new JenisKelamin();
                        selectJenisKelamin.setIdJenisKelamin(-1);
                        selectJenisKelamin.setJenisKelamin("-- Pilih Jenis Kelamin --");
                        jenisKelaminList.add(0, selectJenisKelamin);

                        selectedJenisKelamin = null;
                        jenisKelaminAdapter.clear();
                        jenisKelaminAdapter.addAll(jenisKelaminList);

                        if (selectedJenisKelamin != null) {
                            fetchGedung(selectedJenisKelamin.getJenisKelamin());
                        }
                    }
                } else {
                    Toast.makeText(BookingSekarang.this, "Failed to fetch Jalur data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<JenisKelamin>> call, Throwable t) {
                Toast.makeText(BookingSekarang.this, "Failed to fetch Jalur data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //gedung
    private Gedung selectedGedung;
    private void fetchGedung(String selectedJenisKelamin) {
        Call<List<Gedung>> call = api.getGedungData(selectedJenisKelamin);
        call.enqueue(new Callback<List<Gedung>>() {
            @Override
            public void onResponse(Call<List<Gedung>> call, retrofit2.Response<List<Gedung>> response) {
                if (response.isSuccessful()) {
                    gedungList = response.body();
                    if (gedungList != null) {
                        Gedung selectGedung = new Gedung();
                        selectGedung.setIdGedung(-1);
                        selectGedung.setGedung("-- Pilih Gedung --");
                        gedungList.add(0, selectGedung);

                        selectedGedung = null;
                        gedungAdapter.clear();
                        gedungAdapter.addAll(gedungList);
                    }
                } else {
                    Toast.makeText(BookingSekarang.this, "Failed to fetch Opsi Pembayaran data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Gedung>> call, Throwable t) {
                Toast.makeText(BookingSekarang.this, "Failed to fetch Opsi Pembayaran data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //kamar
    private Kamar selectedKamar;
    private void fetchKamar(String selectedJumlahPenghuni, String selectedGedung) {
        Call<List<Kamar>> call = api.getKamarData(selectedJumlahPenghuni, selectedGedung);
        call.enqueue(new Callback<List<Kamar>>() {
            @Override
            public void onResponse(Call<List<Kamar>> call, retrofit2.Response<List<Kamar>> response) {
                if (response.isSuccessful()) {
                    kamarList = response.body();
                    if (kamarList != null) {
                        Kamar selectKamar = new Kamar();
                        selectKamar.setId_kamar(-1);
                        selectKamar.setKode_kamar("-- Pilih Kamar --");
                        kamarList.add(0, selectKamar);

                        selectedKamar = null;
                        kamarAdapter.clear();
                        kamarAdapter.addAll(kamarList);
                    }
                } else {
                    Toast.makeText(BookingSekarang.this, "Failed to fetch Opsi Pembayaran data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Kamar>> call, Throwable t) {
                Toast.makeText(BookingSekarang.this, "Failed to fetch Opsi Pembayaran data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //harga
    private void fetchHarga(String selectedOpsiPembayaran, String selectedJumlahPenghuni, String selectedKodeKamar) {
        Call<List<Harga>> call = api.getHargaData(selectedOpsiPembayaran, selectedJumlahPenghuni, selectedKodeKamar);
        call.enqueue(new Callback<List<Harga>>() {
            @Override
            public void onResponse(Call<List<Harga>> call, Response<List<Harga>> response) {
                if (response.isSuccessful()) {
                    List<Harga> hargaList = response.body();
                    if (hargaList != null && !hargaList.isEmpty()) {
                        Harga harga = hargaList.get(0);

                        String hargaValue = harga.getHarga();

                        String hargaKamarString = String.valueOf(hargaValue);
                        TextView tv_harga = findViewById(R.id.harga_kamar);
                        tv_harga.setText(hargaKamarString);
                    }

                } else {
                    Toast.makeText(BookingSekarang.this, "Failed to fetch Jumlah Penghuni data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Harga>> call, Throwable t) {
                Toast.makeText(BookingSekarang.this, "Failed to fetch Jumlah Penghuni data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //menyimpan data pemesanan kedalam database
    private void simpanDataPemesanan(String namapemesan, Jalur selectedJalur, OpsiPembayaran selectedOpsiPembayaran,
                                     JumlahPenghuni selectedJumlahPenghuni, JenisKelamin selectedJenisKelamin,
                                     Gedung selectedGedung, Kamar selectedKamar, String id_harga) {

        String userEmail = sharedPreferenceManager.getUser().getEmail_mahasiswa();
        user = sharedPreferenceManager.getUser();

        TextView checkinTextView = findViewById(R.id.checkin_teks);
        TextView checkoutTextView = findViewById(R.id.checkout_teks);
        String tanggalMasuk = checkinTextView.getText().toString();
        String tanggalKeluar = checkoutTextView.getText().toString();

        RetrofitClient retrofitClient = RetrofitClient.getInstance(this);
        Api api = retrofitClient.getApi();

        Call<ResponseBody> call = api.simpanDataPemesanan(
                userEmail,
                namapemesan,
                selectedJalur.getJalur(),
                selectedOpsiPembayaran.getOpsiPembayaran(),
                selectedJumlahPenghuni.getJumlah_penghuni(),
                selectedJenisKelamin.getJenisKelamin(),
                selectedGedung.getGedung(),
                selectedKamar.getKode_kamar(),
                id_harga,
                tanggalMasuk,
                tanggalKeluar
        );

        Log.d("API_CALL", "Sending data:\n" +
                "email: " + userEmail + "\n" +
                "nama: " + namapemesan + "\n" +
                "selectedJalur: " + selectedJalur + "\n" +
                "selectedOpsiPembayaran: " + selectedOpsiPembayaran + "\n" +
                "selectedJumlahPenghuni: " + selectedJumlahPenghuni + "\n" +
                "selectedJenisKelamin: " + selectedJenisKelamin + "\n" +
                "selectedGedung: " + selectedGedung + "\n" +
                "selectedKamar: " + selectedKamar + "\n" +
                "harga: " + id_harga + "\n" +
                "tanggalMasuk: " + tanggalMasuk + "\n" +
                "tanggalKeluar: " + tanggalKeluar);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();

                        Toast.makeText(BookingSekarang.this, "Berhasil Melakukan Pendaftaran", Toast.LENGTH_SHORT).show();

                        Log.d("NOTIFICATION_LOG", "Sending success notification to user");
                        SharedPreferences sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPrefs.edit();
                        editor.putBoolean("notification_pending", true);
                        editor.apply();
                        showSuccessNotification();

                        Intent intent = new Intent(BookingSekarang.this, NotifikasiActivity.class);

                        intent.putExtra("namaPemesan", namapemesan);
                        intent.putExtra("jalur", selectedJalur.getJalur());
                        intent.putExtra("opsiPembayaran", selectedOpsiPembayaran.getOpsiPembayaran());
                        intent.putExtra("jumlahPenghuni", selectedJumlahPenghuni.getJumlah_penghuni());
                        intent.putExtra("jenisKelamin", selectedJenisKelamin.getJenisKelamin());
                        intent.putExtra("gedung", selectedGedung.getGedung());
                        intent.putExtra("kamar", selectedKamar.getKode_kamar());
                        intent.putExtra("harga", id_harga);
                        intent.putExtra("tanggalMasuk", tanggalMasuk);
                        intent.putExtra("tanggalKeluar", tanggalKeluar);

                        intent.putExtra("registration_success", true);

                        startActivity(intent);

                        bookedRoomCodes.add(selectedKamar.getKode_kamar());

                        Log.d(TAG, "Response Body: " + responseBody.toString());
                        Log.d(TAG, "Response Code: " + response.code());


                        Log.d("DEBUG_LOG", "bookedRoomCodes after pemesanan: " + bookedRoomCodes.toString());
                        Log.d("DEBUG_LOG", "kamarList after pemesanan: " + kamarList.toString());
                        Log.d("DEBUG_LOG", "selectedKamar after pemesanan: " + (selectedKamar != null ? selectedKamar.getKode_kamar() : "null"));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(BookingSekarang.this, "Gagal Melakukan Pendaftaran", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(BookingSekarang.this, "Kesalahan Jaringan" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //reset value untuk harga
    private void clearHarga() {
        TextView tv_harga = findViewById(R.id.harga_kamar);
        tv_harga.setText("");
    }

    private void sendSuccessNotificationToAdmin() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "Your_Channel_ID";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Your Channel Name",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.logosemar1)
                .setContentTitle("Pendaftar Baru")
                .setContentText("Cek riwayat pendaftaran untuk melihat daftar pendaftar baru")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Menyiapkan Intent untuk membuka HistoryActivity
        Intent historyIntent = new Intent(this, HistoryActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                historyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        // Menambahkan tindakan pada notifikasi
        builder.setContentIntent(pendingIntent);

        // Menghilangkan notifikasi saat notifikasi di-klik
        builder.setAutoCancel(true);

        // Show the notification
        notificationManager.notify(1, builder.build()); // Use a unique ID for each notification
    }

    private void showSuccessNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "Your_Channel_ID";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create a notification channel
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Your Channel Name",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.logosemar1)
                .setContentTitle("Pendaftaran Berhasil")
                .setContentText("Selamat! Pendaftaran Anda telah berhasil.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Menyiapkan Intent untuk membuka HistoryActivity
        Intent historyIntent = new Intent(this, HistoryActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                historyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        // Menambahkan tindakan pada notifikasi
        builder.setContentIntent(pendingIntent);

        // Menghilangkan notifikasi saat notifikasi di-klik
        builder.setAutoCancel(true);

        // Show the notification
        notificationManager.notify(2, builder.build()); // Use a unique ID for each notification
    }

    //tombol back
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
        if (itemId == R.id.nav_carabooking) {
        } else if (itemId == R.id.nav_home) {
            Intent intent1 = new Intent(BookingSekarang.this, MainActivity.class);
            startActivity(intent1);
        } else if (itemId == R.id.nav_galeri) {
            Intent intent2 = new Intent(BookingSekarang.this, GaleriActivity.class);
            startActivity(intent2);
        } else if (itemId == R.id.nav_snk) {
            Intent intent3 = new Intent(BookingSekarang.this, SnKActivity.class);
            startActivity(intent3);
        } else if (itemId == R.id.nav_login) {
            Intent intent4 = new Intent(BookingSekarang.this, Login.class);
            startActivity(intent4);
        } else if (itemId == R.id.nav_daftar) {
            Intent intent5 = new Intent(BookingSekarang.this, Daftar.class);
            startActivity(intent5);
        } else if (itemId == R.id.nav_helpdesk) {
            Intent intent6 = new Intent(BookingSekarang.this, Helpdesk.class);
            startActivity(intent6);
        } else if (itemId == R.id.nav_about) {
            Intent intent7 = new Intent(BookingSekarang.this, About.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_data_mahasiswa) {
            Intent intent7 = new Intent(BookingSekarang.this, DataMahasiswa.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_ubahpass) {
            Intent intent7 = new Intent(BookingSekarang.this, UbahPassword.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_daftar_kamar) {
            Intent intent8 = new Intent(BookingSekarang.this, DaftarKamar.class);
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
        Intent intent = new Intent(BookingSekarang.this, Login.class);
        startActivity(intent);
        finishAffinity();
    }
}