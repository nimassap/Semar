<?php

require 'conn.php';

$query = "SELECT * FROM cara_daftarkamar";

// Execute the query
$result = mysqli_query($conn, $query);

// Check if there are any rows returned
if (mysqli_num_rows($result) > 0) {
    // Array to hold the data
    $data = array();

    // Fetch each row and add it to the data array
    while ($row = mysqli_fetch_assoc($result)) {
        $data[] = $row;
    }

    // Encode the data array to JSON format and print it
    echo json_encode($data);
} else {
    // If no data is found, return an empty JSON array
    echo json_encode(array());
}

// Close the database connection
header('Content-Type: application/json');
mysqli_close($conn);

?>