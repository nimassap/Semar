<?php
require_once "conn.php";

// Fetch all available rooms that are not booked by the user
$sql = "SELECT k.id_kamar, k.kode_kamar
        FROM kamar k
        LEFT JOIN pemesanan p ON k.id_kamar = p.id_kamar
        WHERE p.id_kamar IS NULL"; // Check if there is no booking for this room

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
