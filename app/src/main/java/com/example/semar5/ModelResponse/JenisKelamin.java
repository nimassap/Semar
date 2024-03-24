package com.example.semar5.ModelResponse;

import com.google.gson.annotations.SerializedName;

public class JenisKelamin {

    @SerializedName("id_jeniskelamin")
    private int idJenisKelamin;

    @SerializedName("jenis_kelamin")
    private String jenisKelamin;

    public int getIdJenisKelamin() {
        return idJenisKelamin;
    }

    public void setIdJenisKelamin(int idJenisKelamin) {
        this.idJenisKelamin = idJenisKelamin;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }



    @Override
    public String toString() {
        return jenisKelamin;
    }

}
