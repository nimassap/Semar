package com.example.semar5.ModelResponse;

import com.example.semar5.CRUD.Mahasiswa;
import com.example.semar5.CRUD.MahasiswaUpdate;
import com.google.gson.annotations.SerializedName;

public class DataMahasiswaUpdateResponse {

    @SerializedName("mahasiswa")
    private MahasiswaUpdate mahasiswa;

    @SerializedName("error")
    private String error;

    @SerializedName("message")
    private String message;

    public MahasiswaUpdate getMahasiswa() {
        return mahasiswa;
    }

    public void setMahasiswa(MahasiswaUpdate mahasiswa) {
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
