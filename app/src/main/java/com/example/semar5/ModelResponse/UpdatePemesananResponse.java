package com.example.semar5.ModelResponse;

import com.google.gson.annotations.SerializedName;

public class UpdatePemesananResponse {

    @SerializedName("mahasiswa")
    private String mahasiswa;

    @SerializedName("jalur")
    private String jalur;

    @SerializedName("opsi_pembayaran")
    private String opsiPembayaran;

    @SerializedName("jumlah_penghuni")
    private String jumlahPenghuni;

    @SerializedName("jenis_kelamin")
    private String jenisKelamin;

    @SerializedName("gedung")
    private String gedung;

    @SerializedName("kamar")
    private String kamar;

    @SerializedName("harga")
    private String harga;

    @SerializedName("tanggal_masuk")
    private String tanggalMasuk;

    @SerializedName("tanggal_keluar")
    private String tanggalKeluar;

    public UpdatePemesananResponse(String mahasiswa, String jalur, String opsiPembayaran, String jumlahPenghuni, String jenisKelamin, String gedung, String kamar, String harga, String tanggalMasuk, String tanggalKeluar) {
        this.mahasiswa = mahasiswa;
        this.jalur = jalur;
        this.opsiPembayaran = opsiPembayaran;
        this.jumlahPenghuni = jumlahPenghuni;
        this.jenisKelamin = jenisKelamin;
        this.gedung = gedung;
        this.kamar = kamar;
        this.harga = harga;
        this.tanggalMasuk = tanggalMasuk;
        this.tanggalKeluar = tanggalKeluar;
    }

    public String getMahasiswa() {
        return mahasiswa;
    }

    public void setMahasiswa(String mahasiswa) {
        this.mahasiswa = mahasiswa;
    }

    public String getJalur() {
        return jalur;
    }

    public void setJalur(String jalur) {
        this.jalur = jalur;
    }

    public String getOpsiPembayaran() {
        return opsiPembayaran;
    }

    public void setOpsiPembayaran(String opsiPembayaran) {
        this.opsiPembayaran = opsiPembayaran;
    }

    public String getJumlahPenghuni() {
        return jumlahPenghuni;
    }

    public void setJumlahPenghuni(String jumlahPenghuni) {
        this.jumlahPenghuni = jumlahPenghuni;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getGedung() {
        return gedung;
    }

    public void setGedung(String gedung) {
        this.gedung = gedung;
    }

    public String getKamar() {
        return kamar;
    }

    public void setKamar(String kamar) {
        this.kamar = kamar;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getTanggalMasuk() {
        return tanggalMasuk;
    }

    public void setTanggalMasuk(String tanggalMasuk) {
        this.tanggalMasuk = tanggalMasuk;
    }

    public String getTanggalKeluar() {
        return tanggalKeluar;
    }

    public void setTanggalKeluar(String tanggalKeluar) {
        this.tanggalKeluar = tanggalKeluar;
    }
}
