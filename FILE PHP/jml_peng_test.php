<?php

require_once "conn.php";

if(isset($_GET['opsi_pembayaran'])){

    $opsiPembayaran = $_GET['opsi_pembayaran'];

    $sql = "SELECT id_jumlahpenghuni, jumlah_penghuni FROM jumlah_penghuni 
            WHERE id_opsipembayaran IN (SELECT id_opsipembayaran FROM opsi_pembayaran WHERE opsi_pembayaran = '$opsiPembayaran')";

    if ($result = $conn->query($sql)) {
        $jumlahPenghuniArray = array();
        while ($row = $result->fetch_assoc()) {
            $jumlahPenghuni = array(
                'id_jumlahpenghuni' => $row['id_jumlahpenghuni'],
                'jumlah_penghuni' => $row['jumlah_penghuni']
            );
            $jumlahPenghuniArray[] = $jumlahPenghuni; 
        }
        header('Content-Type: application/json');
        echo json_encode($jumlahPenghuniArray);
    } else {
        echo "Error in executing query.";
    }

    
} else {
    echo "Missing 'jalur' or 'opsi_pembayaran' parameter.";
}

$conn->close();

?>
