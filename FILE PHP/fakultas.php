<?php

require_once "conn.php";
$sql = "SELECT * FROM fakultas";

if ($result = $conn->query($sql)) {
    $fakultasArray = array();
    while ($row = $result->fetch_assoc()) {
        $fakultas = array(
            'id_fakultas' => $row['id_fakultas'],
            'nama_fakultas' => $row['nama_fakultas']
        );
        array_push($fakultasArray, $fakultas);
    }
    $response = $fakultasArray;
    header('Content-Type: application/json');
    echo json_encode($response);
} else {
    echo "Error in connecting to Database.";
}

$conn->close();

?>