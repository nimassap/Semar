<?php

require 'conn.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST' && isset($_POST['id_pemesanan'])) {
    $id_pemesanan = $_POST['id_pemesanan'];
    
    // Update the status to "Selesai"
    $update_query = "UPDATE pemesanan SET status_pemesanan = 'Dibatalkan' WHERE id_pemesanan = '$id_pemesanan'";
    if (mysqli_query($conn, $update_query)) {
        $response['error'] = false;
        $response['message'] = "Status updated successfully.";
    } else {
        $response['error'] = true;
        $response['message'] = "Error updating status.";
    }
} else {
    $response['error'] = true;
    $response['message'] = "Invalid request.";
}

echo json_encode($response);

$conn->close();

?>