<?php
$user="root";
$pass="";
$host="localhost";
$dbnamne="near_deal27";

$product_id = $_REQUEST['product_id'];
//$product_id=1;
//create connection
$conn = new mysqli($host, $user, $pass, $dbnamne);

//check connection
if($conn->connect_error){
    die("connection failed" .$com->connect_error);
}
$sql = "SELECT p.*,s.Name store_name, s.Photo store_photo, d.*
FROM store s INNER JOIN product p ON(s.Ids=p.Store_id) LEFT JOIN deal d ON (p.Id =d.Product_id)
WHERE p.Id={$product_id} ";

$result = $conn->query($sql);

while($row =$result->fetch_assoc()){
    $deal = array(
        'id' => $row['Id'],
        'name' => $row ['Name'],
        'photo' => $row ['Photo'],        
        'store_name' => $row['store_name'],
        'store_photo' => $row['store_photo'],
        'price' => $row ['Price'],   
        'description' => $row ['Description'],
 
    );
}

echo json_encode(array(
    'success' => true,
    'productDetail' => $deal
),JSON_NUMERIC_CHECK);