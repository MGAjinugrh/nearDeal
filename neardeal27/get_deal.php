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

$sql = "SELECT * FROM product p INNER JOIN deal d ON(p.Id=d.Product_id)
WHERE p.Store_id={$store_id} ";

$result = $conn->query($sql);
$deals = array();

while($row =$result->fetch_assoc()){
    $deal = array(
        'id' => $row['Id'],
        'start_date' => $row['Start_date'],
        'end_date' => $row['End_date'],
        'discount' => $row['Discount'],
        'product' => array(
            'Id' => $row['Id'],
            'Store_id' => $row ['Store_id'],
            'Name' => $row ['Name'],
            'Price' => $row ['Price'],
            'Description' => $row ['Description'],
            'Photo' => $row ['Photo'],
        )
    );
    $deals[]=$deal;

}

echo json_encode(array(
    'success' => true,
    'deal' => $deals
),JSON_NUMERIC_CHECK);