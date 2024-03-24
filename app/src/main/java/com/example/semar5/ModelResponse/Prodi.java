package com.example.semar5.ModelResponse;

import com.google.gson.annotations.SerializedName;

public class Prodi {

    @SerializedName("id_prodi")
    private int idProdi;

    @SerializedName("nama_prodi")
    private String namaProdi;



    public int getIdProdi() {
        return idProdi;
    }

    public void setIdProdi(int idProdi) {
        this.idProdi = idProdi;
    }

    public String getNamaProdi() {
        return namaProdi;
    }

    public void setNamaProdi(String namaProdi) {
        this.namaProdi = namaProdi;
    }

    @Override
    public String toString() {
        return namaProdi;
    }
}
