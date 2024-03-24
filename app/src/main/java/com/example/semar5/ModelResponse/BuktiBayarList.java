package com.example.semar5.ModelResponse;

import com.google.gson.annotations.SerializedName;

public class BuktiBayarList {

    @SerializedName("id_buktipembayaran")
    private int idBuktiBayar;

    @SerializedName("id_mahasiswa")
    private int idMahasiswa;

    @SerializedName("id_pemesanan")
    private int idPemesanan;

    @SerializedName("image_name")
    private String imageName;

    @SerializedName("image_path")
    private String imagePath;

    @SerializedName("upload_date")
    private String uploadDate;

    public BuktiBayarList(int idBuktiBayar, int idMahasiswa, int idPemesanan, String imageName, String imagePath, String uploadDate) {
        this.idBuktiBayar = idBuktiBayar;
        this.idMahasiswa = idMahasiswa;
        this.idPemesanan = idPemesanan;
        this.imageName = imageName;
        this.imagePath = imagePath;
        this.uploadDate = uploadDate;
    }

    public int getIdBuktiBayar() {
        return idBuktiBayar;
    }

    public void setIdBuktiBayar(int idBuktiBayar) {
        this.idBuktiBayar = idBuktiBayar;
    }

    public int getIdMahasiswa() {
        return idMahasiswa;
    }

    public void setIdMahasiswa(int idMahasiswa) {
        this.idMahasiswa = idMahasiswa;
    }

    public int getIdPemesanan() {
        return idPemesanan;
    }

    public void setIdPemesanan(int idPemesanan) {
        this.idPemesanan = idPemesanan;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }
}
