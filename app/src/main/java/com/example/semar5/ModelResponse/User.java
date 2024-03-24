package com.example.semar5.ModelResponse;

public class User {

    private Fakultas fakultas;
    int id_mahasiswa, id_role;
    String nama_mahasiswa, email, password, nim, tgl_lahir, agama, jenis_kelamin, alamat, no_telp, nama_orangtua,
            pekerjaan_orangtua, alamat_orangtua, notelp_orangtua;
    int id_fakultas, id_prodi;


    public User(int id, int role, String email, String password, String nama, String nim, int id_fakultas, int id_prodi, String tgl_lahir, String agama,
                String jns_kelamin, String alamat, String notelp, String nama_orangtua, String pekerjaan_orangtua,
                String alamat_orangtua, String notelp_orangtua) {
        this.id_mahasiswa = id;
        this.id_role = role;
        this.email = email;
        this.password = password;
        this.nama_mahasiswa = nama;
        this.nim = nim;
        this.id_fakultas = id_fakultas;
        this.id_prodi = id_prodi;
        this.tgl_lahir = tgl_lahir;
        this.agama = agama;
        this.jenis_kelamin = jns_kelamin;
        this.alamat = alamat;
        this.no_telp = notelp;
        this.nama_orangtua = nama_orangtua;
        this.pekerjaan_orangtua = pekerjaan_orangtua;
        this.alamat_orangtua = alamat_orangtua;
        this.notelp_orangtua = notelp_orangtua;

        this.fakultas = new Fakultas();
    }

    public Fakultas getFakultas() {
        return fakultas;
    }

    //id
    public int getId_mahasiswa() {
        return id_mahasiswa;
    }

    public void setId_mahasiswa(int id_mahasiswa) {
        this.id_mahasiswa = id_mahasiswa;
    }

    //role
    public int getId_role() {
        return id_role;
    }

    public void setId_role(int id_role) {
        this.id_role = id_role;
    }

    //email
    public String getEmail_mahasiswa() {
        return email;
    }

    public void setEmail_mahasiswa(String email) {
        this.email = email;
    }

    //password
    public String getPassword_mahasiswa() {
        return password;
    }

    public void setPassword_mahasiswa(String password) {
        this.password = password;
    }

    //nama
    public String getNama_mahasiswa() {
        return nama_mahasiswa;
    }

    public void setNama_mahasiswa(String nama_mahasiswa) {
        this.nama_mahasiswa = nama_mahasiswa;
    }

    //nim
    public String getNim_mahasiswa() {
        return nim;
    }

    public void setNim_mahasiswa(String nim) {
        this.nim = nim;
    }

    //fakultas
    public int getid_fakultas() {
        return id_fakultas;
    }

    public void setid_fakultas(int id_fakultas) {
        this.id_fakultas = id_fakultas;
    }

    //fakultas
    public int getid_prodi() {
        return id_prodi;
    }

    public void setid_prodi(int id_prodi) {
        this.id_prodi = id_prodi;
    }

    //tgl lahir
    public String getTgl_lahir_mahasiswa() {
        return tgl_lahir;
    }

    public void setTgl_lahir_mahasiswa(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    //agama
    public String getAgama_mahasiswa() {
        return agama;
    }

    public void setAgama_mahasiswa(String agama) {
        this.agama = agama;
    }

    //jenis kelamin
    public String getJenis_kelamin_mahasiswa() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin_mahasiswa(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    //alamat
    public String getAlamat_mahasiswa() {
        return alamat;
    }

    public void setAlamat_mahasiswa(String alamat) {
        this.alamat = alamat;
    }

    //no telp
    public String getNo_telp_mahasiswa() {
        return no_telp;
    }

    public void setNo_telp_mahasiswa(String no_telp) {
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
}
