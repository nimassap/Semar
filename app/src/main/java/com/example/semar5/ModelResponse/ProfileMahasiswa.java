package com.example.semar5.ModelResponse;

import com.google.gson.annotations.SerializedName;

public class ProfileMahasiswa {
    @SerializedName("id_mahasiswa")
    private String idMahasiswa;
    @SerializedName("id_role")
    private String idRole;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("nama_mahasiswa")
    private String namaMahasiswa;
    @SerializedName("nim")
    private String nim;
    @SerializedName("tgl_lahir")
    private String tanggalLahir;
    @SerializedName("agama")
    private String agama;
    @SerializedName("jenis_kelamin")
    private String jenisKelamin;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("no_telp")
    private String nomorTelepon;
    @SerializedName("nama_orangtua")
    private String namaOrangTua;
    @SerializedName("pekerjaan_orangtua")
    private String pekerjaanOrangTua;
    @SerializedName("alamat_orangtua")
    private String alamatOrangTua;
    @SerializedName("notelp_orangtua")
    private String nomorTeleponOrangTua;

    public ProfileMahasiswa(String idMahasiswa, String idRole, String email, String password, String namaMahasiswa, String nim, String tanggalLahir, String agama, String jenisKelamin, String alamat, String nomorTelepon, String namaOrangTua, String pekerjaanOrangTua, String alamatOrangTua, String nomorTeleponOrangTua) {
        this.idMahasiswa = idMahasiswa;
        this.idRole = idRole;
        this.email = email;
        this.password = password;
        this.namaMahasiswa = namaMahasiswa;
        this.nim = nim;
        this.tanggalLahir = tanggalLahir;
        this.agama = agama;
        this.jenisKelamin = jenisKelamin;
        this.alamat = alamat;
        this.nomorTelepon = nomorTelepon;
        this.namaOrangTua = namaOrangTua;
        this.pekerjaanOrangTua = pekerjaanOrangTua;
        this.alamatOrangTua = alamatOrangTua;
        this.nomorTeleponOrangTua = nomorTeleponOrangTua;
    }

    public String getIdMahasiswa() {
        return idMahasiswa;
    }

    public void setIdMahasiswa(String idMahasiswa) {
        this.idMahasiswa = idMahasiswa;
    }

    public String getIdRole() {
        return idRole;
    }

    public void setIdRole(String idRole) {
        this.idRole = idRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNamaMahasiswa() {
        return namaMahasiswa;
    }

    public void setNamaMahasiswa(String namaMahasiswa) {
        this.namaMahasiswa = namaMahasiswa;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    public String getNamaOrangTua() {
        return namaOrangTua;
    }

    public void setNamaOrangTua(String namaOrangTua) {
        this.namaOrangTua = namaOrangTua;
    }

    public String getPekerjaanOrangTua() {
        return pekerjaanOrangTua;
    }

    public void setPekerjaanOrangTua(String pekerjaanOrangTua) {
        this.pekerjaanOrangTua = pekerjaanOrangTua;
    }

    public String getAlamatOrangTua() {
        return alamatOrangTua;
    }

    public void setAlamatOrangTua(String alamatOrangTua) {
        this.alamatOrangTua = alamatOrangTua;
    }

    public String getNomorTeleponOrangTua() {
        return nomorTeleponOrangTua;
    }

    public void setNomorTeleponOrangTua(String nomorTeleponOrangTua) {
        this.nomorTeleponOrangTua = nomorTeleponOrangTua;
    }
}
