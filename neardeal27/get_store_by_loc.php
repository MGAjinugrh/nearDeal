<?php
$user="root";
$pass="";
$host="localhost";
$dbnamne="near_deal27";

$lat = $_REQUEST['lat'];
$lng = $_REQUEST['lng'];

//create connection
$conn = new mysqli($host, $user, $pass, $dbnamne);

//check connection
if($conn->connect_error){
    die("connection failed" .$com->connect_error);
}

//[nama formula menghitung 2 titik koordinat : mysql great circle distance]

$sql = "SELECT Ids,Name, Lat, Lng, Photo, Telp, Description, Open_hour, Address, (6371 * acos(cos(radians({$lat})) * cos(radians(Lat) ) ". 
"* cos(radians(Lng) - radians({$lng}) ) + sin( radians({$lat}) ) * sin(radians(Lat)) ) ) AS distance ". 
" FROM store ".
" HAVING distance <= 5 ".
" ORDER BY distance ";

$result=$conn->query($sql);
$data = array();

while($row =$result->fetch_assoc()){
    $data[]=$row;
}

echo json_encode(array(
    'success' => true,
    'store' => $data
),JSON_NUMERIC_CHECK);