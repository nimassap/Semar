package com.example.semar5.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.semar5.DetailPesanan;
import com.example.semar5.DetailPesananAdmin;
import com.example.semar5.ModelResponse.Pemesanan;
import com.example.semar5.ModelResponse.User;
import com.example.semar5.R;
import com.example.semar5.Retrofit.SharedPreferenceManager;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PesananDibatalkanAdapter extends RecyclerView.Adapter<PesananDibatalkanAdapter.ViewHolder> {

    private List<Pemesanan> statusDibatalkan;
    private String imageBaseUrl;
    SharedPreferenceManager sharedPreferenceManager;

    public PesananDibatalkanAdapter(List<Pemesanan> statusDibatalkan, String imageBaseUrl) {
        this.statusDibatalkan = statusDibatalkan;
        this.imageBaseUrl = imageBaseUrl;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView dibatalkanKamar;
        private TextView dibatalkanGedung;
        private TextView dibatalkanJmlPenghuni;
        private TextView dibatalkanTglMasuk;
        private TextView dibatalkanTglKeluar;
        private TextView dibatalkanNama;
        private Button dibatalkanButton;

        public ViewHolder(View itemView) {
            super(itemView);

            dibatalkanKamar = itemView.findViewById(R.id.dibatalkan_kamar);
            dibatalkanGedung = itemView.findViewById(R.id.dibatalkan_gedung);
            dibatalkanJmlPenghuni = itemView.findViewById(R.id.dibatalkan_jmlpenghuni);
            dibatalkanTglMasuk = itemView.findViewById(R.id.dibatalkan_tglmasuk);
            dibatalkanTglKeluar = itemView.findViewById(R.id.dibatalkan_tglkeluar);
            dibatalkanNama = itemView.findViewById(R.id.dibatalkan_nama);
            dibatalkanButton = itemView.findViewById(R.id.dibatalkan);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pesanan_dibatalkan_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pemesanan dibatalkan = statusDibatalkan.get(position);

        holder.dibatalkanKamar.setText("Kamar " + dibatalkan.getKodeKamar());
        holder.dibatalkanGedung.setText("Gedung " + dibatalkan.getGedung());
        holder.dibatalkanJmlPenghuni.setText(dibatalkan.getJumlahPenghuni());
        holder.dibatalkanNama.setText(dibatalkan.getNamaMahasiswa());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String tanggalMasukFormatted = dateFormat.format(dibatalkan.getTanggalMasuk());
        String tanggalKeluarFormatted = dateFormat.format(dibatalkan.getTanggalKeluar());

        holder.dibatalkanTglMasuk.setText(tanggalMasukFormatted);
        holder.dibatalkanTglKeluar.setText(tanggalKeluarFormatted);

        holder.dibatalkanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return statusDibatalkan.size();
    }
}