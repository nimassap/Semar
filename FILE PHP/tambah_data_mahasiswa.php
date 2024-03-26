<?php

header('Content-Type: application/json');

require 'conn.php';

$email = $_REQUEST['email'];
$password = md5($_REQUEST['password']);
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

$insert_query = "INSERT INTO mahasiswa (
    email,
    password,
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
) VALUES (
    '$email',
    '$password',
    '$nama_mhs',
    '$nim',
    '$id_fakultas',
    '$id_prodi',
    '$tgl_lahir',
    '$agama',
    '$jk',
    '$alamat',
    '$notelp',
    '$nama_orangtua',
    '$pekerjaan_orangtua',
    '$alamat_orangtua',
    '$notelp_orangtua'
)";

$result = mysqli_query($conn, $insert_query);

$response = array();
if ($result) {
    $new_id = mysqli_insert_id($conn); // Get the auto-generated ID for the new record
    
    $response['new_id'] = $new_id;
    $response['error'] = "200";
    $response['message'] = "Berhasil menambah data mahasiswa";
} else {
    $response['error'] = "400";
    $response['message'] = "Gagal menambah data mahasiswa";
}

echo json_encode($response);

?>
