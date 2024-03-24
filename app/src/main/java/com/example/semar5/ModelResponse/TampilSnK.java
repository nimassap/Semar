package com.example.semar5.ModelResponse;

import com.google.gson.annotations.SerializedName;

public class TampilSnK {

    @SerializedName("id_snk")
    private int id;

    @SerializedName("syarat_ketentuan")
    private String syarat_ketentuan;

    public TampilSnK(int id, String syarat_ketentuan) {
        this.id = id;
        this.syarat_ketentuan = syarat_ketentuan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSyarat_ketentuan() {
        return syarat_ketentuan;
    }

    public void setSyarat_ketentuan(String syarat_ketentuan) {
        this.syarat_ketentuan = syarat_ketentuan;
    }
}
