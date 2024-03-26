<?php

header('Content-Type: application/json');

require 'conn.php';

$id_mahasiswa = $_REQUEST['id_mahasiswa'];

/*function getMahasiswa($id_mahasiswa) {
    require 'conn.php'; 

    $query = "SELECT nama_mahasiswa FROM mahasiswa WHERE id_mahasiswa = '$id_mahasiswa'";
    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        return $row['nama_mahasiswa'];
    } else {
        return 'Unknown Mahasiswa'; 
    }
}*/

function getFakultas($id_fakultas) {
    require 'conn.php'; 

    $query = "SELECT nama_fakultas FROM fakultas WHERE id_fakultas = '$id_fakultas'";
    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        return $row['nama_fakultas'];
    } else {
        return 'Unknown Fakultas'; 
    }
}

function getProdi($id_prodi) {
    require 'conn.php'; 

    $query = "SELECT nama_prodi FROM prodi WHERE id_prodi = '$id_prodi'";
    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        return $row['nama_prodi'];
    } else {
        return 'Unknown Prodi'; 
    }
}

$checkPemesananQuery = "SELECT 
    id_mahasiswa,
    id_role,
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
    FROM mahasiswa WHERE id_mahasiswa = '$id_mahasiswa'";
$resultant = mysqli_query($conn, $checkPemesananQuery);

$response = array();
if (mysqli_num_rows($resultant) > 0) {
    while ($row = $resultant->fetch_assoc()) {

        $id_mahasiswa = $row['id_mahasiswa'];
        $id_role = $row['id_role'];
        $email = $row['email'];
        $password = $row['password'];
        $nama_mahasiswa = $row['nama_mahasiswa'];
        $nim = $row['nim'];
        
        //fakultas
        $id_fakultas = $row['id_fakultas'];
        $nama_fakultas = getFakultas($id_fakultas);

        //prodi
        $id_prodi = $row['id_prodi'];
        $nama_prodi = getProdi($id_prodi);
        
        $tgl_lahir = $row['tgl_lahir'];
        $agama = $row['agama'];
        $jenis_kelamin = $row['jenis_kelamin'];
        $alamat = $row['alamat'];
        $no_telp = $row['no_telp'];
        $nama_orangtua = $row['nama_orangtua'];
        $pekerjaan_orangtua = $row['pekerjaan_orangtua'];
        $alamat_orangtua = $row['alamat_orangtua'];
        $notelp_orangtua = $row['notelp_orangtua'];

        $response['mahasiswa'] = array(
            'id_mahasiswa' => $id_mahasiswa,

            'id_role' => $id_role,
            'email' => $email,
            'password' => $password,
            'nama_mahasiswa' => $nama_mahasiswa,
            'nim' => $nim,

            'id_fakultas' => $id_fakultas,
            'nama_fakultas' => $nama_fakultas,

            'id_prodi' => $id_prodi,
            'nama_prodi' => $nama_prodi,

            'tgl_lahir' => $tgl_lahir,
            'agama' => $agama,
            'jenis_kelamin' => $jenis_kelamin,
            'alamat' => $alamat,
            'no_telp' => $no_telp,
            'nama_orangtua' => $nama_orangtua,
            'pekerjaan_orangtua' => $pekerjaan_orangtua,
            'alamat_orangtua' => $alamat_orangtua,
            'notelp_orangtua' => $notelp_orangtua,
        );
    }
    $response['error'] = "200";
    $response['message'] = "Data ditemukan";
} else {
    $response['error'] = "400";
    $response['message'] = "Data tidak ditemukan";
}

echo json_encode($response);

?>
