package com.example.semar5;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.semar5.Adapter.PesananBelumBayarAdapter;
import com.example.semar5.Adapter.PesananSelesaiAdapter;
import com.example.semar5.ModelResponse.Pemesanan;
import com.example.semar5.ModelResponse.PemesananResponse;
import com.example.semar5.ModelResponse.User;
import com.example.semar5.Retrofit.Api;
import com.example.semar5.Retrofit.RetrofitClient;
import com.example.semar5.Retrofit.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SelesaiFragment extends Fragment {

    private RecyclerView recyclerView;
    SharedPreferenceManager sharedPreferenceManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selesai, container, false);

        sharedPreferenceManager = SharedPreferenceManager.getInstance(getContext());
        User user = sharedPreferenceManager.getUser();

        recyclerView = view.findViewById(R.id.recyclerview_detailpesanan);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (sharedPreferenceManager.isLoggedIn()) {
            int role = user.getId_role();
            Log.d("Debug", "Role: " + role);
            if (role == 1) {
                // Admin login
                RetrofitClient retrofitClient = RetrofitClient.getInstance(getContext());
                Api api = retrofitClient.getApi();

                Call<PemesananResponse> call = api.getPemesananDataSemua();
                call.enqueue(new Callback<PemesananResponse>() {
                    @Override
                    public void onResponse(Call<PemesananResponse> call, Response<PemesananResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            PemesananResponse pemesananResponse = response.body();
                            Log.d("Debug", "API Response: " + pemesananResponse.toString());

                            if (pemesananResponse.getError().equals("200")) {
                                List<Pemesanan> pemesananList = pemesananResponse.getPemesananList();
                                List<Pemesanan> selesaiList = filterSelesai(pemesananList);

                                if (!selesaiList.isEmpty()) {
                                    Log.d("Debug", "selesaiList size: " + selesaiList.size());
                                    String imageBaseUrl = "https://semarundip23.000webhostapp.com/qris/"; // Your image base URL
                                    PesananSelesaiAdapter adapter = new PesananSelesaiAdapter(selesaiList, imageBaseUrl);
                                    recyclerView.setAdapter(adapter);

                                    recyclerView.setVisibility(View.VISIBLE);

                                    TextView textnull = view.findViewById(R.id.selesai_daftar);
                                    textnull.setVisibility(View.GONE);
                                }

                            } else {
                                //Toast.makeText(BelumBayarFragment.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "Mahasiswa object is null");

                                recyclerView.setVisibility(View.GONE);

                                TextView textnull = view.findViewById(R.id.selesai_daftar);
                                textnull.setVisibility(View.VISIBLE);
                            }

                        } else {
                            Log.e(TAG, "Mahasiswa object is null");
                            Log.e(TAG, "API Response is null or not successful");
                            Log.e(TAG, "Error: " + response.message());
                            //Toast.makeText(BelumBayarFragment.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                            recyclerView.setVisibility(View.GONE);

                            TextView textnull = view.findViewById(R.id.selesai_daftar);
                            textnull.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<PemesananResponse> call, Throwable t) {
                        Log.e(TAG, "Error: " + t.getMessage());
                        recyclerView.setVisibility(View.GONE);

                        TextView textnull = view.findViewById(R.id.selesai_daftar);
                        textnull.setVisibility(View.VISIBLE);
                    }
                });


                int userId = user.getId_mahasiswa();
                Call<User> call1 = api.getUserById(userId);
                call1.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call1, Response<User> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            User userResponse = response.body();

                            String nim = userResponse.getNim_mahasiswa();
                            String jenisKelamin = userResponse.getJenis_kelamin_mahasiswa();
                            String agama = userResponse.getAgama_mahasiswa();

                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<User> call1, Throwable t) {

                    }
                });


            } else if (role == 2) {
                int idMahasiswa = user.getId_mahasiswa();

                RetrofitClient retrofitClient = RetrofitClient.getInstance(getContext());
                Api api = retrofitClient.getApi();

                Call<PemesananResponse> call = api.getPemesananData(idMahasiswa);
                call.enqueue(new Callback<PemesananResponse>() {
                    @Override
                    public void onResponse(Call<PemesananResponse> call, Response<PemesananResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            PemesananResponse pemesananResponse = response.body();

                            if (pemesananResponse.getError().equals("200")) {
                                List<Pemesanan> pemesananList = pemesananResponse.getPemesananList();
                                List<Pemesanan> selesaiList = filterSelesai(pemesananList);
                                if (!selesaiList.isEmpty()) {
                                    Log.d("Debug", "selesaiList size: " + selesaiList.size());
                                    String imageBaseUrl = "https://semarundip23.000webhostapp.com/qris/";
                                    PesananSelesaiAdapter adapter = new PesananSelesaiAdapter(selesaiList, imageBaseUrl);
                                    recyclerView.setAdapter(adapter);

                                    recyclerView.setVisibility(View.VISIBLE);

                                    TextView textnull = view.findViewById(R.id.selesai_daftar);
                                    textnull.setVisibility(View.GONE);
                                } else {
                                    recyclerView.setVisibility(View.GONE);

                                    TextView textnull = view.findViewById(R.id.selesai_pesanan);
                                    textnull.setVisibility(View.VISIBLE);
                                }

                            } else {
                                //Toast.makeText(BelumBayarFragment.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "Mahasiswa object is null");

                                recyclerView.setVisibility(View.GONE);

                                TextView textnull = view.findViewById(R.id.selesai_daftar);
                                textnull.setVisibility(View.VISIBLE);
                            }

                        } else {
                            Log.e(TAG, "Mahasiswa object is null");
                            Log.e(TAG, "Error: " + response.message());
                            //Toast.makeText(BelumBayarFragment.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                            recyclerView.setVisibility(View.GONE);

                            TextView textnull = view.findViewById(R.id.selesai_daftar);
                            textnull.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<PemesananResponse> call, Throwable t) {
                        Log.e(TAG, "Error: " + t.getMessage());
                        recyclerView.setVisibility(View.GONE);

                        TextView textnull = view.findViewById(R.id.selesai_daftar);
                        textnull.setVisibility(View.VISIBLE);
                    }
                });
            }

        } else {
            recyclerView.setVisibility(View.GONE);

            TextView textnull = view.findViewById(R.id.selesai_login);
            textnull.setVisibility(View.VISIBLE);
        }

        return view;
    }

    private List<Pemesanan> filterSelesai(List<Pemesanan> pemesananList) {
        List<Pemesanan> filteredList = new ArrayList<>();
        for (Pemesanan pemesanan : pemesananList) {
            if ("Selesai".equals(pemesanan.getStatusPemesanan())) {
                filteredList.add(pemesanan);
            }
        }
        return filteredList;
    }

}