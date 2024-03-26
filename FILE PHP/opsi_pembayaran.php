<?php

require_once "conn.php";

if(isset($_GET['jalur'])){
    $jalur = $_GET['jalur'];
    $sql = "SELECT id_opsipembayaran, opsi_pembayaran FROM opsi_pembayaran WHERE id_jalur = (SELECT id_jalur FROM jalur WHERE jalur = '$jalur')";
    
    if ($result = $conn->query($sql)) {
        $opsiPembayaranArray = array();
        while ($row = $result->fetch_assoc()) {
            $opsiPembayaran = array(
                'id_opsipembayaran' => $row['id_opsipembayaran'],
                'opsi_pembayaran' => $row['opsi_pembayaran']
            );
            $opsiPembayaranArray[] = $opsiPembayaran; 
        }
        header('Content-Type: application/json');
        echo json_encode($opsiPembayaranArray);
    } else {
        echo "Error in executing query.";
    }
}

$conn->close();

?>