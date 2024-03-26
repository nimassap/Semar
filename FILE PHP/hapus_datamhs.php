<?php

require 'conn.php';

if ($_SERVER['REQUEST_METHOD'] == 'DELETE') {
    // Mendapatkan ID mahasiswa yang akan dihapus
    $idMahasiswa = $_GET['id_mahasiswa'];

    // Query SQL untuk menghapus data mahasiswa
    $query = "DELETE FROM mahasiswa WHERE id_mahasiswa = $idMahasiswa";

    if (mysqli_query($conn, $query)) {
        // Data berhasil dihapus
        echo json_encode(array('message' => 'Data mahasiswa berhasil dihapus'));
    } else {
        // Gagal menghapus data
        echo json_encode(array('message' => 'Gagal menghapus data mahasiswa'));
    }
}
?>