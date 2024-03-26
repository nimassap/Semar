<?php

require_once "conn.php";

if(isset($_GET['opsi_pembayaran']) && isset($_GET['jumlah_penghuni']) && isset($_GET['kode_kamar'])){

    $opsi_pembayaran = $_GET['opsi_pembayaran'];
    $jumlah_penghuni = $_GET['jumlah_penghuni'];
    $kode_kamar = $_GET['kode_kamar'];

    // Fetch available rooms that match the criteria and are not booked by the user
    $sql = "SELECT id_harga, harga FROM harga WHERE
    id_opsipembayaran IN (SELECT id_opsipembayaran FROM opsi_pembayaran WHERE opsi_pembayaran = '$opsi_pembayaran') AND 
    id_jumlahpenghuni IN (SELECT id_jumlahpenghuni FROM jumlah_penghuni WHERE jumlah_penghuni = '$jumlah_penghuni') AND 
    id_kamar IN (SELECT id_kamar FROM kamar WHERE kode_kamar = '$kode_kamar')";

    if ($result = $conn->query($sql)) {
        $hargaArray = array();
        while ($row = $result->fetch_assoc()) {
            $harga = array(
                'id_harga' => $row['id_harga'],
                'harga' => $row['harga']
            );
            $hargaArray[] = $harga; 
        }
        header('Content-Type: application/json');
        echo json_encode($hargaArray);
    } else {
        echo "Error in executing query.";
    }

    
} else {
    echo "Missing 'jalur' or 'opsi_pembayaran' or 'jumlah_penghuni' or 'jenis_kelamin' or 'gedung' parameter.";
}

$conn->close();

?>
