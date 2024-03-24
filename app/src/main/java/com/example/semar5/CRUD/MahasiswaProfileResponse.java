package com.example.semar5.CRUD;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MahasiswaProfileResponse {

    @SerializedName("mahasiswa")
    private Mahasiswa mahasiswa;

    @SerializedName("error")
    private String error;

    @SerializedName("message")
    private String message;

    public Mahasiswa getMahasiswa() {
        return mahasiswa;
    }

    public void setMahasiswa(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
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
