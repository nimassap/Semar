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
import com.example.semar5.Pembayaran;
import com.example.semar5.R;
import com.example.semar5.Retrofit.SharedPreferenceManager;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PesananBelumBayarAdapter extends RecyclerView.Adapter<PesananBelumBayarAdapter.ViewHolder> {

    private List<Pemesanan> statusBelumBayar;
    private String imageBaseUrl;
    SharedPreferenceManager sharedPreferenceManager;


    public PesananBelumBayarAdapter(List<Pemesanan> statusBelumBayar, String imageBaseUrl) {
        this.statusBelumBayar = statusBelumBayar;
        this.imageBaseUrl = imageBaseUrl;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView belumbayarKamar;
        private TextView belumbayarGedung;
        private TextView belumbayarJmlPenghuni;
        private TextView belumbayarTglMasuk;
        private TextView belumbayarTglKeluar;
        private TextView belumbayarNama;
        private Button bayarButton;

        public ViewHolder(View itemView) {
            super(itemView);

            belumbayarKamar = itemView.findViewById(R.id.belumbayar_kamar);
            belumbayarGedung = itemView.findViewById(R.id.belumbayar_gedung);
            belumbayarJmlPenghuni = itemView.findViewById(R.id.belumbayar_jmlpenghuni);
            belumbayarTglMasuk = itemView.findViewById(R.id.belumbayar_tglmasuk);
            belumbayarTglKeluar = itemView.findViewById(R.id.belumbayar_tglkeluar);
            belumbayarNama = itemView.findViewById(R.id.belumbayar_nama);
            bayarButton = itemView.findViewById(R.id.bayar);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pesanan_belum_bayar_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pemesanan belumBayar = statusBelumBayar.get(position);

        holder.belumbayarKamar.setText("Kamar " + belumBayar.getKodeKamar());
        holder.belumbayarGedung.setText("Gedung " + belumBayar.getGedung());
        holder.belumbayarJmlPenghuni.setText(belumBayar.getJumlahPenghuni());
        holder.belumbayarNama.setText(belumBayar.getNamaMahasiswa());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String tanggalMasukFormatted = dateFormat.format(belumBayar.getTanggalMasuk());
        String tanggalKeluarFormatted = dateFormat.format(belumBayar.getTanggalKeluar());

        holder.belumbayarTglMasuk.setText(tanggalMasukFormatted);
        holder.belumbayarTglKeluar.setText(tanggalKeluarFormatted);

        sharedPreferenceManager = SharedPreferenceManager.getInstance(holder.bayarButton.getContext());
        User user = sharedPreferenceManager.getUser();

        if (sharedPreferenceManager.isLoggedIn()) {
            int role = user.getId_role();
            if (role == 1) {
                holder.bayarButton.setText("LIHAT RINCIAN");
            } else if (role == 2){
                holder.bayarButton.setText(belumBayar.getHarga());
            }
        }

        holder.bayarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sharedPreferenceManager.isLoggedIn()) {
                    int role = user.getId_role();
                    Log.d("Debug", "Role: " + role);
                    if (role == 1) {
                        holder.bayarButton.setText("LIHAT RINCIAN");

                        Pemesanan selectedPemesanan = statusBelumBayar.get(position);
                        Intent intent = new Intent(v.getContext(), DetailPesananAdmin.class);

                        int idMahasiswa = selectedPemesanan.getIdMahasiswa();
                        int idPemesanan = selectedPemesanan.getIdPemesanan();

                        intent.putExtra("idMahasiswa", idMahasiswa);
                        intent.putExtra("idPemesanan", idPemesanan);

                        Log.d("Debug", "ID Mahasiswa: " + idMahasiswa);
                        Log.d("Debug", "ID Pemesanan: " + idPemesanan);
                        intent.putExtra("namaPemesan", selectedPemesanan.getNamaMahasiswa());
                        intent.putExtra("jalur", selectedPemesanan.getJalur());
                        intent.putExtra("opsiPembayaran", selectedPemesanan.getOpsiPembayaran());
                        intent.putExtra("jumlahPenghuni", selectedPemesanan.getJumlahPenghuni());
                        intent.putExtra("jenisKelamin", selectedPemesanan.getJenisKelamin());
                        intent.putExtra("gedung", selectedPemesanan.getGedung());
                        intent.putExtra("kamar", selectedPemesanan.getKodeKamar());
                        intent.putExtra("harga", selectedPemesanan.getHarga());
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                        String tanggalMasukFormatted = dateFormat.format(selectedPemesanan.getTanggalMasuk());
                        String tanggalKeluarFormatted = dateFormat.format(selectedPemesanan.getTanggalKeluar());
                        intent.putExtra("tanggalMasuk", tanggalMasukFormatted);
                        intent.putExtra("tanggalKeluar", tanggalKeluarFormatted);

                        v.getContext().startActivity(intent);

                    } else if (role == 2) {
                        Pemesanan selectedPemesanan = statusBelumBayar.get(position);
                        Intent intent = new Intent(v.getContext(), DetailPesanan.class);
                        int idMahasiswa = selectedPemesanan.getIdMahasiswa();
                        int idPemesanan = selectedPemesanan.getIdPemesanan();

                        intent.putExtra("idMahasiswa", idMahasiswa);
                        intent.putExtra("idPemesanan", idPemesanan);

                        Log.d("Debug", "ID Mahasiswa: " + idMahasiswa);
                        Log.d("Debug", "ID Pemesanan: " + idPemesanan);
                        intent.putExtra("namaPemesan", selectedPemesanan.getNamaMahasiswa());
                        intent.putExtra("jalur", selectedPemesanan.getJalur());
                        intent.putExtra("opsiPembayaran", selectedPemesanan.getOpsiPembayaran());
                        intent.putExtra("jumlahPenghuni", selectedPemesanan.getJumlahPenghuni());
                        intent.putExtra("jenisKelamin", selectedPemesanan.getJenisKelamin());
                        intent.putExtra("gedung", selectedPemesanan.getGedung());
                        intent.putExtra("kamar", selectedPemesanan.getKodeKamar());
                        intent.putExtra("harga", selectedPemesanan.getHarga());
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                        String tanggalMasukFormatted = dateFormat.format(selectedPemesanan.getTanggalMasuk());
                        String tanggalKeluarFormatted = dateFormat.format(selectedPemesanan.getTanggalKeluar());
                        intent.putExtra("tanggalMasuk", tanggalMasukFormatted);
                        intent.putExtra("tanggalKeluar", tanggalKeluarFormatted);

                        v.getContext().startActivity(intent);
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return statusBelumBayar.size();
    }

    public void updateDataset(List<Pemesanan> newData) {
        statusBelumBayar = newData;
        notifyDataSetChanged();
    }
}