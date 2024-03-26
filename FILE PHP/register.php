<?php

require 'conn.php';

$nama_mahasiswa = $_REQUEST['nama_mahasiswa'];
$no_telp = $_REQUEST['no_telp'];
$email = $_REQUEST['email'];
$password = md5($_REQUEST['password']);
$confirmPassword = md5($_REQUEST['confirm_password']);

$verifyUser = "SELECT * FROM mahasiswa WHERE email = '$email'";
$verifyQuery = mysqli_query($conn,$verifyUser);

if(mysqli_num_rows($verifyQuery) > 0){
    $response['error'] = "002";
    $response['message'] = "User exist";
}
else if ($password !== $confirmPassword) {
    $response['error'] = "003";
    $response['message'] = "Password confirmation does not match";
}  else if (strlen($_REQUEST['password']) < 8){
    $response['error'] = "004";
    $response['message'] = "Password must be at least 8 characters long";
} else {
    $insertQuery = "INSERT INTO mahasiswa(nama_mahasiswa, no_telp, email, password)
                    VALUES ('$nama_mahasiswa', '$no_telp', '$email', '$password')";
    $result = mysqli_query($conn, $insertQuery);

    if ($result) {
        $response['error'] = "000";
        $response['message'] = "Pendaftaran Berhasil";
    } else {
        $response['error'] = "001";
        $response['message'] = "Pendaftaran Gagal";
        $response['mysql_error'] = mysqli_error($conn);
    }
}

echo json_encode($response);

?>