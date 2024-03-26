<?php

header('Content-Type: application/json');

require 'conn.php';

$query = "SELECT * FROM deluxe1";

//ksekusi query
$result = mysqli_query($conn, $query);

//cek kolom
if (mysqli_num_rows($result) > 0) {
    //tampilkan array untuk data
    $data = array();

    //mengambil data pada tiap kolom dan menampilkan dalam bentuk array
    while ($row = mysqli_fetch_assoc($result)) {
        $data[] = $row;
    }

    //encode array kedalam JSON format
    echo json_encode($data);
} else {
    //menampilkan data kosong apabila tidak ada JSON array
    echo json_encode(array());
}

//close connection

mysqli_close($conn);

?>