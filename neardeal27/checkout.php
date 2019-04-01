<?php
session_start();
$user="root";
$pass="";
$host="localhost";
$dbnamne="near_deal27";

$name = $_REQUEST['name'];
$noHp = $_REQUEST['no_hp'];
$address = $_REQUEST['address'];    
$productStr = $_REQUEST['productIds']; //productIds string separated by ,
$pricesStr = $_REQUEST['prices']; //productIds string separated by ,
$user_id=$_REQUEST['user_id'];

/*$name = "name1";
$noHp = "988";
$address = "asdad";    
$productStr = "1,2"; //productIds string separated by ,
$pricesStr = "1200,23000"; //productIds string separated by ,
$user_id=1;
*/

//create connection
$conn = new mysqli($host, $user, $pass, $dbnamne);
//check connection
if($conn->connect_error){
    die("connection failed" .$com->connect_error);
}

if (empty($user_id)||$user_id==0) {
	# code...
	die("You're not allowed to access this api");
}

$sql = "INSERT INTO deal_order VALUES(NULL, {$user_id}, '{$name}', '{$noHp}', '{$address}')";
$conn->query($sql);
$order_id = $conn->insert_id;
$productIds = explode(",", $productStr);
$prices = explode(",", $pricesStr);
foreach($productIds as $k=>$productId) {
    $sql = "INSERT INTO item_order VALUES(NULL, {$order_id}, {$productId}, {$prices[$k]})";
    $conn->query($sql);
}
echo json_encode(array(
    'success' => true,
    'order_id' => $order_id
),JSON_NUMERIC_CHECK); 