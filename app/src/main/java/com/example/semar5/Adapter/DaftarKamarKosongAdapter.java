package com.example.semar5.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.semar5.ModelResponse.Kamar;
import com.example.semar5.R;

import java.util.List;

public class DaftarKamarKosongAdapter extends RecyclerView.Adapter<DaftarKamarKosongAdapter.ViewHolder> {
    private List<Kamar> dataList;

    public DaftarKamarKosongAdapter(List<Kamar> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tampilan_daftar_kamar_kosong, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Kamar data = dataList.get(position);
        holder.itemNumberTextView.setText(String.valueOf(position + 1) + ".");
        holder.textDaftarKamarTextView.setText(data.getKode_kamar());
    }

    @Override
    public int getItemCount() {
        if (dataList != null) {
            return dataList.size();
        } else {
            return 0;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemNumberTextView;
        public TextView textDaftarKamarTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemNumberTextView = itemView.findViewById(R.id.itemNumber);
            textDaftarKamarTextView = itemView.findViewById(R.id.text_daftarkamar);
        }
    }
}
