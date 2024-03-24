package com.example.semar5.ModelResponse;

import com.google.gson.annotations.SerializedName;

public class FotoProfileList {

    @SerializedName("id_fotoprofile")
    private int idFotoProfile;

    @SerializedName("id_mahasiswa")
    private int idMahasiswa;

    @SerializedName("image_name")
    private String imageName;

    @SerializedName("image_path")
    private String imagePath;

    @SerializedName("upload_date")
    private String uploadDate;

    public FotoProfileList(int idFotoProfile, int idMahasiswa, String imageName, String imagePath, String uploadDate) {
        this.idFotoProfile = idFotoProfile;
        this.idMahasiswa = idMahasiswa;
        this.imageName = imageName;
        this.imagePath = imagePath;
        this.uploadDate = uploadDate;
    }

    public int getIdFotoProfile() {
        return idFotoProfile;
    }

    public void setIdFotoProfile(int idFotoProfile) {
        this.idFotoProfile = idFotoProfile;
    }

    public int getIdMahasiswa() {
        return idMahasiswa;
    }

    public void setIdMahasiswa(int idMahasiswa) {
        this.idMahasiswa = idMahasiswa;
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
