package com.example.semar5.ModelResponse;

import com.google.gson.annotations.SerializedName;

public class TampilCaraDaftar {

    @SerializedName("id_caradaftar")
    private int id;

    @SerializedName("cara_daftarkamar")
    private String cara_daftar;

    public TampilCaraDaftar(int id, String cara_daftar) {
        this.id = id;
        this.cara_daftar = cara_daftar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCara_daftar() {
        return cara_daftar;
    }

    public void setCara_daftar(String cara_daftar) {
        this.cara_daftar = cara_daftar;
    }
}
