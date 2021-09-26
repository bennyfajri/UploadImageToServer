<?php
    if($_SERVER['REQUEST_METHOD'] == 'POST'){ 
        include_once("dbconnect.php");   
        class emp{}

        $postdata = file_get_contents("php://input");
        if(isset($postdata)){
            $request1 = json_decode("$postdata");
            $name = $request1->name;
            $image = $request1->image;

            if(isset($name)){
                $response = new emp();
                $response->success = 0;
                $response->message = "Please name not empty";
            } else {
                $random = random_word(20);

                $path = "images/".$random.".jpg";

                $query = mysqli_query($conn, "INSERT INTO tb_photo (name, photo) VALUES ('$name','$actualpath')");
                if($query){
                    file_put_contents($path, base64_decode($image));

                    $response = new emp();
                    $response->success = 1;
                    $response->message = "Successfully Uploaded";
                    die(json_encode($response));
                } else {
                    $response = new emp();
                    $response->success = 0;
                    $response->message = "Error upload image";
                    die(json_encode("$response"));
                }

                mysqli_close($conn);
            }
        }    
}

           // fungsi random string pada gambar untuk menghindari nama file yang sama
           function random_word($id = 20){
            $pool = '1234567890abcdefghijkmnpqrstuvwxyz';
            
            $word = '';
            for ($i = 0; $i < $id; $i++){
                $word .= substr($pool, mt_rand(0, strlen($pool) -1), 1);
            }
            return $word; 
        
        
        }


?>