package com.example.semar5.CRUD;

import com.google.gson.annotations.SerializedName;

public class Mahasiswa {
    @SerializedName("id_mahasiswa")
    private int idMahasiswa;

    @SerializedName("id_role")
    private int idRole;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("nama_mahasiswa")
    private String namaMahasiswa;

    @SerializedName("nim")
    private String nim;

    @SerializedName("id_fakultas")
    private int idFakultas;

    @SerializedName("nama_fakultas")
    private String namaFakultas;

    @SerializedName("id_prodi")
    private int idProdi;

    @SerializedName("nama_prodi")
    private String namaProdi;

    @SerializedName("tgl_lahir")
    private String tglLahir;

    @SerializedName("agama")
    private String agama;

    @SerializedName("jenis_kelamin")
    private String jenisKelamin;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("no_telp")
    private String noTelp;

    @SerializedName("nama_orangtua")
    private String namaOrangtua;

    @SerializedName("pekerjaan_orangtua")
    private String pekerjaanOrangtua;

    @SerializedName("alamat_orangtua")
    private String alamatOrangtua;

    @SerializedName("notelp_orangtua")
    private String noTelpOrangtua;


    public int getIdMahasiswa() {
        return idMahasiswa;
    }

    public void setIdMahasiswa(int idMahasiswa) {
        this.idMahasiswa = idMahasiswa;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
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

    public int getIdFakultas() {
        return idFakultas;
    }

    public void setIdFakultas(int idFakultas) {
        this.idFakultas = idFakultas;
    }

    public String getNamaFakultas() {
        return namaFakultas;
    }

    public void setNamaFakultas(String namaFakultas) {
        this.namaFakultas = namaFakultas;
    }

    public int getIdProdi() {
        return idProdi;
    }

    public void setIdProdi(int idProdi) {
        this.idProdi = idProdi;
    }

    public String getNamaProdi() {
        return namaProdi;
    }

    public void setNamaProdi(String namaProdi) {
        this.namaProdi = namaProdi;
    }

    public String getTglLahir() {
        return tglLahir;
    }

    public void setTglLahir(String tglLahir) {
        this.tglLahir = tglLahir;
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

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getNamaOrangtua() {
        return namaOrangtua;
    }

    public void setNamaOrangtua(String namaOrangtua) {
        this.namaOrangtua = namaOrangtua;
    }

    public String getPekerjaanOrangtua() {
        return pekerjaanOrangtua;
    }

    public void setPekerjaanOrangtua(String pekerjaanOrangtua) {
        this.pekerjaanOrangtua = pekerjaanOrangtua;
    }

    public String getAlamatOrangtua() {
        return alamatOrangtua;
    }

    public void setAlamatOrangtua(String alamatOrangtua) {
        this.alamatOrangtua = alamatOrangtua;
    }

    public String getNoTelpOrangtua() {
        return noTelpOrangtua;
    }

    public void setNoTelpOrangtua(String noTelpOrangtua) {
        this.noTelpOrangtua = noTelpOrangtua;
    }
}