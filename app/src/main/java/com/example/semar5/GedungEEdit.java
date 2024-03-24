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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.semar5.ModelResponse.TampilGedungC;
import com.example.semar5.ModelResponse.TampilGedungE;
import com.example.semar5.ModelResponse.UpdateGedungC;
import com.example.semar5.ModelResponse.UpdateGedungE;
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

public class GedungEEdit extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private TextView namaprofile;
    private SharedPreferenceManager sharedPreferenceManager;
    private User user;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gedung_e_edit);

        //toolbar logo semar
        ImageView imageView = findViewById(R.id.homesemar);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GedungEEdit.this, MainActivity.class);
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


        //sesuaikan activity
        drawerLayout = findViewById(R.id.drawer_layout_gedungeedit);
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


        //button edit activity
        Button saveEdit = findViewById(R.id.save_gedunge);
        saveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDataGedungE();
            }
        });

        tampilDataGedungE();
    }

    //menampilkan data dari database
    private void tampilDataGedungE() {

        EditText tvGedungE_1 = findViewById(R.id.tv_gedunge_1);
        EditText tvGedungE_2 = findViewById(R.id.tv_gedunge_2);
        EditText tvGedungE_3 = findViewById(R.id.tv_gedunge_3);
        EditText tvGedungE_4 = findViewById(R.id.tv_gedunge_4);
        EditText tvGedungE_5 = findViewById(R.id.tv_gedunge_5);
        EditText tvGedungE_6 = findViewById(R.id.tv_gedunge_6);
        EditText tvGedungE_7 = findViewById(R.id.tv_gedunge_7);
        EditText tvGedungE_8 = findViewById(R.id.tv_gedunge_8);
        EditText tvGedungE_9 = findViewById(R.id.tv_gedunge_9);
        EditText tvGedungE_10 = findViewById(R.id.tv_gedunge_10);
        EditText tvGedungE_11 = findViewById(R.id.tv_gedunge_11);
        EditText tvGedungE_12 = findViewById(R.id.tv_gedunge_12);
        EditText tvGedungE_13 = findViewById(R.id.tv_gedunge_13);
        EditText tvGedungE_14 = findViewById(R.id.tv_gedunge_14);
        EditText tvGedungE_15 = findViewById(R.id.tv_gedunge_15);
        EditText tvGedungE_16 = findViewById(R.id.tv_gedunge_16);
        

        RetrofitClient retrofitClient = RetrofitClient.getInstance(this);
        Api api = retrofitClient.getApi();

        Call<List<TampilGedungE>> call = api.getDataGedungE();
        call.enqueue(new Callback<List<TampilGedungE>>() {
            @Override
            public void onResponse(Call<List<TampilGedungE>> call, Response<List<TampilGedungE>> response) {
                if (response.isSuccessful()) {
                    List<TampilGedungE> dataItems = response.body();

                    if (dataItems != null && !dataItems.isEmpty()) {
                        for (TampilGedungE item : dataItems) {
                            switch (item.getId()) {
                                case 1:
                                    tvGedungE_1.setText(item.getTextEdit());
                                    break;
                                case 2:
                                    tvGedungE_2.setText(item.getTextEdit());
                                    break;
                                case 3:
                                    tvGedungE_3.setText(item.getTextEdit());
                                    break;
                                case 4:
                                    tvGedungE_4.setText(item.getTextEdit());
                                    break;
                                case 5:
                                    tvGedungE_5.setText(item.getTextEdit());
                                    break;
                                case 6:
                                    tvGedungE_6.setText(item.getTextEdit());
                                    break;
                                case 7:
                                    tvGedungE_7.setText(item.getTextEdit());
                                    break;
                                case 8:
                                    tvGedungE_8.setText(item.getTextEdit());
                                    break;
                                case 9:
                                    tvGedungE_9.setText(item.getTextEdit());
                                    break;
                                case 10:
                                    tvGedungE_10.setText(item.getTextEdit());
                                    break;
                                case 11:
                                    tvGedungE_11.setText(item.getTextEdit());
                                    break;
                                case 12:
                                    tvGedungE_12.setText(item.getTextEdit());
                                    break;
                                case 13:
                                    tvGedungE_13.setText(item.getTextEdit());
                                    break;
                                case 14:
                                    tvGedungE_14.setText(item.getTextEdit());
                                    break;
                                case 15:
                                    tvGedungE_15.setText(item.getTextEdit());
                                    break;
                                case 16:
                                    tvGedungE_16.setText(item.getTextEdit());
                                    break;
                            }
                        }
                    } else {
                        Toast.makeText(GedungEEdit.this, "No data available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(GedungEEdit.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TampilGedungE>> call, Throwable t) {
                Toast.makeText(GedungEEdit.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }



    //edit data untuk disimpan kembali kedalam database
    private void editDataGedungE() {

        EditText tvGedungE_1 = findViewById(R.id.tv_gedunge_1);
        EditText tvGedungE_2 = findViewById(R.id.tv_gedunge_2);
        EditText tvGedungE_3 = findViewById(R.id.tv_gedunge_3);
        EditText tvGedungE_4 = findViewById(R.id.tv_gedunge_4);
        EditText tvGedungE_5 = findViewById(R.id.tv_gedunge_5);
        EditText tvGedungE_6 = findViewById(R.id.tv_gedunge_6);
        EditText tvGedungE_7 = findViewById(R.id.tv_gedunge_7);
        EditText tvGedungE_8 = findViewById(R.id.tv_gedunge_8);
        EditText tvGedungE_9 = findViewById(R.id.tv_gedunge_9);
        EditText tvGedungE_10 = findViewById(R.id.tv_gedunge_10);
        EditText tvGedungE_11 = findViewById(R.id.tv_gedunge_11);
        EditText tvGedungE_12 = findViewById(R.id.tv_gedunge_12);
        EditText tvGedungE_13 = findViewById(R.id.tv_gedunge_13);
        EditText tvGedungE_14 = findViewById(R.id.tv_gedunge_14);
        EditText tvGedungE_15 = findViewById(R.id.tv_gedunge_15);
        EditText tvGedungE_16 = findViewById(R.id.tv_gedunge_16);

        String editText1 = tvGedungE_1.getText().toString();
        String editText2 = tvGedungE_2.getText().toString();
        String editText3 = tvGedungE_3.getText().toString();
        String editText4 = tvGedungE_4.getText().toString();
        String editText5 = tvGedungE_5.getText().toString();
        String editText6 = tvGedungE_6.getText().toString();
        String editText7 = tvGedungE_7.getText().toString();
        String editText8 = tvGedungE_8.getText().toString();
        String editText9 = tvGedungE_9.getText().toString();
        String editText10 = tvGedungE_10.getText().toString();
        String editText11 = tvGedungE_11.getText().toString();
        String editText12 = tvGedungE_12.getText().toString();
        String editText13 = tvGedungE_13.getText().toString();
        String editText14 = tvGedungE_14.getText().toString();
        String editText15 = tvGedungE_15.getText().toString();
        String editText16 = tvGedungE_16.getText().toString();
        

        RetrofitClient retrofitClient = RetrofitClient.getInstance(this);
        Api api = retrofitClient.getApi();

        Call<UpdateGedungE> call1 = api.updateDataGedungE(1, editText1);
        Call<UpdateGedungE> call2 = api.updateDataGedungE(2, editText2);
        Call<UpdateGedungE> call3 = api.updateDataGedungE(3, editText3);
        Call<UpdateGedungE> call4 = api.updateDataGedungE(4, editText4);
        Call<UpdateGedungE> call5 = api.updateDataGedungE(5, editText5);
        Call<UpdateGedungE> call6 = api.updateDataGedungE(6, editText6);
        Call<UpdateGedungE> call7 = api.updateDataGedungE(7, editText7);
        Call<UpdateGedungE> call8 = api.updateDataGedungE(8, editText8);
        Call<UpdateGedungE> call9 = api.updateDataGedungE(9, editText9);
        Call<UpdateGedungE> call10 = api.updateDataGedungE(10, editText10);
        Call<UpdateGedungE> call11 = api.updateDataGedungE(11, editText11);
        Call<UpdateGedungE> call12 = api.updateDataGedungE(12, editText12);
        Call<UpdateGedungE> call13 = api.updateDataGedungE(13, editText13);
        Call<UpdateGedungE> call14 = api.updateDataGedungE(14, editText14);
        Call<UpdateGedungE> call15 = api.updateDataGedungE(15, editText15);
        Call<UpdateGedungE> call16 = api.updateDataGedungE(16, editText16);

        call1.enqueue(updateResponseCallback);
        call2.enqueue(updateResponseCallback);
        call3.enqueue(updateResponseCallback);
        call4.enqueue(updateResponseCallback);
        call5.enqueue(updateResponseCallback);
        call6.enqueue(updateResponseCallback);
        call7.enqueue(updateResponseCallback);
        call8.enqueue(updateResponseCallback);
        call9.enqueue(updateResponseCallback);
        call10.enqueue(updateResponseCallback);
        call11.enqueue(updateResponseCallback);
        call12.enqueue(updateResponseCallback);
        call13.enqueue(updateResponseCallback);
        call14.enqueue(updateResponseCallback);
        call15.enqueue(updateResponseCallback);
        call16.enqueue(updateResponseCallback);
    }

    private int successCount = 0;
    private int failureCount = 0;
    private final Callback<UpdateGedungE> updateResponseCallback = new Callback<UpdateGedungE>() {
        @Override
        public void onResponse(Call<UpdateGedungE> call, Response<UpdateGedungE> response) {
            if (response.isSuccessful()) {
                UpdateGedungE updateGedungE = response.body();
                String status = updateGedungE.getStatus();
                String message = updateGedungE.getMessage();
                if (status.equals("success")) {
                    successCount++;
                } else if (status.equals("error")) {
                    Toast.makeText(GedungEEdit.this, "Gagal Memperbarui Data" + message, Toast.LENGTH_SHORT).show();
                    failureCount++;
                }

                if (successCount + failureCount == 9) {
                    Toast.makeText(GedungEEdit.this, "Data Berhasil Diperbarui", Toast.LENGTH_SHORT).show();
                    successCount = 0;
                    failureCount = 0;

                    Intent intent = new Intent(GedungEEdit.this, GedungE.class);
                    startActivity(intent);
                }

            } else {
                Toast.makeText(GedungEEdit.this, "Error" + response.message(), Toast.LENGTH_SHORT).show();
                failureCount++;

                if (successCount + failureCount == 9) {
                    Toast.makeText(GedungEEdit.this, "Data Berhasil Diperbarui", Toast.LENGTH_SHORT).show();
                    successCount = 0;
                    failureCount = 0;

                    Intent intent = new Intent(GedungEEdit.this, GedungE.class);
                    startActivity(intent);
                }
            }
        }

        @Override
        public void onFailure(Call<UpdateGedungE> call, Throwable t) {
            Toast.makeText(GedungEEdit.this, "Kesalahan jaringan" + t.getMessage(), Toast.LENGTH_SHORT).show();
            failureCount++;

            if (successCount + failureCount == 9) {
                Toast.makeText(GedungEEdit.this, "Data Berhasil Diperbarui", Toast.LENGTH_SHORT).show();
                successCount = 0;
                failureCount = 0;

                Intent intent = new Intent(GedungEEdit.this, GedungE.class);
                startActivity(intent);
            }
        }
    };



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
            NavigationView navigationView = findViewById(R.id.navdrawer_view);
            Menu menu = navigationView.getMenu();

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
            Intent intent1 = new Intent(GedungEEdit.this, MainActivity.class);
            startActivity(intent1);
        } else if (itemId == R.id.nav_carabooking) {
            Intent intent2 = new Intent(GedungEEdit.this, CaraDaftarActivity.class);
            startActivity(intent2);
        } else if (itemId == R.id.nav_galeri) {
            Intent intent3 = new Intent(GedungEEdit.this, GaleriActivity.class);
            startActivity(intent3);
        } else if (itemId == R.id.nav_snk) {
            Intent intent4 = new Intent(GedungEEdit.this, SnKActivity.class);
            startActivity(intent4);
        } else if (itemId == R.id.nav_login) {
            Intent intent5 = new Intent(GedungEEdit.this, Login.class);
            startActivity(intent5);
        } else if (itemId == R.id.nav_daftar) {
            Intent intent6 = new Intent(GedungEEdit.this, Daftar.class);
            startActivity(intent6);
        } else if (itemId == R.id.nav_helpdesk) {
            Intent intent7 = new Intent(GedungEEdit.this, Helpdesk.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_about) {
            Intent intent8 = new Intent(GedungEEdit.this, About.class);
            startActivity(intent8);
        } else if (itemId == R.id.nav_data_mahasiswa) {
            Intent intent7 = new Intent(GedungEEdit.this, DataMahasiswa.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_ubahpass) {
            Intent intent7 = new Intent(GedungEEdit.this, UbahPassword.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_daftar_kamar) {
            Intent intent8 = new Intent(GedungEEdit.this, DaftarKamar.class);
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
        Intent intent = new Intent(GedungEEdit.this, Login.class);
        startActivity(intent);
        finishAffinity();
    }
}