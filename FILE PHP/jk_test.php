<?php

require_once "conn.php";

if(isset($_GET['jumlah_penghuni'])){

    $jumlahPenghuni = $_GET['jumlah_penghuni'];

    $sql = "SELECT id_jeniskelamin, jenis_kelamin FROM jenis_kelamin 
            WHERE id_jumlahpenghuni IN (SELECT id_jumlahpenghuni FROM jumlah_penghuni WHERE jumlah_penghuni = '$jumlahPenghuni')";

    if ($result = $conn->query($sql)) {
        $jenisKelaminArray = array();
        while ($row = $result->fetch_assoc()) {
            $jenisKelamin = array(
                'id_jeniskelamin' => $row['id_jeniskelamin'],
                'jenis_kelamin' => $row['jenis_kelamin']
            );
            $jenisKelaminArray[] = $jenisKelamin; 
        }
        header('Content-Type: application/json');
        echo json_encode($jenisKelaminArray);
    } else {
        echo "Error in executing query.";
    }

    
} else {
    echo "Missing 'jalur' or 'opsi_pembayaran' or 'jumlah_penghuni' parameter.";
}

$conn->close();

?>
