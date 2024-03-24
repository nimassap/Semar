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

import com.example.semar5.ModelResponse.RegisterResponse;
import com.example.semar5.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Daftar extends AppCompatActivity implements View.OnClickListener {

    EditText et_nama, et_notelp, et_email, et_password, et_confirm_password;
    TextView loginlink;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        et_nama = findViewById(R.id.daftar_nama);
        et_notelp = findViewById(R.id.daftar_nohp);
        et_email = findViewById(R.id.daftar_email);
        et_password = findViewById(R.id.daftar_password);
        et_confirm_password = findViewById(R.id.daftar_konfirmpassword);
        loginlink = findViewById(R.id.login_link);
        register = findViewById(R.id.daftar_button);

        loginlink.setOnClickListener(this);
        register.setOnClickListener(this);


    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.daftar_button) {
            registerUser();
        } else if (id == R.id.login_link) {
            loginUser();
        }
    }

    private void loginUser() {
        Intent intent = new Intent(Daftar.this, Login.class);
        startActivity(intent);
    }

    private void registerUser() {
        String nama = et_nama.getText().toString();
        String notelp = et_notelp.getText().toString();
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();
        String confirmPassword = et_confirm_password.getText().toString();

        if (nama.isEmpty()){
            et_nama.requestFocus();
            et_nama.setError("Nama tidak boleh kosong");
            return;
        }
        if (notelp.isEmpty()){
            et_notelp.requestFocus();
            et_notelp.setError("Nomor Telepon tidak boleh kosong");
            return;
        }
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
        if (password.isEmpty()){
            et_password.requestFocus();
            et_password.setError("Password tidak boleh kosong");
            return;
        }
        if (password.length()<8){
            et_password.requestFocus();
            et_password.setError("Password harus minimal 8 karakter");
            return;
        }
        if (!password.equals(confirmPassword)) {
            et_confirm_password.requestFocus();
            et_confirm_password.setError("Password tidak sesuai");
            return;
        }

        Call<RegisterResponse> call = RetrofitClient
                .getInstance(getApplicationContext())
                .getApi()
                .register(nama, notelp, email, password, confirmPassword);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse registerResponse = response.body();
                if (response.isSuccessful()){
                    Toast.makeText(Daftar.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Daftar.this, Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(Daftar.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Daftar.this, Daftar.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(Daftar.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Daftar.this, Daftar.class);
                startActivity(intent);
            }
        });

    }


}