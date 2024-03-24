package com.example.semar5;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.semar5.CRUD.Mahasiswa;
import com.example.semar5.CRUD.MahasiswaProfileResponse;
import com.example.semar5.ModelResponse.Fakultas;
import com.example.semar5.ModelResponse.Prodi;
import com.example.semar5.ModelResponse.User;
import com.example.semar5.Retrofit.Api;
import com.example.semar5.Retrofit.RetrofitClient;
import com.example.semar5.Retrofit.SharedPreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesananSelesaiDownload extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static final String EXTRA_MAHASISWA_ID = "extra_mahasiswa_id";
    private int mahasiswaId;
    private LinearLayout linearLayout;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    SharedPreferenceManager sharedPreferenceManager;
    private TextView namaprofile;
    private User user;
    private File pdfFile;
    private Api api;
    //Prodi prodiData;
    Fakultas fakultasData;
    private int selectedFakultasId = -1;
    private int selectedProdiId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan_selesai_download);

        //header logo
        ImageView imageView = findViewById(R.id.homesemar);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PesananSelesaiDownload.this, MainActivity.class);
                startActivity(intent);
            }
        });


        //menampilkan foto profile di side bar navigation
        navigationView = findViewById(R.id.navdrawer_view);
        View headerView = navigationView.getHeaderView(0);

        CircleImageView profileImage = headerView.findViewById(R.id.profile_image);

        SharedPreferenceManager sharedPreference = SharedPreferenceManager.getInstance(this);
        int userId = sharedPreference.getUser().getId_mahasiswa();
        String imageUriString = sharedPreference.getImageUri(userId);
        if (!imageUriString.isEmpty()) {
            Uri imageUri = Uri.parse(imageUriString);

            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_fotoprofile)
                    .error(R.drawable.logosemar);

            Glide.with(this)
                    .load(imageUri)
                    .apply(requestOptions)
                    .into(profileImage);
        } else {
            Log.e("Error", "Failed to load image");
        }



        sharedPreferenceManager = SharedPreferenceManager.getInstance(this);
        user = sharedPreferenceManager.getUser();

        if (sharedPreferenceManager.isLoggedIn()) {
            int role = user.getId_role();
            Log.d("Debug", "Role: " + role);
            if (role == 1) {
                Intent intent = getIntent();
                if (intent != null && intent.hasExtra(EXTRA_MAHASISWA_ID)) {
                    mahasiswaId = intent.getIntExtra(EXTRA_MAHASISWA_ID, -1);

                    fetchProfileData(mahasiswaId);
                } else {
                    Toast.makeText(this, "Invalid Mahasiswa ID", Toast.LENGTH_SHORT).show();
                }

                String jalur = intent.getStringExtra("jalur");
                String opsiPembayaran = intent.getStringExtra("opsiPembayaran");
                String jumlahPenghuni = intent.getStringExtra("jumlahPenghuni");
                String jenisKelamin = intent.getStringExtra("jenisKelamin");
                String gedung = intent.getStringExtra("gedung");
                String kamar = intent.getStringExtra("kamar");
                String harga = intent.getStringExtra("harga");
                String tanggalMasuk = intent.getStringExtra("tanggalMasuk");
                String tanggalKeluar = intent.getStringExtra("tanggalKeluar");

                TextView jalurTextView = findViewById(R.id.detail_jalur);
                TextView opsiPembayaranTextView = findViewById(R.id.detail_opsi_pembayaran);
                TextView jumlahPenghuniTextView = findViewById(R.id.detail_jumlah_penghuni);
                TextView jenisKelaminTextView = findViewById(R.id.detail_jenis_kelamin);
                TextView gedungTextView = findViewById(R.id.detail_gedung);
                TextView kamarTextView = findViewById(R.id.detail_kamar);
                TextView hargaTextView = findViewById(R.id.detail_harga);
                TextView tglMasukTextView = findViewById(R.id.detail_tanggal_masuk);
                TextView tglKeluarTextView = findViewById(R.id.detail_tanggal_keluar);

                jalurTextView.setText(jalur);
                opsiPembayaranTextView.setText(opsiPembayaran);
                jumlahPenghuniTextView.setText(jumlahPenghuni);
                jenisKelaminTextView.setText(jenisKelamin);
                gedungTextView.setText(gedung);
                kamarTextView.setText(kamar);
                hargaTextView.setText(harga);
                tglMasukTextView.setText(tanggalMasuk);
                tglKeluarTextView.setText(tanggalKeluar);

            } else if (role == 2) {
                Intent intent = getIntent();
                if (intent != null && intent.hasExtra(EXTRA_MAHASISWA_ID)) {
                    mahasiswaId = intent.getIntExtra(EXTRA_MAHASISWA_ID, -1);

                    fetchProfileData(mahasiswaId);
                } else {
                    Toast.makeText(this, "Invalid Mahasiswa ID", Toast.LENGTH_SHORT).show();
                }

                String jalur = intent.getStringExtra("jalur");
                String opsiPembayaran = intent.getStringExtra("opsiPembayaran");
                String jumlahPenghuni = intent.getStringExtra("jumlahPenghuni");
                String jenisKelamin = intent.getStringExtra("jenisKelamin");
                String gedung = intent.getStringExtra("gedung");
                String kamar = intent.getStringExtra("kamar");
                String harga = intent.getStringExtra("harga");
                String tanggalMasuk = intent.getStringExtra("tanggalMasuk");
                String tanggalKeluar = intent.getStringExtra("tanggalKeluar");

                TextView jalurTextView = findViewById(R.id.detail_jalur);
                TextView opsiPembayaranTextView = findViewById(R.id.detail_opsi_pembayaran);
                TextView jumlahPenghuniTextView = findViewById(R.id.detail_jumlah_penghuni);
                TextView jenisKelaminTextView = findViewById(R.id.detail_jenis_kelamin);
                TextView gedungTextView = findViewById(R.id.detail_gedung);
                TextView kamarTextView = findViewById(R.id.detail_kamar);
                TextView hargaTextView = findViewById(R.id.detail_harga);
                TextView tglMasukTextView = findViewById(R.id.detail_tanggal_masuk);
                TextView tglKeluarTextView = findViewById(R.id.detail_tanggal_keluar);

                jalurTextView.setText(jalur);
                opsiPembayaranTextView.setText(opsiPembayaran);
                jumlahPenghuniTextView.setText(jumlahPenghuni);
                jenisKelaminTextView.setText(jenisKelamin);
                gedungTextView.setText(gedung);
                kamarTextView.setText(kamar);
                hargaTextView.setText(harga);
                tglMasukTextView.setText(tanggalMasuk);
                tglKeluarTextView.setText(tanggalKeluar);
            }
        }


        pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "formulir_pendaftaran.pdf");


        linearLayout = findViewById(R.id.linear_bukti);


        //sesuaikan activity
        drawerLayout = findViewById(R.id.drawer_layout_detailpesanan_download);
        navigationView = findViewById(R.id.navdrawer_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        //hide or show item
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_logout).setVisible(false);
        menu.findItem(R.id.nav_ubahpass).setVisible(false);
        menu.findItem(R.id.nav_data_mahasiswa).setVisible(false);
        menu.findItem(R.id.nav_daftar_kamar).setVisible(false);


        //side bar navigation
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navdrawer_open,R.string.navdrawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        //ganti setiap activity
        navigationView.setCheckedItem(R.id.nav_home);



        //bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_history);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.bottom_home) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.anim.slide_right_effect, R.anim.slide_left_effect);
                    return true;
                } else if (itemId == R.id.bottom_notif) {
                    startActivity(new Intent(getApplicationContext(), NotifikasiActivity.class));
                    overridePendingTransition(R.anim.slide_right_effect, R.anim.slide_left_effect);
                    return true;
                } else if (itemId == R.id.bottom_history) {
                    startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                    overridePendingTransition(R.anim.slide_right_effect, R.anim.slide_left_effect);
                    return true;
                } else if (itemId == R.id.bottom_profile) {
                    if (sharedPreferenceManager.isLoggedIn()) {
                        int role = sharedPreferenceManager.getUser().getId_role();
                        if (role == 1) {
                            startActivity(new Intent(getApplicationContext(), ProfileActivityAdmin.class));
                            overridePendingTransition(R.anim.slide_right_effect, R.anim.slide_left_effect);
                            return true;
                        } else if (role == 2) {
                            startActivity(new Intent(getApplicationContext(), ProfileActivityAfter1.class));
                            overridePendingTransition(R.anim.slide_right_effect, R.anim.slide_left_effect);
                            return true;
                        }
                    } else {
                        startActivity(new Intent(getApplicationContext(), ProfileActivityBefore.class));
                        overridePendingTransition(R.anim.slide_right_effect, R.anim.slide_left_effect);
                        return true;
                    }
                }
                return false;
            }
        });

    }

    private void fetchProfileData(int mahasiswaId) {
        RetrofitClient retrofitClient = RetrofitClient.getInstance(this);
        Api apiService = retrofitClient.getApi();

        Call<MahasiswaProfileResponse> call = apiService.getMahasiswaDataId(mahasiswaId);
        call.enqueue(new Callback<MahasiswaProfileResponse>() {
            @Override
            public void onResponse(Call<MahasiswaProfileResponse> call, Response<MahasiswaProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MahasiswaProfileResponse mahasiswaResponse = response.body();

                    if (mahasiswaResponse.getError().equals("200")){
                        Mahasiswa mahasiswa = mahasiswaResponse.getMahasiswa();

                        TextView et_namalengkap = findViewById(R.id.detail_nama_pemesan);
                        TextView et_nim = findViewById(R.id.detail_nim);
                        TextView et_fakultas = findViewById(R.id.detail_fakultas);
                        TextView et_prodi = findViewById(R.id.detail_prodi);
                        TextView et_tgllahir = findViewById(R.id.detail_tl);
                        TextView et_agama = findViewById(R.id.detail_agama);
                        TextView et_jk = findViewById(R.id.detail_jenis_kelamin);
                        TextView et_alamat = findViewById(R.id.detail_alamat);
                        TextView et_notelp = findViewById(R.id.detail_notelp);
                        TextView et_namaortu = findViewById(R.id.detail_namaortu);
                        TextView et_pekerjaanortu = findViewById(R.id.detail_kerjaortu);
                        TextView et_alamatortu = findViewById(R.id.detail_alamatortu);
                        TextView et_notelportu = findViewById(R.id.detail_notelportu);

                        et_namalengkap.setText(mahasiswa.getNamaMahasiswa());
                        et_nim.setText(mahasiswa.getNim());
                        et_fakultas.setText(mahasiswa.getNamaFakultas());
                        et_prodi.setText(mahasiswa.getNamaProdi());
                        et_tgllahir.setText(mahasiswa.getTglLahir());
                        et_agama.setText(mahasiswa.getAgama());
                        et_jk.setText(mahasiswa.getJenisKelamin());
                        et_alamat.setText(mahasiswa.getAlamat());
                        et_notelp.setText(mahasiswa.getNoTelp());
                        et_namaortu.setText(mahasiswa.getNamaOrangtua());
                        et_pekerjaanortu.setText(mahasiswa.getPekerjaanOrangtua());
                        et_alamatortu.setText(mahasiswa.getAlamatOrangtua());
                        et_notelportu.setText(mahasiswa.getNoTelpOrangtua());

                        String namaMhs = et_namalengkap.getText().toString();
                        String nim = et_nim.getText().toString();
                        String fakultas = et_fakultas.getText().toString();
                        String prodi = et_prodi.getText().toString();
                        String tl = et_tgllahir.getText().toString();
                        String agama = et_agama.getText().toString();
                        String jk = et_jk.getText().toString();
                        String alamat = et_alamat.getText().toString();
                        String notelp = et_notelp.getText().toString();
                        String namaOrtu = et_namaortu.getText().toString();
                        String pekerjaanOrtu = et_pekerjaanortu.getText().toString();
                        String alamatOrtu = et_alamatortu.getText().toString();
                        String notelpOrtu = et_notelportu.getText().toString();

                        Button downloadButton = findViewById(R.id.download_bukti);
                        downloadButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int storagePermission = ActivityCompat.checkSelfPermission(PesananSelesaiDownload.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

                                if (storagePermission != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(PesananSelesaiDownload.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                                } else {
                                    createAndSavePDF(mahasiswa);
                                }
                            }
                        });

                    } else {
                        Toast.makeText(PesananSelesaiDownload.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Mahasiswa object is null");
                    }
                } else {
                    Log.e(TAG, "Mahasiswa object is null");
                    Log.e(TAG, "Error: " + response.message());
                    Toast.makeText(PesananSelesaiDownload.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MahasiswaProfileResponse> call, Throwable t) {
                Log.e("RetrofitError", "Error: " + t.getMessage());
                Toast.makeText(PesananSelesaiDownload.this, "Kesalahan Jaringan. Gagal mengambil data", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private ProgressDialog progressDialog;
    private void createAndSavePDF(Mahasiswa mahasiswa) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang mengunduh...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setProgress(0);
        progressDialog.setCancelable(false);
        progressDialog.show();

        String namaPemesan = mahasiswa.getNamaMahasiswa();
        String nim = mahasiswa.getNim();
        String prodi = mahasiswa.getNamaProdi();
        String fakultas = mahasiswa.getNamaFakultas();
        String tl = mahasiswa.getTglLahir();
        String agama = mahasiswa.getAgama();
        String alamatmhs = mahasiswa.getAlamat();
        String notelpmhs = mahasiswa.getNoTelp();
        String namaortu = mahasiswa.getNamaOrangtua();
        String pekerjaanortu = mahasiswa.getPekerjaanOrangtua();
        String alamatortu = mahasiswa.getAlamatOrangtua();
        String notelportu = mahasiswa.getNoTelpOrangtua();

        String filePath = Environment.getExternalStorageDirectory() + "/form_pendaftaran_rusunawa_" + namaPemesan + ".pdf";
        Document document = new Document(PageSize.A4);
        document.setMargins(72, 72, 72, 72);

        Font timesRomanFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12);

        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.kop);
            Bitmap bitmap = bitmapDrawable.getBitmap();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();

            Image image = Image.getInstance(byteArray);
            image.setAlignment(Element.ALIGN_CENTER);
            image.scaleToFit(450, 300);

            document.add(image);

            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setLineColor(BaseColor.BLACK);
            lineSeparator.setLineWidth(1);

            document.add(new Chunk(lineSeparator));

            // konten PDF
            Chunk titleChunk = new Chunk("FORMULIR PENDAFTARAN");
            titleChunk.setFont(FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD));
            titleChunk.setUnderline(0.1f, -2f);

            Paragraph title = new Paragraph(titleChunk);
            title.setAlignment(Element.ALIGN_CENTER);

            document.add(title);

            Paragraph spacer1 = new Paragraph(" ");
            spacer1.setSpacingAfter(15f);

            document.add(spacer1);

            Calendar calendar = Calendar.getInstance();
            int tahunMasuk = calendar.get(Calendar.YEAR);

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", new Locale("id", "ID"));

            String tanggalSekarang = sdf.format(new Date());

            document.add(new Paragraph("Yang bertanda tangan dibawah ini :", timesRomanFont));

            Font contentFont = FontFactory.getFont("Times-Roman", 12);
            PdfPTable table = new PdfPTable(2);

            String[] labelArray = {
                    "Nama",
                    "Nomor Induk Mahasiswa",
                    "Program Studi/Jurusan",
                    "Fakultas",
                    "Tanggal Lahir",
                    "Agama",
                    "Alamat Rumah Asal",
                    "No Telepon/HP",
                    "Nama Orang Tua",
                    "Pekerjaan Orang Tua",
                    "Alamat Orang Tua",
                    "No Telepon/HP Orang Tua",
                    "Tahun Masuk Rusunawa"
            };

            String[] contentArray = {
                    ":   " + namaPemesan,
                    ":   " + nim,
                    ":   " + prodi,
                    ":   " + fakultas,
                    ":   " + tl,
                    ":   " + agama,
                    ":   " + alamatmhs,
                    ":   " + notelpmhs,
                    ":   " + namaortu,
                    ":   " + pekerjaanortu,
                    ":   " + alamatortu,
                    ":   " + notelportu,
                    ":   " + tahunMasuk
            };

            float labelColumnWidth = 230f;

            table.setTotalWidth(new float[]{labelColumnWidth, PageSize.A4.getWidth() - labelColumnWidth});

            for (int i = 0; i < labelArray.length; i++) {
                String label = labelArray[i];
                String content = contentArray[i];

                String labelText = label;
                PdfPCell labelCell = new PdfPCell(new Phrase(labelText, timesRomanFont));
                labelCell.setBorder(Rectangle.NO_BORDER);
                table.addCell(labelCell);

                PdfPCell inputCell = new PdfPCell(new Phrase(content, contentFont));
                inputCell.setBorder(Rectangle.NO_BORDER);
                table.addCell(inputCell);
            }

            document.add(table);

            Paragraph spacer2 = new Paragraph();
            spacer2.setSpacingAfter(50);

            document.add(spacer2);

            document.add(new Paragraph("Bersama ini kami mengajukan permohonan untuk menyewa Rusunawa Undip, dan bersedia mematuhi peraturan, tata tertib, dan ketentuan yang berlaku.", timesRomanFont));
            document.add(new Paragraph("Kami telah melengkapi permohonan ini berupa:", timesRomanFont));
            document.add(new Paragraph("1.  Foto kopi Kartu Tanda Mahasiswa;", timesRomanFont));
            document.add(new Paragraph("2.  Pas photo ukuran 3 x 4 sebanyak 1 lembar;", timesRomanFont));
            document.add(new Paragraph("3.  Kwitansi bukti pembayaran;", timesRomanFont));
            document.add(new Paragraph("4.  Kartu Peserta Didik (Bagi mahasiswa penerima Bidik Misi)", timesRomanFont));

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Menyetujui / Mengetahui                                                  Semarang, " + tanggalSekarang, timesRomanFont));
            document.add(new Paragraph("Supervisor Rusunawa                                                        Pemohon,", timesRomanFont));

            BitmapDrawable bitmapDrawable2 = (BitmapDrawable) getResources().getDrawable(R.drawable.ttd);
            Bitmap bitmap2 = bitmapDrawable2.getBitmap();

            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            bitmap2.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream2);
            byte[] byteArray2 = byteArrayOutputStream2.toByteArray();

            Image image2 = Image.getInstance(byteArray2);
            image2.setAlignment(Element.ALIGN_LEFT);
            image2.scaleToFit(100, 50);

            document.add(image2);

            document.add(new Paragraph("Triyono, S.H., M.Kn.                                                         " + namaPemesan , timesRomanFont));
            document.add(new Paragraph("NIP. 196712251994031002                                                      ", timesRomanFont));

            PdfContentByte contentByte = writer.getDirectContent();
            float topMarginInCentimeters = 4f;
            float rightMarginInCentimeters = 0.7f;

            float topMarginInPoints = topMarginInCentimeters * 28.35f;
            float rightMarginInPoints = rightMarginInCentimeters * 28.35f;

            float photoBoxWidth = 60;
            float photoBoxHeight = 80;

            float photoBoxX = PageSize.A4.getWidth() - document.rightMargin() - rightMarginInPoints - photoBoxWidth;
            float photoBoxY = PageSize.A4.getHeight() - document.topMargin() - topMarginInPoints - photoBoxHeight;

            contentByte.saveState();
            contentByte.setColorStroke(BaseColor.BLACK);
            contentByte.rectangle(photoBoxX, photoBoxY, photoBoxWidth, photoBoxHeight);
            contentByte.stroke();
            contentByte.restoreState();

            contentByte.beginText();
            contentByte.setColorFill(BaseColor.BLACK);
            BaseFont baseFont = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
            float fontSize = 12;
            contentByte.setFontAndSize(baseFont, fontSize);

            float textWidth = baseFont.getWidthPoint("Photo 3x4", fontSize);
            float textHeight = fontSize;

            float textX = photoBoxX + (photoBoxWidth - textWidth) / 2;
            float textY = photoBoxY + (photoBoxHeight - textHeight) / 2;

            contentByte.setTextMatrix(textX, textY);
            contentByte.showText("Photo 3x4");
            contentByte.endText();

            progressDialog.setProgress(25);
            progressDialog.dismiss();

            document.close();

            Toast.makeText(this, "Berhasil download file PDF", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Terjadi kesalahan saat download PDF.", Toast.LENGTH_SHORT).show();
        }
    }



    //side bar hide or show login logout
    @Override
    protected void onStart() {
        super.onStart();

        navigationView = findViewById(R.id.navdrawer_view);
        View headerView = navigationView.getHeaderView(0);

        namaprofile = headerView.findViewById(R.id.header_nameprofile);
        sharedPreferenceManager = SharedPreferenceManager.getInstance(this);
        user = sharedPreferenceManager.getUser();
        namaprofile.setText(user.getNama_mahasiswa());

        ImageView logo = headerView.findViewById(R.id.header_logo);
        CircleImageView fotoprofile = headerView.findViewById(R.id.profile_image);
        TextView namaprofile = headerView.findViewById(R.id.header_nameprofile);

        if (sharedPreferenceManager.isLoggedIn()) {
            int role = sharedPreferenceManager.getUser().getId_role();
            Menu menu = navigationView.getMenu();

            fotoprofile.setVisibility(View.VISIBLE);
            namaprofile.setVisibility(View.VISIBLE);

            if (role == 1) {
                menu.findItem(R.id.nav_logout).setVisible(true);
                menu.findItem(R.id.nav_ubahpass).setVisible(true);
                menu.findItem(R.id.nav_login).setVisible(false);
                menu.findItem(R.id.nav_daftar).setVisible(false);
                menu.findItem(R.id.nav_data_mahasiswa).setVisible(true);
                menu.findItem(R.id.nav_daftar_kamar).setVisible(true);
                logo.setVisibility(View.GONE);

            } else if (role == 2) {
                menu.findItem(R.id.nav_logout).setVisible(true);
                menu.findItem(R.id.nav_ubahpass).setVisible(true);
                menu.findItem(R.id.nav_login).setVisible(false);
                menu.findItem(R.id.nav_daftar).setVisible(false);
                menu.findItem(R.id.nav_data_mahasiswa).setVisible(false);
                menu.findItem(R.id.nav_daftar_kamar).setVisible(false);
                logo.setVisibility(View.GONE);

            }
        } else {
            logo.setVisibility(View.VISIBLE);
            fotoprofile.setVisibility(View.GONE);
            namaprofile.setVisibility(View.GONE);
        }
    }



    //kembali ke activity sebelumnya
    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }


    //side bar navigation
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            Intent intent1 = new Intent(PesananSelesaiDownload.this, MainActivity.class);
            startActivity(intent1);
        } else if (itemId == R.id.nav_carabooking) {
            Intent intent2 = new Intent(PesananSelesaiDownload.this, CaraDaftarActivity.class);
            startActivity(intent2);
        } else if (itemId == R.id.nav_galeri) {
            Intent intent3 = new Intent(PesananSelesaiDownload.this, GaleriActivity.class);
            startActivity(intent3);
        } else if (itemId == R.id.nav_snk) {
            Intent intent4 = new Intent(PesananSelesaiDownload.this, SnKActivity.class);
            startActivity(intent4);
        } else if (itemId == R.id.nav_login) {
            Intent intent5 = new Intent(PesananSelesaiDownload.this, Login.class);
            startActivity(intent5);
        } else if (itemId == R.id.nav_daftar) {
            Intent intent6 = new Intent(PesananSelesaiDownload.this, Daftar.class);
            startActivity(intent6);
        } else if (itemId == R.id.nav_helpdesk) {
            Intent intent7 = new Intent(PesananSelesaiDownload.this, Helpdesk.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_about) {
            Intent intent8 = new Intent(PesananSelesaiDownload.this, About.class);
            startActivity(intent8);
        } else if (itemId == R.id.nav_data_mahasiswa) {
            Intent intent7 = new Intent(PesananSelesaiDownload.this, DataMahasiswa.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_ubahpass) {
            Intent intent7 = new Intent(PesananSelesaiDownload.this, UbahPassword.class);
            startActivity(intent7);
        } else if (itemId == R.id.nav_daftar_kamar) {
            Intent intent8 = new Intent(PesananSelesaiDownload.this, DaftarKamar.class);
            startActivity(intent8);
        } else if (itemId == R.id.nav_logout) {
            logoutUser();
            return true;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    //logout
    private void logoutUser() {
        sharedPreferenceManager.logout();

        Toast.makeText(this, "Logout Berhasil", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(PesananSelesaiDownload.this, Login.class);
        startActivity(intent);
        finishAffinity();
    }
}