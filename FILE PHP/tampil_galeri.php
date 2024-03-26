<?php

header('Content-Type: application/json');
require 'conn.php';

$directory = 'gallery/';
$imageUrls = [];

$files = scandir($directory);
foreach ($files as $file) {
    if ($file !== '.' && $file !== '..' && is_file($directory . $file) && is_image($file)) {
        $imageUrls[] = "https://semarundip23.000webhostapp.com/gallery/" . urlencode($file);
    }
}

echo json_encode($imageUrls);

function is_image($file) {
    $imageExtensions = ['jpg', 'jpeg', 'png', 'gif'];
    $extension = strtolower(pathinfo($file, PATHINFO_EXTENSION));
    return in_array($extension, $imageExtensions);
}

?>