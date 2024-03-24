package com.example.semar5.CRUD;

public class MahasiswaUpdate {

    int id_mahasiswa, id_role;
    String nama_mahasiswa, email, password, nim, tgl_lahir, agama, jenis_kelamin, alamat, no_telp, nama_orangtua,
            pekerjaan_orangtua, alamat_orangtua, notelp_orangtua;
    int id_fakultas, id_prodi;

    public MahasiswaUpdate(int id_mahasiswa, int id_role, String nama_mahasiswa, String email, String password, String nim, String tgl_lahir, String agama, String jenis_kelamin, String alamat, String no_telp, String nama_orangtua, String pekerjaan_orangtua, String alamat_orangtua, String notelp_orangtua, int id_fakultas, int id_prodi) {
        this.id_mahasiswa = id_mahasiswa;
        this.id_role = id_role;
        this.nama_mahasiswa = nama_mahasiswa;
        this.email = email;
        this.password = password;
        this.nim = nim;
        this.tgl_lahir = tgl_lahir;
        this.agama = agama;
        this.jenis_kelamin = jenis_kelamin;
        this.alamat = alamat;
        this.no_telp = no_telp;
        this.nama_orangtua = nama_orangtua;
        this.pekerjaan_orangtua = pekerjaan_orangtua;
        this.alamat_orangtua = alamat_orangtua;
        this.notelp_orangtua = notelp_orangtua;
        this.id_fakultas = id_fakultas;
        this.id_prodi = id_prodi;
    }

    public int getId_mahasiswa() {
        return id_mahasiswa;
    }

    public void setId_mahasiswa(int id_mahasiswa) {
        this.id_mahasiswa = id_mahasiswa;
    }

    public int getId_role() {
        return id_role;
    }

    public void setId_role(int id_role) {
        this.id_role = id_role;
    }

    public String getNama_mahasiswa() {
        return nama_mahasiswa;
    }

    public void setNama_mahasiswa(String nama_mahasiswa) {
        this.nama_mahasiswa = nama_mahasiswa;
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

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public String getNama_orangtua() {
        return nama_orangtua;
    }

    public void setNama_orangtua(String nama_orangtua) {
        this.nama_orangtua = nama_orangtua;
    }

    public String getPekerjaan_orangtua() {
        return pekerjaan_orangtua;
    }

    public void setPekerjaan_orangtua(String pekerjaan_orangtua) {
        this.pekerjaan_orangtua = pekerjaan_orangtua;
    }

    public String getAlamat_orangtua() {
        return alamat_orangtua;
    }

    public void setAlamat_orangtua(String alamat_orangtua) {
        this.alamat_orangtua = alamat_orangtua;
    }

    public String getNotelp_orangtua() {
        return notelp_orangtua;
    }

    public void setNotelp_orangtua(String notelp_orangtua) {
        this.notelp_orangtua = notelp_orangtua;
    }

    public int getId_fakultas() {
        return id_fakultas;
    }

    public void setId_fakultas(int id_fakultas) {
        this.id_fakultas = id_fakultas;
    }

    public int getId_prodi() {
        return id_prodi;
    }

    public void setId_prodi(int id_prodi) {
        this.id_prodi = id_prodi;
    }
}
