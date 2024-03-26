<?php

header('Content-Type: application/json');

require 'conn.php';

$email = $_REQUEST['email'];
$nama_mhs = $_REQUEST['nama_mahasiswa'];
$nim = $_REQUEST['nim'];
$id_fakultas = $_REQUEST['id_fakultas'];
$id_prodi = $_REQUEST['id_prodi'];
$tgl_lahir = $_REQUEST['tgl_lahir'];
$agama = $_REQUEST['agama'];
$jk = $_REQUEST['jenis_kelamin'];
$alamat = $_REQUEST['alamat'];
$notelp = $_REQUEST['no_telp'];
$nama_orangtua = $_REQUEST['nama_orangtua'];
$pekerjaan_orangtua = $_REQUEST['pekerjaan_orangtua'];
$alamat_orangtua = $_REQUEST['alamat_orangtua'];
$notelp_orangtua = $_REQUEST['notelp_orangtua'];

/*function getFakultasID($fakultas) {
    require 'conn.php'; 

    $query = "SELECT id_fakultas FROM fakultas WHERE nama_fakultas = '$fakultas'";
    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        return $row['id_fakultas'];
    } else {
        return null; 
    }
}
$id_fakultas = getFakultasID($update_fakultas);


function getProdiID($prodi) {
    require 'conn.php'; 

    $query = "SELECT id_prodi FROM prodi WHERE nama_prodi = '$prodi'";
    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        return $row['id_prodi'];
    } else {
        return null; 
    }
}
$id_prodi = getProdiID($update_prodi);*/


$update_query = "UPDATE mahasiswa SET 
        email = '$email',
        nama_mahasiswa = '$nama_mhs',
        nim = '$nim',
        id_fakultas = '$id_fakultas',
        id_prodi = '$id_prodi',
        tgl_lahir = '$tgl_lahir',
        agama = '$agama', 
        jenis_kelamin = '$jk',
        alamat = '$alamat',
        no_telp = '$notelp',
        nama_orangtua = '$nama_orangtua', 
        pekerjaan_orangtua = '$pekerjaan_orangtua', 
        alamat_orangtua = '$alamat_orangtua', 
        notelp_orangtua = '$notelp_orangtua' 
        WHERE email = '$email'";
$result = mysqli_query($conn,$update_query);

$response = array();
if ($result) {
    $fetchuser = mysqli_query($conn, "SELECT 
    id_mahasiswa,
    id_role,
    email,
    nama_mahasiswa, 
    nim,
    id_fakultas,
    id_prodi, 
    tgl_lahir, 
    agama, 
    jenis_kelamin, 
    alamat, 
    no_telp, 
    nama_orangtua, 
    pekerjaan_orangtua, 
    alamat_orangtua, 
    notelp_orangtua 
    FROM mahasiswa WHERE email = '$email'");

if (mysqli_num_rows($fetchuser) > 0) {
    $response['mahasiswa'] = $fetchuser->fetch_assoc();
    $response['error'] = "200";
    $response['message'] = "Berhasil menyimpan data";
} else {
    $response['mahasiswa'] = (object) array();
    $response['error'] = "400";
    $response['message'] = "User update, but details not fetched";
}
} else {
$response['mahasiswa'] = (object) array();
$response['error'] = "400";
$response['message'] = "Gagal menyimpan data";
}


echo json_encode($response);

?>