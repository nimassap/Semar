<?php

header('Content-Type: application/json');

require 'conn.php';

//$id_mahasiswa = $_REQUEST['id_mahasiswa'];

function getMahasiswa($id_mahasiswa) {
    require 'conn.php'; 

    $query = "SELECT nama_mahasiswa FROM mahasiswa WHERE id_mahasiswa = '$id_mahasiswa'";
    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        return $row['nama_mahasiswa'];
    } else {
        return 'Unknown Mahasiswa'; 
    }
}

function getJalur($id_jalur) {
    require 'conn.php'; 

    $query = "SELECT jalur FROM jalur WHERE id_jalur = '$id_jalur'";
    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        return $row['jalur'];
    } else {
        return 'Unknown Jalur'; 
    }
}

function getOpsiPembayaran($id_opsipembayaran) {
    require 'conn.php'; 

    $query = "SELECT opsi_pembayaran FROM opsi_pembayaran WHERE id_opsipembayaran = '$id_opsipembayaran'";
    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        return $row['opsi_pembayaran'];
    } else {
        return 'Unknown Opsi Pembayaran'; 
    }
}

function getJumlahPenghuni($id_jumlahpenghuni) {
    require 'conn.php'; 

    $query = "SELECT jumlah_penghuni FROM jumlah_penghuni WHERE id_jumlahpenghuni = '$id_jumlahpenghuni'";
    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        return $row['jumlah_penghuni'];
    } else {
        return 'Unknown Jumlah Penghuni'; 
    }
}

function getJenisKelamin($id_jeniskelamin) {
    require 'conn.php'; 

    $query = "SELECT jenis_kelamin FROM jenis_kelamin WHERE id_jeniskelamin = '$id_jeniskelamin'";
    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        return $row['jenis_kelamin'];
    } else {
        return 'Unknown Jenis Kelamin'; 
    }
}

function getGedung($id_gedung) {
    require 'conn.php'; 

    $query = "SELECT gedung FROM gedung WHERE id_gedung = '$id_gedung'";
    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        return $row['gedung'];
    } else {
        return 'Unknown Gedung'; 
    }
}

function getKamar($id_kamar) {
    require 'conn.php'; 

    $query = "SELECT kode_kamar FROM kamar WHERE id_kamar = '$id_kamar'";
    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        return $row['kode_kamar'];
    } else {
        return 'Unknown Kamar'; 
    }
}

function getHarga($id_harga) {
    require 'conn.php'; 

    $query = "SELECT harga FROM harga WHERE id_harga = '$id_harga'";
    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        return $row['harga'];
    } else {
        return 'Unknown Kamar'; 
    }
}


$checkUser = "SELECT * FROM pemesanan";
$result = mysqli_query($conn,$checkUser);

$checkPemesananQuery = "SELECT 
    id_pemesanan,
    id_mahasiswa,
    id_jalur,
    id_opsipembayaran,	
    id_jumlahpenghuni,	
    id_jeniskelamin,
    id_gedung,
    id_kamar,	
    id_harga,	
    tanggal_masuk,	
    tanggal_keluar,
    status_pemesanan
    FROM pemesanan";
$resultant = mysqli_query($conn, $checkPemesananQuery);

if (mysqli_num_rows($resultant) > 0) {
    while ($row = $resultant->fetch_assoc()) {

        //mahasiswa
        $id_mahasiswa = $row['id_mahasiswa'];
        $nama_mahasiswa = getMahasiswa($id_mahasiswa);
        
        //jalur
        $id_jalur = $row['id_jalur'];
        $jalur = getJalur($id_jalur);

        //opsi pembayaran
        $id_opsipembayaran = $row['id_opsipembayaran'];
        $opsi_pembayaran = getOpsiPembayaran($id_opsipembayaran);

        //jumlah penghuni
        $id_jumlahpenghuni = $row['id_jumlahpenghuni'];
        $jumlah_penghuni = getJumlahPenghuni($id_jumlahpenghuni);

        //jenis kelamin
        $id_jeniskelamin = $row['id_jeniskelamin'];
        $jenis_kelamin = getJenisKelamin($id_jeniskelamin);

        //gedung
        $id_gedung = $row['id_gedung'];
        $gedung = getGedung($id_gedung);

        //kamar
        $id_kamar = $row['id_kamar'];
        $kode_kamar = getKamar($id_kamar);

        //harga
        $id_harga = $row['id_harga'];
        $harga = getHarga($id_harga);

        //pemesanan
        $pemesananData = array(
            'id_pemesanan' => $row['id_pemesanan'],

            'id_mahasiswa' => $id_mahasiswa,
            'nama_mahasiswa' => $nama_mahasiswa,

            'id_jalur' => $id_jalur,
            'jalur' => $jalur,

            'id_opsipembayaran' => $id_opsipembayaran,
            'opsi_pembayaran' => $opsi_pembayaran,

            'id_jumlahpenghuni' => $id_jumlahpenghuni,
            'jumlah_penghuni' => $jumlah_penghuni,

            'id_jeniskelamin' => $id_jeniskelamin,
            'jenis_kelamin' => $jenis_kelamin,

            'id_gedung' => $id_gedung,
            'gedung' => $gedung,

            'id_kamar' => $id_kamar,
            'kode_kamar' => $kode_kamar,

            'id_harga' => $id_harga,
            'harga' => $harga,

            'tanggal_masuk' => $row['tanggal_masuk'],
            'tanggal_keluar' => $row['tanggal_keluar'],
            
            'status_pemesanan' => $row['status_pemesanan'],
        );
        $response['pemesanan'][] = $pemesananData;
    }
    $response['error'] = "200";
    $response['message'] = "Data ditemukan";
} else {
    $response['error'] = "400";
    $response['message'] = "Data tidak ditemukan";
}

echo json_encode($response);

?>