package com.example.semar5.ModelResponse;

import com.google.gson.annotations.SerializedName;

public class Gedung {

    @SerializedName("id_gedung")
    private int idGedung;

    @SerializedName("gedung")
    private String gedung;

    public int getIdGedung() {
        return idGedung;
    }

    public void setIdGedung(int idGedung) {
        this.idGedung = idGedung;
    }

    public String getGedung() {
        return gedung;
    }

    public void setGedung(String gedung) {
        this.gedung = gedung;
    }

    @Override
    public String toString() {
        return gedung;
    }

}
