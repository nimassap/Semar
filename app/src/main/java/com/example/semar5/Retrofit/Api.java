package com.example.semar5.Retrofit;

import com.example.semar5.CRUD.MahasiswaProfileResponse;
import com.example.semar5.CRUD.MahasiswaResponse;
import com.example.semar5.ModelResponse.BuktiBayarList;
import com.example.semar5.ModelResponse.DataMahasiswaUpdateResponse;
import com.example.semar5.ModelResponse.Fakultas;
import com.example.semar5.ModelResponse.FotoProfileList;
import com.example.semar5.ModelResponse.Gedung;
import com.example.semar5.ModelResponse.Harga;
import com.example.semar5.ModelResponse.Jalur;
import com.example.semar5.ModelResponse.JenisKelamin;
import com.example.semar5.ModelResponse.JumlahPenghuni;
import com.example.semar5.ModelResponse.Kamar;
import com.example.semar5.ModelResponse.LoginResponse;
import com.example.semar5.ModelResponse.OpsiPembayaran;
import com.example.semar5.ModelResponse.PemesananResponse;
import com.example.semar5.ModelResponse.Prodi;
import com.example.semar5.ModelResponse.ProfileMahasiswa;
import com.example.semar5.ModelResponse.ProfileMahasiswaResponse;
import com.example.semar5.ModelResponse.RegisterResponse;
import com.example.semar5.ModelResponse.Role;
import com.example.semar5.ModelResponse.StatusBelumBayar;
import com.example.semar5.ModelResponse.TambahDataMhsResponse;
import com.example.semar5.ModelResponse.TampilCaraDaftar;
import com.example.semar5.ModelResponse.TampilDeluxe1;
import com.example.semar5.ModelResponse.TampilDeluxe2;
import com.example.semar5.ModelResponse.TampilDeluxe3;
import com.example.semar5.ModelResponse.TampilDeluxe4;
import com.example.semar5.ModelResponse.TampilDeluxe5a;
import com.example.semar5.ModelResponse.TampilDeluxe5b;
import com.example.semar5.ModelResponse.TampilGedungA;
import com.example.semar5.ModelResponse.TampilGedungB;
import com.example.semar5.ModelResponse.TampilGedungC;
import com.example.semar5.ModelResponse.TampilGedungD;
import com.example.semar5.ModelResponse.TampilGedungE;
import com.example.semar5.ModelResponse.TampilSnK;
import com.example.semar5.ModelResponse.TampilStandard;
import com.example.semar5.ModelResponse.TampilVipAc;
import com.example.semar5.ModelResponse.TampilVipNonAc;
import com.example.semar5.ModelResponse.UbahPasswordResponse;
import com.example.semar5.ModelResponse.UpdateCaraDaftar;
import com.example.semar5.ModelResponse.UpdateDeluxe1;
import com.example.semar5.ModelResponse.UpdateDeluxe2;
import com.example.semar5.ModelResponse.UpdateDeluxe3;
import com.example.semar5.ModelResponse.UpdateDeluxe4;
import com.example.semar5.ModelResponse.UpdateDeluxe5a;
import com.example.semar5.ModelResponse.UpdateDeluxe5b;
import com.example.semar5.ModelResponse.UpdateGedungA;
import com.example.semar5.ModelResponse.UpdateGedungB;
import com.example.semar5.ModelResponse.UpdateGedungC;
import com.example.semar5.ModelResponse.UpdateGedungD;
import com.example.semar5.ModelResponse.UpdateGedungE;
import com.example.semar5.ModelResponse.UpdatePemesananResponse;
import com.example.semar5.ModelResponse.UpdateSnK;
import com.example.semar5.ModelResponse.UpdateStandard;
import com.example.semar5.ModelResponse.UpdateVipAc;
import com.example.semar5.ModelResponse.UpdateVipNonAc;
import com.example.semar5.ModelResponse.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface Api {

    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterResponse> register(
            @Field("nama_mahasiswa") String nama_mahasiswa,
            @Field("no_telp") String no_telp,
            @Field("email") String email,
            @Field("password") String password,
            @Field("confirm_password") String confirmPassword
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );


    @GET("profile_tampil.php")
    Call<List<ProfileMahasiswa>> getProfileMahasiswa(@Query("email") String email);


    @GET("fakultas.php")
    Call<List<Fakultas>> getFakultasData();

    @GET("prodi.php")
    Call<List<Prodi>> getProdiData(@Query("nama_fakultas") String fakultas);

    @FormUrlEncoded
    @POST("update_profile_mahasiswa.php")
    Call<ProfileMahasiswaResponse> updateData(
            @Field("email") String email,
            @Field("nama_mahasiswa") String nama_mahasiswa,
            @Field("nim") String nim,
            @Field("id_fakultas") int id_fakultas,
            @Field("id_prodi") int id_prodi,
            @Field("tgl_lahir") String tgl_lahir,
            @Field("agama") String agama,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("alamat") String alamat,
            @Field("no_telp") String no_telp,
            @Field("nama_orangtua") String nama_orangtua,
            @Field("pekerjaan_orangtua") String pekerjaan_orangtua,
            @Field("alamat_orangtua") String alamat_orangtua,
            @Field("notelp_orangtua") String notelp_orangtua
    );


    @FormUrlEncoded
    @POST("update_data_mahasiswa.php")
    Call<DataMahasiswaUpdateResponse> updateDataMahasiswa(
            @Field("email") String email,
            @Field("nama_mahasiswa") String nama_mahasiswa,
            @Field("nim") String nim,
            @Field("id_fakultas") int id_fakultas,
            @Field("id_prodi") int id_prodi,
            @Field("tgl_lahir") String tgl_lahir,
            @Field("agama") String agama,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("alamat") String alamat,
            @Field("no_telp") String no_telp,
            @Field("nama_orangtua") String nama_orangtua,
            @Field("pekerjaan_orangtua") String pekerjaan_orangtua,
            @Field("alamat_orangtua") String alamat_orangtua,
            @Field("notelp_orangtua") String notelp_orangtua
    );


    @FormUrlEncoded
    @POST("tambah_data_mahasiswa.php")
    Call<TambahDataMhsResponse> tambahDataMhs(
            @Field("email") String email,
            @Field("password") String password,
            @Field("nama_mahasiswa") String nama_mahasiswa,
            @Field("nim") String nim,
            @Field("id_fakultas") int id_fakultas,
            @Field("id_prodi") int id_prodi,
            @Field("tgl_lahir") String tgl_lahir,
            @Field("agama") String agama,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("alamat") String alamat,
            @Field("no_telp") String no_telp,
            @Field("nama_orangtua") String nama_orangtua,
            @Field("pekerjaan_orangtua") String pekerjaan_orangtua,
            @Field("alamat_orangtua") String alamat_orangtua,
            @Field("notelp_orangtua") String notelp_orangtua
    );


    @GET("jalur.php")
    Call<List<Jalur>> getJalurData();

    @GET("opsi_pembayaran.php")
    Call<List<OpsiPembayaran>> getOpsiPembayaranData(@Query("jalur") String jalur);

    @GET("jml_peng_test.php")
    Call<List<JumlahPenghuni>> getJumlahPenghuniData(@Query("opsi_pembayaran") String opsiPembayaran);

    @GET("jk_test.php")
    Call<List<JenisKelamin>> getJenisKelaminData(@Query("jumlah_penghuni") String jumlah_penghuni);

    @GET("gd_test.php")
    Call<List<Gedung>> getGedungData(@Query("jenis_kelamin") String jenisKelamin);

    @GET("km_test.php")
    Call<List<Kamar>> getKamarData(@Query("jumlah_penghuni") String jumlah_penghuni,
                                   @Query("gedung") String gedung);

    @GET("harga_test.php")
    Call<List<Harga>> getHargaData(@Query("opsi_pembayaran") String opsi_pembayaran,
                                   @Query("jumlah_penghuni") String jumlah_penghuni,
                                   @Query("kode_kamar") String kodeKamar);


    @GET("role_tampil.php")
    Call<List<Role>> getRoleData();


    @FormUrlEncoded
    @POST("pendaftaran_kamar.php")
    Call<ResponseBody> simpanDataPemesanan(
            @Field("email") String userEmail,
            @Field("update_mahasiswa") String userId,
            @Field("update_jalur") String selectedJalur,
            @Field("update_opsipembayaran") String selectedOpsiPembayaran,
            @Field("update_jumlahpenghuni") String selectedJumlahPenghuni,
            @Field("update_jeniskelamin") String selectedJenisKelamin,
            @Field("update_gedung") String selectedGedung,
            @Field("update_kamar") String selectedKamar,
            @Field("update_harga") String formattedValue,
            @Field("update_tanggal_masuk") String tanggalMasuk,
            @Field("update_tanggal_keluar") String tanggalKeluar
    );

    @FormUrlEncoded
    @POST("update_pendaftaran_kamar.php")
    Call<ResponseBody> updateDataPemesanan(
            @Query("id_pemesanan") int idPemesanan,
            @Field("email") String userEmail,
            @Field("update_mahasiswa") String userId,
            @Field("update_jalur") String selectedJalur,
            @Field("update_opsipembayaran") String selectedOpsiPembayaran,
            @Field("update_jumlahpenghuni") String selectedJumlahPenghuni,
            @Field("update_jeniskelamin") String selectedJenisKelamin,
            @Field("update_gedung") String selectedGedung,
            @Field("update_kamar") String selectedKamar,
            @Field("update_harga") String formattedValue,
            @Field("update_tanggal_masuk") String tanggalMasuk,
            @Field("update_tanggal_keluar") String tanggalKeluar
    );

    //deluxe 1
    //tampil data deluxe 1
    @GET("deluxe1_tampil.php")
    Call<List<TampilDeluxe1>> getDataDeluxe1();

    //update data deluxe 1
    @FormUrlEncoded
    @POST("deluxe1_update.php")
    Call<UpdateDeluxe1> updateDataDeluxe1(
            @Field("id_deluxe1") int id,
            @Field("text_edit") String text
    );

    //menampilkan image slider deluxe 1
    @GET("tampil_gambar_deluxe1.php")
    Call<List<String>> getGambarDeluxe1();


    //deluxe 2
    //tampil data deluxe 2
    @GET("deluxe2_tampil.php")
    Call<List<TampilDeluxe2>> getDataDeluxe2();

    //update data deluxe 2
    @FormUrlEncoded
    @POST("deluxe2_update.php")
    Call<UpdateDeluxe2> updateDataDeluxe2(
            @Field("id_deluxe2") int id,
            @Field("text_edit") String text
    );

    //menampilkan image slider deluxe 2
    @GET("tampil_gambar_deluxe2.php")
    Call<List<String>> getGambarDeluxe2();


    //deluxe 3
    //tampil data deluxe 3
    @GET("deluxe3_tampil.php")
    Call<List<TampilDeluxe3>> getDataDeluxe3();

    //update data deluxe 3
    @FormUrlEncoded
    @POST("deluxe3_update.php")
    Call<UpdateDeluxe3> updateDataDeluxe3(
            @Field("id_deluxe3") int id,
            @Field("text_edit") String text
    );

    //menampilkan image slider deluxe 3
    @GET("tampil_gambar_deluxe3.php")
    Call<List<String>> getGambarDeluxe3();


    //deluxe 4
    //tampil data deluxe 4
    @GET("deluxe4_tampil.php")
    Call<List<TampilDeluxe4>> getDataDeluxe4();

    //update data deluxe 4
    @FormUrlEncoded
    @POST("deluxe4_update.php")
    Call<UpdateDeluxe4> updateDataDeluxe4(
            @Field("id_deluxe4") int id,
            @Field("text_edit") String text
    );

    //menampilkan image slider deluxe 4
    @GET("tampil_gambar_deluxe4.php")
    Call<List<String>> getGambarDeluxe4();


    //deluxe 5a
    //tampil data deluxe 5a
    @GET("deluxe5a_tampil.php")
    Call<List<TampilDeluxe5a>> getDataDeluxe5a();

    //update data deluxe 5a
    @FormUrlEncoded
    @POST("deluxe5a_update.php")
    Call<UpdateDeluxe5a> updateDataDeluxe5a(
            @Field("id_deluxe5a") int id,
            @Field("text_edit") String text
    );

    //menampilkan image slider deluxe 5a
    @GET("tampil_gambar_deluxe5a.php")
    Call<List<String>> getGambarDeluxe5a();


    //deluxe 5b
    //tampil data deluxe 5b
    @GET("deluxe5b_tampil.php")
    Call<List<TampilDeluxe5b>> getDataDeluxe5b();

    //update data deluxe 5b
    @FormUrlEncoded
    @POST("deluxe5b_update.php")
    Call<UpdateDeluxe5b> updateDataDeluxe5b(
            @Field("id_deluxe5b") int id,
            @Field("text_edit") String text
    );

    //menampilkan image slider deluxe 5b
    @GET("tampil_gambar_deluxe5b.php")
    Call<List<String>> getGambarDeluxe5b();


    //standard
    //tampil data standard
    @GET("standard_tampil.php")
    Call<List<TampilStandard>> getDataStandard();

    //update data standard
    @FormUrlEncoded
    @POST("standard_update.php")
    Call<UpdateStandard> updateDataStandard(
            @Field("id_standard") int id,
            @Field("text_edit") String text
    );

    //menampilkan image slider standard
    @GET("tampil_gambar_standard.php")
    Call<List<String>> getGambarStandard();


    //vip ac
    //tampil data vip ac
    @GET("vipac_tampil.php")
    Call<List<TampilVipAc>> getDataVipAc();

    //update data vip ac
    @FormUrlEncoded
    @POST("vipac_update.php")
    Call<UpdateVipAc> updateDataVipAc(
            @Field("id_vipac") int id,
            @Field("text_edit") String text
    );

    //menampilkan image slider vip ac
    @GET("tampil_gambar_vipac.php")
    Call<List<String>> getGambarVipAc();


    //vip non ac
    //tampil data vip non ac
    @GET("vipnonac_tampil.php")
    Call<List<TampilVipNonAc>> getDataVipNonAc();

    //update data vip non ac
    @FormUrlEncoded
    @POST("vipnonac_update.php")
    Call<UpdateVipNonAc> updateDataVipNonAc(
            @Field("id_vipnonac") int id,
            @Field("text_edit") String text
    );

    //menampilkan image slider vip non ac
    @GET("tampil_gambar_vipnonac.php")
    Call<List<String>> getGambarVipNonAc();



    //gedung a
    //tampil data gedung a
    @GET("gedung_a_tampil.php")
    Call<List<TampilGedungA>> getDataGedungA();

    //update data gedung a
    @FormUrlEncoded
    @POST("gedunga_update.php")
    Call<UpdateGedungA> updateDataGedungA(
            @Field("id_gedunga") int id,
            @Field("text_edit") String text
    );

    //menampilkan image slider gedung a
    @GET("tampil_gambar_gedunga.php")
    Call<List<String>> getGambarGedungA();



    //gedung b
    //tampil data gedung b
    @GET("gedung_b_tampil.php")
    Call<List<TampilGedungB>> getDataGedungB();

    //update data gedung b
    @FormUrlEncoded
    @POST("gedungb_update.php")
    Call<UpdateGedungB> updateDataGedungB(
            @Field("id_gedungb") int id,
            @Field("text_edit") String text
    );

    //menampilkan image slider gedung b
    @GET("tampil_gambar_gedungb.php")
    Call<List<String>> getGambarGedungB();



    //gedung c
    //tampil data gedung c
    @GET("gedung_c_tampil.php")
    Call<List<TampilGedungC>> getDataGedungC();

    //update data gedung c
    @FormUrlEncoded
    @POST("gedungc_update.php")
    Call<UpdateGedungC> updateDataGedungC(
            @Field("id_gedungc") int id,
            @Field("text_edit") String text
    );

    //menampilkan image slider gedung c
    @GET("tampil_gambar_gedungc.php")
    Call<List<String>> getGambarGedungC();



    //gedung d
    //tampil data gedung d
    @GET("gedung_d_tampil.php")
    Call<List<TampilGedungD>> getDataGedungD();

    //update data gedung d
    @FormUrlEncoded
    @POST("gedungd_update.php")
    Call<UpdateGedungD> updateDataGedungD(
            @Field("id_gedungd") int id,
            @Field("text_edit") String text
    );

    //menampilkan image slider gedung d
    @GET("tampil_gambar_gedungd.php")
    Call<List<String>> getGambarGedungD();



    //gedung e
    //tampil data gedung e
    @GET("gedung_e_tampil.php")
    Call<List<TampilGedungE>> getDataGedungE();

    //update data gedung e
    @FormUrlEncoded
    @POST("gedunge_update.php")
    Call<UpdateGedungE> updateDataGedungE(
            @Field("id_gedunge") int id,
            @Field("text_edit") String text
    );

    //menampilkan image slider gedung e
    @GET("tampil_gambar_gedunge.php")
    Call<List<String>> getGambarGedungE();




    @GET("caradaftar_tampil.php")
    Call<List<TampilCaraDaftar>> getDataCaraDaftar();

    @FormUrlEncoded
    @POST("caradaftar_update.php")
    Call<UpdateCaraDaftar> updateDataCaraDaftar(
            @Field("id_caradaftar") int id,
            @Field("cara_daftarkamar") String text
    );


    @GET("snk_tampil.php")
    Call<List<TampilSnK>> getDataSnK();

    @FormUrlEncoded
    @POST("snk_update.php")
    Call<UpdateSnK> updateDataSnK(
            @Field("id_snk") int id,
            @Field("syarat_ketentuan") String text
    );





    @GET("data_mahasiswa.php")
    Call<MahasiswaResponse> getMahasiswaData();

    @GET("hapus_datamhs.php") // Update this with your actual endpoint
    Call<ResponseBody> deleteMahasiswa(@Query("id_mahasiswa") int idMahasiswa);

    @GET("data_mahasiswa_id.php")
    Call<MahasiswaProfileResponse> getMahasiswaDataId(@Query("id_mahasiswa") int idMahasiswa);

    /*@GET("mahasiswa/{id_mahasiswa}")
    Call<Mahasiswa> getMahasiswaById(@Path("id_mahasiswa") int idMahasiswa);*/

    /*@GET("pemesanan_tampil.php")
    Call<PemesananResponse> getPemesananData(@Query("id_mahasiswa") int idMahasiswa);*/

    @GET("pemesanan_data_id.php")
    Call<PemesananResponse> getPemesananData(@Query("id_mahasiswa") int idMahasiswa);

    @GET("pemesanan_data_semua.php")
    Call<PemesananResponse> getPemesananDataSemua();

    @FormUrlEncoded
    @POST("test_update(1).php")
    Call<UpdatePemesananResponse> updatePemesananData(
            @Field("id_mahasiswa") int idMahasiswa,
            @Field("update_mahasiswa") String updateMahasiswa,
            @Field("update_jalur") String updateJalur,
            @Field("update_opsipembayaran") String updateOpsiPembayaran,
            @Field("update_jumlahpenghuni") String updateJumlahPenghuni,
            @Field("update_jeniskelamin") String updateJenisKelamin,
            @Field("update_gedung") String updateGedung,
            @Field("update_kamar") String updateKamar,
            @Field("update_harga") String updateHarga,
            @Field("update_tanggal_masuk") String updateTanggalMasuk,
            @Field("update_tanggal_keluar") String updateTanggalKeluar
    );

    @GET("status_belum_bayar.php")
    Call<List<StatusBelumBayar>> getPemesananBelumBayar(@Query("id_mahasiswa") int id_mahasiswa);

    @GET("qris.php")
    Call<List<String>> getQrisData();

    /*@Multipart
    @POST("upload_bukti_bayar.php")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part image);*/

    @Multipart
    @POST("upload_bukti_test.php")
    Call<ResponseBody> uploadImage(
            @Part("id_mahasiswa") RequestBody userId,
            @Part("id_pemesanan") RequestBody pemesananId,
            @Part MultipartBody.Part image
    );

    @GET("tampil_bukti_bayar.php")
    Call<List<BuktiBayarList>> getBuktiPembayaran(
            @Query("id_mahasiswa") int idMahasiswa,
            @Query("id_pemesanan") int idPemesanan
    );

    @GET
    Call<ResponseBody> downloadBuktiAdmin(@Url String imageUrl);

    @GET("tampil_foto_profile.php")
    Call<List<FotoProfileList>> getFotoProfile(
            @Query("id_mahasiswa") int idMahasiswa
    );

    @Multipart
    @POST("upload_foto_profile.php")
    Call<ResponseBody> uploadFotoProfile(
            @Part("id_mahasiswa") RequestBody userId,
            @Part MultipartBody.Part image
    );


    //ubah password
    @FormUrlEncoded
    @POST("ubah_password.php")
    Call<UbahPasswordResponse> ubahPassword(
            @Field("email") String email,
            @Field("old_password") String oldPassword,
            @Field("new_password") String newPassword,
            @Field("confirm_password") String confirmPassword
    );


    //menampilkan galeri
    @GET("tampil_galeri.php")
    Call<List<String>> getGambarGaleri();






    @FormUrlEncoded
    @POST("konfirmasi_status.php")
    Call<ResponseBody> konfirmasiStatus(
            @Field("id_pemesanan") int idPemesanan
    );

    @FormUrlEncoded
    @POST("batalkan_status.php")
    Call<ResponseBody> batalkanStatus(
            @Field("id_pemesanan") int idPemesanan
    );


    //kamar tersedia
    @GET("daftar_kamar_tersedia.php")
    Call<List<Kamar>> getKamarTersedia();

    //kamar terisi
    @GET("daftar_kamar_terisi.php")
    Call<List<Kamar>> getKamarTerisi();

    @GET("mahasiswa/{id_mahasiswa}")
    Call<User> getUserById(@Path("id_mahasiswa") int userId);
}
