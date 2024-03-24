package com.example.semar5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.semar5.ModelResponse.Fakultas;
import com.example.semar5.ModelResponse.Prodi;
import com.example.semar5.ModelResponse.ProfileMahasiswaResponse;
import com.example.semar5.ModelResponse.TambahDataMhsResponse;
import com.example.semar5.ModelResponse.User;
import com.example.semar5.Retrofit.Api;
import com.example.semar5.Retrofit.RetrofitClient;
import com.example.semar5.Retrofit.SharedPreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

public class DataMahasiswaTambah extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DatePickerDialog.OnDateSetListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private TextView namaprofile;
    private SharedPreferenceManager sharedPreferenceManager;
    private User user;
    private Spinner spinnerFakultas;
    private Spinner spinnerProdi;
    private List<Fakultas> fakultasList;
    private List<Prodi> prodiList;
    private ArrayAdapter<Fakultas> fakultasAdapter;
    private ArrayAdapter<Prodi> prodiAdapter;
    private Api api;
    private int selectedFakultasId = -1;
    private int selectedProdiId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_mahasiswa_tambah);

        //header logo
        ImageView imageView = findViewById(R.id.homesemar);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenttoolbar = new Intent(DataMahasiswaTambah.this, MainActivity.class);
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


        //spinner fakultas & prodi
        spinnerFakultas = findViewById(R.id.spinner_fakultas);
        spinnerProdi = findViewById(R.id.spinner_prodi);

        fakultasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        fakultasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFakultas.setAdapter(fakultasAdapter);

        prodiAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        prodiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProdi.setAdapter(prodiAdapter);

        api = RetrofitClient.getInstance(getApplicationContext()).getApi();

        //spinner
        //fakultas
        spinnerFakultas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Fakultas selectedFakultas = (Fakultas) adapterView.getItemAtPosition(position);
                spinnerProdi.setSelection(0);
                if (selectedFakultas != null) {

                    //prodi
                    spinnerProdi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                            Prodi selectedProdi = (Prodi) adapterView.getItemAtPosition(position);
                            if (selectedProdi != null) {
                                selectedProdiId = selectedProdi.getIdProdi();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            selectedProdiId = -1;
                        }
                    });
                    selectedFakultasId = selectedFakultas.getIdFakultas();
                    fetchProdi(selectedFakultas.getNamaFakultas());


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedFakultasId = -1;
            }
        });
        fetchFakultas();



        //date picker
        ImageView tl = (ImageView) findViewById(R.id.profilepick_tl);
        tl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment_TL();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });



        //button tambah data mahasiswa
        Button tambah = findViewById(R.id.tambah_datamhs);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tambahDataMhs();
            }
        });



        //layout
        drawerLayout = findViewById(R.id.drawer_layout_datamahasiswa);
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




    //fakultas
    private Fakultas selectedFakultas;
    private void fetchFakultas() {
        Call<List<Fakultas>> call = api.getFakultasData();
        call.enqueue(new Callback<List<Fakultas>>() {
            @Override
            public void onResponse(Call<List<Fakultas>> call, retrofit2.Response<List<Fakultas>> response) {
                if (response.isSuccessful()) {
                    fakultasList = response.body();
                    if (fakultasList != null) {
                        Fakultas selectFakultas = new Fakultas();
                        selectFakultas.setIdFakultas(-1);
                        selectFakultas.setNamaFakultas("-- Pilih Fakultas --");
                        fakultasList.add(0, selectFakultas);

                        selectedFakultas = null;
                        fakultasAdapter.clear();
                        fakultasAdapter.addAll(fakultasList);

                        if (selectedFakultas != null) {
                            fetchProdi(selectedFakultas.getNamaFakultas());
                        }
                    }
                } else {
                    Toast.makeText(DataMahasiswaTambah.this, "Gagal mengambil data Fakultas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Fakultas>> call, Throwable t) {
                Toast.makeText(DataMahasiswaTambah.this, "Kesalahan jaringan : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //prodi
    private Prodi selectedProdi;
    private void fetchProdi(String selectedFakultas) {
        Call<List<Prodi>> call = api.getProdiData(selectedFakultas);
        call.enqueue(new Callback<List<Prodi>>() {
            @Override
            public void onResponse(Call<List<Prodi>> call, retrofit2.Response<List<Prodi>> response) {
                if (response.isSuccessful()) {
                    prodiList = response.body();
                    if (prodiList != null) {
                        Prodi selectProdi = new Prodi();
                        selectProdi.setIdProdi(-1);
                        selectProdi.setNamaProdi("-- Pilih Program Studi --");
                        prodiList.add(0, selectProdi);

                        selectedProdi = null;
                        prodiAdapter.clear();
                        prodiAdapter.addAll(prodiList);

                        if (selectedProdi != null) {
                            //fetchProdi(selectedFakultas,selectedProdi.getNamaProdi());
                        }

                    }
                } else {
                    Toast.makeText(DataMahasiswaTambah.this, "Gagal mengambil data Program Studi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Prodi>> call, Throwable t) {
                Toast.makeText(DataMahasiswaTambah.this, "Kesalahan jaringan : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //tanggal lahir
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance().format(calendar.getTime());

        EditText tl = (EditText) findViewById(R.id.tl_teks);
        tl.setText(currentDateString);
    }



    private void tambahDataMhs() {

        EditText et_email = findViewById(R.id.profile_email);
        EditText et_pass = findViewById(R.id.profile_password);
        EditText et_namalengkap = findViewById(R.id.profile_namalengkap);
        EditText et_nim = findViewById(R.id.profile_nim);
        EditText et_tgllahir = findViewById(R.id.tl_teks);
        EditText et_agama = findViewById(R.id.profile_agama);
        EditText et_jk = findViewById(R.id.profile_jk);
        EditText et_alamat = findViewById(R.id.profile_alamat);
        EditText et_notelp = findViewById(R.id.profile_notelp);
        EditText et_namaortu = findViewById(R.id.profile_namaortu);
        EditText et_pekerjaanortu = findViewById(R.id.profile_pekerjaanortu);
        EditText et_alamatortu = findViewById(R.id.alamat_ortu);
        EditText et_notelportu = findViewById(R.id.profile_notelportu);

        String email = et_email.getText().toString().trim();
        String pass = et_pass.getText().toString().trim();
        String nama = et_namalengkap.getText().toString().trim();
        String nim = et_nim.getText().toString().trim();
        String tgl_lahir = et_tgllahir.getText().toString().trim();
        String agama = et_agama.getText().toString().trim();
        String jeniskel = et_jk.getText().toString().trim();
        String alamat = et_alamat.getText().toString().trim();
        String nohp = et_notelp.getText().toString().trim();
        String namaortu = et_namaortu.getText().toString().trim();
        String pekerjaanortu = et_pekerjaanortu.getText().toString().trim();
        String alamatortu = et_alamatortu.getText().toString().trim();
        String nohportu = et_notelportu.getText().toString().trim();


        if (email.isEmpty()){
            et_email.setError("Masukkan email");
            et_email.requestFocus();
            return;
        }
        if (pass.isEmpty()){
            et_pass.setError("Masukkan password");
            et_pass.requestFocus();
            return;
        }
        if (nama.isEmpty()){
            et_namalengkap.setError("Masukkan nama");
            et_namalengkap.requestFocus();
            return;
        }
        if (nim.isEmpty()){
            et_nim.setError("Masukkan nim");
            et_nim.requestFocus();
            return;
        }
        if (tgl_lahir.isEmpty()){
            et_tgllahir.setError("Masukkan tanggal lahir");
            et_tgllahir.requestFocus();
            return;
        }
        if (agama.isEmpty()){
            et_agama.setError("Masukkan agama");
            et_agama.requestFocus();
            return;
        }
        if (jeniskel.isEmpty()){
            et_jk.setError("Masukkan jenis kelamin");
            et_jk.requestFocus();
            return;
        }
        if (alamat.isEmpty()){
            et_alamat.setError("Masukkan alamat");
            et_alamat.requestFocus();
            return;
        }
        if (nohp.isEmpty()){
            et_notelp.setError("Masukkan nomor telepon");
            et_notelp.requestFocus();
            return;
        }
        if (namaortu.isEmpty()){
            et_namaortu.setError("Masukkan nama orang tua");
            et_namaortu.requestFocus();
            return;
        }
        if (pekerjaanortu.isEmpty()){
            et_pekerjaanortu.setError("Masukkan pekerjaan orang tua");
            et_pekerjaanortu.requestFocus();
            return;
        }
        if (alamatortu.isEmpty()){
            et_alamatortu.setError("Masukkan alamat orang tua");
            et_alamatortu.requestFocus();
            return;
        }
        if (nohportu.isEmpty()){
            et_notelportu.setError("Masukkan nomor telepon orang tua");
            et_notelportu.requestFocus();
            return;
        }

        RetrofitClient retrofitClient = RetrofitClient.getInstance(this);
        Api api = retrofitClient.getApi();



        Call <TambahDataMhsResponse> call = api.tambahDataMhs(email, pass, nama, nim, selectedFakultasId, selectedProdiId, tgl_lahir, agama, jeniskel, alamat, nohp, namaortu, pekerjaanortu, alamatortu, nohportu);
        call.enqueue(new Callback<TambahDataMhsResponse>() {
            @Override
            public void onResponse(Call<TambahDataMhsResponse> call, retrofit2.Response<TambahDataMhsResponse> response) {
                TambahDataMhsResponse update = response.body();
                if (response.isSuccessful()){
                    if (update.getError().equals("200")){
                        int idMahasiswa = user.getId_mahasiswa();
                        Toast.makeText(DataMahasiswaTambah.this, update.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DataMahasiswaTambah.this, DataMahasiswaTambahKamar.class);
                        intent.putExtra(DataMahasiswaTambahKamar.EXTRA_MAHASISWA_ID, idMahasiswa);
                        intent.putExtra("namaPemesan", nama);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        //finish();
                    }
                    else {
                        Toast.makeText(DataMahasiswaTambah.this, update.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(DataMahasiswaTambah.this, "Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TambahDataMhsResponse> call, Throwable t) {
                Toast.makeText(DataMahasiswaTambah.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
            Intent intent1 = new Intent(DataMahasiswaTambah.this, GaleriActivity.class);
            startActivity(intent1);
        } else if (itemId == R.id.nav_carabooking) {
            Intent intent2 = new Intent(DataMahasiswaTambah.this, CaraDaftarActivity.class);
            startActivity(intent2);
        } else if (itemId == R.id.nav_snk) {
            Intent intent3 = new Intent(DataMahasiswaTambah.this, SnKActivity.class);
            startActivity(intent3);
        } else if (itemId == R.id.nav_login) {
            Intent intent4 = new Intent(DataMahasiswaTambah.this, Login.class);
            startActivity(intent4);
        } else if (itemId == R.id.nav_daftar) {
            Intent intent5 = new Intent(DataMahasiswaTambah.this, Daftar.class);
            startActivity(intent5);
        } else if (itemId == R.id.nav_helpdesk) {
            Intent intent6 = new Intent(DataMahasiswaTambah.this, Helpdesk.class);
            startActivity(intent6);
        } else if (itemId == R.id.nav_about) {
            Intent intent7 = new Intent(DataMahasiswaTambah.this, About.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_data_mahasiswa) {
            Intent intent7 = new Intent(DataMahasiswaTambah.this, DataMahasiswa.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_ubahpass) {
            Intent intent7 = new Intent(DataMahasiswaTambah.this, UbahPassword.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_daftar_kamar) {
            Intent intent8 = new Intent(DataMahasiswaTambah.this, DaftarKamar.class);
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
        Intent intent = new Intent(DataMahasiswaTambah.this, Login.class);
        startActivity(intent);
        finishAffinity();
    }

}