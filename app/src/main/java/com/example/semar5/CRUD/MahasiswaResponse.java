package com.example.semar5.CRUD;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MahasiswaResponse {

    @SerializedName("mahasiswa")
    private List<Mahasiswa> mahasiswaList;

    @SerializedName("error")
    private String error;

    @SerializedName("message")
    private String message;

    public List<Mahasiswa> getMahasiswaList() {
        return mahasiswaList;
    }

    public void setMahasiswaList(List<Mahasiswa> mahasiswaList) {
        this.mahasiswaList = mahasiswaList;
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
