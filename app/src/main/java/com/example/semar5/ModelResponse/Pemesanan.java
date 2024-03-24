package com.example.semar5.ModelResponse;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Pemesanan {

    @SerializedName("id_pemesanan")
    private int idPemesanan;

    @SerializedName("id_mahasiswa")
    private int idMahasiswa;

    @SerializedName("nama_mahasiswa")
    private String namaMahasiswa;


    @SerializedName("id_jalur")
    private int idJalur;

    @SerializedName("jalur")
    private String jalur;


    @SerializedName("id_opsipembayaran")
    private int idOpsiPembayaran;

    @SerializedName("opsi_pembayaran")
    private String opsiPembayaran;


    @SerializedName("id_jumlahpenghuni")
    private int idJumlahPenghuni;

    @SerializedName("jumlah_penghuni")
    private String jumlahPenghuni;


    @SerializedName("id_jeniskelamin")
    private int idJenisKelamin;

    @SerializedName("jenis_kelamin")
    private String jenisKelamin;


    @SerializedName("id_gedung")
    private int idGedung;

    @SerializedName("gedung")
    private String gedung;


    @SerializedName("id_kamar")
    private int idKamar;

    @SerializedName("kode_kamar")
    private String kodeKamar;


    @SerializedName("id_harga")
    private int idHarga;

    @SerializedName("harga")
    private String harga;


    @SerializedName("tanggal_masuk")
    private Date tanggalMasuk;

    @SerializedName("tanggal_keluar")
    private Date tanggalKeluar;

    @SerializedName("status_pemesanan")
    private String statusPemesanan;

    public Pemesanan(int idPemesanan, int idMahasiswa, String namaMahasiswa, int idJalur, String jalur, int idOpsiPembayaran, String opsiPembayaran, int idJumlahPenghuni, String jumlahPenghuni, int idJenisKelamin, String jenisKelamin, int idGedung, String gedung, int idKamar, String kodeKamar, int idHarga, String harga, Date tanggalMasuk, Date tanggalKeluar, String statusPemesanan) {
        this.idPemesanan = idPemesanan;
        this.idMahasiswa = idMahasiswa;
        this.namaMahasiswa = namaMahasiswa;
        this.idJalur = idJalur;
        this.jalur = jalur;
        this.idOpsiPembayaran = idOpsiPembayaran;
        this.opsiPembayaran = opsiPembayaran;
        this.idJumlahPenghuni = idJumlahPenghuni;
        this.jumlahPenghuni = jumlahPenghuni;
        this.idJenisKelamin = idJenisKelamin;
        this.jenisKelamin = jenisKelamin;
        this.idGedung = idGedung;
        this.gedung = gedung;
        this.idKamar = idKamar;
        this.kodeKamar = kodeKamar;
        this.idHarga = idHarga;
        this.harga = harga;
        this.tanggalMasuk = tanggalMasuk;
        this.tanggalKeluar = tanggalKeluar;
        this.statusPemesanan = statusPemesanan;
    }

    public int getIdPemesanan() {
        return idPemesanan;
    }

    public void setIdPemesanan(int idPemesanan) {
        this.idPemesanan = idPemesanan;
    }

    public int getIdMahasiswa() {
        return idMahasiswa;
    }

    public void setIdMahasiswa(int idMahasiswa) {
        this.idMahasiswa = idMahasiswa;
    }

    public String getNamaMahasiswa() {
        return namaMahasiswa;
    }

    public void setNamaMahasiswa(String namaMahasiswa) {
        this.namaMahasiswa = namaMahasiswa;
    }

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

    public int getIdOpsiPembayaran() {
        return idOpsiPembayaran;
    }

    public void setIdOpsiPembayaran(int idOpsiPembayaran) {
        this.idOpsiPembayaran = idOpsiPembayaran;
    }

    public String getOpsiPembayaran() {
        return opsiPembayaran;
    }

    public void setOpsiPembayaran(String opsiPembayaran) {
        this.opsiPembayaran = opsiPembayaran;
    }

    public int getIdJumlahPenghuni() {
        return idJumlahPenghuni;
    }

    public void setIdJumlahPenghuni(int idJumlahPenghuni) {
        this.idJumlahPenghuni = idJumlahPenghuni;
    }

    public String getJumlahPenghuni() {
        return jumlahPenghuni;
    }

    public void setJumlahPenghuni(String jumlahPenghuni) {
        this.jumlahPenghuni = jumlahPenghuni;
    }

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

    public int getIdKamar() {
        return idKamar;
    }

    public void setIdKamar(int idKamar) {
        this.idKamar = idKamar;
    }

    public String getKodeKamar() {
        return kodeKamar;
    }

    public void setKodeKamar(String kodeKamar) {
        this.kodeKamar = kodeKamar;
    }

    public int getIdHarga() {
        return idHarga;
    }

    public void setIdHarga(int idHarga) {
        this.idHarga = idHarga;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public Date getTanggalMasuk() {
        return tanggalMasuk;
    }

    public void setTanggalMasuk(Date tanggalMasuk) {
        this.tanggalMasuk = tanggalMasuk;
    }

    public Date getTanggalKeluar() {
        return tanggalKeluar;
    }

    public void setTanggalKeluar(Date tanggalKeluar) {
        this.tanggalKeluar = tanggalKeluar;
    }

    public String getStatusPemesanan() {
        return statusPemesanan;
    }

    public void setStatusPemesanan(String statusPemesanan) {
        this.statusPemesanan = statusPemesanan;
    }
}