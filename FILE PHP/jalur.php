<?php

require_once "conn.php";
$sql = "SELECT * FROM jalur";

if ($result = $conn->query($sql)) {
    $jalurArray = array();
    while ($row = $result->fetch_assoc()) {
        $jalur = array(
            'id_jalur' => $row['id_jalur'],
            'jalur' => $row['jalur']
        );
        array_push($jalurArray, $jalur);
    }
    $response = $jalurArray;
    header('Content-Type: application/json');
    echo json_encode($response);
} else {
    echo "Error in connecting to Database.";
}

$conn->close();

?>