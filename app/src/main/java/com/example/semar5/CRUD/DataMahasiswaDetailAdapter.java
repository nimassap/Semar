package com.example.semar5.CRUD;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.semar5.DataMahasiswaTampil;
import com.example.semar5.ModelResponse.Pemesanan;
import com.example.semar5.R;
import com.example.semar5.Retrofit.SharedPreferenceManager;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class DataMahasiswaDetailAdapter extends RecyclerView.Adapter<DataMahasiswaDetailAdapter.ViewHolder> {

    private List<Pemesanan> dataMahasiswa;
    private String imageBaseUrl;
    SharedPreferenceManager sharedPreferenceManager;


    public DataMahasiswaDetailAdapter(List<Pemesanan> dataMahasiswa, String imageBaseUrl) {
        this.dataMahasiswa = dataMahasiswa;
        this.imageBaseUrl = imageBaseUrl;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView dataMahasiswaKamar;
        private TextView dataMahasiswaGedung;
        private TextView dataMahasiswaJmlPenghuni;
        private TextView dataMahasiswaTglMasuk;
        private TextView dataMahasiswaTglKeluar;
        private Button bayarButton;

        public ViewHolder(View itemView) {
            super(itemView);

            dataMahasiswaKamar = itemView.findViewById(R.id.belumbayar_kamar);
            dataMahasiswaGedung = itemView.findViewById(R.id.belumbayar_gedung);
            dataMahasiswaJmlPenghuni = itemView.findViewById(R.id.belumbayar_jmlpenghuni);
            dataMahasiswaTglMasuk = itemView.findViewById(R.id.belumbayar_tglmasuk);
            dataMahasiswaTglKeluar = itemView.findViewById(R.id.belumbayar_tglkeluar);
            bayarButton = itemView.findViewById(R.id.bayar_datamhs);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_mahasiswa_detail_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pemesanan dataMhs = dataMahasiswa.get(position);

        holder.dataMahasiswaKamar.setText("Kamar " + dataMhs.getKodeKamar());
        holder.dataMahasiswaGedung.setText("Gedung " + dataMhs.getGedung());
        holder.dataMahasiswaJmlPenghuni.setText(dataMhs.getJumlahPenghuni());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String tanggalMasukFormatted = dateFormat.format(dataMhs.getTanggalMasuk());
        String tanggalKeluarFormatted = dateFormat.format(dataMhs.getTanggalKeluar());

        holder.dataMahasiswaTglMasuk.setText(tanggalMasukFormatted);
        holder.dataMahasiswaTglKeluar.setText(tanggalKeluarFormatted);
        holder.itemView.setTag(dataMhs.getIdMahasiswa());

        holder.bayarButton.setText(dataMhs.getHarga());
        holder.bayarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idMahasiswa = (int) holder.itemView.getTag();
                //int idPemesanan = (int) holder.itemView.getTag();
                Pemesanan selectedPemesanan = dataMahasiswa.get(position);

                Intent intent = new Intent(v.getContext(), DataMahasiswaTampil.class);
                intent.putExtra(DataMahasiswaTampil.EXTRA_MAHASISWA_ID_DETAIL, idMahasiswa);
                Log.d("Debug", "ID Mahasiswa: " + idMahasiswa);
                int idPemesanan = selectedPemesanan.getIdPemesanan();
                intent.putExtra("idPemesanan", idPemesanan);
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
        });

    }

    @Override
    public int getItemCount() {
        return dataMahasiswa.size();
    }

    public void updateDataset(List<Pemesanan> newData) {
        dataMahasiswa = newData;
        notifyDataSetChanged();
    }
}