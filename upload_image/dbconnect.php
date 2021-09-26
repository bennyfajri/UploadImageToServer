<?php
    $server = "localhost";
    $username = "root";
    $password = "";
    $database = "db_upload_image";

    $conn = mysqli_connect($server, $username, $password, $database);
    if(mysqli_connect_errno()){
        echo "Failed connect to MySQL: " . mysqli_connect_error();
    }

?>