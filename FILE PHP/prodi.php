<?php

require_once "conn.php";

if(isset($_GET['nama_fakultas'])){
    $fakultas = $_GET['nama_fakultas'];
    $sql = "SELECT id_prodi, nama_prodi FROM prodi WHERE id_fakultas = (SELECT id_fakultas FROM fakultas WHERE nama_fakultas = '$fakultas')";
    
    if ($result = $conn->query($sql)) {
        $prodiArray = array();
        while ($row = $result->fetch_assoc()) {
            $prodi = array(
                'id_prodi' => $row['id_prodi'],
                'nama_prodi' => $row['nama_prodi']
            );
            $prodiArray[] = $prodi; 
        }
        header('Content-Type: application/json');
        echo json_encode($prodiArray);
    } else {
        echo "Error in executing query.";
    }
}

$conn->close();

?>