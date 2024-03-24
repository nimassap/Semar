package com.example.semar5.CRUD;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.semar5.DataMahasiswa;
import com.example.semar5.DataMahasiswaKamar;
import com.example.semar5.DataMahasiswaProfile;
import com.example.semar5.R;
import com.example.semar5.Retrofit.Api;
import com.example.semar5.Retrofit.RetrofitClient;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.MahasiswaViewHolder> {

    private List<Mahasiswa> dataList;
    private Context context;

    public MahasiswaAdapter(Context context, List<Mahasiswa> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MahasiswaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.datamhs_list, parent, false);
        return new MahasiswaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MahasiswaViewHolder holder, int position) {
        Mahasiswa mahasiswa = dataList.get(position);
        holder.bindData(mahasiswa);

        holder.btnOptions.setTag(mahasiswa.getIdMahasiswa());
        holder.itemView.setTag(mahasiswa.getIdMahasiswa());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MahasiswaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvNamaMhs;
        private ImageButton btnOptions;

        public MahasiswaViewHolder(View itemView) {
            super(itemView);
            tvNamaMhs = itemView.findViewById(R.id.nama_mhs_fetch);
            btnOptions = itemView.findViewById(R.id.btn_options);

            btnOptions.setOnClickListener(this);
            tvNamaMhs.setOnClickListener(this);
        }

        public void bindData(Mahasiswa mahasiswa) {
            tvNamaMhs.setText("Nama : " + mahasiswa.getNamaMahasiswa());
        }

        @Override
        public void onClick(View v) {
            if (v == btnOptions) {
                showPopupMenu(v);
            } else {
                int idMahasiswa = (int) itemView.getTag();

                Intent intent = new Intent(context, DataMahasiswaProfile.class);
                intent.putExtra(DataMahasiswaProfile.EXTRA_MAHASISWA_ID, idMahasiswa);

                context.startActivity(intent);
            }
        }

        private void showPopupMenu(View view) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.popup_menu);
            int idMahasiswa = (int) btnOptions.getTag();

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int itemId = item.getItemId();
                    if (itemId == R.id.menu_delete) {
                        deleteMahasiswa(idMahasiswa);
                        //Toast.makeText(context, "Delete Clicked for Mahasiswa ID: " + idMahasiswa, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    return false;
                }
            });

            popupMenu.show();
        }

        private void deleteMahasiswa(int idMahasiswa) {
            RetrofitClient retrofitClient = RetrofitClient.getInstance(context);
            Api apiService = retrofitClient.getApi();

            Call<ResponseBody> call = apiService.deleteMahasiswa(idMahasiswa);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Log.d("DELETE_MAHASISWA", "Data mahasiswa berhasil dihapus");
                        Toast.makeText(context, "Data mahasiswa berhasil dihapus", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, DataMahasiswa.class);
                        context.startActivity(intent);
                    } else {
                        Log.e("DELETE_MAHASISWA", "Gagal menghapus data mahasiswa, kode respons: " + response.code());
                        Toast.makeText(context, "Gagal menghapus data mahasiswa", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("DELETE_MAHASISWA", "Gagal menghubungi server: " + t.getMessage());
                    Toast.makeText(context, "Gagal menghubungi server", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}