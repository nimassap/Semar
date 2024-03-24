package com.example.semar5;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.semar5.ModelResponse.BuktiBayarList;
import com.example.semar5.ModelResponse.Fakultas;
import com.example.semar5.ModelResponse.FotoProfileList;
import com.example.semar5.ModelResponse.Jalur;
import com.example.semar5.ModelResponse.LoginResponse;
import com.example.semar5.ModelResponse.OpsiPembayaran;
import com.example.semar5.ModelResponse.Prodi;
import com.example.semar5.ModelResponse.ProfileMahasiswa;
import com.example.semar5.ModelResponse.ProfileMahasiswaResponse;
import com.example.semar5.ModelResponse.Role;
import com.example.semar5.ModelResponse.TampilDeluxe1;
import com.example.semar5.ModelResponse.User;
import com.example.semar5.Retrofit.Api;
import com.example.semar5.Retrofit.RetrofitClient;
import com.example.semar5.Retrofit.SharedPreferenceManager;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.Manifest;

public class ProfileActivityAfter1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DatePickerDialog.OnDateSetListener, View.OnClickListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    CircleImageView fotoprofile;
    TextView uploadpp;
    private TextView namaprofile;
    private SharedPreferenceManager sharedPreferenceManager;
    private User user;
    EditText et_namalengkap, et_role, et_nim, et_tgllahir, et_agama, et_alamat, et_notelp, et_jk, et_namaortu, et_pekerjaanortu, et_alamatortu, et_notelportu;
    Button save_profile;
    private static final int REQUEST_PERMISSION_AND_IMAGE_PICK = 1;
    //private Uri imageUri;
    private Uri imageUri = Uri.EMPTY;
    private int userId;
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("imageUri", imageUri.toString());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_after1);

        Log.d("ProfileActivityAfter1", "onCreate called");

        sharedPreferenceManager = SharedPreferenceManager.getInstance(getApplicationContext());

        et_namalengkap = findViewById(R.id.profile_namalengkap);
        et_nim = findViewById(R.id.profile_nim);
        et_tgllahir = findViewById(R.id.tl_teks);
        et_agama = findViewById(R.id.profile_agama);
        et_jk = findViewById(R.id.profile_jk);
        et_alamat = findViewById(R.id.profile_alamat);
        et_notelp = findViewById(R.id.profile_notelp);
        et_namaortu = findViewById(R.id.profile_namaortu);
        et_pekerjaanortu = findViewById(R.id.profile_pekerjaanortu);
        et_alamatortu = findViewById(R.id.alamat_ortu);
        et_notelportu = findViewById(R.id.profile_notelportu);


        String email = sharedPreferenceManager.getUser().getEmail_mahasiswa();


        et_namalengkap.setText(sharedPreferenceManager.getUser().getNama_mahasiswa());
        et_nim.setText(sharedPreferenceManager.getUser().getNim_mahasiswa());
        et_tgllahir.setText(sharedPreferenceManager.getUser().getTgl_lahir_mahasiswa());
        et_agama.setText(sharedPreferenceManager.getUser().getAgama_mahasiswa());
        et_jk.setText(sharedPreferenceManager.getUser().getJenis_kelamin_mahasiswa());
        et_alamat.setText(sharedPreferenceManager.getUser().getAlamat_mahasiswa());
        et_notelp.setText(sharedPreferenceManager.getUser().getNo_telp_mahasiswa());
        et_namaortu.setText(sharedPreferenceManager.getUser().getNama_orangtua());
        et_pekerjaanortu.setText(sharedPreferenceManager.getUser().getPekerjaan_orangtua());
        et_alamatortu.setText(sharedPreferenceManager.getUser().getAlamat_orangtua());
        et_notelportu.setText(sharedPreferenceManager.getUser().getNotelp_orangtua());



        save_profile = findViewById(R.id.save_profile);
        save_profile.setOnClickListener(this);



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





        //header logo
        ImageView imageView = findViewById(R.id.helpdesk_profile);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenthelpdeskprofil = new Intent(ProfileActivityAfter1.this, Helpdesk.class);
                startActivity(intenthelpdeskprofil);
            }
        });



        //foto profile
        fotoprofile = findViewById(R.id.profile_image1);
        uploadpp = findViewById(R.id.upload_pp);
        uploadpp.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                // Jika izin belum diberikan, minta izin
                ActivityCompat.requestPermissions(ProfileActivityAfter1.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION_AND_IMAGE_PICK);
            } else {
                // Izin sudah diberikan, buka picker gambar
                openImagePicker();
            }
        });


        if (savedInstanceState != null) {
            String savedImageUri = savedInstanceState.getString("imageUri");
            if (savedImageUri != null) {
                imageUri = Uri.parse(savedImageUri);
            }
        }


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




        //sesuaikan activity
        drawerLayout = findViewById(R.id.drawer_layout_profileafter);
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


        //date picker
        ImageView tl = (ImageView) findViewById(R.id.profilepick_tl);
        tl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment_TL();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });



        //bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_profile);
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

        fetchProfileData();

        imageUri = getStoredImageUriFromSharedPreferences(userId);
    }


    private void fetchProfileData() {
        int fakultasIndex = findFakultasIndex(selectedFakultasId);
        if (fakultasIndex != -1) {
            spinnerFakultas.setSelection(fakultasIndex);
        } else {
            spinnerFakultas.setSelection(0);
        }

        int prodiIndex = findProdiIndex(selectedProdiId);
        if (prodiIndex != -1) {
            spinnerProdi.setSelection(prodiIndex);
        } else {
            spinnerProdi.setSelection(0);
        }
    }

    private int findFakultasIndex(int fakultasId) {
        for (int i = 0; i < fakultasAdapter.getCount(); i++) {
            Fakultas fakultas = fakultasAdapter.getItem(i);
            if (fakultas.getIdFakultas() == fakultasId) {
                return i;
            }
        }
        return -1;
    }

    private int findProdiIndex(int prodiId) {
        for (int i = 0; i < prodiAdapter.getCount(); i++) {
            Prodi prodi = prodiAdapter.getItem(i);
            if (prodi.getIdProdi() == prodiId) {
                return i;
            }
        }
        return -1;
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
                    Log.d("FakultasData", "Jumlah Fakultas: " + fakultasList.size());
                    Log.d("FakultasData", "Jumlah Item di Spinner: " + fakultasAdapter.getCount());

                    if (fakultasList != null) {
                        sharedPreferenceManager = SharedPreferenceManager.getInstance(getApplicationContext());
                        int userFakultasId = sharedPreferenceManager.getUser().getid_fakultas();
                        Log.d("UserFakultasId", "User Fakultas ID: " + userFakultasId);
                        Fakultas selectedUserFakultas = null;



                        for (Fakultas fakultas : fakultasList) {
                            Log.d("FakultasData", "Fakultas ID: " + fakultas.getIdFakultas());
                            if (fakultas.getIdFakultas() == userFakultasId) {
                                selectedUserFakultas = fakultas;
                                break;
                            }
                        }

                        selectedFakultas = selectedUserFakultas;

                        fakultasAdapter.clear();
                        if (userFakultasId == 0) {
                            Fakultas selectFakultas = new Fakultas();
                            selectFakultas.setIdFakultas(-1);
                            selectFakultas.setNamaFakultas("-- Pilih Fakultas --");
                            fakultasList.add(0, selectFakultas);
                        }
                        fakultasAdapter.addAll(fakultasList);

                        if (selectedFakultas != null) {
                            fetchProdi(selectedFakultas.getNamaFakultas());
                            int selectedIndex = fakultasList.indexOf(selectedFakultas);
                            spinnerFakultas.setSelection(selectedIndex);
                        } else {
                            //Toast.makeText(ProfileActivityAfter1.this, "Fakultas kosong", Toast.LENGTH_SHORT).show();
                            Log.d("Fakultas Data", "Fakultas Kosong");
                        }

                        fakultasAdapter.notifyDataSetChanged();
                    }



                } else {
                    Toast.makeText(ProfileActivityAfter1.this, "Gagal mengambil data Fakultas", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<List<Fakultas>> call, Throwable t) {
                Toast.makeText(ProfileActivityAfter1.this, "Kesalahan jaringan : " + t.getMessage(), Toast.LENGTH_SHORT).show();
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

                    Prodi selectProdi = new Prodi();
                    selectProdi.setIdProdi(-1);
                    selectProdi.setNamaProdi("-- Pilih Program Studi --");
                    prodiList.add(0, selectProdi);

                    if (prodiList != null) {
                        sharedPreferenceManager = SharedPreferenceManager.getInstance(getApplicationContext());
                        int userProdiId = sharedPreferenceManager.getUser().getid_prodi();
                        Log.d("UserProdiId", "User Prodi ID: " + userProdiId);
                        Prodi selectedUserProdi = null;

                        for (Prodi prodi : prodiList) {
                            Log.d("ProdiData", "Prodi ID: " + prodi.getIdProdi());
                            if (prodi.getIdProdi() == userProdiId) {
                                selectedUserProdi = prodi;
                                break;
                            }
                        }

                        selectedProdi = selectedUserProdi;

                        //selectedProdi = null;
                        prodiAdapter.clear();
                        prodiAdapter.addAll(prodiList);

                        if (selectedProdi != null) {
                            int selectedIndex = prodiList.indexOf(selectedProdi);
                            spinnerProdi.setSelection(selectedIndex);
                        } else {
                            //Toast.makeText(ProfileActivityAfter1.this, "Fakultas kosong", Toast.LENGTH_SHORT).show();
                            Log.d("Prodi Data", "Prodi Kosong");
                        }

                        prodiAdapter.notifyDataSetChanged();

                    }



                } else {
                    Toast.makeText(ProfileActivityAfter1.this, "Gagal mengambil data Program Studi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Prodi>> call, Throwable t) {
                Toast.makeText(ProfileActivityAfter1.this, "Kesalahan jaringan : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_AND_IMAGE_PICK) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker();
            } else {
                Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }




    private void openImagePicker() {
        Log.d("Debug", "Opening image picker");

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        Log.d("Debug", "Launching image picker intent");
        startActivityForResult(intent, REQUEST_PERMISSION_AND_IMAGE_PICK);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("ProfileActivityAfter1", "onActivityResult called");

        if (requestCode == REQUEST_PERMISSION_AND_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();

            int userId = user.getId_mahasiswa();
            String imageUriString = imageUri.toString();
            saveImageUriToSharedPreferences(userId, imageUriString);
            uploadImageToServer(userId, imageUri);
        }
    }



    private void saveImageUriToSharedPreferences(int userId, String imageUriString) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("imageUri" + userId, imageUriString);
        editor.apply();
    }


    private Uri getStoredImageUriFromSharedPreferences(int userId) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String imageUriString = sharedPreferences.getString("imageUri" + userId, "");
        return Uri.parse(imageUriString);
    }




    private void uploadImageToServer(int userId, Uri imageUri) {
        RetrofitClient retrofitClient = RetrofitClient.getInstance(this);
        Api api = retrofitClient.getApi();

        File imageFile = new File(getRealPathFromURI(imageUri));

        String userIdString = String.valueOf(userId);

        RequestBody userIdPart = RequestBody.create(MediaType.parse("text/plain"), userIdString);
        RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), imageRequestBody);

        Call<ResponseBody> call = api.uploadFotoProfile(userIdPart, imagePart);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Gambar berhasil diunggah
                    Log.d("Status", "Berhasil");
                    Toast.makeText(ProfileActivityAfter1.this, "Berhasil upload", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProfileActivityAfter1.this, "Gagal upload", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("UPLOAD_ERROR", "Error: " + t.getMessage());
                Toast.makeText(ProfileActivityAfter1.this, "Kesalahan Jaringan, Coba lagi", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ProfileActivityAfter1", "onResume called");

        if (imageUri != null && !imageUri.equals(Uri.EMPTY)) {
            File imageFile = new File(getRealPathFromURI(imageUri));
            if (imageFile.exists()) {
                Log.d("ProfileActivityAfter1", "Image file exists: " + imageFile.getAbsolutePath());
                ImageView profileImage = findViewById(R.id.profile_image1);
                profileImage.setImageURI(imageUri);
            } else {
                Log.d("ProfileActivityAfter1", "Image file does not exist");
            }
        }
    }




    private MultipartBody.Part createImagePart(String partName, Uri fileUri) {
        File file = new File(getRealPathFromURI(fileUri));
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }


    private String getRealPathFromURI(Uri uri) {
        String realPath = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            realPath = cursor.getString(columnIndex);
            cursor.close();
        }
        return realPath;
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
            Intent intent1 = new Intent(ProfileActivityAfter1.this, MainActivity.class);
            startActivity(intent1);
        } else if (itemId == R.id.nav_carabooking) {
            Intent intent2 = new Intent(ProfileActivityAfter1.this, CaraDaftarActivity.class);
            startActivity(intent2);
        } else if (itemId == R.id.nav_galeri) {
            Intent intent3 = new Intent(ProfileActivityAfter1.this, GaleriActivity.class);
            startActivity(intent3);
        } else if (itemId == R.id.nav_snk) {
            Intent intent4 = new Intent(ProfileActivityAfter1.this, SnKActivity.class);
            startActivity(intent4);
        } else if (itemId == R.id.nav_login) {
            Intent intent5 = new Intent(ProfileActivityAfter1.this, Login.class);
            startActivity(intent5);
        } else if (itemId == R.id.nav_daftar) {
            Intent intent6 = new Intent(ProfileActivityAfter1.this, Daftar.class);
            startActivity(intent6);
        } else if (itemId == R.id.nav_helpdesk) {
            Intent intent7 = new Intent(ProfileActivityAfter1.this, Helpdesk.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_about) {
            Intent intent8 = new Intent(ProfileActivityAfter1.this, About.class);
            startActivity(intent8);
        } else if (itemId == R.id.nav_data_mahasiswa) {
            Intent intent7 = new Intent(ProfileActivityAfter1.this, DataMahasiswa.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_ubahpass) {
            Intent intent7 = new Intent(ProfileActivityAfter1.this, UbahPassword.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_daftar_kamar) {
            Intent intent8 = new Intent(ProfileActivityAfter1.this, DaftarKamar.class);
            startActivity(intent8);
        } else if (itemId == R.id.nav_logout) {
            logoutUser();
            return true;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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


    //button update data profile
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save_profile) {
            updateData();
        }
    }


    //update data profile ke database
    private void updateData() {
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

        String email = sharedPreferenceManager.getUser().getEmail_mahasiswa();

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

        Call <ProfileMahasiswaResponse> call = api.updateData(email, nama, nim, selectedFakultasId, selectedProdiId, tgl_lahir, agama, jeniskel, alamat, nohp, namaortu, pekerjaanortu, alamatortu, nohportu);
        call.enqueue(new Callback<ProfileMahasiswaResponse>() {
            @Override
            public void onResponse(Call<ProfileMahasiswaResponse> call, retrofit2.Response<ProfileMahasiswaResponse> response) {
                ProfileMahasiswaResponse update = response.body();
                if (response.isSuccessful()){
                    if (update.getError().equals("200")){
                        sharedPreferenceManager.saveUser(update.getUser());
                        Toast.makeText(ProfileActivityAfter1.this, update.getMessage(), Toast.LENGTH_SHORT).show();
                        updateUI(update.getUser());
                        Intent intent = new Intent(ProfileActivityAfter1.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(ProfileActivityAfter1.this, update.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(ProfileActivityAfter1.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileMahasiswaResponse> call, Throwable t) {
                Toast.makeText(ProfileActivityAfter1.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateUI(User user) {
        et_namalengkap.setText(user.getNama_mahasiswa());
        et_nim.setText(user.getNim_mahasiswa());
        et_tgllahir.setText(user.getTgl_lahir_mahasiswa());
        et_agama.setText(user.getAgama_mahasiswa());
        et_jk.setText(user.getJenis_kelamin_mahasiswa());
        et_alamat.setText(user.getAlamat_mahasiswa());
        et_notelp.setText(user.getNo_telp_mahasiswa());
        et_namaortu.setText(user.getNama_orangtua());
        et_pekerjaanortu.setText(user.getPekerjaan_orangtua());
        et_alamatortu.setText(user.getAlamat_orangtua());
        et_notelportu.setText(user.getNotelp_orangtua());
    }


    //logout
    private void logoutUser() {
        sharedPreferenceManager.logout();

        Toast.makeText(this, "Logout Berhasil", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ProfileActivityAfter1.this, Login.class);
        startActivity(intent);
        finishAffinity();
    }

}