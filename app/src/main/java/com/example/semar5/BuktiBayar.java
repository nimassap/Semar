package com.example.semar5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.semar5.ModelResponse.BuktiBayarList;
import com.example.semar5.ModelResponse.BuktiBayarResponse;
import com.example.semar5.ModelResponse.User;
import com.example.semar5.Retrofit.Api;
import com.example.semar5.Retrofit.RetrofitClient;
import com.example.semar5.Retrofit.SharedPreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import android.Manifest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuktiBayar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private TextView namaprofile;
    private SharedPreferenceManager sharedPreferenceManager;
    private User user;
    private String imageBaseUrlAdmin;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private ProgressBar uploadProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bukti_bayar);

        //header logo
        ImageView imageView = findViewById(R.id.homesemar);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuktiBayar.this, MainActivity.class);
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


        Intent intent = getIntent();
        int idMahasiswa = intent.getIntExtra("idMahasiswa", -1);
        int idPemesanan = intent.getIntExtra("idPemesanan", -1);
        imageBaseUrlAdmin = getIntent().getStringExtra("imageBuktiBayar");


        /*sharedPreferenceManager = SharedPreferenceManager.getInstance(this);
        User user = sharedPreferenceManager.getUser();

        Button download = findViewById(R.id.download);
        Button konfirm = findViewById(R.id.konfirm);
        Button batal = findViewById(R.id.batal);

        if (sharedPreferenceManager.isLoggedIn()) {
            int role = user.getId_role();
            Log.d("Debug", "Role: " + role);
            if (role == 1) {
                download.setVisibility(View.VISIBLE);
                konfirm.setVisibility(View.GONE);
                batal.setVisibility(View.GONE);

                download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        requestWriteExternalStoragePermission();
                    }
                });

            } else if (role == 2) {
                //konfirmasi pesanan
                download.setVisibility(View.GONE);
                konfirm.setVisibility(View.VISIBLE);
                batal.setVisibility(View.VISIBLE);
                konfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RetrofitClient retrofitClient = RetrofitClient.getInstance(BuktiBayar.this);
                        Api api = retrofitClient.getApi();

                        Call<ResponseBody> call = api.konfirmasiStatus(idPemesanan);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(BuktiBayar.this, "Berhasil mengubah status pendaftaran", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(BuktiBayar.this, HistoryActivity.class);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(BuktiBayar.this, "Gagal mengubah status pendaftaran", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(BuktiBayar.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });


                //batalkan pesanan
                download.setVisibility(View.GONE);
                konfirm.setVisibility(View.VISIBLE);
                batal.setVisibility(View.VISIBLE);
                batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RetrofitClient retrofitClient = RetrofitClient.getInstance(BuktiBayar.this);
                        Api api = retrofitClient.getApi();

                        Call<ResponseBody> call = api.batalkanStatus(idPemesanan);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(BuktiBayar.this, "Berhasil mengubah status pendaftaran", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(BuktiBayar.this, HistoryActivity.class);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(BuktiBayar.this, "Gagal mengubah status pendaftaran", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(BuktiBayar.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        }*/


        buktiBayarTampil(idMahasiswa, idPemesanan);


        //sesuaikan activity
        drawerLayout = findViewById(R.id.drawer_layout_buktibayar);
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
        bottomNavigationView.setSelectedItemId(R.id.bottom_history);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.bottom_notif) {
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



    private void requestWriteExternalStoragePermission(String imageUrl) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_EXTERNAL_STORAGE); // Use the constant here
        } else {
            downloadImage(imageUrl);
        }
    }



    private void buktiBayarTampil(int idMahasiswa, int idPemesanan) {
        Log.d("API_RESPONSE", "Fetching bukti bayar for idMahasiswa: " + idMahasiswa);
        Log.d("API_RESPONSE", "Fetching bukti bayar for idPemesanan: " + idPemesanan);

        RetrofitClient retrofitClient = RetrofitClient.getInstance(this);
        Api api = retrofitClient.getApi();

        TextView textBukti = findViewById(R.id.text_buktibayar);
        ImageView gambarBukti = findViewById(R.id.gambar_bukti);
        Button konfirm = findViewById(R.id.konfirm);
        Button download = findViewById(R.id.download);
        Button batal = findViewById(R.id.batal);

        sharedPreferenceManager = SharedPreferenceManager.getInstance(this);
        User user = sharedPreferenceManager.getUser();

        Call<List<BuktiBayarList>> call = api.getBuktiPembayaran(idMahasiswa, idPemesanan);
        call.enqueue(new Callback<List<BuktiBayarList>>() {
            @Override
            public void onResponse(Call<List<BuktiBayarList>> call, Response<List<BuktiBayarList>> response) {
                if (response.isSuccessful()) {
                    List<BuktiBayarList> imageDataList = response.body();
                    Log.d("API_RESPONSE", "Image data list size: " + imageDataList.size());
                    Log.d("API_RESPONSE", "Response Body: " + response.body());

                    if (imageDataList != null && !imageDataList.isEmpty()) {
                        // Assuming you want to load and display the first image in the list
                        String imageUrl = imageBaseUrlAdmin + imageDataList.get(0).getImagePath();


                        Glide.with(BuktiBayar.this)
                                .load(imageUrl)
                                .placeholder(R.drawable.loading5)
                                .error(R.drawable.loading6)
                                .into(gambarBukti);
                        gambarBukti.setVisibility(View.VISIBLE);
                        textBukti.setVisibility(View.GONE);
                        download.setVisibility(View.VISIBLE);
                        konfirm.setVisibility(View.VISIBLE);
                        batal.setVisibility(View.VISIBLE);


                        if (sharedPreferenceManager.isLoggedIn()) {
                            int role = user.getId_role();
                            Log.d("Debug", "Role: " + role);
                            if (role == 1) {
                                //download bukti gambar
                                download.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (imageDataList != null && !imageDataList.isEmpty()) {
                                            String imageUrl = imageBaseUrlAdmin + imageDataList.get(0).getImagePath();
                                            requestWriteExternalStoragePermission(imageUrl);
                                        }
                                    }
                                });


                                konfirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        RetrofitClient retrofitClient = RetrofitClient.getInstance(BuktiBayar.this);
                                        Api api = retrofitClient.getApi();

                                        Call<ResponseBody> call = api.konfirmasiStatus(idPemesanan);
                                        call.enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                if (response.isSuccessful()) {
                                                    Toast.makeText(BuktiBayar.this, "Berhasil mengubah status pendaftaran", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(BuktiBayar.this, HistoryActivity.class);
                                                    startActivity(intent);

                                                } else {
                                                    Toast.makeText(BuktiBayar.this, "Gagal mengubah status pendaftaran", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                Toast.makeText(BuktiBayar.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });


                                //batalkan pesanan
                                batal.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        RetrofitClient retrofitClient = RetrofitClient.getInstance(BuktiBayar.this);
                                        Api api = retrofitClient.getApi();

                                        Call<ResponseBody> call = api.batalkanStatus(idPemesanan);
                                        call.enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                if (response.isSuccessful()) {
                                                    Toast.makeText(BuktiBayar.this, "Berhasil mengubah status pendaftaran", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(BuktiBayar.this, HistoryActivity.class);
                                                    startActivity(intent);

                                                } else {
                                                    Toast.makeText(BuktiBayar.this, "Gagal mengubah status pendaftaran", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                Toast.makeText(BuktiBayar.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });
                            }
                        }


                    } else {
                        Log.e("API_RESPONSE", "Image data list is empty");
                        gambarBukti.setVisibility(View.GONE);
                        textBukti.setVisibility(View.VISIBLE);
                        konfirm.setVisibility(View.GONE);
                        download.setVisibility(View.GONE);
                        batal.setVisibility(View.GONE);
                    }
                } else {
                    Log.e("API_RESPONSE", "Response Code: " + response.code());
                    Log.e("API_RESPONSE", "Error Body: " + response.errorBody().toString());
                    Toast.makeText(BuktiBayar.this, "Failed to retrieve image data", Toast.LENGTH_SHORT).show();
                    gambarBukti.setVisibility(View.GONE);
                    textBukti.setVisibility(View.VISIBLE);
                    konfirm.setVisibility(View.GONE);
                    download.setVisibility(View.GONE);
                    batal.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<BuktiBayarList>> call, Throwable t) {
                Log.e("API_RESPONSE", "onFailure: " + t.getMessage());
                t.printStackTrace();
                Toast.makeText(BuktiBayar.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
                gambarBukti.setVisibility(View.GONE);
                textBukti.setVisibility(View.VISIBLE);
                konfirm.setVisibility(View.GONE);
                download.setVisibility(View.GONE);
                batal.setVisibility(View.GONE);
            }
        });


    }


    private void downloadImage(String imageUrl) {
        uploadProgressBar = findViewById(R.id.uploadProgressBar);
        uploadProgressBar.setVisibility(View.VISIBLE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String fileName = "BuktiBayarMahasiswa" + timeStamp + ".jpg"; // Change the file name if needed
            String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            File file = new File(directory, fileName);

            RetrofitClient retrofitClient = RetrofitClient.getInstance(this);
            Api api = retrofitClient.getApi();

            Call<ResponseBody> call = api.downloadBuktiAdmin(imageUrl);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    uploadProgressBar.setVisibility(View.GONE);

                    if (response.isSuccessful()) {
                        // Save the image to external storage
                        Log.d("DownloadImage", "Response body size: " + response.body().contentLength());
                        boolean success = FileUtils.saveResponseBodyToFile(response.body(), file);
                        if (success) {
                            Toast.makeText(BuktiBayar.this, "Berhasil men-download gambar", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(BuktiBayar.this, "Gagal men-download gambar", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("DownloadImage", "Response code: " + response.code());
                        Toast.makeText(BuktiBayar.this, "Gagal men-download gambar", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    uploadProgressBar.setVisibility(View.GONE);

                    Log.e("DownloadImage", "onFailure: " + t.getMessage());
                    Toast.makeText(BuktiBayar.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }


    public static class FileUtils {
        public static boolean saveResponseBodyToFile(ResponseBody body, File file) {
            try {
                InputStream inputStream = null;
                OutputStream outputStream = null;

                try {
                    byte[] fileReader = new byte[4096];
                    inputStream = body.byteStream();
                    outputStream = new FileOutputStream(file);

                    while (true) {
                        int read = inputStream.read(fileReader);
                        if (read == -1) {
                            break;
                        }

                        outputStream.write(fileReader, 0, read);
                    }

                    outputStream.flush();
                    return true;
                } catch (IOException e) {
                    return false;
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }

                    if (outputStream != null) {
                        outputStream.close();
                    }
                }
            } catch (IOException e) {
                return false;
            }
        }
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
            Intent intent1 = new Intent(BuktiBayar.this, MainActivity.class);
            startActivity(intent1);
        } else if (itemId == R.id.nav_carabooking) {
            Intent intent2 = new Intent(BuktiBayar.this, CaraDaftarActivity.class);
            startActivity(intent2);
        } else if (itemId == R.id.nav_galeri) {
            Intent intent3 = new Intent(BuktiBayar.this, GaleriActivity.class);
            startActivity(intent3);
        } else if (itemId == R.id.nav_snk) {
            Intent intent4 = new Intent(BuktiBayar.this, SnKActivity.class);
            startActivity(intent4);
        } else if (itemId == R.id.nav_login) {
            Intent intent5 = new Intent(BuktiBayar.this, Login.class);
            startActivity(intent5);
        } else if (itemId == R.id.nav_daftar) {
            Intent intent6 = new Intent(BuktiBayar.this, Daftar.class);
            startActivity(intent6);
        } else if (itemId == R.id.nav_helpdesk) {
            Intent intent7 = new Intent(BuktiBayar.this, Helpdesk.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_about) {
            Intent intent8 = new Intent(BuktiBayar.this, About.class);
            startActivity(intent8);
        } else if (itemId == R.id.nav_data_mahasiswa) {
            Intent intent7 = new Intent(BuktiBayar.this, DataMahasiswa.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_ubahpass) {
            Intent intent7 = new Intent(BuktiBayar.this, UbahPassword.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_daftar_kamar) {
            Intent intent8 = new Intent(BuktiBayar.this, DaftarKamar.class);
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
        Intent intent = new Intent(BuktiBayar.this, Login.class);
        startActivity(intent);
        finishAffinity();
    }
}