<?php

require 'conn.php';

if (isset($_REQUEST['id_gedunga']) && isset($_REQUEST['text_edit'])) {
    // Sanitize and store the POST data in variables
    $id = mysqli_real_escape_string($conn, $_REQUEST['id_gedunga']);
    $text = mysqli_real_escape_string($conn, $_REQUEST['text_edit']);

    // SQL query to update data in the database
    $query = "UPDATE gedung_a SET text_edit = '$text' WHERE id_gedunga = '$id'";

    // Execute the query
    if (mysqli_query($conn, $query)) {
        // The update was successful
        $response = array('status' => 'success', 'message' => 'Data updated successfully');
    } else {
        // The update failed
        $response = array('status' => 'error', 'message' => 'Failed to update data');
    }
} else {
    // Required parameters not found in the POST request
    $response = array('status' => 'error', 'message' => 'Required parameters missing');
}

// Convert the response array to JSON format and print it
echo json_encode($response);

// Close the database connection
mysqli_close($conn);

?>
