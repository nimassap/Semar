package com.example.semar5.ModelResponse;

import com.google.gson.annotations.SerializedName;

public class Fakultas {

    @SerializedName("id_fakultas")
    private int idFakultas;

    @SerializedName("nama_fakultas")
    private String namaFakultas;


    public int getIdFakultas() {
        return idFakultas;
    }

    public void setIdFakultas(int idFakultas) {
        this.idFakultas = idFakultas;
    }

    public String getNamaFakultas() {
        return namaFakultas;
    }

    public void setNamaFakultas(String namaFakultas) {
        this.namaFakultas = namaFakultas;
    }

    @Override
    public String toString() {
        return namaFakultas;
    }
}
