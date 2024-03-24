package com.example.semar5.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.semar5.HistoryActivity;
import com.example.semar5.MainActivity;
import com.example.semar5.ModelResponse.Pemesanan;
import com.example.semar5.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NotificationAdapterAdmin extends RecyclerView.Adapter<NotificationAdapterAdmin.PemesananViewHolder> {
    private List<Pemesanan> pemesananList;
    private OnItemClickListener listener;

    public NotificationAdapterAdmin(List<Pemesanan> pemesananList) {
        this.pemesananList = pemesananList;
    }

    public interface OnItemClickListener {
        void onItemClick(Pemesanan pemesanan);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public PemesananViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tampilan_notifikasi, parent, false);
        final PemesananViewHolder viewHolder = new PemesananViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PemesananViewHolder holder, int position) {
        Pemesanan pemesanan = pemesananList.get(position);

        holder.namaMahasiswaTextView.setText("Pendaftar Baru!");
        holder.statusTextView.setText("Nama : " + pemesanan.getNamaMahasiswa() + "\nStatus : " + pemesanan.getStatusPemesanan() + "\n\nKlik disini untuk melihat daftar pendaftar baru");

        // Ambil tanggal masuk dari objek Pemesanan
        Date tanggalMasuk = pemesanan.getTanggalMasuk();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); // Sesuaikan dengan format tanggal Anda
        String formattedDate = dateFormat.format(tanggalMasuk);
        holder.tanggalTextView.setText("Tanggal Masuk : " + formattedDate);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(pemesanan);

                    Intent intent = new Intent(view.getContext(), HistoryActivity.class);
                    intent.putExtra("pemesanan_id", pemesanan.getIdPemesanan());
                    view.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return pemesananList.size();
    }

    static class PemesananViewHolder extends RecyclerView.ViewHolder {
        TextView namaMahasiswaTextView;
        TextView statusTextView;
        TextView tanggalTextView;

        PemesananViewHolder(@NonNull View itemView) {
            super(itemView);
            namaMahasiswaTextView = itemView.findViewById(R.id.notification_title);
            statusTextView = itemView.findViewById(R.id.notification_message);
            tanggalTextView = itemView.findViewById(R.id.notification_time);
        }
    }
}