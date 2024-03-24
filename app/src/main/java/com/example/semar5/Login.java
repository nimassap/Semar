package com.example.semar5;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.semar5.ModelResponse.LoginResponse;
import com.example.semar5.ModelResponse.Role;
import com.example.semar5.Retrofit.RetrofitClient;
import com.example.semar5.Retrofit.SharedPreferenceManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText et_email, et_password;
    TextView daftarlink;
    Button login;
    SharedPreferenceManager sharedPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email = findViewById(R.id.login_email);
        et_password = findViewById(R.id.login_password);
        daftarlink = findViewById(R.id.daftar_link);
        login = findViewById(R.id.login_button);

        daftarlink.setOnClickListener(this);
        login.setOnClickListener(this);

        sharedPreferenceManager = new SharedPreferenceManager(getApplicationContext());
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.daftar_link) {
            registerUser();
        } else if (id == R.id.login_button) {
            loginUser();
        }
    }

    private void registerUser() {
        Intent intent = new Intent(Login.this, Daftar.class);
        startActivity(intent);
    }

    private void loginUser() {
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();

        if (email.isEmpty()){
            et_email.requestFocus();
            et_email.setError("Email tidak boleh kosong");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_email.requestFocus();
            et_email.setError("Masukkan email yang benar");
            return;
        }

        if (!password.equals(et_password.getText().toString())) {
            et_password.requestFocus();
            et_password.setError("Passwords salah");
            return;
        }

        Call<LoginResponse> call = RetrofitClient
                .getInstance(getApplicationContext())
                .getApi()
                .login(email, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null) {
                        if (loginResponse.getError().equals("200")) {
                            int role = loginResponse.getUser().getId_role();

                            if (role == 1) {
                                sharedPreferenceManager.saveUser(loginResponse.getUser());
                                Toast.makeText(Login.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, ProfileActivityAdmin.class);
                                startActivity(intent);
                                finishAffinity();
                            } else if (role == 2) {
                                sharedPreferenceManager.saveUser(loginResponse.getUser());
                                Toast.makeText(Login.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, ProfileActivityAfter1.class);
                                startActivity(intent);
                                finishAffinity();
                            }
                        } else {
                            Toast.makeText(Login.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(Login.this, "Terjadi kesalahan pada server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (sharedPreferenceManager.isLoggedIn()){
            Intent intent = new Intent(Login.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}