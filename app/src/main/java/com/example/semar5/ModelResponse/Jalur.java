package com.example.semar5.ModelResponse;

import com.google.gson.annotations.SerializedName;

public class Jalur {

    @SerializedName("id_jalur")
    private int idJalur;

    @SerializedName("jalur")
    private String jalur;

    // Getters and setters

    public int getIdJalur() {
        return idJalur;
    }

    public void setIdJalur(int idJalur) {
        this.idJalur = idJalur;
    }

    public String getJalur() {
        return jalur;
    }

    public void setJalur(String jalur) {
        this.jalur = jalur;
    }

    @Override
    public String toString() {
        return jalur;
    }

}
