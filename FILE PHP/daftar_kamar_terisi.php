<?php
require_once "conn.php";

// Fetch rooms with specific booking statuses
$statuses = array('Belum Bayar', 'Selesai'); // List of statuses to include

$sql = "SELECT k.id_kamar, k.kode_kamar
        FROM kamar k
        INNER JOIN pemesanan p ON k.id_kamar = p.id_kamar
        WHERE p.status_pemesanan IN ('" . implode("', '", $statuses) . "')";

if ($result = $conn->query($sql)) {
    $kamarArray = array();
    while ($row = $result->fetch_assoc()) {
        $kamar = array(
            'id_kamar' => $row['id_kamar'],
            'kode_kamar' => $row['kode_kamar']
        );
        $kamarArray[] = $kamar;
    }
    header('Content-Type: application/json');
    echo json_encode($kamarArray);
} else {
    echo "Error in executing query.";
}

$conn->close();

?>
