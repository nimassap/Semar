package com.example.semar5.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.semar5.BuktiBayar;
import com.example.semar5.DataMahasiswaProfile;
import com.example.semar5.DetailPesanan;
import com.example.semar5.DetailPesananAdmin;
import com.example.semar5.HistoryActivity;
import com.example.semar5.ModelResponse.Pemesanan;
import com.example.semar5.ModelResponse.User;
import com.example.semar5.PesananSelesaiDownload;
import com.example.semar5.R;
import com.example.semar5.Retrofit.Api;
import com.example.semar5.Retrofit.RetrofitClient;
import com.example.semar5.Retrofit.SharedPreferenceManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesananSelesaiAdapter extends RecyclerView.Adapter<PesananSelesaiAdapter.ViewHolder> {

    private List<Pemesanan> statusSelesai;
    private String imageBaseUrl;
    SharedPreferenceManager sharedPreferenceManager;

    public PesananSelesaiAdapter(List<Pemesanan> statusSelesai, String imageBaseUrl) {
        this.statusSelesai = statusSelesai;
        this.imageBaseUrl = imageBaseUrl;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView selesaiKamar;
        private TextView selesaiGedung;
        private TextView selesaiJmlPenghuni;
        private TextView selesaiTglMasuk;
        private TextView selesaiTglKeluar;
        private TextView selesaiNama;
        private Button downloadButton;

        public ViewHolder(View itemView) {
            super(itemView);
            selesaiKamar = itemView.findViewById(R.id.selesai_kamar);
            selesaiGedung = itemView.findViewById(R.id.selesai_gedung);
            selesaiJmlPenghuni = itemView.findViewById(R.id.selesai_jmlpenghuni);
            selesaiTglMasuk = itemView.findViewById(R.id.selesai_tglmasuk);
            selesaiTglKeluar = itemView.findViewById(R.id.selesai_tglkeluar);
            selesaiNama = itemView.findViewById(R.id.selesai_nama);
            downloadButton = itemView.findViewById(R.id.download);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pesanan_selesai_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pemesanan selesai = statusSelesai.get(position);

        holder.selesaiKamar.setText("Kamar " + selesai.getKodeKamar());
        holder.selesaiGedung.setText("Gedung " + selesai.getGedung());
        holder.selesaiJmlPenghuni.setText(selesai.getJumlahPenghuni());
        holder.selesaiNama.setText(selesai.getNamaMahasiswa());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String tanggalMasukFormatted = dateFormat.format(selesai.getTanggalMasuk());
        String tanggalKeluarFormatted = dateFormat.format(selesai.getTanggalKeluar());

        holder.selesaiTglMasuk.setText(tanggalMasukFormatted);
        holder.selesaiTglKeluar.setText(tanggalKeluarFormatted);

        holder.downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferenceManager = SharedPreferenceManager.getInstance(v.getContext());
                User user = sharedPreferenceManager.getUser();



                if (sharedPreferenceManager.isLoggedIn()) {
                    int role = user.getId_role();
                    Log.d("Debug", "Role: " + role);
                    if (role == 1) {
                        Pemesanan selectedPemesanan = statusSelesai.get(position);
                        Intent intent = new Intent(v.getContext(), PesananSelesaiDownload.class);
                        int idMahasiswa = selectedPemesanan.getIdMahasiswa();

                        intent.putExtra(PesananSelesaiDownload.EXTRA_MAHASISWA_ID, idMahasiswa);

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
                        holder.downloadButton.setText("DOWNLOAD");

                        Pemesanan selectedPemesanan = statusSelesai.get(position);
                        Intent intent = new Intent(v.getContext(), PesananSelesaiDownload.class);
                        int idMahasiswa = selectedPemesanan.getIdMahasiswa();
                        intent.putExtra(PesananSelesaiDownload.EXTRA_MAHASISWA_ID, idMahasiswa);

                        int idPemesanan = selectedPemesanan.getIdPemesanan();
                        String nim = user.getNim_mahasiswa();
                        String tanggalLahir = user.getTgl_lahir_mahasiswa();
                        String jk = user.getJenis_kelamin_mahasiswa();
                        String agama = user.getAgama_mahasiswa();
                        String alamatmhs = user.getAlamat_mahasiswa();
                        String notelpmhs = user.getNo_telp_mahasiswa();
                        String namaortu = user.getNama_orangtua();
                        String pekerjaanortu = user.getPekerjaan_orangtua();
                        String alamatortu = user.getAlamat_orangtua();
                        String notelportu = user.getNotelp_orangtua();

                        intent.putExtra("idMahasiswa", idMahasiswa);
                        intent.putExtra("idPemesanan", idPemesanan);

                        intent.putExtra("nim", nim);
                        intent.putExtra("tanggalLahir", tanggalLahir);
                        intent.putExtra("jk", jk);
                        intent.putExtra("agama", agama);
                        intent.putExtra("alamatmhs", alamatmhs);
                        intent.putExtra("notelpmhs", notelpmhs);
                        intent.putExtra("namaortu", namaortu);
                        intent.putExtra("pekerjaanortu", pekerjaanortu);
                        intent.putExtra("alamatortu", alamatortu);
                        intent.putExtra("notelportu", notelportu);

                        Log.d("Debug", "ID Mahasiswa: " + idMahasiswa);
                        Log.d("Debug", "ID Pemesanan: " + idPemesanan);
                        intent.putExtra("namaPemesan", selectedPemesanan.getNamaMahasiswa());
                        intent.putExtra("jalur", selectedPemesanan.getJalur());
                        intent.putExtra("opsiPembayaran", selectedPemesanan.getOpsiPembayaran());
                        intent.putExtra("jumlahPenghuni", selectedPemesanan.getJumlahPenghuni());
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
        return statusSelesai.size();
    }
}