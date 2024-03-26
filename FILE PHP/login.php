<?php

header('Content-Type: application/json');

require 'conn.php';

$email = $_REQUEST['email'];
$password = md5($_REQUEST['password']);

function getRoleTextFromId($id_role) {
    require 'conn.php'; // Assuming you have the database connection established here

    $query = "SELECT role FROM role WHERE id_role = '$id_role'";
    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        return $row['role'];
    } else {
        return 'Unknown Role'; // Default role name in case id_role is not found
    }
}



$checkUser = "SELECT * FROM mahasiswa WHERE email = '$email'";
$result = mysqli_query($conn,$checkUser);



if(mysqli_num_rows($result) > 0){
    $checkUserQuery = "SELECT id_mahasiswa, id_role, email, password, nama_mahasiswa, nim, tgl_lahir, agama, jenis_kelamin, alamat, no_telp,
    nama_orangtua, pekerjaan_orangtua, alamat_orangtua, notelp_orangtua
     FROM mahasiswa WHERE email = '$email' and password = '$password'";
    $resultant = mysqli_query($conn,$checkUserQuery);


if(mysqli_num_rows($resultant) > 0){
    while($row = $resultant->fetch_assoc()){
        $response['user'] = $row;
        // Get the id_role from the user data
        $roleId = $row['id_role'];
        // Retrieve the role_name from the role table based on id_role
        $roleText = getRoleTextFromId($roleId);
        // Add the role information to the response
        $response['role'] = array('id_role' => $roleId, 'role' => $roleText);
    }
    $response['error'] = "200";
    $response['message'] = "Login Berhasil";
    
}
else{
    
    $response['error'] = "400";
    $response['message'] = "Password salah";
}

}
else{
    
    $response['error'] = "400";
    $response['message'] = "user does not exist";
}

echo json_encode($response);

?>