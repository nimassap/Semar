<?php

header('Content-Type: application/json');

require 'conn.php';

$email = $_REQUEST['email'];
$sqlGetUserID = "SELECT id_mahasiswa FROM mahasiswa WHERE email = '$email'";
$result = $conn->query($sqlGetUserID);

$response = array();

if ($_SERVER['REQUEST_METHOD'] == 'POST' && isset($_FILES['image']) && isset($_POST['id_mahasiswa'])) {
    $id_mahasiswa = $_POST['id_mahasiswa'];
    $target_dir = "fotoProfile/" . $id_mahasiswa . "/";
    if (!is_dir($target_dir)) {
        mkdir($target_dir, 0755, true);
    }

    $image_name = basename($_FILES["image"]["name"]);
    $target_file = $target_dir . $image_name;
    $uploadOk = 1;
    $imageFileType = strtolower(pathinfo($target_file, PATHINFO_EXTENSION));

    // Check file size
    if ($_FILES["image"]["size"] > 10000000) {
        $uploadOk = 0;
        $response['error'] = true;
        $response['message'] = "File is too large.";
    }

    // Allow only certain file formats
    if ($imageFileType != "jpg" && $imageFileType != "png" && $imageFileType != "jpeg" && $imageFileType != "gif") {
        $uploadOk = 0;
        $response['error'] = true;
        $response['message'] = "Invalid file format.";
    }

    if ($uploadOk == 1) {
        if (move_uploaded_file($_FILES["image"]["tmp_name"], $target_file)) {
            // Insert image information into the database
            $image_path = $target_file; // Simpan path gambar
            $insert_query = "INSERT INTO foto_profile (id_mahasiswa, image_name, image_path) VALUES ('$id_mahasiswa', '$image_name', '$image_path')";
    
            if (mysqli_query($conn, $insert_query)) {
                $response['error'] = false;
                $response['message'] = "Image uploaded and saved successfully.";
            } else {
                $response['error'] = true;
                $response['message'] = "Error saving image information.";
            }
        } else {
            $response['error'] = true;
            $response['message'] = "Error uploading image.";
        }
    }
} else {
    $response['error'] = true;
    $response['message'] = "Invalid request.";
}

echo json_encode($response);

?>