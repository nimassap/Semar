package com.example.semar5.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.semar5.ModelResponse.Kamar;
import com.example.semar5.ModelResponse.Pemesanan;
import com.example.semar5.R;

import java.util.List;

public class DaftarKamarTerisiAdapter extends RecyclerView.Adapter<DaftarKamarTerisiAdapter.ViewHolder> {
    private List<Kamar> kamarList;
    private List<Pemesanan> pemesananList;

    public DaftarKamarTerisiAdapter(List<Kamar> kamarList, List<Pemesanan> pemesananList) {
        this.kamarList = kamarList;
        this.pemesananList = pemesananList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tampilan_daftar_kamar_terisi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Kamar kamarData = kamarList.get(position);
        Pemesanan pemesananData = pemesananList.get(position);

        holder.itemNumberTextView.setText(String.valueOf(position + 1) + ".");
        holder.textDaftarKamarTextView.setText(kamarData.getKode_kamar());
        holder.textDaftarKamarNama.setText("Nama : " + pemesananData.getNamaMahasiswa());
        holder.textDaftarKamarStatus.setText("Status : " + pemesananData.getStatusPemesanan());
    }

    @Override
    public int getItemCount() {
        if (kamarList != null && pemesananList != null) {
            return Math.min(kamarList.size(), pemesananList.size());
        } else {
            return 0;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemNumberTextView;
        public TextView textDaftarKamarTextView;
        public TextView textDaftarKamarNama;
        public TextView textDaftarKamarStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            itemNumberTextView = itemView.findViewById(R.id.itemNumber);
            textDaftarKamarTextView = itemView.findViewById(R.id.text_daftarkamar);
            textDaftarKamarNama = itemView.findViewById(R.id.text_daftarkamar_nama);
            textDaftarKamarStatus = itemView.findViewById(R.id.text_daftarkamar_status);
        }
    }
}
