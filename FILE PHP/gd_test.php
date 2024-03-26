<?php

require_once "conn.php";

if(isset($_GET['jenis_kelamin'])){

    $jenisKelamin = $_GET['jenis_kelamin'];

    $sql = "SELECT id_gedung, gedung FROM gedung 
            WHERE id_jeniskelamin IN (SELECT id_jeniskelamin FROM jenis_kelamin WHERE jenis_kelamin = '$jenisKelamin')";

    if ($result = $conn->query($sql)) {
        $gedungArray = array();
        while ($row = $result->fetch_assoc()) {
            $gedung = array(
                'id_gedung' => $row['id_gedung'],
                'gedung' => $row['gedung']
            );
            $gedungArray[] = $gedung; 
        }
        header('Content-Type: application/json');
        echo json_encode($gedungArray);
    } else {
        echo "Error in executing query.";
    }

    
} else {
    echo "Missing 'jalur' or 'opsi_pembayaran' or 'jumlah_penghuni' or 'jenis_kelamin' parameter.";
}

$conn->close();

?>
