<?php

header('Content-Type: application/json');

require 'conn.php';

$email = $_REQUEST['email'];

$sqlGetUserID = "SELECT id_mahasiswa FROM mahasiswa WHERE email = '$email'";
$result = $conn->query($sqlGetUserID);

// Get data from the request
$id_pemesanan = $_REQUEST['id_pemesanan'];
$update_mahasiswa = $_REQUEST['update_mahasiswa'];
$update_jalur = $_REQUEST['update_jalur'];
$update_opsipembayaran = $_REQUEST['update_opsipembayaran'];
$update_jumlahpenghuni = $_REQUEST['update_jumlahpenghuni'];
$update_jeniskelamin = $_REQUEST['update_jeniskelamin'];
$update_gedung = $_REQUEST['update_gedung'];
$update_kamar = $_REQUEST['update_kamar'];
$update_harga = $_REQUEST['update_harga'];
$update_tanggal_masuk = $_REQUEST['update_tanggal_masuk'];
$update_tanggal_keluar = $_REQUEST['update_tanggal_keluar'];


function getMahasiswaID($nama_mahasiswa) {
    require 'conn.php'; 

    $query = "SELECT id_mahasiswa FROM mahasiswa WHERE nama_mahasiswa = '$nama_mahasiswa'";
    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        return $row['id_mahasiswa'];
    } else {
        return null; 
    }
}
$id_mahasiswa = getMahasiswaID($update_mahasiswa);


function getJalurID($jalur) {
    require 'conn.php'; 

    $query = "SELECT id_jalur FROM jalur WHERE jalur = '$jalur'";
    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        return $row['id_jalur'];
    } else {
        return null; 
    }
}
$id_jalur = getJalurID($update_jalur);


function getOpsiPembayaranID($opsi_pembayaran) {
    require 'conn.php'; 

    $query = "SELECT id_opsipembayaran FROM opsi_pembayaran WHERE opsi_pembayaran = '$opsi_pembayaran'";
    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        return $row['id_opsipembayaran'];
    } else {
        return null; 
    }
}
$id_opsipembayaran = getOpsiPembayaranID($update_opsipembayaran);


function getJumlahPenghuniID($jumlah_penghuni) {
    require 'conn.php'; 

    $query = "SELECT id_jumlahpenghuni FROM jumlah_penghuni WHERE jumlah_penghuni = '$jumlah_penghuni'";
    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        return $row['id_jumlahpenghuni'];
    } else {
        return null; 
    }
}
$id_jumlahpenghuni = getJumlahPenghuniID($update_jumlahpenghuni);


function getJenisKelaminID($jenis_kelamin) {
    require 'conn.php'; 

    $query = "SELECT id_jeniskelamin FROM jenis_kelamin WHERE jenis_kelamin = '$jenis_kelamin'";
    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        return $row['id_jeniskelamin'];
    } else {
        return null; 
    }
}
$id_jeniskelamin = getJenisKelaminID($update_jeniskelamin);


function getGedungID($gedung) {
    require 'conn.php'; 

    $query = "SELECT id_gedung FROM gedung WHERE gedung = '$gedung'";
    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        return $row['id_gedung'];
    } else {
        return null; 
    }
}
$id_gedung = getGedungID($update_gedung);


function getKamarID($kode_kamar) {
    require 'conn.php'; 

    $query = "SELECT id_kamar FROM kamar WHERE kode_kamar = '$kode_kamar'";
    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        return $row['id_kamar'];
    } else {
        return null; 
    }
}
$id_kamar = getKamarID($update_kamar);


function getHargaID($harga) {
    require 'conn.php'; 

    $query = "SELECT id_harga FROM harga WHERE harga = '$harga'";
    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        return $row['id_harga'];
    } else {
        return null; 
    }
}
$id_harga = getHargaID($update_harga);


if ($result->num_rows > 0) {
    $row = $result->fetch_assoc();
    
    $update_tanggal_masuk = date('Y-m-d', strtotime($update_tanggal_masuk));
    $update_tanggal_keluar = date('Y-m-d', strtotime($update_tanggal_keluar));

    error_log("Debug: id_mahasiswa: $id_mahasiswa, id_jalur: $id_jalur, id_opsipembayaran: $id_opsipembayaran, id_jumlahpenghuni: $id_jumlahpenghuni, id_jeniskelamin: $id_jeniskelamin, id_gedung: $id_gedung, id_kamar: $id_kamar, id_harga: $id_harga, tanggal_masuk: $update_tanggal_masuk, tanggal_keluar: $update_tanggal_keluar");


    $updateQuery = "UPDATE pemesanan 
    SET id_mahasiswa = '$id_mahasiswa', 
        id_jalur = '$id_jalur', 
        id_opsipembayaran = '$id_opsipembayaran', 
        id_jumlahpenghuni = '$id_jumlahpenghuni', 
        id_jeniskelamin = '$id_jeniskelamin', 
        id_gedung = '$id_gedung', 
        id_kamar = '$id_kamar', 
        id_harga = '$id_harga', 
        tanggal_masuk = '$update_tanggal_masuk', 
        tanggal_keluar = '$update_tanggal_keluar'
    WHERE id_pemesanan = '$id_pemesanan'";

    if ($conn->query($updateQuery) === TRUE) {
        echo "Data berhasil diperbarui";
    } else {
        error_log("Error: " . $updateQuery . "<br>" . $conn->error);
        echo "Error: " . $updateQuery . "<br>" . $conn->error;
    }
} else {
    echo "Pengguna tidak ditemukan";
}

$conn->close();

?>