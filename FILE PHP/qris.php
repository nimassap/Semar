<?php

header('Content-Type: application/json');
require 'conn.php';

$directory = 'qris/';
$files = scandir($directory);
$imageUrls = [];

foreach ($files as $file) {
    if ($file !== '.' && $file !== '..') {
        $imageUrls[] = urlencode($file);
    }
}

echo json_encode($imageUrls);

?>