<?php
require_once "conn.php";

if (isset($_GET['jumlah_penghuni']) && isset($_GET['gedung'])) {
    $jumlah_penghuni = $_GET['jumlah_penghuni'];
    $gedung = $_GET['gedung'];

    // Fetch available rooms that match the criteria and are not booked by the user
    $sql = "SELECT k.id_kamar, k.kode_kamar 
            FROM kamar k
            LEFT JOIN pemesanan p ON k.id_kamar = p.id_kamar
            WHERE k.id_jumlahpenghuni IN (SELECT id_jumlahpenghuni FROM jumlah_penghuni WHERE jumlah_penghuni = '$jumlah_penghuni') 
            AND k.id_gedung IN (SELECT id_gedung FROM gedung WHERE gedung = '$gedung')
            AND p.id_kamar IS NULL"; // Check if there is no booking for this room

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
} else {
    echo "Missing 'jumlah_penghuni' or 'gedung' parameter.";
}

$conn->close();
?>