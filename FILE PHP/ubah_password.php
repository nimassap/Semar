<?php

require 'conn.php';

$email = $_REQUEST['email'];
$oldPassword = md5($_REQUEST['old_password']);
$newPassword = md5($_REQUEST['new_password']);
$confirmPassword = md5($_REQUEST['confirm_password']);

// Lakukan validasi input
if ($newPassword != $confirmPassword) {
    $response['success'] = false;
    $response['message'] = 'Konfirmasi password tidak sesuai.';
    echo json_encode($response);
    exit();
}

// Lakukan query untuk mendapatkan data user berdasarkan email
$query = "SELECT * FROM mahasiswa WHERE email = '$email'";
$result = mysqli_query($conn, $query);

if ($result && mysqli_num_rows($result) > 0) {
    $row = mysqli_fetch_assoc($result);

    // Cek apakah password lama sesuai dengan yang tersimpan di database
    if ($oldPassword == $row['password']) {
        // Password lama sesuai, hash password baru dan update di database
        $updateQuery = "UPDATE mahasiswa SET password = '$newPassword' WHERE email = '$email'";
        $updateResult = mysqli_query($conn, $updateQuery);

        if ($updateResult) {
            $response['success'] = true;
            $response['message'] = 'Password berhasil diubah.';
        } else {
            $response['success'] = false;
            $response['message'] = 'Gagal mengubah password.';
        }
    } else {
        $response['success'] = false;
        $response['message'] = 'Password lama tidak sesuai.';
    }
} else {
    $response['success'] = false;
    $response['message'] = 'Email tidak ditemukan.';
}

echo json_encode($response);

?>