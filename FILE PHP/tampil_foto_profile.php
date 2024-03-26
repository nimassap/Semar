<?php

header('Content-Type: application/json');

require 'conn.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET' && isset($_GET['id_mahasiswa'])) {
    $id_mahasiswa = $_GET['id_mahasiswa'];
    
    $sql = "SELECT id_fotoprofile, id_mahasiswa, image_name, image_path, upload_date FROM foto_profile WHERE id_mahasiswa = '$id_mahasiswa'";
    $result = $conn->query($sql);

    $imageData = array();
    while ($row = $result->fetch_assoc()) {
        $imageData[] = $row;
    }

    echo json_encode($imageData);
} else {
    $response['error'] = true;
    $response['message'] = "Invalid request.";
    echo json_encode($response);
}

$conn->close();

?>
