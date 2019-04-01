<?php
$user="root";
$pass="";
$host="localhost";
$dbnamne="near_deal27";

$store_id = $_REQUEST['store_id'];
//$store_id = 1;

//create connection
$conn = new mysqli($host, $user, $pass, $dbnamne);
 
//check connection
if($conn->connect_error){
    die("connection failed" .$com->connect_error);
}
 
//[nama formula menghitung 2 titik koordinat : mysql great circle distance]
 
//$sql = "SELECT * FROM store";
$sql = "SELECT * FROM product WHERE Store_id={$store_id}";

$result = $conn->query($sql);
$data = array();
 
while($row =$result->fetch_assoc()){
    $data[]=$row;
}

echo json_encode(array(
    'success' => true,
    'product' => $data
),JSON_NUMERIC_CHECK);