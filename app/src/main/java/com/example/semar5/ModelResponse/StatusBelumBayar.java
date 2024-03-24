package com.example.semar5.ModelResponse;

import java.util.Date;

public class StatusBelumBayar {

    private int id_pemesanan;
    private int id_mahasiswa;
    private int id_jalur;
    private int id_opsipembayaran;
    private int id_jumlahpenghuni;
    private int id_jeniskelamin;
    private int id_gedung;
    private int id_kamar;
    private int id_harga;
    private Date tanggal_masuk;
    private Date tanggal_keluar;
    private String status_pemesanan;

    public StatusBelumBayar(int id_pemesanan, int id_mahasiswa, int id_jalur, int id_opsipembayaran, int id_jumlahpenghuni, int id_jeniskelamin, int id_gedung, int id_kamar, int id_harga, Date tanggal_masuk, Date tanggal_keluar, String status_pemesanan) {
        this.id_pemesanan = id_pemesanan;
        this.id_mahasiswa = id_mahasiswa;
        this.id_jalur = id_jalur;
        this.id_opsipembayaran = id_opsipembayaran;
        this.id_jumlahpenghuni = id_jumlahpenghuni;
        this.id_jeniskelamin = id_jeniskelamin;
        this.id_gedung = id_gedung;
        this.id_kamar = id_kamar;
        this.id_harga = id_harga;
        this.tanggal_masuk = tanggal_masuk;
        this.tanggal_keluar = tanggal_keluar;
        this.status_pemesanan = status_pemesanan;
    }

    public int getId_pemesanan() {
        return id_pemesanan;
    }

    public void setId_pemesanan(int id_pemesanan) {
        this.id_pemesanan = id_pemesanan;
    }

    public int getId_mahasiswa() {
        return id_mahasiswa;
    }

    public void setId_mahasiswa(int id_mahasiswa) {
        this.id_mahasiswa = id_mahasiswa;
    }

    public int getId_jalur() {
        return id_jalur;
    }

    public void setId_jalur(int id_jalur) {
        this.id_jalur = id_jalur;
    }

    public int getId_opsipembayaran() {
        return id_opsipembayaran;
    }

    public void setId_opsipembayaran(int id_opsipembayaran) {
        this.id_opsipembayaran = id_opsipembayaran;
    }

    public int getId_jumlahpenghuni() {
        return id_jumlahpenghuni;
    }

    public void setId_jumlahpenghuni(int id_jumlahpenghuni) {
        this.id_jumlahpenghuni = id_jumlahpenghuni;
    }

    public int getId_jeniskelamin() {
        return id_jeniskelamin;
    }

    public void setId_jeniskelamin(int id_jeniskelamin) {
        this.id_jeniskelamin = id_jeniskelamin;
    }

    public int getId_gedung() {
        return id_gedung;
    }

    public void setId_gedung(int id_gedung) {
        this.id_gedung = id_gedung;
    }

    public int getId_kamar() {
        return id_kamar;
    }

    public void setId_kamar(int id_kamar) {
        this.id_kamar = id_kamar;
    }

    public int getId_harga() {
        return id_harga;
    }

    public void setId_harga(int id_harga) {
        this.id_harga = id_harga;
    }

    public Date getTanggal_masuk() {
        return tanggal_masuk;
    }

    public void setTanggal_masuk(Date tanggal_masuk) {
        this.tanggal_masuk = tanggal_masuk;
    }

    public Date getTanggal_keluar() {
        return tanggal_keluar;
    }

    public void setTanggal_keluar(Date tanggal_keluar) {
        this.tanggal_keluar = tanggal_keluar;
    }

    public String getStatus_pemesanan() {
        return status_pemesanan;
    }

    public void setStatus_pemesanan(String status_pemesanan) {
        this.status_pemesanan = status_pemesanan;
    }
}
