package com.example.semar5.ModelResponse;

import com.example.semar5.CRUD.Mahasiswa;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PemesananResponse {

    @SerializedName("pemesanan")
    private List<Pemesanan> pemesananList;

    @SerializedName("error")
    private String error;

    @SerializedName("message")
    private String message;

    public PemesananResponse(List<Pemesanan> pemesananList, String error, String message) {
        this.pemesananList = pemesananList;
        this.error = error;
        this.message = message;
    }

    public List<Pemesanan> getPemesananList() {
        return pemesananList;
    }

    public void setPemesananList(List<Pemesanan> pemesananList) {
        this.pemesananList = pemesananList;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}